package org.mvel3.benchmark;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.mvel3.ClassManager;
import org.mvel3.CompilerParameters;
import org.mvel3.Evaluator;
import org.mvel3.MVEL;
import org.mvel3.Type;
import org.mvel3.benchmark.domain.FactionState;
import org.mvel3.lambdaextractor.LambdaRegistry;
import org.mvel3.transpiler.context.Declaration;
import org.openjdk.jmh.annotations.*;

/**
 * Measures throughput and latency of pre-compiled expressions evaluated against
 * varying contexts. This is the dominant Chronicler pattern: same compiled
 * predicate, many different faction states per tick.
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 2, jvmArgsAppend = {
        "-Dmvel3.compiler.lambda.persistence=false",
        "-Dmvel3.compiler.lambda.resetOnTestStartup=true"
})
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 2)
public class WarmEvaluationBenchmark {

    @State(Scope.Thread)
    public static class MapPredicateState {

        Evaluator<Map<String, Object>, Void, Boolean> evaluator;
        Map<String, Object> context;

        @Setup(Level.Trial)
        public void compile() {
            LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();

            Map<String, Type<?>> types = new HashMap<>();
            types.put("influence", Type.type(int.class));
            types.put("atWar", Type.type(boolean.class));
            types.put("stability", Type.type(int.class));

            MVEL mvel = new MVEL();
            evaluator = mvel.compileMapExpression(
                    "influence > 50 && !atWar && stability > 30",
                    Boolean.class, Collections.emptySet(), types);

            context = new HashMap<>();
        }

        @Setup(Level.Iteration)
        public void mutateContext() {
            ThreadLocalRandom rng = ThreadLocalRandom.current();
            context.put("influence", rng.nextInt(0, 100));
            context.put("atWar", rng.nextBoolean());
            context.put("stability", rng.nextInt(0, 100));
        }
    }

    @State(Scope.Thread)
    public static class PojoPredicateState {

        Evaluator<FactionState, Void, Boolean> evaluator;
        FactionState context;

        @Setup(Level.Trial)
        public void compile() {
            LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();

            CompilerParameters<FactionState, Void, Boolean> params =
                    MVEL.<FactionState>pojo(FactionState.class,
                                    Declaration.of("influence", int.class),
                                    Declaration.of("atWar", boolean.class),
                                    Declaration.of("stability", int.class))
                            .<Boolean>out(Boolean.class)
                            .expression("influence > 50 && !atWar && stability > 30")
                            .imports(Collections.emptySet())
                            .classManager(new ClassManager())
                            .build();

            MVEL mvel = new MVEL();
            evaluator = mvel.compilePojoEvaluator(params);

            context = new FactionState();
        }

        @Setup(Level.Iteration)
        public void mutateContext() {
            ThreadLocalRandom rng = ThreadLocalRandom.current();
            context.setInfluence(rng.nextInt(0, 100));
            context.setAtWar(rng.nextBoolean());
            context.setStability(rng.nextInt(0, 100));
        }
    }

    @State(Scope.Thread)
    public static class MapComplexPredicateState {

        Evaluator<Map<String, Object>, Void, Boolean> evaluator;
        Map<String, Object> context;

        @Setup(Level.Trial)
        public void compile() {
            LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();

            Map<String, Type<?>> types = new HashMap<>();
            types.put("influence", Type.type(int.class));
            types.put("stability", Type.type(int.class));
            types.put("treasury", Type.type(double.class));
            types.put("factionName", Type.type(String.class));

            MVEL mvel = new MVEL();
            evaluator = mvel.compileMapExpression(
                    "influence > 50 && stability > 30 && treasury > 1000.0 && factionName != null",
                    Boolean.class, Collections.emptySet(), types);

            context = new HashMap<>();
        }

        @Setup(Level.Iteration)
        public void mutateContext() {
            ThreadLocalRandom rng = ThreadLocalRandom.current();
            context.put("influence", rng.nextInt(0, 100));
            context.put("stability", rng.nextInt(0, 100));
            context.put("treasury", rng.nextDouble(0, 5000));
            context.put("factionName", rng.nextBoolean() ? "Faction" + rng.nextInt(100) : null);
        }
    }

    @Benchmark
    public Boolean evalMapPredicate(MapPredicateState state) {
        return state.evaluator.eval(state.context);
    }

    @Benchmark
    public Boolean evalPojoPredicate(PojoPredicateState state) {
        return state.evaluator.eval(state.context);
    }

    @Benchmark
    public Boolean evalMapComplexPredicate(MapComplexPredicateState state) {
        return state.evaluator.eval(state.context);
    }
}
