# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

MVEL3 is a complete rewrite of the MVEL expression language for Java. Unlike MVEL2 (which interprets at runtime), MVEL3 **transpiles** MVEL expressions into Java source code, compiles them in-memory via javac, and executes the resulting bytecode. This is an alpha release—APIs may change.

## Build Commands

**Prerequisites:** JDK 17+, Maven 3.8.7+. You must first build and install the [javaparser-mvel](https://github.com/mvel/javaparser-mvel) fork locally (`mvn clean install` in that repo).

```bash
mvn clean install          # Full build with tests
mvn clean compile          # Compile only (includes ANTLR4 code generation)
mvn test                   # Run all tests
mvn test -Dtest=MVELTranspilerTest                        # Run a single test class
mvn test -Dtest=MVELTranspilerTest#testAssignmentIncrement # Run a single test method
```

Tests use JUnit 5 (Jupiter) with AssertJ assertions. Surefire is configured with `mvel3.compiler.lambda.resetOnTestStartup=true` and alphabetical run order.

## Architecture

The compilation pipeline flows through four stages:

1. **Parse** — ANTLR4 grammar (`src/main/antlr4/.../Mvel3Parser.g4`, `Mvel3Lexer.g4`) parses MVEL expressions into a parse tree. Generated sources go to `target/generated-sources/antlr4/`.

2. **Transpile** — `MVELTranspiler` converts MVEL parse trees into JavaParser AST nodes. `MVELToJavaRewriter` applies MVEL-specific AST transformations (e.g., null-safe access, coercion, operator overloading). Supporting rewriters: `CoerceRewriter`, `OverloadRewriter`.

3. **Generate** — `CompilationUnitGenerator` wraps the transpiled AST into a full Java `CompilationUnit` implementing the `Evaluator` interface. The generated class contains an `eval()` method with the transpiled logic.

4. **Compile & Load** — `KieMemoryCompiler` compiles the generated Java source in-memory using javac. `ClassManager` manages the resulting bytecode. `LambdaRegistry` optionally caches/persists compiled lambda classes to disk.

### Key Classes

- **`MVEL`** — Public API entry point with fluent builders (`map()`, `list()`, `pojo()`)
- **`MVELCompiler`** — Orchestrates the full pipeline: transpile → generate → compile → instantiate
- **`MVELBuilder`** — Fluent builder chain: context → output type → expression/block → imports → compile
- **`Evaluator<C, W, O>`** — Interface implemented by generated evaluator classes (C=context, W=with-object, O=output)
- **`CompilerParameters`** — Immutable record holding all compilation inputs
- **`MVELTranspiler`** — Core transpilation logic (MVEL parse tree → JavaParser AST)
- **`MVELToJavaRewriter`** — AST-level rewriting rules for MVEL semantics
- **`MvelParser`** — Parser abstraction (ANTLR4-based via `Antlr4MvelParser`)

### Three Context Types

Expressions are compiled against one of three context types, which determines how variables are extracted at runtime:
- **MAP** — Variables via `map.get("name")`, written back via `MVEL.putMap()`
- **LIST** — Variables via `list.get(index)`, written back via `MVEL.setList()`
- **POJO** — Variables via getter methods, written back via setter methods

### Dependencies

- **javaparser-mvel** — Custom fork of JavaParser with MVEL3-specific AST nodes (must be installed locally)
- **ANTLR4** — Parser generator for MVEL grammar
- **ASM** — Bytecode manipulation for lambda extraction

## Source Layout

- `src/main/antlr4/` — ANTLR4 grammar files (Mvel3Lexer.g4, Mvel3Parser.g4, JavaLexer.g4, JavaParser.g4)
- `org.mvel3` — Core API: MVEL, MVELCompiler, MVELBuilder, Evaluator, CompilerParameters, ClassManager
- `org.mvel3.transpiler` — Transpilation: MVELTranspiler, MVELToJavaRewriter, CoerceRewriter, OverloadRewriter
- `org.mvel3.parser` — Parser abstraction and ANTLR4 implementation
- `org.mvel3.javacompiler` — In-memory Java compilation (KieMemoryCompiler)
- `org.mvel3.lambdaextractor` — Lambda class caching, persistence, and bytecode extraction
- `org.mvel3.util` — Utilities for strings, types, streams, and class resolution
