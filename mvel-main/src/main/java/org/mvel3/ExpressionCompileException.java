package org.mvel3;

public class ExpressionCompileException extends DynamisExpressionException {

    private final String generatedSource;
    private final String diagnostics;

    public ExpressionCompileException(String message, String generatedSource, String diagnostics) {
        super(message);
        this.generatedSource = generatedSource;
        this.diagnostics = diagnostics;
    }

    public ExpressionCompileException(String message, String generatedSource, String diagnostics, Throwable cause) {
        super(message, cause);
        this.generatedSource = generatedSource;
        this.diagnostics = diagnostics;
    }

    public String getGeneratedSource() {
        return generatedSource;
    }

    public String getDiagnostics() {
        return diagnostics;
    }
}
