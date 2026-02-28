package org.mvel3;

public class ExpressionTranspileException extends DynamisExpressionException {

    private final String nodeDescription;

    public ExpressionTranspileException(String message, String nodeDescription) {
        super(message);
        this.nodeDescription = nodeDescription;
    }

    public ExpressionTranspileException(String message, String nodeDescription, Throwable cause) {
        super(message, cause);
        this.nodeDescription = nodeDescription;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }
}
