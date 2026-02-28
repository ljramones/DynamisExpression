# Repository Guidelines

## Project Structure & Module Organization
- Core sources live in `src/main/java/org/mvel3`.
- Parser grammars are in `src/main/antlr4/org/mvel3/parser/antlr4` (primary) and `src/main/javacc` (legacy).
- JavaCC support classes are in `src/main/javacc-support`.
- Runtime templates are in `src/main/resources/org/mvel3`.
- Tests live under `src/test/java` and test resources under `src/test/resources`.
- Generated parser sources are written to `target/generated-sources/*` during Maven builds.

## Build, Test, and Development Commands
- `mvn clean install` - full build and test run; produces installable artifacts.
- `mvn clean verify` - CI-equivalent validation (used in GitHub Actions).
- `mvn clean compile` - compile only, including ANTLR4/JavaCC source generation.
- `mvn test` - run all unit tests.
- `mvn test -Dtest=MVELCompilerTest` - run one test class.
- `mvn test -Dtest=MVELCompilerTest#testMapEvaluator` - run one test method.

Prereq: install `javaparser-mvel` locally before building this repository.

## Coding Style & Naming Conventions
- Java 17 is required; use 4-space indentation and UTF-8 sources.
- Follow existing package conventions (`org.mvel3.*`) and keep classes focused by layer (`parser`, `transpiler`, `javacompiler`, etc.).
- Use `UpperCamelCase` for classes, `lowerCamelCase` for methods/fields, and descriptive test names like `testMapEvaluatorWithGenerics`.
- Prefer clear, explicit method names over abbreviations in compiler/transpiler paths.

## Testing Guidelines
- Frameworks: JUnit 5 (Jupiter) + AssertJ.
- Surefire includes `**/*Test.java` and `**/*Tests.java`; keep test file names aligned.
- Add tests with each parser/transpiler behavior change, including regression coverage for rewritten expressions.
- Before opening a PR, run `mvn clean verify` locally.

## Commit & Pull Request Guidelines
- This workspace snapshot does not include `.git` history; use concise, imperative commit subjects (for example, `Fix null-safe rewrite for list access`) and include scope when useful.
- Keep commits logically small and buildable.
- PRs should include: problem statement, approach summary, test evidence (`mvn clean verify` output), and linked issues.
- For parser or transpiler changes, include before/after expression examples in the PR description.
