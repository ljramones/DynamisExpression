package org.mvel3;

public class ExpressionParseException extends DynamisExpressionException {

    private final String expression;
    private final int line;
    private final int column;

    public ExpressionParseException(String message, String expression, int line, int column) {
        super(message);
        this.expression = expression;
        this.line = line;
        this.column = column;
    }

    public ExpressionParseException(String message, String expression, int line, int column, Throwable cause) {
        super(message, cause);
        this.expression = expression;
        this.line = line;
        this.column = column;
    }

    public String getExpression() {
        return expression;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
