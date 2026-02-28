package org.mvel3.compiler.classfile;

import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.TypeKind;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.Map;

import static java.lang.constant.ConstantDescs.*;

/**
 * Utility methods for converting JavaParser types to Classfile API descriptors,
 * and for emitting boxing/unboxing/widening bytecode.
 */
public final class ClassfileTypeUtils {

    private ClassfileTypeUtils() {}

    // Primitive type descriptors
    private static final Map<String, ClassDesc> PRIMITIVE_DESCRIPTORS = Map.of(
        "int", CD_int,
        "long", CD_long,
        "double", CD_double,
        "float", CD_float,
        "boolean", CD_boolean,
        "byte", CD_byte,
        "char", CD_char,
        "short", CD_short
    );

    // Primitive name → boxed ClassDesc
    private static final Map<String, ClassDesc> BOXING_TYPES = Map.of(
        "int", CD_Integer,
        "long", CD_Long,
        "double", CD_Double,
        "float", CD_Float,
        "boolean", CD_Boolean,
        "byte", CD_Byte,
        "char", CD_Character,
        "short", CD_Short
    );

    // Boxed FQCN → primitive ClassDesc (for unboxing detection)
    private static final Map<String, ClassDesc> UNBOXING_TYPES = Map.ofEntries(
        Map.entry("java.lang.Integer", CD_int),
        Map.entry("java.lang.Long", CD_long),
        Map.entry("java.lang.Double", CD_double),
        Map.entry("java.lang.Float", CD_float),
        Map.entry("java.lang.Boolean", CD_boolean),
        Map.entry("java.lang.Byte", CD_byte),
        Map.entry("java.lang.Character", CD_char),
        Map.entry("java.lang.Short", CD_short),
        Map.entry("Integer", CD_int),
        Map.entry("Long", CD_long),
        Map.entry("Double", CD_double),
        Map.entry("Float", CD_float),
        Map.entry("Boolean", CD_boolean),
        Map.entry("Byte", CD_byte),
        Map.entry("Character", CD_char),
        Map.entry("Short", CD_short)
    );

    // Boxed FQCN → unbox method name
    private static final Map<String, String> UNBOX_METHOD_NAMES = Map.ofEntries(
        Map.entry("java.lang.Integer", "intValue"),
        Map.entry("java.lang.Long", "longValue"),
        Map.entry("java.lang.Double", "doubleValue"),
        Map.entry("java.lang.Float", "floatValue"),
        Map.entry("java.lang.Boolean", "booleanValue"),
        Map.entry("java.lang.Byte", "byteValue"),
        Map.entry("java.lang.Character", "charValue"),
        Map.entry("java.lang.Short", "shortValue"),
        Map.entry("Integer", "intValue"),
        Map.entry("Long", "longValue"),
        Map.entry("Double", "doubleValue"),
        Map.entry("Float", "floatValue"),
        Map.entry("Boolean", "booleanValue"),
        Map.entry("Byte", "byteValue"),
        Map.entry("Character", "charValue"),
        Map.entry("Short", "shortValue")
    );

    /**
     * Convert a JavaParser Type node to a ClassDesc.
     */
    public static ClassDesc toClassDesc(Type type) {
        if (type.isPrimitiveType()) {
            String name = type.asPrimitiveType().asString();
            ClassDesc desc = PRIMITIVE_DESCRIPTORS.get(name);
            if (desc != null) return desc;
        }
        if (type.isVoidType()) {
            return CD_void;
        }
        if (type.isClassOrInterfaceType()) {
            return classDescFromName(type.asClassOrInterfaceType().getNameWithScope());
        }
        if (type.isArrayType()) {
            ClassDesc component = toClassDesc(type.asArrayType().getComponentType());
            return component.arrayType();
        }
        return classDescFromName(type.asString());
    }

