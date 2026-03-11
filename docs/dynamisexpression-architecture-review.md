# DynamisExpression Architecture Boundary Ratification Review

Date: 2026-03-11

## 1. Intent and Scope

DynamisExpression should be a constrained expression subsystem that owns parsing, transpilation, and evaluation of expressions against explicitly provided data contexts.

DynamisExpression should own:
- expression parsing
- expression evaluation
- bounded evaluation contexts (map/list/pojo/with-root)
- scripting support utilities for expression compilation and execution
- deterministic rule evaluation semantics where callers require determinism
- query/predicate evaluation over caller-provided data

DynamisExpression should not own:
- execution authority over engine runtime lifecycle
- authoritative world mutation
- simulation control
- orchestration authority
- scripting authority itself

## 2. Repo Overview (Grounded)

Repository layout (`DynamisExpression/pom.xml`) is a 3-module reactor:
- `javaparser-mvel-main`: forked JavaParser stack with MVEL-specific AST support.
- `mvel-main`: core expression transpiler/compiler/runtime (`org.mvel3.*`).
- `mvel-benchmarks`: JMH compilation/evaluation benchmarks.

Major expression engine components in `mvel-main`:
- Front-door API: `MVEL`, `MVELBuilder`, `CompilerParameters`, `Evaluator`.
- Parse/transpile pipeline: `MVELTranspiler`, `MVELToJavaRewriter`, `TranspilerContext`, ANTLR parser components.
- Code generation/compile: `CompilationUnitGenerator`, `MVELCompiler`, `KieMemoryCompiler`, classfile emitter path.
- Runtime/class loading: `ClassManager` using hidden-class definition.

Public API/runtime integration points:
- Builder-based compile paths for map/list/pojo contexts.
- Convenience runtime path `MVEL.executeExpression(...)`.
- Imports/static imports are caller-configurable and used in rewrite/resolve paths.
- Benchmarks include usage patterns tied to expression-driven scripting workloads (e.g., “Chronicler pattern” in benchmark docs).

Evaluation context mechanisms:
- Explicit declaration-based variable binding.
- Context extraction from map/list indices/pojo getters.
- Assignment rewrite support back to map/list/pojo setters.

## 3. Strict Ownership Statement

DynamisExpression should exclusively own:
- expression grammar parsing and AST handling
- expression-to-Java transpilation and rewrite logic
- expression compilation strategy (classfile emit and/or in-memory javac)
- evaluator generation and invocation contracts
- evaluation-context binding mechanics (map/list/pojo/with-root)
- expression-level type resolution/coercion/overload handling
- safe runtime evaluation utilities and error/reporting surfaces

## 4. Explicit Non-Ownership

DynamisExpression must not own:
- authoritative world mutation or world-state truth
- ECS authority
- world lifecycle orchestration
- physics authority
- collision authority
- rendering/GPU authority
- AI decision authority
- gameplay execution authority
- persistence/session authority

Expressions must not be treated as an unrestricted engine execution surface.

## 5. Dependency Rules

Allowed dependency patterns:
- parser/transpiler libraries and AST/model dependencies (`javaparser-mvel`, ANTLR)
- caller-provided data models and evaluation contexts
- scripting interface layer usage where scripting owns execution policy
- explicit imports/static imports provided through bounded policies

Forbidden dependency patterns:
- direct dependencies on WorldEngine/ECS/Physics/Collision/LightEngine/GPU runtime internals
- direct simulation/world mutation APIs owned by other subsystems
- hidden access channels to engine internals outside declared evaluation context
- unrestricted import/method-execution surfaces in production embeddings

Repo-grounded observations:
- No direct dependencies on Dynamis world/simulation/render subsystems were found in module dependencies/import scans.
- Runtime supports dynamic class compilation/loading and method invocation based on provided imports/context, so embedding policy is the boundary-critical control.

## 6. Public vs Internal Boundary Assessment

Boundary split is partial and broad.

Findings:
- `mvel-main` exposes many implementation-level classes directly under public packages (`org.mvel3`, `org.mvel3.transpiler`, `org.mvel3.javacompiler`, `org.mvel3.compiler.classfile`, etc.).
- No separate API-only module/package boundary is enforced; compile pipeline internals are publicly reachable.
- Core API (`MVEL`, `Evaluator`, builder types) exists, but internal compiler/transpiler/runtime machinery is not strongly encapsulated.

Assessment: public API exists, but internal implementation leakage is significant; stricter API/internal partitioning would improve boundary hygiene.

## 7. Authority Leakage or Overlap

No direct engine-authority overlap was found with WorldEngine/ECS/Physics/Collision/Rendering subsystems at dependency level.

However, execution-surface risk exists:
- `MVEL.executeExpression(...)` provides direct compile-and-run convenience path.
- Context mutation is first-class (map/list updates and pojo setter rewrites).
- Method/static invocation resolution is available through imports/staticImports and rewrite/emit pipelines.
- Runtime compiles and loads generated classes dynamically (`ClassManager`, `defineHiddenClass`).

Implication:
- DynamisExpression itself does not directly mutate engine world state.
- But if host integrations expose powerful context objects/imports, expressions can execute side effects beyond pure evaluation.

This is a governance risk, not a direct dependency-layer leak.

## 8. Relationship Clarification

- Scripting:
  - DynamisExpression should be consumed by scripting as an expression evaluator.
  - DynamisExpression should return values/predicates/derived outputs.
  - DynamisExpression should not become a standalone scripting authority.

- WorldEngine:
  - DynamisExpression should consume bounded snapshots/data projections only.
  - DynamisExpression should not directly mutate world lifecycle/state.

- ECS:
  - DynamisExpression should consume bounded component views/projections.
  - DynamisExpression should not own ECS storage or lifecycle.

- Physics/Collision:
  - DynamisExpression should consume derived facts only when required.
  - DynamisExpression should not execute simulation/query authority directly.

- DynamisAI:
  - DynamisExpression may evaluate AI rule predicates over provided data.
  - DynamisExpression should not own AI planning/decision orchestration.

- Content systems:
  - DynamisExpression should consume authored expressions/rules.
  - Content ownership remains outside expression runtime.

- Event systems:
  - DynamisExpression should emit evaluation results for external handlers.
  - Event execution/routing authority remains outside expression runtime.

## 9. Ratification Result

**Boundary ratified with minor tightening recommended**.

Justification:
- Repository scope is correctly centered on expression parsing/transpilation/evaluation and does not directly depend on world/simulation/render authorities.
- Tightening is needed because current API/runtime surfaces can execute side-effecting logic if host integrations provide broad imports/context objects, making it possible to bypass intended scripting/engine policy boundaries.

## 10. Boundary Rules Going Forward

- Expressions must evaluate against bounded contexts only.
- Expressions must not directly mutate authoritative world state.
- Expression evaluation policy in production must constrain imports/static imports and callable surfaces.
- Engine mutation must occur through explicit scripting/runtime APIs, not ad hoc expression execution.
- DynamisExpression should expose a constrained API tier separate from compiler/runtime internals.
- Host integrations must avoid passing privileged engine internals directly into expression contexts.
- Deterministic evaluation modes should remain explicitly configurable and testable for rule-critical paths.
