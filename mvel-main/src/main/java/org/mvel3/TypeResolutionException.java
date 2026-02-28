package org.mvel3;

public class TypeResolutionException extends ExpressionTranspileException {

    private final String typeName;

    public TypeResolutionException(String typeName, Throwable cause) {
        super("Unable to resolve type '" + typeName + "'", typeName, cause);
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
