package org.mvel3.benchmark;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mvel3.Evaluator;
import org.mvel3.MVEL;
import org.mvel3.Type;
import org.mvel3.lambdaextractor.LambdaRegistry;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Measures full transpile-to-bytecode compilation cost. Shows what a cache miss
 * costs in tick time.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgsAppend = {
        "-Dmvel3.compiler.lambda.persistence=false",
        "-Dmvel3.compiler.lambda.resetOnTestStartup=true"
})
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class CompilationCostBenchmark {

    @State(Scope.Thread)
    public static class SimpleState {

        final Map<String, Type<?>> types = new HashMap<>();
        final MVEL mvel = new MVEL();

        @Setup(Level.Trial)
        public void init() {
            types.put("a", Type.type(int.class));
            types.put("b", Type.type(int.class));
        }

        @Setup(Level.Invocation)
        public void resetRegistry() {
            LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();
        }
    }

    @State(Scope.Thread)
    public static class PredicateState {

        final Map<String, Type<?>> types = new HashMap<>();
        final MVEL mvel = new MVEL();

        @Setup(Level.Trial)
        public void init() {
            types.put("influence", Type.type(int.class));
            types.put("atWar", Type.type(boolean.class));
            types.put("stability", Type.type(int.class));
        }

        @Setup(Level.Invocation)
        public void resetRegistry() {
            LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();
        }
    }

    @State(Scope.Thread)
    public static class ComplexState {

        final Map<String, Type<?>> types = new HashMap<>();
        final MVEL mvel = new MVEL();

        @Setup(Level.Trial)
        public void init() {
            types.put("a", Type.type(int.class));
            types.put("b", Type.type(int.class));
        }

        @Setup(Level.Invocation)
        public void resetRegistry() {
            LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();
        }
    }

    @Benchmark
    public Evaluator<?, ?, ?> compileSimpleExpression(SimpleState state) {
        return state.mvel.compileMapExpression(
                "a + b",
                Integer.class, Collections.emptySet(), state.types);
    }

    @Benchmark
    public Evaluator<?, ?, ?> compilePredicateExpression(PredicateState state) {
        return state.mvel.compileMapExpression(
                "influence > 50 && !atWar && stability > 30",
                Boolean.class, Collections.emptySet(), state.types);
    }

    @Benchmark
    public Evaluator<?, ?, ?> compileComplexExpression(ComplexState state) {
        return state.mvel.compileMapBlock(
                "a = a + 1; b = b * 2; return a + b;",
                Integer.class, Collections.emptySet(), state.types);
    }
}
