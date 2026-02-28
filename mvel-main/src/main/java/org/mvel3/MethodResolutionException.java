package org.mvel3;

public class MethodResolutionException extends ExpressionTranspileException {

    private final String className;
    private final String methodName;
    private final int argCount;

    public MethodResolutionException(String className, String methodName, int argCount) {
        super("No method '" + methodName + "' with " + argCount + " parameter(s) found on type '" + className + "'",
              className + "." + methodName);
        this.className = className;
        this.methodName = methodName;
        this.argCount = argCount;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getArgCount() {
        return argCount;
    }
}
