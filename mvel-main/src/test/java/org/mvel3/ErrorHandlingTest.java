package org.mvel3;

import org.junit.jupiter.api.Test;
import org.mvel3.parser.antlr4.Antlr4MvelParser;
import org.mvel3.test.TestClassManager;
import org.mvel3.transpiler.MVELTranspilerException;
import org.mvel3.transpiler.context.Declaration;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mvel3.MVELCompilerTest.getImports;

class ErrorHandlingTest {

    // 1. ExpressionParseException — invalid syntax
    @Test
    void parseError_carriesExpressionText() {
        String badExpression = "foo.getName( +++ bar";
        assertThatThrownBy(() -> Antlr4MvelParser.parseExpressionAsAntlrAST(badExpression))
            .isInstanceOf(ExpressionParseException.class)
            .satisfies(e -> {
                ExpressionParseException pe = (ExpressionParseException) e;
                assertThat(pe.getExpression()).isEqualTo(badExpression);
                assertThat(pe.getMessage()).contains("Parse error");
            });
    }

    // 2. MethodResolutionException — structured fields
    @Test
    void methodResolution_reportsClassAndMethodName() {
        MethodResolutionException ex = new MethodResolutionException("com.example.MyClass", "getNonExistent", 0);
        assertThat(ex.getClassName()).isEqualTo("com.example.MyClass");
        assertThat(ex.getMethodName()).isEqualTo("getNonExistent");
        assertThat(ex.getArgCount()).isEqualTo(0);
        assertThat(ex.getMessage()).contains("getNonExistent");
        assertThat(ex.getMessage()).contains("com.example.MyClass");
        assertThat(ex).isInstanceOf(ExpressionTranspileException.class);
        assertThat(ex).isInstanceOf(DynamisExpressionException.class);
    }

    // 3. ExpressionCompileException — javac failure carries diagnostics
    @Test
    void compileError_carriesDiagnostics() {
        assertThatThrownBy(() -> {
            Map<String, Type<?>> types = new HashMap<>();
            types.put("foo", Type.type(Foo.class));

            // nonExistentProperty doesn't exist on Foo — passes transpilation but fails javac
            MVEL.<Object>map(Declaration.from(types))
                .<String>out(String.class)
                .expression("foo.nonExistentProperty")
                .classManager(new TestClassManager())
                .imports(getImports())
                .compile();
        }).isInstanceOf(ExpressionCompileException.class)
          .satisfies(e -> {
              ExpressionCompileException ece = (ExpressionCompileException) e;
              assertThat(ece.getDiagnostics()).contains("cannot find symbol");
              assertThat(ece.getGeneratedSource()).isNotNull();
              assertThat(ece).isInstanceOf(DynamisExpressionException.class);
          });
    }

    // 4. ExpressionEvaluationException — wrong eval method
    @Test
    void evaluationError_wrongEvalMethod() {
        Map<String, Type<?>> types = new HashMap<>();
        types.put("x", Type.type(Integer.class));

        Map<String, Object> vars = new HashMap<>();
        vars.put("x", 42);

        Evaluator<Map<String, Object>, Void, Integer> evaluator = MVEL.<Object>map(Declaration.from(types))
            .<Integer>out(Integer.class)
            .expression("x")
            .classManager(new TestClassManager())
            .imports(getImports())
            .compile();

        // eval(C) should work
        assertThat(evaluator.eval(vars)).isEqualTo(42);

        // evalWith(W) should throw since this is a MAP evaluator
        assertThatThrownBy(() -> evaluator.evalWith(null))
            .isInstanceOf(ExpressionEvaluationException.class)
            .hasMessageContaining("does not implement evalWith");
    }

    // 5. Hierarchy — all exceptions extend DynamisExpressionException
    @Test
    void exceptionHierarchy_allExtendRoot() {
        assertThat(DynamisExpressionException.class).isAssignableFrom(ExpressionParseException.class);
        assertThat(DynamisExpressionException.class).isAssignableFrom(ExpressionTranspileException.class);
        assertThat(DynamisExpressionException.class).isAssignableFrom(ExpressionCompileException.class);
        assertThat(DynamisExpressionException.class).isAssignableFrom(ExpressionEvaluationException.class);
        assertThat(DynamisExpressionException.class).isAssignableFrom(MethodResolutionException.class);
        assertThat(DynamisExpressionException.class).isAssignableFrom(TypeResolutionException.class);
        assertThat(ExpressionTranspileException.class).isAssignableFrom(MethodResolutionException.class);
        assertThat(ExpressionTranspileException.class).isAssignableFrom(TypeResolutionException.class);
        assertThat(ExpressionCompileException.class).isAssignableFrom(
            org.mvel3.javacompiler.KieMemoryCompilerException.class);
    }

    // 6. MVELTranspilerException — backward compat
    @Test
    void transpilerException_isSubtypeOfTranspile() {
        assertThat(ExpressionTranspileException.class).isAssignableFrom(MVELTranspilerException.class);

        MVELTranspilerException ex = new MVELTranspilerException("test error");
        assertThat(ex).isInstanceOf(ExpressionTranspileException.class);
        assertThat(ex).isInstanceOf(DynamisExpressionException.class);
        assertThat(ex.getMessage()).isEqualTo("test error");
    }

    // 7. catch(DynamisExpressionException) catches all subtypes
    @Test
    void catchRoot_catchesAllSubtypes() {
        assertCaughtByRoot(new ExpressionParseException("test", "expr", 1, 1));
        assertCaughtByRoot(new ExpressionTranspileException("test", "node"));
        assertCaughtByRoot(new ExpressionCompileException("test", null, null));
        assertCaughtByRoot(new ExpressionEvaluationException("test"));
        assertCaughtByRoot(new MethodResolutionException("Class", "method", 0));
        assertCaughtByRoot(new TypeResolutionException("SomeType", null));
    }

    private void assertCaughtByRoot(DynamisExpressionException ex) {
        try {
            throw ex;
        } catch (DynamisExpressionException caught) {
            assertThat(caught).isSameAs(ex);
        }
    }
}
