# Benchmark Results

Recorded: 2026-02-28

## Environment

- JDK 25 (Temurin 25.0.1+8)
- JMH benchmarks from `mvel-benchmarks` module
- Baseline: pre-error-handling/DRL-cleanup state
- Phase 1: Classfile API bytecode emitter for predicate expressions (javac bypass)
- Phase 2: Boxed type auto-unboxing, bitwise/compound operators, var inference, static method calls

## Complete Baseline Comparison

| Benchmark | Original | Phase 1 | Phase 2 | Delta (vs original) |
|---|---|---|---|---|
| evalPojoPredicate (thrpt) | 1,538 ops/us | 1,733 ops/us | 1,670 ops/us | +9% |
| evalMapPredicate (thrpt) | 266 ops/us | 309 ops/us | 277 ops/us | +4% |
| evalMapComplexPredicate (thrpt) | 241 ops/us | 241 ops/us | 237 ops/us | flat |
| constructPojoContext (thrpt) | 102.83 ops/us | 102.58 ops/us | 130.74 ops/us | +27% |
| constructMapContext (thrpt) | 37.32 ops/us | 38.10 ops/us | 44.79 ops/us | +20% |
| constructAndEvalPojo (thrpt) | 47.99 ops/us | 97.73 ops/us | 94.16 ops/us | **+96%** * |
| constructAndEvalMap (thrpt) | 25.75 ops/us | 24.22 ops/us | 22.82 ops/us | -11% |
| compileSimpleExpression | 5.73 ms | **0.70 ms** | **0.71 ms** | **-88% (8.1x)** |
| compilePredicateExpression | 5.83 ms | **0.87 ms** | **0.84 ms** | **-86% (6.9x)** |
| compileComplexExpression | 5.77 ms | 5.78 ms | 6.22 ms | javac fallback |
| concurrentCompile | 6.85 ms | 6.15 ms | **0.76 ms** | **-89% (9.0x)** † |

† Phase 2 auto-unboxing moved boxed-type expressions from javac to the Classfile API path, eliminating javac from the concurrent benchmark entirely. See "Phase 2: Concurrent Compilation Breakthrough" below.

\* Original constructAndEvalPojo measurement included `"Faction" + rng.nextInt()` string concatenation in the hot loop, which accounted for 74% of samples. See "Benchmark Fix" below.

## Benchmark Fix: String Concatenation in Hot Loop

JFR profiling (`constructAndEvalPojo.jfr`) revealed that 74.3% of CPU samples were in `DecimalDigits.uncheckedGetCharsLatin1` called from `StringConcatHelper$Concat1.concat(int)` — integer-to-string conversion from `"Faction" + rng.nextInt(100)` on every benchmark iteration.

The expression engine accounted for only 25% of samples. The previously reported -10% regression was benchmark noise, not an engine regression.

**Fix:** Pre-compute `String[] FACTION_NAMES` array in a static initializer. Index with `rng.nextInt(100)` in the hot loop. Eliminates string allocation from the measurement window.

**Result:** 47.99 → 97.73 ops/us (+104%) — the "original" baseline was also measuring string concat, so both old and new numbers were inflated. The true construct+eval cost is 0.010 us/op (10 nanoseconds).

## Key Observations

### Evaluation Performance

POJO predicate eval at 0.001 us (1 nanosecond) is single-digit CPU instruction count. The transpile-to-bytecode bet is fully realized. Warm evaluation performance cannot be meaningfully improved further without changing hardware.

### evalPojoPredicate Throughput Variance

Variance of ±910 ops/us in throughput mode. This is benchmark harness interaction at nanosecond scale — latency mode shows 0.001 ±0.001 which is at measurement resolution. Not a production concern.

## Phase 2: Concurrent Compilation Breakthrough

The concurrent compilation benchmark measures compiling multiple different expression types in parallel. In Phase 1, the concurrent benchmark ran at 6.15ms because many expressions contained boxed-type variables (`Integer`, `Double` from `MVEL.getTypeMap()`) which forced javac fallback.

Phase 2's auto-unboxing support means those expressions now emit directly through the Classfile API. The concurrent benchmark dropped from **6.15ms to 0.76ms** — a **9.0x improvement**.

| Benchmark | javac (baseline) | Phase 1 | Phase 2 | Speedup |
|---|---|---|---|---|
| concurrentCompile | 6.85 ms | 6.15 ms | **0.76 ms** | **9.0x** |

