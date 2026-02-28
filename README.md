# DynamisExpression

MVEL3 expression language transpiler — compiles MVEL expressions into Java bytecode via transpilation to Java source, in-memory javac compilation, and bytecode execution.

## Modules

| Module | Description | GroupId | Version |
|---|---|---|---|
| `javaparser-mvel-main` | Fork of JavaParser with ~28 MVEL3/DRL-specific AST node types | `org.mvel.javaparser` | `3.25.5-mvel3-SNAPSHOT` |
| `mvel-main` | MVEL3 transpiler — parses, transpiles, compiles, and executes MVEL expressions | `org.mvel` | `3.0.0-SNAPSHOT` |
| `mvel-benchmarks` | JMH benchmark suite for MVEL3 expression evaluation | `org.mvel` | `3.0.0-SNAPSHOT` |

## Prerequisites

- **JDK 17** (required — tests fail on JDK 21)
- **Maven 3.8.7+**

## Build

Build all modules from the project root:

```bash
mvn clean install
```

The Maven reactor resolves build order automatically based on declared dependencies (`javaparser-mvel-main` builds first, then `mvel-main`, then `mvel-benchmarks`).

### Build individual modules

```bash
# javaparser-mvel fork only
cd javaparser-mvel-main && ./mvnw clean install

# MVEL3 transpiler only (requires javaparser-mvel installed)
cd mvel-main && mvn clean install

# Benchmarks only (requires mvel3 installed)
cd mvel-benchmarks && mvn clean package
```

### Run tests

```bash
# All tests from root
mvn test

# Single test class
cd mvel-main && mvn test -Dtest=MVELTranspilerTest

# Single test method
cd mvel-main && mvn test -Dtest=MVELTranspilerTest#testAssignmentIncrement

# javaparser-mvel tests including slow tests
cd javaparser-mvel-main && ./mvnw -B --errors clean test --activate-profiles AlsoSlowTests
```

### Run benchmarks

```bash
cd mvel-benchmarks
mvn clean package
java -jar target/benchmarks.jar        # Run all benchmarks
java -jar target/benchmarks.jar -l     # List available benchmarks
```

## Architecture

The MVEL3 transpilation pipeline:

```
MVEL source
  -> ANTLR4 parse tree
    -> JavaParser AST (with MVEL nodes)
      -> Rewrite to pure Java AST
        -> Java source
          -> javac in-memory
            -> Bytecode execution
```

**Four stages:**

1. **Parse** — ANTLR4 grammar produces a parse tree from MVEL expressions
2. **Transpile** — `MVELTranspiler` converts the parse tree into a JavaParser AST; `MVELToJavaRewriter` applies MVEL-specific rewrites (null-safe access, coercion, operator overloading)
3. **Generate** — `CompilationUnitGenerator` wraps the transpiled AST into a Java `CompilationUnit` implementing `Evaluator<C, W, O>`
4. **Compile & Load** — `KieMemoryCompiler` compiles generated Java via javac; `ClassManager` manages bytecode

See [mvel-main/README.md](mvel-main/README.md) for detailed API usage and examples.
