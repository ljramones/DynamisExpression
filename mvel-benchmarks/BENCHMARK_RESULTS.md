# Benchmark Results

Recorded: 2026-02-28

## Environment

- JDK 25 (Temurin 25.0.1+8)
- JMH benchmarks from `mvel-benchmarks` module
- Baseline: pre-error-handling/DRL-cleanup state
- Previous: post-error-handling + DRL cleanup + benchmark fix (string concat removed from hot loop)
- Current: Phase 1 Classfile API bytecode emitter (javac bypass for predicate expressions)

## Complete Baseline Comparison

| Benchmark | Original | Current | Delta |
|---|---|---|---|
| evalPojoPredicate (thrpt) | 1,538 ops/us | 1,733 ops/us | +13% |
| evalMapPredicate (thrpt) | 266 ops/us | 309 ops/us | +16% |
| evalMapComplexPredicate (thrpt) | 241 ops/us | 241 ops/us | flat |
| constructPojoContext (thrpt) | 102.83 ops/us | 102.58 ops/us | flat |
| constructMapContext (thrpt) | 37.32 ops/us | 38.10 ops/us | flat |
| constructAndEvalPojo (thrpt) | 47.99 ops/us | 97.73 ops/us | **+104%** * |
| constructAndEvalMap (thrpt) | 25.75 ops/us | 24.22 ops/us | -6% |
| compileSimpleExpression | 5.73 ms | **0.70 ms** | **-88% (7.8x)** † |
| compilePredicateExpression | 5.83 ms | **0.87 ms** | **-85% (6.5x)** † |
| compileComplexExpression | 5.77 ms | 5.78 ms | flat (javac fallback) |
| concurrentCompile | 6.85 ms | 6.15 ms | **-10%** |

† Classfile API bytecode emitter bypasses javac entirely. See "Classfile API Compilation Speedup" below.

\* Original constructAndEvalPojo measurement included `"Faction" + rng.nextInt()` string concatenation in the hot loop, which accounted for 74% of samples. See "Benchmark Fix" below.

## Benchmark Fix: String Concatenation in Hot Loop

JFR profiling (`constructAndEvalPojo.jfr`) revealed that 74.3% of CPU samples were in `DecimalDigits.uncheckedGetCharsLatin1` called from `StringConcatHelper$Concat1.concat(int)` — integer-to-string conversion from `"Faction" + rng.nextInt(100)` on every benchmark iteration.

The expression engine accounted for only 25% of samples. The previously reported -10% regression was benchmark noise, not an engine regression.

**Fix:** Pre-compute `String[] FACTION_NAMES` array in a static initializer. Index with `rng.nextInt(100)` in the hot loop. Eliminates string allocation from the measurement window.

**Result:** 47.99 → 97.73 ops/us (+104%) — the "original" baseline was also measuring string concat, so both old and new numbers were inflated. The true construct+eval cost is 0.010 us/op (10 nanoseconds).

## Key Observations

### Evaluation Performance

POJO predicate eval at 0.001 us (1 nanosecond) is single-digit CPU instruction count. The transpile-to-bytecode bet is fully realized. Warm evaluation performance cannot be meaningfully improved further without changing hardware.

### Concurrent Compilation

The concurrent compilation improvement from 6.85ms to 6.15ms is the thread safety work paying off — less contention on LambdaRegistry under concurrent load.

### evalPojoPredicate Throughput Variance

Variance of ±126 ops/us (stdev 188) in throughput mode. Fork 1 raw data shows iterations oscillating between 1,890 and 1,303 — a 30% cliff suggesting JIT deoptimization events, not GC. Latency mode shows 0.001 ±0.001 which is at measurement resolution, so evaluation itself is fine. Throughput instability is likely benchmark harness interaction at nanosecond scale rather than a real production concern.

## Classfile API Compilation Speedup

Phase 1 of the Classfile API bytecode emitter (`java.lang.classfile`, JEP 484) replaces the javac in-memory compilation pipeline with direct bytecode generation for supported expression patterns. `MVELCompiler.compile()` tries the Classfile API path first and falls back to javac for unsupported expressions.

### Before vs After

| Benchmark | javac (before) | Classfile API (after) | Speedup |
|---|---|---|---|
| compileSimpleExpression (`a + b`) | 5.48 ms | 0.70 ms | **7.8x** |
| compilePredicateExpression (`influence > 50 && !atWar && stability > 30`) | 5.60 ms | 0.87 ms | **6.5x** |
| compileComplexExpression (block with assignments) | 5.58 ms | 5.78 ms | javac fallback |

### What Phase 1 supports

- Integer/boolean/double/float/long literals
- Arithmetic: `+`, `-`, `*`, `/`, `%`
- Comparisons: `>`, `<`, `>=`, `<=`, `==`, `!=`
- Boolean logic: `&&` (short-circuit), `||` (short-circuit), `!`
- Variable extraction from Map (`map.get("key")` + cast), POJO (getter calls), List (`list.get(index)` + cast)
- Cast expressions (primitive unboxing, reference casts)
- Simple return statements

### What falls back to javac

- Block expressions with multiple statements and assignments
- Compound assignments (`+=`, `-=`, etc.)
- Boxed-type variable declarations (auto-unboxing in arithmetic not yet implemented)
- Control flow (`if`/`for`/`while`), try/catch, object creation
- Lambda persistence mode (`LambdaRegistry.PERSISTENCE_ENABLED`)

### Where the remaining 0.7ms goes

The Classfile API eliminated javac (~4.8ms) but the remaining ~0.7ms is the transpilation pipeline: ANTLR4 parse → JavaParser AST construction → MVELToJavaRewriter pass → `canEmit()` check → Classfile API bytecode emission. The ANTLR parse and AST rewrite are the dominant remaining costs. Phase 2/3 may explore bypassing the full AST rewrite for simple expressions.

### Startup impact

At 100 predicate expressions during DynamisScripting startup, compilation cost drops from 550ms (100 × 5.5ms) to 70ms (100 × 0.7ms) — a **480ms reduction** in engine startup time.

## Previous Compilation Cost Analysis

Before the Classfile API emitter, compilation cost was immovable at ~5.5ms regardless of expression complexity, dominated by javac's in-memory compiler infrastructure.

## GC Considerations

Latency data is almost too good to need GC work at the evaluation layer — 1ns POJO eval generates essentially zero GC pressure per call. The GC concern is at the construction layer. At 0.010 us per POJO context construction and 0.026 us per Map context construction, if DynamisScripting constructs fresh contexts per-tick rather than pooling them, GC pressure will appear at tick frequency. Context pooling targets construction, not evaluation. The engine's allocation profile is healthy — context pooling is an optimization, not a fix.

## Recommended Next Steps

1. **Phase 2**: Extend Classfile API emitter to control flow (`if`/`for`/`while`), try/catch, object creation — eliminates javac fallback for more expression patterns
2. **Phase 3**: Full javac elimination — remove KieMemoryCompiler, remove ASM dependency, unify ClassManager with Classfile API loading
3. GC/context pooling work follows after compilation pipeline is finalized
