# javaparser-mvel / mvel3 Relationship Analysis

## Overview

This is a fork of [JavaParser](https://github.com/javaparser/javaparser) (v3.25.5) that adds ~28 MVEL3/DRL-specific AST node types. It is a hard dependency of [mvel3](https://github.com/mvel/mvel) (`../mvel-main`), which transpiles MVEL expressions into Java source code.

## How mvel3 Uses javaparser-mvel

javaparser-mvel is **not** lightly used — it is the **core IR (intermediate representation)** for the entire mvel3 transpiler. There is no separate MVEL AST. The pipeline:

```
MVEL source → ANTLR4 parse tree → javaparser AST (with MVEL nodes) → rewrite to pure Java AST → print to Java source → javac compile → bytecode
```

- **48 Java files** in mvel3 import from `com.github.javaparser`
- **~160 unique import statements** spanning parsing, transpilation, printing, lambda extraction, and type solving
- `Mvel3ToJavaParserVisitor` alone is 3,333 lines constructing javaparser nodes

## MVEL AST Nodes: What's Used vs. What Isn't

### Actively Used in Transpilation

These nodes have a full pipeline path (parse → rewrite → output):

| Node | What mvel3 Does With It |
|------|------------------------|
| `BigDecimalLiteralExpr` | Transpiled to `BigDecimal.valueOf(...)` or `new BigDecimal(...)` |
| `BigIntegerLiteralExpr` | Transpiled to `BigInteger.valueOf(...)` |
| `DrlNameExpr` | Used in variable analysis to detect used variables |
| `InlineCastExpr` | Rewritten to standard Java casts |
| `ListCreationLiteralExpression` | Rewritten to `Arrays.asList(...)` or `Collections.emptyList()` |
| `ListCreationLiteralExpressionElement` | Unwrapped during transpilation |
| `MapCreationLiteralExpression` | Rewritten to `Map.of(...)` or `Collections.emptyMap()` |
| `MapCreationLiteralExpressionKeyValuePair` | Unwrapped during transpilation |
| `NullSafeFieldAccessExpr` | Rewritten to null-safe conditional |
| `NullSafeMethodCallExpr` | Rewritten to null-safe conditional |
| `TemporalLiteralExpr` | Rewritten to `Duration.ofHours(3).plusMinutes(30)` etc. |
| `TemporalLiteralChunkExpr` | Carries value and TimeUnit within TemporalLiteralExpr |
| `TemporalChunkExpr` | Base type for temporal chunks |
| `ModifyStatement` | Rewritten to flat statement block + `update(obj)` call |
| `WithStatement` | Rewritten to flat statement block (no `update()`) |
| `AbstractContextStatement` | Supertype for ModifyStatement/WithStatement |

### NOT Followed Through (No Transpilation Path)

These exist in javaparser-mvel but are only used in `MVELPrintVisitor`, legacy JavaCC utilities, or parser tests — they have **no functional transpilation**:

| Node | Where It Appears | Why It Exists |
|------|-----------------|---------------|
| `RuleDeclaration` | `DrlToJavaRewriter` (stubbed), `MVELPrintVisitor` | Drools rule engine |
| `RuleBody` | `MVELPrintVisitor` only | Drools rule engine |
| `RuleConsequence` | `MVELPrintVisitor` only | Drools rule engine |
| `RulePattern` | `MVELPrintVisitor` only | Drools rule engine |
| `RuleJoinedPatterns` | `MVELPrintVisitor` only | Drools rule engine |
| `DrlxExpression` | Legacy JavaCC utilities, `MVELPrintVisitor` | Drools DRL bindings |
| `OOPathExpr` | Parser tests only | Drools OO-path navigation |
| `OOPathChunk` | Parser tests only | Drools OO-path navigation |
| `PointFreeExpr` | Parser tests, print visitor | Drools temporal operators |
| `HalfPointFreeExpr` | Parser tests only | Drools constraint syntax |
| `HalfBinaryExpr` | `AstUtils` (JavaCC), print visitor | Drools constraint syntax |
| `FullyQualifiedInlineCastExpr` | `MVELPrintVisitor` only | Variant of InlineCastExpr |
| `TemporalLiteralInfiniteChunkExpr` | `MVELPrintVisitor` only | Infinite temporal value |

### Visitor Infrastructure

| Type | Used By |
|------|---------|
| `DrlVoidVisitor` | Implemented by `MVELPrintVisitor` |
| `DrlVoidVisitorAdapter` | Extended by `VariableAnalyser` |
| `DrlCloneVisitor` | Mostly stubbed (returns `null` for most MVEL nodes) |

## Key mvel3 Classes That Depend on javaparser

| Class | Lines | Role |
|-------|-------|------|
| `Mvel3ToJavaParserVisitor` | 3,333 | ANTLR4 parse tree → javaparser AST construction |
| `MVELToJavaRewriter` | 1,614 | Rewrites MVEL AST nodes into standard Java AST |
| `MVELPrintVisitor` | 978 | Extends javaparser's `DefaultPrettyPrinterVisitor`, prints MVEL nodes |
| `MVELTranspiler` | — | Configures parser, delegates to `MvelParser`, wraps results |
| `TranspilerContext` | — | Holds `TypeSolver`, `JavaSymbolSolver`, `JavaParserFacade`, working `CompilationUnit` |
| `CompilationUnitGenerator` / `MVELCompiler` | — | Assembles output `CompilationUnit` before handing to javac |
| `LambdaUtils` / `VariableNameNormalizerVisitor` | — | Uses `StaticJavaParser` and `ModifierVisitor` for lambda normalization |

## The Real Story

This was built by the **Drools team** (Toshiya Kobayashi at Red Hat is the common developer on both projects). javaparser-mvel was originally created for the **Drools rule engine** (`drlx-parser` project), which needed all the rule/DRL-specific nodes. When mvel3 was started as a standalone expression language transpiler, they reused the same fork but only needed the expression-level nodes.

The rule-related nodes (`RuleDeclaration`, `OOPathExpr`, `PointFreeExpr`, etc.) were never meant for mvel3 — they're Drools baggage that comes along because mvel3 depends on the whole javaparser-mvel fork.

## Assessment of javaparser-mvel Itself

### Strengths
- Solid foundation (mature upstream JavaParser project)
- Clean separation of MVEL nodes under `org.mvel3.parser.ast`
- Metamodel + code generator pipeline eliminates boilerplate
- Good CI (Ubuntu, macOS, Windows with JDK 17)

### Weaknesses
- `DrlCloneVisitor` is mostly stubbed — deep cloning MVEL trees silently produces broken results
- Fork maintenance burden — pinned to JavaParser 3.25.5, upstream continues to evolve
- No MVEL grammar in the JavaCC file — MVEL nodes are constructed programmatically, not parsed
- JDK constraint is narrow (only 17 works; POM says 11–17 but MVEL3 needs 17, tests fail on 21)
- `RuleDeclaration` is explicitly "not tested in mvel3 project" per its own source comment
- Java source level 8 with JDK 17 build — prevents using modern Java features
- Test coverage for MVEL nodes is thin within this repo

### The Drools Umbilical Cord

Both packages carry vestiges of the Drools ecosystem:
- **javaparser-mvel**: ~13 unused DRL/rule AST node types
- **mvel3**: `drools-compiler` dependency (POM itself says "likely out of this project scope"), `DrlToJavaRewriter` partially implemented, `VariableAnalyser` and `MVELPrintVisitor` extend Drools interfaces