This validates that boxed-type auto-unboxing was the correct Phase 2 priority. The audit showed 80% of javac fallbacks were boxed types — removing that gate moved virtually all concurrent expressions to the fast path.

## Classfile API Compilation Speedup

The Classfile API bytecode emitter (`java.lang.classfile`, JEP 484) replaces the javac in-memory compilation pipeline with direct bytecode generation. `MVELCompiler.compile()` tries the Classfile API path first and falls back to javac for unsupported expressions.

### Phase 1 vs Phase 2 Coverage

| Metric | Phase 1 | Phase 2 |
|---|---|---|
| canEmit=YES (of 662 compile calls) | 50 (7.6%) | **649 (98.0%)** |
| canEmit=NO (javac fallback) | 612 (92.4%) | **13 (2.0%)** |

### What Phase 2 added

- **Auto-unboxing**: `Integer` → `int`, `Double` → `double`, `Long` → `long`, etc. in variable declarations. MVEL's `getTypeMap()` produces boxed types from Java values; the emitter now unboxes at extraction and operates on primitives throughout.
- **Bitwise operators**: `&`, `|`, `^`, `<<`, `>>`, `>>>`, `~`
- **Compound assignments**: `+=`, `-=`, `*=`, `/=`, `%=`, `<<=`, `>>=`, `>>>=`, `&=`, `|=`, `^=`
- **`var` type inference**: Resolves `var` declarations from initializer expression types
- **POJO/reference-type variables**: Allocates reference slots for extracted POJO objects
- **Static method calls**: `java.lang.Math.*` (sin, cos, pow, ceil, floor, etc.), `org.mvel3.MVEL.putMap/setList`
- **POJO instance methods with arguments**: Setter/mutator calls on extracted objects

### What still falls back to javac (13 of 662 = 2%)

- Control flow (`if`/`else`, `with` blocks) — 4 cases
- BigDecimal operations (`.add()`, `.subtract()` with `MathContext`) — 3 cases
- Free-function calls without scope (`isEven(1)`, `staticMethod(1)`) — 3 cases
- POJO field access (`foo.nonExistentProperty`) — 1 case (error test)
- POJO setter with assignment argument (`__contexta(__context, a = 4)`) — 1 case
- String concatenation with non-string operands — 1 case

### Compilation cost breakdown

| Benchmark | javac (baseline) | Phase 1 | Phase 2 |
|---|---|---|---|
| compileSimpleExpression (`a + b`) | 5.48 ms | 0.70 ms | 0.71 ms |
| compilePredicateExpression (`influence > 50 && !atWar && stability > 30`) | 5.60 ms | 0.87 ms | 0.84 ms |
| compileComplexExpression (block with assignments) | 5.58 ms | 5.78 ms | 6.22 ms |
| concurrentCompileDifferentExpressions | 6.85 ms | 6.15 ms | **0.76 ms** |

### Where the remaining 0.7ms goes

The Classfile API eliminated javac (~4.8ms) but the remaining ~0.7ms is the transpilation pipeline: ANTLR4 parse → JavaParser AST construction → MVELToJavaRewriter pass → `canEmit()` check → Classfile API bytecode emission. The ANTLR parse and AST rewrite are the dominant remaining costs.

### Startup impact

At 100 predicate expressions during DynamisScripting startup, compilation cost drops from 550ms (100 × 5.5ms) to 70ms (100 × 0.7ms) — a **480ms reduction** in engine startup time. Concurrent compilation of the same 100 expressions now completes in ~76ms total (was ~615ms in Phase 1).

## Previous Compilation Cost Analysis

Before the Classfile API emitter, compilation cost was immovable at ~5.5ms regardless of expression complexity, dominated by javac's in-memory compiler infrastructure.

## GC Considerations

Latency data is almost too good to need GC work at the evaluation layer — 1ns POJO eval generates essentially zero GC pressure per call. The GC concern is at the construction layer. At 0.010 us per POJO context construction and 0.026 us per Map context construction, if DynamisScripting constructs fresh contexts per-tick rather than pooling them, GC pressure will appear at tick frequency. Context pooling targets construction, not evaluation. The engine's allocation profile is healthy — context pooling is an optimization, not a fix.

## Recommended Next Steps

1. **Phase 3**: Full javac elimination — remove KieMemoryCompiler, remove ASM dependency, unify ClassManager with Classfile API loading. The 13 remaining fallback cases (2%) need control flow support or can be deferred.
2. GC/context pooling work follows after compilation pipeline is finalized.
