# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What This Is

A fork of [JavaParser](https://github.com/javaparser/javaparser) that adds custom MVEL3/DRL AST nodes and visitor modifications. The upstream project parses Java 1.0–17; this fork extends it for [MVEL3](https://github.com/mvel/mvel) expression language parsing. GroupId: `org.mvel.javaparser`, version: `3.25.5-mvel3-SNAPSHOT`.

## Build Commands

```bash
# Full build + install (required before first IDE use to generate JavaCC sources)
./mvnw clean install

# Run all tests (quick tests only)
./mvnw clean test

# Run all tests including slow tests (what CI runs)
./mvnw -B --errors clean test --activate-profiles AlsoSlowTests

# Generate JavaCC parser sources (needed for IDE, already done by clean install)
./mvnw javacc:javacc

# Run checkstyle validation
./mvnw -B checkstyle:check

# Run a single test class
./mvnw -pl javaparser-core-testing test -Dtest=ClassName

# Run a single test method
./mvnw -pl javaparser-core-testing test -Dtest=ClassName#methodName
```

**JDK requirement:** JDK 17 only. The enforcer allows 11–17, but MVEL3 requires 17, and tests fail on 21.

## Code Generation Pipeline

When AST node fields or node classes are added/removed, two generators must run **in order**:

1. **`./run_core_metamodel_generator.sh`** — Introspects AST node classes via reflection, generates `*MetaModel.java` files in `com.github.javaparser.metamodel` and updates `JavaParserMetaModel`.

2. **`./run_core_generators.sh`** — Uses the metamodel to regenerate boilerplate in every AST node (`accept()`, `clone()`, getters/setters, `remove()`, `replace()`, type-casting methods) and all visitor classes.

Generated methods carry `@Generated` annotations indicating which generator produced them. Do not hand-edit generated code.

## Module Architecture

| Module | Purpose |
|---|---|
| `javaparser-core` | Parser, AST nodes (Java + MVEL), visitors, metamodel, pretty-printer, resolution interfaces. Contains the JavaCC grammar (`src/main/javacc/java.jj`) |
| `javaparser-core-generators` | Code generators for AST node boilerplate and visitor classes (run manually) |
| `javaparser-core-metamodel-generator` | Generates metamodel by introspecting node classes (run manually, before core-generators) |
| `javaparser-core-serialization` | JSON serialization of AST |
| `javaparser-core-testing` | JUnit 5 unit tests for the parser |
| `javaparser-core-testing-bdd` | JBehave BDD story-based tests |
| `javaparser-symbol-solver-core` | Type/symbol resolution engine (reflection, Javassist, source-based) |
| `javaparser-symbol-solver-testing` | Tests for the symbol solver |

## MVEL-Specific Code

All MVEL/DRL AST nodes live under `javaparser-core/src/main/java/org/mvel3/parser/ast/`:

- **`expr/`** — MVEL expression nodes: `DrlxExpression`, `OOPathExpr`, `OOPathChunk`, `InlineCastExpr`, `PointFreeExpr`, `HalfBinaryExpr`, `NullSafeFieldAccessExpr`, `NullSafeMethodCallExpr`, `DrlNameExpr`, `BigDecimalLiteralExpr`, `BigIntegerLiteralExpr`, `TemporalLiteralExpr`, `ModifyStatement`, `WithStatement`, `MapCreationLiteralExpression`, `ListCreationLiteralExpression`, `RuleDeclaration`, and related types (~28 node classes)
- **`visitor/`** — `DrlVoidVisitor`, `DrlGenericVisitor` (extend standard visitors), `DrlVoidVisitorAdapter`, `DrlGenericVisitorWithDefaults`, `DrlCloneVisitor`

## Visitor Pattern Design

Two-tier visitor integration:

1. **Simple/universal MVEL nodes** (`InlineCastExpr`, `BigDecimalLiteralExpr`, `BigIntegerLiteralExpr`) are added directly to the standard `GenericVisitor`/`VoidVisitor` interfaces — all visitor implementations must handle them.

2. **DRL-specific nodes** (`DrlxExpression`, `OOPathExpr`, `PointFreeExpr`, `RuleDeclaration`, etc.) are behind extension interfaces `DrlGenericVisitor`/`DrlVoidVisitor`. The `DrlVoidVisitorAdapter` wraps any standard visitor to make it safe on MVEL-containing trees.

## Adding a New MVEL AST Node

1. Create the node class in `org.mvel3.parser.ast.expr`
2. Add it to `ALL_NODE_CLASSES` in `MetaModelGenerator.java`
3. Run `./run_core_metamodel_generator.sh`
4. Run `./run_core_generators.sh`
5. Manually add `visit()` methods to `DrlVoidVisitor`, `DrlGenericVisitor`, `DrlCloneVisitor`, `DrlVoidVisitorAdapter`, and `DrlGenericVisitorWithDefaults`

## Key Conventions

- Checkstyle config: `dev-files/JavaParser-CheckStyle.xml`
- The JavaCC grammar file is at `javaparser-core/src/main/javacc/java.jj` (~5800 lines). MVEL grammar rules are **not** in this file — MVEL nodes are constructed programmatically or via external tooling (`drlx-parser` project).
- Tests use JUnit 5 (core-testing) and JBehave (core-testing-bdd). BDD stories are in `src/test/resources/**/*.story`.
- Java source level is 8 (for compatibility), but build requires JDK 17.