    /**
     * Convert a type name string (possibly FQ, possibly simple) to ClassDesc.
     */
    public static ClassDesc classDescFromName(String name) {
        ClassDesc prim = PRIMITIVE_DESCRIPTORS.get(name);
        if (prim != null) return prim;
        // Handle common well-known types by simple name
        return switch (name) {
            case "String", "java.lang.String" -> CD_String;
            case "Object", "java.lang.Object" -> CD_Object;
            case "Integer", "java.lang.Integer" -> CD_Integer;
            case "Long", "java.lang.Long" -> CD_Long;
            case "Double", "java.lang.Double" -> CD_Double;
            case "Float", "java.lang.Float" -> CD_Float;
            case "Boolean", "java.lang.Boolean" -> CD_Boolean;
            case "Byte", "java.lang.Byte" -> CD_Byte;
            case "Character", "java.lang.Character" -> CD_Character;
            case "Short", "java.lang.Short" -> CD_Short;
            case "Map", "java.util.Map" -> ClassDesc.of("java.util.Map");
            case "List", "java.util.List" -> ClassDesc.of("java.util.List");
            case "Void", "java.lang.Void" -> ClassDesc.of("java.lang.Void");
            default -> {
                if (name.contains(".")) {
                    yield ClassDesc.of(name);
                }
                // Simple name without package — assume java.lang
                yield ClassDesc.of("java.lang." + name);
            }
        };
    }

    /**
     * Get the TypeKind for a JavaParser primitive type.
     */
    public static TypeKind toTypeKind(Type type) {
        if (type.isPrimitiveType()) {
            return switch (type.asPrimitiveType().getType()) {
                case INT -> TypeKind.INT;
                case LONG -> TypeKind.LONG;
                case DOUBLE -> TypeKind.DOUBLE;
                case FLOAT -> TypeKind.FLOAT;
                case BOOLEAN -> TypeKind.BOOLEAN;
                case BYTE -> TypeKind.BYTE;
                case CHAR -> TypeKind.CHAR;
                case SHORT -> TypeKind.SHORT;
            };
        }
        return TypeKind.REFERENCE;
    }

    /**
     * True if this is a primitive type (int, long, double, float, boolean, byte, char, short).
     */
    public static boolean isPrimitive(Type type) {
        return type.isPrimitiveType();
    }

    /**
     * True if this is an integral type (int, long, short, byte, char).
     */
    public static boolean isIntegralType(Type type) {
        if (!type.isPrimitiveType()) return false;
        return switch (type.asPrimitiveType().getType()) {
            case INT, LONG, SHORT, BYTE, CHAR -> true;
            default -> false;
        };
    }

    /**
     * True if this is a floating-point type (float, double).
     */
    public static boolean isFloatingType(Type type) {
        if (!type.isPrimitiveType()) return false;
        return switch (type.asPrimitiveType().getType()) {
            case FLOAT, DOUBLE -> true;
            default -> false;
        };
    }

    /**
     * True if this type is a known boxed wrapper type.
     */
    public static boolean isBoxedType(String typeName) {
        return UNBOXING_TYPES.containsKey(typeName);
    }

    /**
     * Emit widening conversion from one primitive type to another.
     * Returns true if a widening was emitted, false if the types are the same.
     */
    public static boolean emitWidening(CodeBuilder code, PrimitiveType.Primitive from, PrimitiveType.Primitive to) {
        if (from == to) return false;
        switch (from) {
            case INT, SHORT, BYTE, CHAR -> {
                switch (to) {
                    case LONG -> code.i2l();
                    case FLOAT -> code.i2f();
                    case DOUBLE -> code.i2d();
                    default -> { return false; }
                }
            }
            case LONG -> {
                switch (to) {
                    case FLOAT -> code.l2f();
                    case DOUBLE -> code.l2d();
                    default -> { return false; }
                }
            }
            case FLOAT -> {
                if (to == PrimitiveType.Primitive.DOUBLE) {
                    code.f2d();
                } else {
                    return false;
                }
            }
            default -> { return false; }
        }
        return true;
    }

    /**
     * Emit boxing: primitive value on stack → boxed wrapper object on stack.
     * E.g. int → Integer.valueOf(int)
     */
    public static void emitBoxing(CodeBuilder code, Type primitiveType) {
        if (!primitiveType.isPrimitiveType()) return;
        String primName = primitiveType.asPrimitiveType().asString();
        ClassDesc boxedDesc = BOXING_TYPES.get(primName);
        ClassDesc primDesc = PRIMITIVE_DESCRIPTORS.get(primName);
        if (boxedDesc == null || primDesc == null) return;
        code.invokestatic(boxedDesc, "valueOf", MethodTypeDesc.of(boxedDesc, primDesc));
    }

