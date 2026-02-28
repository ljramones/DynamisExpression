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
 * Measures context allocation and population cost, isolated from evaluation.
 * Shows GC pressure from context creation alone. Run with {@code -prof gc} to
 * get {@code gc.alloc.rate.norm} (bytes/op).
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 2, jvmArgsAppend = {
        "-Dmvel3.compiler.lambda.persistence=false",
        "-Dmvel3.compiler.lambda.resetOnTestStartup=true"
})
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 2)
public class ContextConstructionBenchmark {

    @State(Scope.Thread)
    public static class EvalState {

        Evaluator<Map<String, Object>, Void, Boolean> mapEvaluator;
        Evaluator<FactionState, Void, Boolean> pojoEvaluator;

        @Setup(Level.Trial)
        public void compile() {
            LambdaRegistry.INSTANCE.resetAndRemoveAllPersistedFiles();

            // MAP evaluator
            Map<String, Type<?>> types = new HashMap<>();
            types.put("influence", Type.type(int.class));
            types.put("atWar", Type.type(boolean.class));
            types.put("stability", Type.type(int.class));
            types.put("treasury", Type.type(double.class));
            types.put("factionName", Type.type(String.class));

            MVEL mvel = new MVEL();
            mapEvaluator = mvel.compileMapExpression(
                    "influence > 50 && !atWar && stability > 30",
                    Boolean.class, Collections.emptySet(), types);

            // POJO evaluator
            CompilerParameters<FactionState, Void, Boolean> params =
                    MVEL.<FactionState>pojo(FactionState.class,
                                    Declaration.of("influence", int.class),
                                    Declaration.of("atWar", boolean.class),
                                    Declaration.of("stability", int.class),
                                    Declaration.of("treasury", double.class),
                                    Declaration.of("factionName", String.class))
                            .<Boolean>out(Boolean.class)
                            .expression("influence > 50 && !atWar && stability > 30")
                            .imports(Collections.emptySet())
                            .classManager(new ClassManager())
                            .build();

            pojoEvaluator = mvel.compilePojoEvaluator(params);
        }
    }

    @Benchmark
    public Map<String, Object> constructMapContext() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        Map<String, Object> ctx = new HashMap<>();
        ctx.put("influence", rng.nextInt(0, 100));
        ctx.put("stability", rng.nextInt(0, 100));
        ctx.put("treasury", rng.nextDouble(0, 5000));
        ctx.put("factionName", "Faction" + rng.nextInt(100));
        ctx.put("atWar", rng.nextBoolean());
        return ctx;
    }

    @Benchmark
    public FactionState constructPojoContext() {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        FactionState fs = new FactionState();
        fs.setInfluence(rng.nextInt(0, 100));
        fs.setStability(rng.nextInt(0, 100));
        fs.setTreasury(rng.nextDouble(0, 5000));
        fs.setFactionName("Faction" + rng.nextInt(100));
        fs.setAtWar(rng.nextBoolean());
        return fs;
    }

    @Benchmark
    public Boolean constructAndEvalMap(EvalState state) {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        Map<String, Object> ctx = new HashMap<>();
        ctx.put("influence", rng.nextInt(0, 100));
        ctx.put("stability", rng.nextInt(0, 100));
        ctx.put("treasury", rng.nextDouble(0, 5000));
        ctx.put("factionName", "Faction" + rng.nextInt(100));
        ctx.put("atWar", rng.nextBoolean());
        return state.mapEvaluator.eval(ctx);
    }

    @Benchmark
    public Boolean constructAndEvalPojo(EvalState state) {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        FactionState fs = new FactionState();
        fs.setInfluence(rng.nextInt(0, 100));
        fs.setStability(rng.nextInt(0, 100));
        fs.setTreasury(rng.nextDouble(0, 5000));
        fs.setFactionName("Faction" + rng.nextInt(100));
        fs.setAtWar(rng.nextBoolean());
        return state.pojoEvaluator.eval(fs);
    }
}
