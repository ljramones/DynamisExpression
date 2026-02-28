package org.mvel3.benchmark;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.mvel3.Evaluator;
import org.mvel3.MVEL;
import org.mvel3.Type;
import org.mvel3.lambdaextractor.LambdaRegistry;
import org.openjdk.jmh.annotations.*;

/**
 * Two threads compiling different expressions simultaneously against the shared
 * LambdaRegistry. Proves thread safety under load and gives a contention
 * baseline.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgsAppend = {
        "-Dmvel3.compiler.lambda.persistence=false",
        "-Dmvel3.compiler.lambda.resetOnTestStartup=true"
})
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Threads(2)
public class ConcurrentCompilationBenchmark {

    @State(Scope.Benchmark)
    public static class SharedState {

        final String[] expressions = {
                "a + b",
                "a * b + a"
        };

        final Map<String, Type<?>> types = new HashMap<>();

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
    public static class ThreadState {

        private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(0);
        int threadIndex;

        @Setup(Level.Trial)
        public void init() {
            threadIndex = THREAD_COUNTER.getAndIncrement() % 2;
        }
    }

    @Benchmark
    public Evaluator<?, ?, ?> concurrentCompileDifferentExpressions(
            SharedState shared, ThreadState local) {
        MVEL mvel = new MVEL();
        return mvel.compileMapExpression(
                shared.expressions[local.threadIndex],
                Integer.class, Collections.emptySet(), shared.types);
    }
}