    /**
     * Emit boxing for a primitive TypeKind.
     */
    public static void emitBoxing(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.invokestatic(CD_Integer, "valueOf", MethodTypeDesc.of(CD_Integer, CD_int));
            case LONG -> code.invokestatic(CD_Long, "valueOf", MethodTypeDesc.of(CD_Long, CD_long));
            case DOUBLE -> code.invokestatic(CD_Double, "valueOf", MethodTypeDesc.of(CD_Double, CD_double));
            case FLOAT -> code.invokestatic(CD_Float, "valueOf", MethodTypeDesc.of(CD_Float, CD_float));
            case BOOLEAN -> code.invokestatic(CD_Boolean, "valueOf", MethodTypeDesc.of(CD_Boolean, CD_boolean));
            case BYTE -> code.invokestatic(CD_Byte, "valueOf", MethodTypeDesc.of(CD_Byte, CD_byte));
            case CHAR -> code.invokestatic(CD_Character, "valueOf", MethodTypeDesc.of(CD_Character, CD_char));
            case SHORT -> code.invokestatic(CD_Short, "valueOf", MethodTypeDesc.of(CD_Short, CD_short));
            default -> {} // reference types: no boxing needed
        }
    }

    /**
     * Emit unboxing: boxed wrapper on stack → primitive value on stack.
     * E.g. Integer on stack → intValue() → int on stack.
     */
    public static void emitUnboxing(CodeBuilder code, String boxedTypeName) {
        ClassDesc primDesc = UNBOXING_TYPES.get(boxedTypeName);
        String methodName = UNBOX_METHOD_NAMES.get(boxedTypeName);
        if (primDesc == null || methodName == null) return;
        ClassDesc boxedDesc = BOXING_TYPES.values().stream()
            .filter(bd -> {
                // Match by looking up the unbox method's return type
                String pName = primNameForBoxed(boxedTypeName);
                return pName != null && BOXING_TYPES.get(pName) == bd;
            })
            .findFirst()
            .orElse(classDescFromName(boxedTypeName));
        code.invokevirtual(classDescFromName(boxedTypeName), methodName, MethodTypeDesc.of(primDesc));
    }

    /**
     * Emit unboxing for a known cast: checkcast to boxed type, then call xxxValue().
     */
    public static void emitCheckcastAndUnbox(CodeBuilder code, String boxedTypeName) {
        ClassDesc boxedClassDesc = classDescFromName(boxedTypeName);
        code.checkcast(boxedClassDesc);
        emitUnboxing(code, boxedTypeName);
    }

    /**
     * Get the primitive type name for a boxed type name (e.g. "java.lang.Integer" → "int").
     */
    public static String primNameForBoxed(String boxedTypeName) {
        return switch (boxedTypeName) {
            case "Integer", "java.lang.Integer" -> "int";
            case "Long", "java.lang.Long" -> "long";
            case "Double", "java.lang.Double" -> "double";
            case "Float", "java.lang.Float" -> "float";
            case "Boolean", "java.lang.Boolean" -> "boolean";
            case "Byte", "java.lang.Byte" -> "byte";
            case "Character", "java.lang.Character" -> "char";
            case "Short", "java.lang.Short" -> "short";
            default -> null;
        };
    }

    /**
     * Determine the wider type for binary arithmetic between two primitives.
     * Rules: double > float > long > int (byte/short/char widen to int).
     */
    public static PrimitiveType.Primitive widenedType(PrimitiveType.Primitive left, PrimitiveType.Primitive right) {
        if (left == PrimitiveType.Primitive.DOUBLE || right == PrimitiveType.Primitive.DOUBLE) {
            return PrimitiveType.Primitive.DOUBLE;
        }
        if (left == PrimitiveType.Primitive.FLOAT || right == PrimitiveType.Primitive.FLOAT) {
            return PrimitiveType.Primitive.FLOAT;
        }
        if (left == PrimitiveType.Primitive.LONG || right == PrimitiveType.Primitive.LONG) {
            return PrimitiveType.Primitive.LONG;
        }
        return PrimitiveType.Primitive.INT;
    }

    /**
     * Get the slot size for a type (2 for long/double, 1 for everything else).
     */
    public static int slotSize(Type type) {
        if (type.isPrimitiveType()) {
            return switch (type.asPrimitiveType().getType()) {
                case LONG, DOUBLE -> 2;
                default -> 1;
            };
        }
        return 1;
    }
}
