package org.mvel3.compiler.classfile;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mvel3.*;
import org.mvel3.lambdaextractor.LambdaRegistry;
import org.mvel3.transpiler.TranspiledResult;
import org.mvel3.transpiler.context.Declaration;

import java.lang.invoke.MethodHandles;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ClassfileEvaluatorEmitter} — validates that the Classfile API bytecode emitter
 * produces evaluators equivalent to the javac pipeline.
 */
class ClassfileEvaluatorEmitterTest {

    @BeforeAll
    static void reset() {
        LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();
    }

    // ── MAP context tests ──────────────────────────────────────────────────

    @Test
    void mapPredicate_intComparison() {
        // Expression: influence > 50
        Map<String, Type<?>> types = new HashMap<>();
        types.put("influence", Type.type(int.class));

        var result = transpileMap("influence > 50", Boolean.class, types);
        assertThat(ClassfileEvaluatorEmitter.canEmit(result)).isTrue();

        byte[] bytecode = ClassfileEvaluatorEmitter.emit(compilerParamsMap("influence > 50", Boolean.class, types), result);
        assertThat(bytecode).isNotNull();
        assertThat(bytecode.length).isGreaterThan(0);

        // Load and execute
        Evaluator<Map<String, Object>, Void, Boolean> evaluator = loadAndInstantiate(bytecode, result);

        Map<String, Object> ctx = new HashMap<>();
        ctx.put("influence", 75);
        assertThat(evaluator.eval(ctx)).isTrue();

        ctx.put("influence", 25);
        assertThat(evaluator.eval(ctx)).isFalse();
    }

    @Test
    void mapPredicate_booleanLogic() {
        // Expression: influence > 50 && !atWar
        Map<String, Type<?>> types = new HashMap<>();
        types.put("influence", Type.type(int.class));
        types.put("atWar", Type.type(boolean.class));

        var result = transpileMap("influence > 50 && !atWar", Boolean.class, types);
        assertThat(ClassfileEvaluatorEmitter.canEmit(result)).isTrue();

        byte[] bytecode = ClassfileEvaluatorEmitter.emit(compilerParamsMap("influence > 50 && !atWar", Boolean.class, types), result);

        Evaluator<Map<String, Object>, Void, Boolean> evaluator = loadAndInstantiate(bytecode, result);

        Map<String, Object> ctx = new HashMap<>();
        ctx.put("influence", 75);
        ctx.put("atWar", false);
        assertThat(evaluator.eval(ctx)).isTrue();

        ctx.put("atWar", true);
        assertThat(evaluator.eval(ctx)).isFalse();

        ctx.put("influence", 25);
        ctx.put("atWar", false);
        assertThat(evaluator.eval(ctx)).isFalse();
    }

    @Test
    void mapPredicate_intArithmetic() {
        // Expression: a + b
        Map<String, Type<?>> types = new HashMap<>();
        types.put("a", Type.type(int.class));
        types.put("b", Type.type(int.class));

        var result = transpileMap("a + b", Integer.class, types);
        assertThat(ClassfileEvaluatorEmitter.canEmit(result)).isTrue();

        byte[] bytecode = ClassfileEvaluatorEmitter.emit(compilerParamsMap("a + b", Integer.class, types), result);

        Evaluator<Map<String, Object>, Void, Integer> evaluator = loadAndInstantiate(bytecode, result);

        Map<String, Object> ctx = new HashMap<>();
        ctx.put("a", 10);
        ctx.put("b", 32);
        assertThat(evaluator.eval(ctx)).isEqualTo(42);
    }

    @Test
    void mapPredicate_compoundPredicate() {
        // Expression: influence > 50 && !atWar && stability > 30
        // This is the benchmark expression
        Map<String, Type<?>> types = new HashMap<>();
        types.put("influence", Type.type(int.class));
        types.put("atWar", Type.type(boolean.class));
        types.put("stability", Type.type(int.class));

        var result = transpileMap("influence > 50 && !atWar && stability > 30", Boolean.class, types);
        assertThat(ClassfileEvaluatorEmitter.canEmit(result)).isTrue();

        byte[] bytecode = ClassfileEvaluatorEmitter.emit(
                compilerParamsMap("influence > 50 && !atWar && stability > 30", Boolean.class, types), result);

        Evaluator<Map<String, Object>, Void, Boolean> evaluator = loadAndInstantiate(bytecode, result);

        Map<String, Object> ctx = new HashMap<>();
        ctx.put("influence", 75);
        ctx.put("atWar", false);
        ctx.put("stability", 50);
        assertThat(evaluator.eval(ctx)).isTrue();

        ctx.put("stability", 10);
        assertThat(evaluator.eval(ctx)).isFalse();
    }

    // ── Javac fallback tests ─────────────────────────────────────────────
    // These verify that the 9 documented permanent fallback cases produce
    // correct results through the javac pipeline. Each test represents one
    // fallback category from the canEmit() javadoc.

