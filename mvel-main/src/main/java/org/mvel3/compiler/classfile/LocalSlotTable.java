package org.mvel3.compiler.classfile;

import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.TypeKind;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tracks local variable name → (slot, type) mappings for Classfile API bytecode emission.
 * <p>
 * Slot 0 is always {@code this}, slot 1 is always {@code __context} (the eval parameter).
 * Long and double types consume two consecutive slots per JVM spec.
 */
public final class LocalSlotTable {

    private record Entry(int slot, Type type) {}

    private final Map<String, Entry> entries = new LinkedHashMap<>();
    private int nextSlot;

    /**
     * Create a slot table pre-populated with {@code this} (slot 0) and the context parameter (slot 1).
     *
     * @param contextParamName the parameter name (e.g. "__context", "map", "pojo")
     * @param contextType      the JavaParser Type of the context parameter
     */
    public LocalSlotTable(String contextParamName, Type contextType) {
        // slot 0: this (reference, 1 slot)
        nextSlot = 1;
        // slot 1: context parameter
        entries.put(contextParamName, new Entry(1, contextType));
        nextSlot = 2; // context is always a reference type (1 slot)
    }

    /**
     * Allocate a new local variable slot. Longs and doubles consume 2 slots.
     *
     * @return the allocated slot index
     */
    public int allocate(String name, Type type) {
        int slot = nextSlot;
        entries.put(name, new Entry(slot, type));
        nextSlot += ClassfileTypeUtils.slotSize(type);
        return slot;
    }

    /**
     * Look up the slot for a named variable.
     *
     * @throws IllegalArgumentException if the variable is not in the table
     */
    public int slot(String name) {
        Entry entry = entries.get(name);
        if (entry == null) {
            throw new IllegalArgumentException("Unknown variable: " + name);
        }
        return entry.slot;
    }

    /**
     * Look up the type for a named variable.
     *
     * @throws IllegalArgumentException if the variable is not in the table
     */
    public Type type(String name) {
        Entry entry = entries.get(name);
        if (entry == null) {
            throw new IllegalArgumentException("Unknown variable: " + name);
        }
        return entry.type;
    }

    /**
     * Check if a variable name exists in this table.
     */
    public boolean contains(String name) {
        return entries.containsKey(name);
    }

    /**
     * Emit the correct load instruction for the named variable based on its type.
     */
    public void loadVar(CodeBuilder code, String name) {
        Entry entry = entries.get(name);
        if (entry == null) {
            throw new IllegalArgumentException("Unknown variable: " + name);
        }
        TypeKind kind = ClassfileTypeUtils.toTypeKind(entry.type);
        code.loadLocal(kind, entry.slot);
    }

    /**
     * Emit the correct store instruction for the named variable based on its type.
     */
    public void storeVar(CodeBuilder code, String name) {
        Entry entry = entries.get(name);
        if (entry == null) {
            throw new IllegalArgumentException("Unknown variable: " + name);
        }
        TypeKind kind = ClassfileTypeUtils.toTypeKind(entry.type);
        code.storeLocal(kind, entry.slot);
    }

    /**
     * Get the TypeKind for a named variable.
     */
    public TypeKind typeKind(String name) {
        return ClassfileTypeUtils.toTypeKind(type(name));
    }

    /**
     * Get the next available slot index.
     */
    public int nextSlot() {
        return nextSlot;
    }
}
