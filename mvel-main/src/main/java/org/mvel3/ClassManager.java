package org.mvel3;

import org.mvel3.methodutils.MethodByteCodeExtractor;
import org.mvel3.methodutils.Murmur3F;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * This defines the classes. It ensures only one class exists for an equal set of blocks.
 */
public class ClassManager {
    private Map<String, Class<?>> classes;

    private Map<ClassEntry, ClassEntry> entries;

    private Supplier<MethodHandles.Lookup> lookupSupplier;

    public ClassManager() {
        this(() -> MethodHandles.lookup());
    }

    public ClassManager(Supplier<MethodHandles.Lookup> lookupSupplier) {
        this.lookupSupplier = lookupSupplier;
        this.classes = new ConcurrentHashMap<>();
        this.entries = new ConcurrentHashMap<>();
    }

    public <T> Class<T> getClass(String name) {
        return (Class<T>) classes.get(name);
    }

    public Map<String, Class<?>> getClasses() {
        return classes;
    }

    public Supplier<Lookup> getLookupSupplier() {
        return lookupSupplier;
    }

    public void define(Map<String, byte[]> byteCode) {
        for (Map.Entry<String, byte[]> entry : byteCode.entrySet()) {
            try {
                ClassEntry newEntry = new ClassEntry(entry.getKey(), entry.getValue());
                entries.computeIfAbsent(newEntry, e -> {
                    try {
                        MethodHandles.Lookup lookup = lookupSupplier.get();
                        Class<?> c = lookup.defineHiddenClass(entry.getValue(), true).lookupClass();
                        classes.put(entry.getKey(), c);
                    } catch (IllegalAccessException ex) {
                        throw new ExpressionCompileException(
                            "Failed to define hidden class '" + entry.getKey() + "': access denied",
                            null, ex.getMessage(), ex);
                    }
                    return e;
                });
            } catch (ExpressionCompileException e) {
                throw e;
            } catch (Exception e) {
                throw new ExpressionCompileException(
                    "Failed to define hidden class '" + entry.getKey() + "'",
                    null, e.getMessage(), e);
            }
        }
    }

    public static class ClassEntry {
        private String name;
        private byte[] bytes;

        private String byteCode;

        private byte[] hash;

        private int hashCode;

        public ClassEntry(String name, byte[] bytes) {
            this.name = name;
            this.bytes = bytes;

            // TODO in the future this should use JavaParser AST, to avoid the compilation steps.
            this.byteCode = MethodByteCodeExtractor.extract("eval", bytes);

            Murmur3F murmur = new Murmur3F();
            murmur.update(byteCode.getBytes());

            // This murmur hash provides better hashcodes and earlier exit of equals testing
            hash = murmur.getValueBytesBigEndian();
            hashCode = Arrays.hashCode(hash);
        }

        public String getName() {
            return name;
        }

        public byte[] getBytes() {
            return bytes;
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ClassEntry that = (ClassEntry) o;

            // Murmur hash comparison has high chance of uniqueness and should provide early exit if not equal
            if (!Arrays.equals(hash, that.hash)) {
                return false;
            }
            return byteCode.equals(that.byteCode);
        }

        @Override
        public String toString() {
            return "ClassEntry{" +
                   "name='" + name + '\'' +
                   '}';
        }
    }


}
