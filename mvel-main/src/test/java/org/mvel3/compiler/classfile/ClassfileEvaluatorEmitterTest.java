package org.mvel3.compiler.classfile;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mvel3.*;
import org.mvel3.lambdaextractor.LambdaRegistry;
import org.mvel3.transpiler.TranspiledResult;
import org.mvel3.transpiler.context.Declaration;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    // ── Helper methods ────────────────────────────────────────────────────

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
