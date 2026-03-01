package org.mvel3.methodutils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mvel3.*;
import org.mvel3.lambdaextractor.LambdaRegistry;
import org.mvel3.transpiler.context.Declaration;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MethodByteCodeExtractor} — validates that the Classfile API-based
 * bytecode extraction produces deterministic, deduplication-safe hashes.
 * <p>
 * These tests use the full compilation pipeline (javac path with persistence) to
 * generate real evaluator bytecode, then verify the extracted bytecode strings and
 * Murmur3 hashes behave correctly for deduplication.
 */
class MethodByteCodeExtractorTest {

    @BeforeAll
    static void reset() {
        LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();
    }

    /**
     * Murmur3 hash stability: identical expressions produce identical hashes.
     * This is the core requirement for ClassManager's deduplication.
     */
    @Test
    void sameExpression_sameHash() {
        Map<String, Type<?>> types = new HashMap<>();
        types.put("a", Type.type(int.class));
        types.put("b", Type.type(int.class));

        byte[] bytecode1 = compileToJavacBytecode("a + b", Integer.class, types);
        byte[] bytecode2 = compileToJavacBytecode("a + b", Integer.class, types);

        String extracted1 = MethodByteCodeExtractor.extract("eval", bytecode1);
        String extracted2 = MethodByteCodeExtractor.extract("eval", bytecode2);

        assertThat(extracted1).isNotNull();
        assertThat(extracted2).isNotNull();
        assertThat(extracted1).isEqualTo(extracted2);

        // Murmur3 hashes must match
        assertThat(murmurHash(extracted1)).isEqualTo(murmurHash(extracted2));
    }

    /**
     * Murmur3 hash stability: different expressions produce different hashes.
     */
    @Test
    void differentExpressions_differentHashes() {
        Map<String, Type<?>> types = new HashMap<>();
        types.put("a", Type.type(int.class));
        types.put("b", Type.type(int.class));

        byte[] bytecodeAdd = compileToJavacBytecode("a + b", Integer.class, types);
        byte[] bytecodeMul = compileToJavacBytecode("a * b", Integer.class, types);

        String extractedAdd = MethodByteCodeExtractor.extract("eval", bytecodeAdd);
        String extractedMul = MethodByteCodeExtractor.extract("eval", bytecodeMul);

        assertThat(extractedAdd).isNotNull();
        assertThat(extractedMul).isNotNull();
        assertThat(extractedAdd).isNotEqualTo(extractedMul);

        assertThat(murmurHash(extractedAdd)).isNotEqualTo(murmurHash(extractedMul));
    }

    /**
     * Extract produces a non-empty string for a valid method.
     */
    @Test
    void extract_producesNonEmptyString() {
        Map<String, Type<?>> types = new HashMap<>();
        types.put("a", Type.type(int.class));

        byte[] bytecode = compileToJavacBytecode("a > 0", Boolean.class, types);
        String extracted = MethodByteCodeExtractor.extract("eval", bytecode);

        assertThat(extracted).isNotNull().isNotEmpty();
    }

    /**
     * Extract returns null for a non-existent method.
     */
    @Test
    void extract_returnsNullForMissingMethod() {
        Map<String, Type<?>> types = new HashMap<>();
        types.put("a", Type.type(int.class));

        byte[] bytecode = compileToJavacBytecode("a > 0", Boolean.class, types);
        String extracted = MethodByteCodeExtractor.extract("nonExistent", bytecode);

        assertThat(extracted).isNull();
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    /**
     * Compile an expression through the javac pipeline and return raw bytecode.
     * Uses the persistence path to get bytecode from KieMemoryCompiler.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private byte[] compileToJavacBytecode(String expression, Class<?> outType, Map<String, Type<?>> types) {
        Declaration[] declarations = Declaration.from(types);
        ClassManager classManager = new ClassManager();

        CompilerParameters params = new CompilerParameters<>(
                ContextType.MAP,
                Thread.currentThread().getContextClassLoader(),
                classManager,
                Collections.emptySet(),
                Collections.emptySet(),
                Type.type(outType),
                new Declaration<>("map", Map.class),
                java.util.Arrays.asList(declarations),
                new Declaration<>("__with", Void.class),
                ContentType.EXPRESSION,
                expression,
                "GeneratorEvaluator__",
                "eval",
                null
        );

        // Use the javac path: transpile → generate CompilationUnit → compile
        MVELCompiler compiler = new MVELCompiler();
        var transpiled = compiler.transpile(params);
        var unit = new CompilationUnitGenerator(transpiled.getTranspilerContext().getParser())
                .createCompilationUnit(transpiled, params);

        String javaFQN = unit.getPackageDeclaration()
                .map(pd -> pd.getNameAsString() + "." + unit.findFirst(
                        com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class)
                        .map(c -> c.getNameAsString()).orElse(""))
                .orElse("");

        Map<String, String> sources = Collections.singletonMap(
                javaFQN, org.mvel3.parser.printer.PrintUtil.printNode(unit));
        org.mvel3.javacompiler.KieMemoryCompiler.compile(classManager, sources,
                Thread.currentThread().getContextClassLoader());

        // Return the raw bytecode from ClassManager
        // ClassManager.define() stores bytecode internally; we need to get it from
        // the internal map. Since we compiled to classManager, we can re-extract.
        // Actually, KieMemoryCompiler.compile() calls classManager.define(bytecodeMap),
        // so the class is loaded. We need the raw bytes.
        // Let's compile again to a fresh ClassManager to capture the bytes.
        ClassManager rawManager = new ClassManager();
        org.mvel3.javacompiler.KieMemoryCompiler.compile(rawManager, sources,
                Thread.currentThread().getContextClassLoader());

        // The bytecode is in rawManager but there's no public getter for bytes.
        // Instead, we can use the persistence variant which writes files.
        // Simpler: use KieMemoryCompiler.compileNoLoad which returns byte[].
        Map<String, byte[]> bytecodeMap = org.mvel3.javacompiler.KieMemoryCompiler.compileNoLoad(
                sources, Thread.currentThread().getContextClassLoader(), null);
        return bytecodeMap.values().iterator().next();
    }

    private byte[] murmurHash(String input) {
        Murmur3F murmur = new Murmur3F();
        murmur.update(input.getBytes());
        return murmur.getValueBytesBigEndian();
    }
}
