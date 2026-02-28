Honest Assessment of MVEL3

What it is

A complete rewrite of the MVEL expression language that takes a fundamentally different approach from MVEL2: instead of interpreting expressions at runtime, it transpiles them to Java source, compiles in-memory via javac, and runs bytecode. The core idea is sound — you get type safety and JIT-optimized performance at the cost of upfront compilation time.

The Good

Architecture is well-conceived. The four-stage pipeline (parse → transpile → generate → compile) has clean separation of concerns. Using ANTLR4 for parsing and JavaParser for AST manipulation are solid choices. The fluent builder API (MVEL.map(...).out(...).expression(...).compile()) is ergonomic and type-safe with the triple-typed Evaluator<C, W, O> interface.

The transpiler core works. The main compilation path for arithmetic, comparisons, method calls, assignments, and control flow across all three context types (Map, List, POJO) is functional and well-tested. MVELTranspilerTest alone has ~1,900 lines of test cases.

The Concerning

It's firmly alpha-quality. This isn't just a label — there are concrete indicators:

- 11 disabled tests for broken features: power operator (**), unsigned left shift (<<<), and single-quote strings are all non-functional
- 20+ TODO/FIXME comments scattered across the codebase, some in critical paths (static method resolution, primitive type handling, generics in lambdas)
- Only 3.0.0-SNAPSHOT — never published to Maven Central
- Multiple features explicitly marked incomplete in the CHANGELOG (null-safe operators, custom operators, modify blocks)

Thread safety is not addressed. ClassManager uses unsynchronized HashMaps with exposed mutable references. LambdaRegistry (a singleton) has non-atomic ID counters (nextPhysicalId++) and unsynchronized maps. MVEL.get() has a classic check-then-act race. If you use this in a multi-threaded environment (which is most Java environments), you will eventually hit data corruption.

Error handling is poor. Exceptions are routinely swallowed (MVELCompiler.getMethod() silently returns null on failure), wrapped generically (catch (Exception e) { throw new RuntimeException(e) }), or have unhelpful messages ("class expected"). When compilation fails, diagnosing why will be painful.

The javaparser fork is a maintenance liability. The project depends on a custom fork (javaparser-mvel at version 3.25.5-mvel3-1) that must be cloned and built locally before you can even compile MVEL3. This fork adds MVEL-specific AST nodes. There's no clear plan for keeping it synchronized with upstream JavaParser, and it creates a significant barrier for contributors.

Drools coupling shouldn't be here. The POM itself admits the drools-compiler dependency is "likely out of this project scope." DrlToJavaRewriter is partially implemented, yet it pulls in a heavy transitive dependency tree. Several other classes (VariableAnalyser, MVELPrintVisitor) also extend Drools interfaces.

Dual parser maintenance. Both ANTLR4 and JavaCC parsers are compiled on every build despite ANTLR4 being the designated primary. The legacy JavaCC parser adds build complexity and code surface area for no current benefit.

Test Coverage

Tests cover happy paths well but have significant gaps:

- ~85% happy-path, ~15% edge-case tests — inverted from what mature projects target
- No tests for invalid syntax, type mismatches, null handling, or concurrent compilation
- DSLTest.java is 28 lines with no assertions
- Commented-out test methods in MVELCompilerTest (e.g., testPojoEvaluatorReturns())
- No stress tests or memory leak detection

API Design

The public API has raw type leaks — compileListBlock() and compileListExpression() accept raw Class instead of Class<R>, and Type.LIST/Type.MAP are raw types. No @Nullable/@NonNull annotations anywhere. There's no code quality enforcement at all — no Checkstyle, SpotBugs, PMD, or EditorConfig.

Bottom Line

MVEL3 has a solid architectural vision and the transpiler approach is genuinely interesting. The core expression compilation works. But it is not production-ready — it's an early alpha with broken features, thread-safety holes, poor error handling, and a fragile dependency on a custom fork. If you're evaluating this for a project, you'd be adopting it knowing you'll hit rough edges and potentially need to fix issues yourself. If you're contributing to it, the biggest wins would be fixing thread safety, removing the Drools coupling, and completing the disabled test features.
