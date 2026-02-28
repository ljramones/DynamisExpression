# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Structure

This workspace contains two interdependent Java projects that together implement the MVEL3 expression language transpiler:

- **`mvel-main/`** — MVEL3: transpiles MVEL expressions into Java source, compiles in-memory via javac, runs bytecode. Alpha-quality (`3.0.0-SNAPSHOT`).
- **`javaparser-mvel-main/`** — Fork of JavaParser (v3.25.5) adding ~28 MVEL3/DRL-specific AST node types. Serves as the core IR for the MVEL3 transpiler. GroupId: `org.mvel.javaparser`, version: `3.25.5-mvel3-SNAPSHOT`.

**Dependency direction:** `mvel-main` depends on `javaparser-mvel-main`. The javaparser fork must be built and installed first.

## Build Commands

**Prerequisites:** JDK 17, Maven 3.8.7+

### Build order (javaparser-mvel first, then mvel3)

```bash
# 1. Build and install javaparser-mvel fork
cd javaparser-mvel-main && ./mvnw clean install && cd ..

# 2. Build MVEL3
cd mvel-main && mvn clean install && cd ..
```

### javaparser-mvel-main

```bash
./mvnw clean install                                    # Full build + install (generates JavaCC sources)
./mvnw clean test                                       # Quick tests only
./mvnw -B --errors clean test --activate-profiles AlsoSlowTests  # All tests including slow (CI mode)
./mvnw javacc:javacc                                    # Generate JavaCC parser sources (for IDE setup)
./mvnw -B checkstyle:check                              # Run checkstyle
./mvnw -pl javaparser-core-testing test -Dtest=ClassName          # Single test class
./mvnw -pl javaparser-core-testing test -Dtest=ClassName#method   # Single test method
```

### mvel-main

```bash
mvn clean install                                       # Full build with tests
mvn clean compile                                       # Compile only (runs ANTLR4 codegen)
mvn test                                                # All tests
mvn test -Dtest=MVELTranspilerTest                      # Single test class
mvn test -Dtest=MVELTranspilerTest#testAssignmentIncrement  # Single test method
mvn clean verify                                        # CI-equivalent validation
```

## Architecture Overview

### MVEL3 Transpilation Pipeline (mvel-main)

```
MVEL source → ANTLR4 parse tree → JavaParser AST (with MVEL nodes) → rewrite to pure Java AST → Java source → javac in-memory → bytecode
```

Four stages:

1. **Parse** — ANTLR4 grammar (`src/main/antlr4/.../Mvel3Parser.g4`, `Mvel3Lexer.g4`) produces parse tree.
2. **Transpile** — `MVELTranspiler` and `Mvel3ToJavaParserVisitor` (3,333 lines) convert parse tree into JavaParser AST. `MVELToJavaRewriter` (1,614 lines) applies MVEL-specific rewrites (null-safe access, coercion, operator overloading). Supporting rewriters: `CoerceRewriter`, `OverloadRewriter`.
3. **Generate** — `CompilationUnitGenerator` wraps transpiled AST into a Java `CompilationUnit` implementing `Evaluator<C, W, O>`.
4. **Compile & Load** — `KieMemoryCompiler` compiles generated Java via javac. `ClassManager` manages bytecode. `LambdaRegistry` caches compiled lambdas.

### Key MVEL3 Classes

- **`MVEL`** — Public API with fluent builders (`map()`, `list()`, `pojo()`)
- **`MVELCompiler`** — Orchestrates full pipeline
- **`MVELBuilder`** — Fluent builder: context → output type → expression/block → imports → compile
- **`Evaluator<C, W, O>`** — Interface for generated evaluators (C=context, W=with-object, O=output)

### Three Context Types

- **MAP** — Variables via `map.get("name")`
- **LIST** — Variables via `list.get(index)`
- **POJO** — Variables via getter/setter methods

### javaparser-mvel Module Structure

| Module | Purpose |
|---|---|
| `javaparser-core` | Parser, AST nodes (Java + MVEL), visitors, metamodel, pretty-printer. JavaCC grammar at `src/main/javacc/java.jj` |
| `javaparser-core-generators` | Code generators for AST node boilerplate and visitors (run manually) |
| `javaparser-core-metamodel-generator` | Generates metamodel via reflection (run manually, before generators) |
| `javaparser-core-testing` | JUnit 5 unit tests |
| `javaparser-core-testing-bdd` | JBehave BDD story-based tests |
| `javaparser-symbol-solver-core` | Type/symbol resolution engine |
| `javaparser-symbol-solver-testing` | Symbol solver tests |
| `javaparser-core-serialization` | JSON serialization of AST |

### MVEL AST Nodes in javaparser-mvel

All MVEL/DRL nodes live under `javaparser-core/src/main/java/org/mvel3/parser/ast/`. Two-tier visitor pattern: simple MVEL nodes (`InlineCastExpr`, `BigDecimalLiteralExpr`, etc.) are in standard `GenericVisitor`/`VoidVisitor`; DRL-specific nodes use extension interfaces `DrlGenericVisitor`/`DrlVoidVisitor`.

### Adding a New MVEL AST Node (javaparser-mvel)

1. Create node class in `org.mvel3.parser.ast.expr`
2. Add to `ALL_NODE_CLASSES` in `MetaModelGenerator.java`
3. Run `./run_core_metamodel_generator.sh`
4. Run `./run_core_generators.sh`
5. Manually add `visit()` methods to `DrlVoidVisitor`, `DrlGenericVisitor`, `DrlCloneVisitor`, `DrlVoidVisitorAdapter`, `DrlGenericVisitorWithDefaults`

Do not hand-edit methods with `@Generated` annotations.

## Key Constraints

- **JDK 17 only** — javaparser-mvel enforcer allows 11–17, but MVEL3 requires 17 and tests fail on 21
- **javaparser-mvel source level is Java 8** (for compatibility), but must build with JDK 17
- **mvel-main source level is Java 17**
- Tests use JUnit 5 (Jupiter) + AssertJ. mvel-main Surefire runs alphabetically with `mvel3.compiler.lambda.resetOnTestStartup=true`
- Checkstyle config for javaparser-mvel: `dev-files/JavaParser-CheckStyle.xml`

## Known Issues

- Alpha quality: 11 disabled tests (power operator, unsigned left shift, single-quote strings), 20+ TODOs in critical paths
- Thread safety not addressed in `ClassManager`, `LambdaRegistry`, `MVEL.get()`
- `DrlCloneVisitor` mostly stubbed (returns null for most MVEL nodes)