    /**
     * Fallback category: scope-less free-function calls (DRL static import pattern).
     * canEmit=false because the emitter cannot resolve functions without a scope.
     * Javac resolves them via static imports on the generated parent class.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void fallback_scopelessFreeFunctionCall_producesCorrectResult() {
        Map<String, Type<?>> types = new HashMap<>();
        types.put("foo", Type.type(Foo.class));
        types.put("bar", Type.type(Bar.class));

        Set<String> staticImports = new HashSet<>();
        staticImports.add(Person.class.getCanonicalName() + ".isEven");

        // Full pipeline via fluent builder — falls back to javac
        Evaluator evaluator = MVEL.map(Declaration.from(types))
                .out(String.class)
                .expression("foo.getName() + bar.getName() + isEven(1)")
                .imports(getImports())
                .staticImports(staticImports)
                .classManager(new ClassManager())
                .classLoader(ClassLoader.getSystemClassLoader())
                .generatedSuperName(GeneratedParentClass.class.getCanonicalName())
                .compile();

        Foo foo = new Foo();
        foo.setName("Alice");
        Bar bar = new Bar();
        bar.setName("Bob");

        Map<String, Object> ctx = new HashMap<>();
        ctx.put("foo", foo);
        ctx.put("bar", bar);
        // isEven() is a test stub that always returns true
        assertThat(evaluator.eval(ctx)).isEqualTo("AliceBobtrue");
    }

    /**
     * Fallback category: List generic erasure — List.get() returns Object,
     * emitter cannot resolve chained methods (.getName()) on the erased type.
     * Javac has the full type solver and handles this correctly.
     */
    @Test
    void fallback_listGenericErasure_producesCorrectResult() {
        Map<String, Type<?>> types = new HashMap<>();
        types.put("foos", Type.type(List.class, "<Foo>"));

        Foo foo1 = new Foo();
        foo1.setName("Alice");
        Foo foo2 = new Foo();
        foo2.setName("Bob");

        List<Foo> foos = new ArrayList<>();
        foos.add(foo1);
        foos.add(foo2);

        Map<String, Object> ctx = new HashMap<>();
        ctx.put("foos", foos);

        MVEL mvel = new MVEL();
        Evaluator<Map<String, Object>, Void, String> evaluator =
                mvel.compileMapExpression("foos[0].name + foos[1].name",
                        String.class, getImports(), types);
        assertThat(evaluator.eval(ctx)).isEqualTo("AliceBob");
    }

    /**
     * Fallback category: BigDecimal + var compound assignment.
     * var infers BigDecimal from 0B literal; compound += resolves to .add()
     * which the emitter cannot find on the var-inferred type.
     */
    @Test
    void fallback_bigDecimalVarCompound_producesCorrectResult() {
        Map<String, Type<?>> types = new HashMap<>();

        MVEL mvel = new MVEL();
        Evaluator<Map<String, Object>, Void, Object> evaluator =
                mvel.compileMapBlock("var s1=0B;s1+=1;s1+=1; return s1;",
                        Object.class, getImports(), types);

        Map<String, Object> ctx = new HashMap<>();
        assertThat(evaluator.eval(ctx).toString()).isEqualTo("2");
    }

    // ── Helper methods ────────────────────────────────────────────────────

    private static Set<String> getImports() {
        Set<String> imports = new HashSet<>();
        imports.add("java.util.List");
        imports.add("java.util.ArrayList");
        imports.add("java.util.HashMap");
        imports.add("java.util.Map");
        imports.add("java.math.BigDecimal");
        imports.add(Foo.class.getCanonicalName());
        imports.add(Person.class.getCanonicalName());
        return imports;
    }

    private <R> TranspiledResult transpileMap(String expression, Class<R> outType, Map<String, Type<?>> types) {
        CompilerParameters<Map<String, Object>, Void, R> params = compilerParamsMap(expression, outType, types);
        MVELCompiler compiler = new MVELCompiler();
        return compiler.transpile(params);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <R> CompilerParameters<Map<String, Object>, Void, R> compilerParamsMap(
            String expression, Class<R> outType, Map<String, Type<?>> types) {

        Declaration[] declarations = Declaration.from(types);

        return (CompilerParameters<Map<String, Object>, Void, R>) (CompilerParameters) new CompilerParameters<>(
                ContextType.MAP,
                Thread.currentThread().getContextClassLoader(),
                new ClassManager(),
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
    }

    @SuppressWarnings("unchecked")
    private <C, W, O> Evaluator<C, W, O> loadAndInstantiate(byte[] bytecode, TranspiledResult result) {
        try {
            // Use defineHiddenClass directly — bypasses ClassManager's ASM-based
            // MethodByteCodeExtractor which doesn't support JDK 25 classfile version (69).
            // This will be cleaned up in Phase 3 when ASM is removed.
            // The Lookup must be in the same package as the generated class (org.mvel3).
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(
                    Evaluator.class, MethodHandles.lookup());
            Class<?> clazz = lookup.defineHiddenClass(bytecode, true).lookupClass();
            return (Evaluator<C, W, O>) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load emitted class", e);
        }
    }
}
