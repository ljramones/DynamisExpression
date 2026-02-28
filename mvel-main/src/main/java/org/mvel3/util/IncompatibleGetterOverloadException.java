package org.mvel3.util;

import org.mvel3.MethodResolutionException;

public class IncompatibleGetterOverloadException extends MethodResolutionException {

    private static final long serialVersionUID = -2359365094676377091L;
    private Class<?> klass;
    private String oldName;
    private Class<?> oldType;
    private String newName;
    private Class<?> newType;

    public IncompatibleGetterOverloadException(Class<?> klass, String oldName, Class<?> oldType, String newName, Class<?> newType) {
        super(klass.getName(), oldName + " vs " + newName, 0);
        this.klass = klass;
        this.oldName = oldName;
        this.oldType = oldType;
        this.newName = newName;
        this.newType = newType;
    }

    public Class<?> getKlass() {
        return klass;
    }

    public String getOldName() {
        return oldName;
    }

    public Class<?> getOldType() {
        return oldType;
    }

    public String getNewName() {
        return newName;
    }

    public Class<?> getNewType() {
        return newType;
    }

}
