package org.mvel3.compiler.classfile;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import org.mvel3.CompilerParameters;
import org.mvel3.ContextType;
import org.mvel3.transpiler.TranspiledResult;

import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.classfile.TypeKind;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;
import java.util.List;
import java.util.Set;

import static java.lang.constant.ConstantDescs.*;

/**
 * Emits evaluator classes as bytecode using the JDK 25 Classfile API,
 * bypassing javac entirely for supported expression patterns.
 * <p>
 * Phase 1 supports: predicate expressions (comparisons, arithmetic, boolean logic,
 * field access via getters, method calls, literals, string concatenation).
 * Unsupported expressions fall back to the javac pipeline.
 */
public final class ClassfileEvaluatorEmitter {

    private ClassfileEvaluatorEmitter() {}

    private static final ClassDesc CD_Evaluator = ClassDesc.of("org.mvel3.Evaluator");
    private static final ClassDesc CD_Map = ClassDesc.of("java.util.Map");
    private static final ClassDesc CD_List = ClassDesc.of("java.util.List");

    /**
     * Check whether the transpiled method body can be emitted directly as bytecode.
     * <p>
     * Phase 1 is conservative: only emits when ALL variables are primitive-typed
     * (avoiding boxed→primitive auto-unboxing in arithmetic contexts) and the
     * method body contains only supported statement/expression types.
     */
    public static boolean canEmit(TranspiledResult result) {
        MethodDeclaration method = result.getUnit()
                .findFirst(MethodDeclaration.class)
                .orElse(null);
        if (method == null || method.getBody().isEmpty()) return false;

        BlockStmt body = method.getBody().get();

        // Must have at least one return statement (expressions always have a return)
        boolean hasReturn = false;
        for (Statement stmt : body.getStatements()) {
            if (!isSupportedStatement(stmt)) return false;
            if (stmt instanceof ReturnStmt) hasReturn = true;
        }
        return hasReturn;
    }

    private static boolean isStringType(Type type) {
        if (type.isClassOrInterfaceType()) {
            String name = type.asClassOrInterfaceType().getNameWithScope();
            return "String".equals(name) || "java.lang.String".equals(name);
        }
        return false;
    }

    /**
     * Emit a complete evaluator class as bytecode.
     *
     * @param params the compiler parameters (context type, declarations, output type, etc.)
     * @param result the transpiled result containing the rewritten JavaParser AST
     * @return byte[] suitable for {@code ClassManager.define()} or {@code defineHiddenClass()}
     */
    public static <C, W, O> byte[] emit(CompilerParameters<C, W, O> params, TranspiledResult result) {
        MethodDeclaration method = result.getUnit()
                .findFirst(MethodDeclaration.class)
                .orElseThrow(() -> new IllegalStateException("No method in transpiled result"));

        String packageName = result.getUnit().getPackageDeclaration()
                .map(pd -> pd.getNameAsString())
                .orElseThrow(() -> new IllegalStateException("No package in transpiled result"));

        String className = result.getUnit()
                .findFirst(com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class)
                .map(c -> c.getNameAsString())
                .orElseThrow(() -> new IllegalStateException("No class in transpiled result"));

        String fqcn = packageName + "." + className;
        ClassDesc thisClass = ClassDesc.of(fqcn);

        // Determine context and output ClassDescs
        ClassDesc contextDesc = classDescForType(params.contextDeclaration().type());
        ClassDesc outputDesc = classDescForType(params.outType());

        // Parse the eval method parameter type from the AST
        Parameter evalParam = method.getParameter(0);
        Type paramType = evalParam.getType();
        ClassDesc paramDesc = ClassfileTypeUtils.toClassDesc(paramType);

        // Parse the return type from the AST
        Type returnType = method.getType();
        ClassDesc returnDesc = ClassfileTypeUtils.toClassDesc(returnType);

        // Build the method type descriptor: (paramType) -> returnType
        MethodTypeDesc evalMethodType = MethodTypeDesc.of(returnDesc, paramDesc);

        BlockStmt body = method.getBody().get();

        return ClassFile.of().build(thisClass, cb -> {
            cb.withFlags(AccessFlag.PUBLIC, AccessFlag.FINAL);
            cb.withSuperclass(CD_Object);
            cb.withInterfaceSymbols(CD_Evaluator);

            // No-arg constructor: invokespecial Object.<init>()V; return
            cb.withMethodBody(
                    INIT_NAME, // "<init>"
                    MTD_void,  // ()V
                    ClassFile.ACC_PUBLIC,
                    code -> {
                        code.aload(0);
                        code.invokespecial(CD_Object, INIT_NAME, MTD_void);
                        code.return_();
                    }
            );

            // eval method with concrete types
            cb.withMethodBody(
                    method.getNameAsString(),
                    evalMethodType,
                    ClassFile.ACC_PUBLIC,
                    code -> emitMethodBody(code, body, params, paramType)
            );

            // Bridge method: eval(Object) → eval(ConcreteType) for type erasure
            if (!paramDesc.equals(CD_Object)) {
                MethodTypeDesc bridgeType = MethodTypeDesc.of(CD_Object, CD_Object);
                cb.withMethodBody(
                        method.getNameAsString(),
                        bridgeType,
                        ClassFile.ACC_PUBLIC | ClassFile.ACC_BRIDGE | ClassFile.ACC_SYNTHETIC,
                        code -> {
                            code.aload(0); // this
                            code.aload(1); // Object arg
                            code.checkcast(paramDesc);
                            code.invokevirtual(thisClass, method.getNameAsString(), evalMethodType);
                            code.areturn();
                        }
                );
            }
        });
    }

    // ── Method body emission ──────────────────────────────────────────────

    private static <C, W, O> void emitMethodBody(
            CodeBuilder code,
            BlockStmt body,
            CompilerParameters<C, W, O> params,
            Type contextParamType) {

        // Build slot table: slot 0 = this, slot 1 = __context
        String contextParamName = params.contextDeclaration().name();
        LocalSlotTable slots = new LocalSlotTable(contextParamName, contextParamType);

        // Determine the method's return type for proper boxing at return sites
        Class<?> outClass = params.outType().getClazz();

        List<Statement> stmts = body.getStatements();

        for (Statement stmt : stmts) {
            emitStatement(code, stmt, slots, params, outClass);
        }
    }

    // ── Statement emission ────────────────────────────────────────────────

    private static <C, W, O> void emitStatement(
            CodeBuilder code,
            Statement stmt,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params,
            Class<?> outClass) {

        switch (stmt) {
            case ReturnStmt rs -> emitReturnStmt(code, rs, slots, params, outClass);
            case ExpressionStmt es -> emitExpressionStmt(code, es, slots, params);
            default -> throw new UnsupportedOperationException(
                    "Unsupported statement type: " + stmt.getClass().getSimpleName());
        }
    }

    private static <C, W, O> void emitReturnStmt(
            CodeBuilder code,
            ReturnStmt rs,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params,
            Class<?> outClass) {

        if (rs.getExpression().isEmpty()) {
            code.return_();
            return;
        }

        Expression expr = rs.getExpression().get();
        emitExpression(code, expr, slots, params);

        // The concrete method signature returns the boxed type (e.g. Boolean, Integer).
        // If the expression left a primitive on the stack, box it to match the return type.
        TypeKind exprKind = inferTypeKind(expr, slots);
        if (exprKind == TypeKind.REFERENCE) {
            code.areturn();
        } else {
            // Box using the method's declared return type, not the expression type.
            // E.g. comparison expr yields int on stack, but return type is Boolean → Boolean.valueOf(boolean)
            emitBoxingForReturnType(code, exprKind, outClass);
            code.areturn();
        }
    }

    /**
     * Box the primitive value on the stack to match the method's return type.
     * E.g. if stack has int and return type is Boolean, emit Boolean.valueOf(boolean).
     */
    private static void emitBoxingForReturnType(CodeBuilder code, TypeKind stackKind, Class<?> returnClass) {
        // Determine what boxed type the return class expects
        if (returnClass == Boolean.class) {
            // Stack has int (boolean representation), box to Boolean
            code.invokestatic(CD_Boolean, "valueOf", MethodTypeDesc.of(CD_Boolean, CD_boolean));
        } else if (returnClass == Integer.class || returnClass == int.class) {
            code.invokestatic(CD_Integer, "valueOf", MethodTypeDesc.of(CD_Integer, CD_int));
        } else if (returnClass == Long.class || returnClass == long.class) {
            code.invokestatic(CD_Long, "valueOf", MethodTypeDesc.of(CD_Long, CD_long));
        } else if (returnClass == Double.class || returnClass == double.class) {
            code.invokestatic(CD_Double, "valueOf", MethodTypeDesc.of(CD_Double, CD_double));
        } else if (returnClass == Float.class || returnClass == float.class) {
            code.invokestatic(CD_Float, "valueOf", MethodTypeDesc.of(CD_Float, CD_float));
        } else {
            // Generic fallback: box based on what's on the stack
            ClassfileTypeUtils.emitBoxing(code, stackKind);
        }
    }

    private static <C, W, O> void emitExpressionStmt(
            CodeBuilder code,
            ExpressionStmt es,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        Expression expr = es.getExpression();

        if (expr instanceof VariableDeclarationExpr vde) {
            // Variable declaration: Type varName = initializer;
            for (var declarator : vde.getVariables()) {
                Type varType = declarator.getType();
                String varName = declarator.getNameAsString();

                // Determine the slot type:
                // 1. Boxed wrappers (Integer, etc.) → unbox to primitive equivalent
                // 2. var (VarType) → infer from initializer expression
                // 3. Primitives/String → use as-is
                // 4. Other reference types (Foo, List, Map, etc.) → REFERENCE slot
                Type slotType = varType;
                boolean needsUnbox = false;

                if (ClassfileTypeUtils.isBoxedWrapperType(varType)) {
                    slotType = ClassfileTypeUtils.toPrimitiveType(varType);
                    needsUnbox = true;
                } else if (varType.isVarType() && declarator.getInitializer().isPresent()) {
                    // var: infer type from initializer
                    slotType = inferTypeFromExpression(declarator.getInitializer().get(), slots);
                    // If inferred as boxed wrapper, unbox
                    if (ClassfileTypeUtils.isBoxedWrapperType(slotType)) {
                        needsUnbox = true;
                        slotType = ClassfileTypeUtils.toPrimitiveType(slotType);
                    }
                }

                int slot = slots.allocate(varName, slotType);

                if (declarator.getInitializer().isPresent()) {
                    emitExpression(code, declarator.getInitializer().get(), slots, params);
                    if (needsUnbox) {
                        String boxedName = varType.isVarType()
                                ? inferBoxedNameFromExpression(declarator.getInitializer().get())
                                : varType.asClassOrInterfaceType().getNameWithScope();
                        ClassfileTypeUtils.emitUnboxing(code, boxedName);
                    }
                    code.storeLocal(ClassfileTypeUtils.toTypeKind(slotType), slot);
                }
            }
        } else if (expr instanceof AssignExpr ae) {
            emitAssignExpr(code, ae, slots, params);
        } else {
            // Expression statement whose value is discarded
            emitExpression(code, expr, slots, params);
            // pop the result if needed — for now, most expression stmts are var decls
        }
    }

    // ── Expression emission (Commits 3-4 will expand this) ────────────────

    static <C, W, O> void emitExpression(
            CodeBuilder code,
            Expression expr,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        switch (expr) {
            case IntegerLiteralExpr ile -> emitIntLiteral(code, ile);
            case LongLiteralExpr lle -> emitLongLiteral(code, lle);
            case DoubleLiteralExpr dle -> emitDoubleLiteral(code, dle);
            case BooleanLiteralExpr ble -> emitBooleanLiteral(code, ble);
            case StringLiteralExpr sle -> code.ldc(sle.getValue());
            case NullLiteralExpr _ -> code.aconst_null();
            case NameExpr ne -> emitNameExpr(code, ne, slots);
            case EnclosedExpr ee -> emitExpression(code, ee.getInner(), slots, params);
            case CastExpr ce -> emitCastExpr(code, ce, slots, params);
            case UnaryExpr ue -> emitUnaryExpr(code, ue, slots, params);
            case BinaryExpr be -> emitBinaryExpr(code, be, slots, params);
            case MethodCallExpr mce -> emitMethodCallExpr(code, mce, slots, params);
            case FieldAccessExpr fae -> emitFieldAccessExpr(code, fae, slots, params);
            default -> throw new UnsupportedOperationException(
                    "Unsupported expression type: " + expr.getClass().getSimpleName()
                    + " — " + expr);
        }
    }

    // ── Literal emission ──────────────────────────────────────────────────

    private static void emitIntLiteral(CodeBuilder code, IntegerLiteralExpr ile) {
        int value = ile.asNumber().intValue();
        emitIntConstant(code, value);
    }

    static void emitIntConstant(CodeBuilder code, int value) {
        switch (value) {
            case -1 -> code.iconst_m1();
            case 0 -> code.iconst_0();
            case 1 -> code.iconst_1();
            case 2 -> code.iconst_2();
            case 3 -> code.iconst_3();
            case 4 -> code.iconst_4();
            case 5 -> code.iconst_5();
            default -> {
                if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
                    code.bipush(value);
                } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                    code.sipush(value);
                } else {
                    code.ldc(value);
                }
            }
        }
    }

    private static void emitLongLiteral(CodeBuilder code, LongLiteralExpr lle) {
        String text = lle.getValue();
        // Strip trailing L/l
        if (text.endsWith("L") || text.endsWith("l")) {
            text = text.substring(0, text.length() - 1);
        }
        long value = Long.parseLong(text);
        if (value == 0L) {
            code.lconst_0();
        } else if (value == 1L) {
            code.lconst_1();
        } else {
            code.ldc(value);
        }
    }

    private static void emitDoubleLiteral(CodeBuilder code, DoubleLiteralExpr dle) {
        String text = dle.getValue();
        // Strip trailing D/d/F/f if present
        if (text.endsWith("D") || text.endsWith("d") || text.endsWith("F") || text.endsWith("f")) {
            text = text.substring(0, text.length() - 1);
        }
        double value = Double.parseDouble(text);
        if (value == 0.0) {
            code.dconst_0();
        } else if (value == 1.0) {
            code.dconst_1();
        } else {
            code.ldc(value);
        }
    }

    private static void emitBooleanLiteral(CodeBuilder code, BooleanLiteralExpr ble) {
        code.ldc(ble.getValue() ? 1 : 0);
    }

    // ── Variable access ───────────────────────────────────────────────────

    private static void emitNameExpr(CodeBuilder code, NameExpr ne, LocalSlotTable slots) {
        String name = ne.getNameAsString();
        if (slots.contains(name)) {
            slots.loadVar(code, name);
        } else {
            throw new UnsupportedOperationException("Unknown variable: " + name);
        }
    }

    // ── Cast expression ───────────────────────────────────────────────────

    private static <C, W, O> void emitCastExpr(
            CodeBuilder code,
            CastExpr ce,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        emitExpression(code, ce.getExpression(), slots, params);

        Type targetType = ce.getType();
        if (targetType.isPrimitiveType()) {
            // Unbox: assume stack has boxed type, checkcast + unbox
            String boxedName = boxedNameForPrimitive(targetType.asPrimitiveType());
            ClassfileTypeUtils.emitCheckcastAndUnbox(code, boxedName);
        } else {
            // Reference cast
            code.checkcast(ClassfileTypeUtils.toClassDesc(targetType));
        }
    }

    // ── Unary expression ──────────────────────────────────────────────────

    private static <C, W, O> void emitUnaryExpr(
            CodeBuilder code,
            UnaryExpr ue,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        switch (ue.getOperator()) {
            case LOGICAL_COMPLEMENT -> {
                // !expr → emit expr, then XOR with 1 to flip boolean
                emitExpression(code, ue.getExpression(), slots, params);
                code.iconst_1();
                code.ixor();
            }
            case MINUS -> {
                // -expr → emit expr, then negate
                emitExpression(code, ue.getExpression(), slots, params);
                TypeKind kind = inferTypeKind(ue.getExpression(), slots);
                switch (kind) {
                    case INT -> code.ineg();
                    case LONG -> code.lneg();
                    case DOUBLE -> code.dneg();
                    case FLOAT -> code.fneg();
                    default -> throw new UnsupportedOperationException(
                            "Cannot negate type: " + kind);
                }
            }
            case BITWISE_COMPLEMENT -> {
                // ~expr → emit expr, XOR with -1
                emitExpression(code, ue.getExpression(), slots, params);
                TypeKind kind = inferTypeKind(ue.getExpression(), slots);
                switch (kind) {
                    case INT -> { code.iconst_m1(); code.ixor(); }
                    case LONG -> { code.ldc(-1L); code.lxor(); }
                    default -> throw new UnsupportedOperationException(
                            "Cannot bitwise-complement type: " + kind);
                }
            }
            default -> throw new UnsupportedOperationException(
                    "Unsupported unary operator: " + ue.getOperator());
        }
    }

    // ── Binary expression ─────────────────────────────────────────────────

    private static <C, W, O> void emitBinaryExpr(
            CodeBuilder code,
            BinaryExpr be,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        BinaryExpr.Operator op = be.getOperator();

        // Short-circuit: && and ||
        if (op == BinaryExpr.Operator.AND) {
            emitShortCircuitAnd(code, be, slots, params);
            return;
        }
        if (op == BinaryExpr.Operator.OR) {
            emitShortCircuitOr(code, be, slots, params);
            return;
        }

        // Comparison operators on int: >, <, >=, <=, ==, !=
        if (isIntComparison(be, slots)) {
            emitIntComparison(code, be, slots, params);
            return;
        }

        // Arithmetic operators
        emitExpression(code, be.getLeft(), slots, params);
        emitExpression(code, be.getRight(), slots, params);

        TypeKind leftKind = inferTypeKind(be.getLeft(), slots);
        TypeKind rightKind = inferTypeKind(be.getRight(), slots);

        switch (op) {
            case PLUS -> emitAdd(code, leftKind);
            case MINUS -> emitSub(code, leftKind);
            case MULTIPLY -> emitMul(code, leftKind);
            case DIVIDE -> emitDiv(code, leftKind);
            case REMAINDER -> emitRem(code, leftKind);
            case BINARY_AND -> emitBitwiseAnd(code, leftKind);
            case BINARY_OR -> emitBitwiseOr(code, leftKind);
            case XOR -> emitBitwiseXor(code, leftKind);
            case LEFT_SHIFT -> emitShl(code, leftKind);
            case SIGNED_RIGHT_SHIFT -> emitShr(code, leftKind);
            case UNSIGNED_RIGHT_SHIFT -> emitUshr(code, leftKind);
            default -> throw new UnsupportedOperationException(
                    "Unsupported binary operator: " + op);
        }
    }

    private static <C, W, O> void emitShortCircuitAnd(
            CodeBuilder code,
            BinaryExpr be,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {
        // left && right: if left is false, result is false (short-circuit)
        var falseLabel = code.newLabel();
        var endLabel = code.newLabel();

        emitExpression(code, be.getLeft(), slots, params);
        code.ifeq(falseLabel);         // if left == 0 (false), jump to false

        emitExpression(code, be.getRight(), slots, params);
        code.goto_(endLabel);

        code.labelBinding(falseLabel);
        code.iconst_0();                // push false

        code.labelBinding(endLabel);
    }

    private static <C, W, O> void emitShortCircuitOr(
            CodeBuilder code,
            BinaryExpr be,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        var trueLabel = code.newLabel();
        var endLabel = code.newLabel();

        emitExpression(code, be.getLeft(), slots, params);
        code.ifne(trueLabel);           // if left != 0 (true), jump to true

        emitExpression(code, be.getRight(), slots, params);
        code.goto_(endLabel);

        code.labelBinding(trueLabel);
        code.iconst_1();                // push true

        code.labelBinding(endLabel);
    }

    private static boolean isIntComparison(BinaryExpr be, LocalSlotTable slots) {
        TypeKind leftKind = inferTypeKind(be.getLeft(), slots);
        if (leftKind != TypeKind.INT && leftKind != TypeKind.BOOLEAN) return false;
        return switch (be.getOperator()) {
            case GREATER, LESS, GREATER_EQUALS, LESS_EQUALS, EQUALS, NOT_EQUALS -> true;
            default -> false;
        };
    }

    private static <C, W, O> void emitIntComparison(
            CodeBuilder code,
            BinaryExpr be,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        var trueLabel = code.newLabel();
        var endLabel = code.newLabel();

        emitExpression(code, be.getLeft(), slots, params);
        emitExpression(code, be.getRight(), slots, params);

        switch (be.getOperator()) {
            case GREATER -> code.if_icmpgt(trueLabel);
            case LESS -> code.if_icmplt(trueLabel);
            case GREATER_EQUALS -> code.if_icmpge(trueLabel);
            case LESS_EQUALS -> code.if_icmple(trueLabel);
            case EQUALS -> code.if_icmpeq(trueLabel);
            case NOT_EQUALS -> code.if_icmpne(trueLabel);
            default -> throw new IllegalStateException("Not a comparison: " + be.getOperator());
        }

        code.iconst_0();               // false
        code.goto_(endLabel);

        code.labelBinding(trueLabel);
        code.iconst_1();               // true

        code.labelBinding(endLabel);
    }

    // ── Arithmetic helpers ────────────────────────────────────────────────

    private static void emitAdd(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.iadd();
            case LONG -> code.ladd();
            case DOUBLE -> code.dadd();
            case FLOAT -> code.fadd();
            default -> throw new UnsupportedOperationException("Cannot add type: " + kind);
        }
    }

    private static void emitSub(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.isub();
            case LONG -> code.lsub();
            case DOUBLE -> code.dsub();
            case FLOAT -> code.fsub();
            default -> throw new UnsupportedOperationException("Cannot subtract type: " + kind);
        }
    }

    private static void emitMul(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.imul();
            case LONG -> code.lmul();
            case DOUBLE -> code.dmul();
            case FLOAT -> code.fmul();
            default -> throw new UnsupportedOperationException("Cannot multiply type: " + kind);
        }
    }

    private static void emitDiv(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.idiv();
            case LONG -> code.ldiv();
            case DOUBLE -> code.ddiv();
            case FLOAT -> code.fdiv();
            default -> throw new UnsupportedOperationException("Cannot divide type: " + kind);
        }
    }

    private static void emitRem(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.irem();
            case LONG -> code.lrem();
            case DOUBLE -> code.drem();
            case FLOAT -> code.frem();
            default -> throw new UnsupportedOperationException("Cannot modulo type: " + kind);
        }
    }

    // ── Bitwise helpers ──────────────────────────────────────────────────

    private static void emitBitwiseAnd(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.iand();
            case LONG -> code.land();
            default -> throw new UnsupportedOperationException("Cannot bitwise-AND type: " + kind);
        }
    }

    private static void emitBitwiseOr(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.ior();
            case LONG -> code.lor();
            default -> throw new UnsupportedOperationException("Cannot bitwise-OR type: " + kind);
        }
    }

    private static void emitBitwiseXor(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.ixor();
            case LONG -> code.lxor();
            default -> throw new UnsupportedOperationException("Cannot bitwise-XOR type: " + kind);
        }
    }

    private static void emitShl(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.ishl();
            case LONG -> code.lshl();
            default -> throw new UnsupportedOperationException("Cannot left-shift type: " + kind);
        }
    }

    private static void emitShr(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.ishr();
            case LONG -> code.lshr();
            default -> throw new UnsupportedOperationException("Cannot right-shift type: " + kind);
        }
    }

    private static void emitUshr(CodeBuilder code, TypeKind kind) {
        switch (kind) {
            case INT -> code.iushr();
            case LONG -> code.lushr();
            default -> throw new UnsupportedOperationException("Cannot unsigned-right-shift type: " + kind);
        }
    }

    // ── Method call emission ──────────────────────────────────────────────

    private static <C, W, O> void emitMethodCallExpr(
            CodeBuilder code,
            MethodCallExpr mce,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String methodName = mce.getNameAsString();

        // Map.get("key") pattern: scope.get(StringLiteral)
        if (mce.getScope().isPresent() && methodName.equals("get")
                && mce.getArguments().size() == 1) {
            Expression scope = mce.getScope().get();
            Expression arg = mce.getArgument(0);

            if (scope instanceof NameExpr scopeName && slots.contains(scopeName.getNameAsString())) {
                TypeKind scopeKind = slots.typeKind(scopeName.getNameAsString());
                if (scopeKind == TypeKind.REFERENCE) {
                    // Could be Map.get(key) or List.get(index)
                    emitExpression(code, scope, slots, params);
                    emitExpression(code, arg, slots, params);

                    if (arg instanceof StringLiteralExpr) {
                        // Map.get(String) → invokeinterface Map.get(Object)Object
                        code.invokeinterface(CD_Map, "get",
                                MethodTypeDesc.of(CD_Object, CD_Object));
                    } else if (arg instanceof IntegerLiteralExpr) {
                        // List.get(int) → invokeinterface List.get(int)Object
                        code.invokeinterface(CD_List, "get",
                                MethodTypeDesc.of(CD_Object, CD_int));
                    } else {
                        // Generic: treat as Map.get(Object)
                        code.invokeinterface(CD_Map, "get",
                                MethodTypeDesc.of(CD_Object, CD_Object));
                    }
                    return;
                }
            }
        }

        // Static method calls on java.lang.Math
        if (mce.getScope().isPresent() && isStaticMathScope(mce.getScope().get())) {
            emitMathStaticCall(code, mce, slots, params);
            return;
        }

        // Static method calls on org.mvel3.MVEL (putMap, setList)
        if (mce.getScope().isPresent() && isStaticMvelScope(mce.getScope().get())) {
            emitMvelStaticCall(code, mce, slots, params);
            return;
        }

        // POJO getter pattern: __context.getXxx()
        if (mce.getScope().isPresent() && mce.getArguments().isEmpty()) {
            Expression scope = mce.getScope().get();
            if (scope instanceof NameExpr scopeName && slots.contains(scopeName.getNameAsString())) {
                Type scopeType = slots.type(scopeName.getNameAsString());
                ClassDesc scopeDesc = ClassfileTypeUtils.toClassDesc(scopeType);

                // Determine return type from the parent VariableDeclarator
                ClassDesc returnDesc = inferMethodReturnType(mce);
                emitExpression(code, scope, slots, params);
                code.invokevirtual(scopeDesc, methodName,
                        MethodTypeDesc.of(returnDesc));
                return;
            }
        }

        // POJO setter/instance method with arguments: scope.methodName(args)
        if (mce.getScope().isPresent()) {
            Expression scope = mce.getScope().get();
            if (scope instanceof NameExpr scopeName && slots.contains(scopeName.getNameAsString())) {
                Type scopeType = slots.type(scopeName.getNameAsString());
                ClassDesc scopeDesc = ClassfileTypeUtils.toClassDesc(scopeType);

                // Emit scope (receiver)
                emitExpression(code, scope, slots, params);

                // Build argument descriptors and emit arguments
                ClassDesc[] argDescs = new ClassDesc[mce.getArguments().size()];
                for (int i = 0; i < mce.getArguments().size(); i++) {
                    Expression arg = mce.getArgument(i);
                    emitExpression(code, arg, slots, params);
                    TypeKind argKind = inferTypeKind(arg, slots);
                    // Box primitives to Object for generic methods
                    if (argKind != TypeKind.REFERENCE) {
                        ClassfileTypeUtils.emitBoxing(code, argKind);
                    }
                    argDescs[i] = CD_Object;
                }

                // Infer return type
                ClassDesc returnDesc = inferMethodReturnType(mce);
                code.invokevirtual(scopeDesc, methodName,
                        MethodTypeDesc.of(returnDesc, argDescs));
                return;
            }
        }

        throw new UnsupportedOperationException(
                "Unsupported method call: " + mce);
    }

    private static final ClassDesc CD_Math = ClassDesc.of("java.lang.Math");
    private static final ClassDesc CD_MVEL = ClassDesc.of("org.mvel3.MVEL");

    /**
     * Emit a static method call on java.lang.Math.
     * All Math methods take double args and return double.
     */
    private static <C, W, O> void emitMathStaticCall(
            CodeBuilder code,
            MethodCallExpr mce,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String methodName = mce.getNameAsString();
        int argCount = mce.getArguments().size();

        // Emit arguments, widening to double where needed
        for (Expression arg : mce.getArguments()) {
            emitExpression(code, arg, slots, params);
            TypeKind kind = inferTypeKind(arg, slots);
            if (kind == TypeKind.INT) {
                code.i2d();
            } else if (kind == TypeKind.FLOAT) {
                code.f2d();
            } else if (kind == TypeKind.LONG) {
                code.l2d();
            }
        }

        // Build method type: all doubles in, double out
        ClassDesc[] argDescs = new ClassDesc[argCount];
        java.util.Arrays.fill(argDescs, CD_double);
        code.invokestatic(CD_Math, methodName, MethodTypeDesc.of(CD_double, argDescs));
    }

    /**
     * Emit a static method call on org.mvel3.MVEL (putMap, setList).
     */
    private static <C, W, O> void emitMvelStaticCall(
            CodeBuilder code,
            MethodCallExpr mce,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String methodName = mce.getNameAsString();

        if ("putMap".equals(methodName)) {
            // MVEL.putMap(Map context, String key, Object value) → Object
            // Arg 0: Map (reference, no boxing needed)
            emitExpression(code, mce.getArgument(0), slots, params);
            // Arg 1: String key (reference, no boxing needed)
            emitExpression(code, mce.getArgument(1), slots, params);
            // Arg 2: Object value (box if primitive)
            emitExpression(code, mce.getArgument(2), slots, params);
            TypeKind valKind = inferTypeKind(mce.getArgument(2), slots);
            if (valKind != TypeKind.REFERENCE) {
                ClassfileTypeUtils.emitBoxing(code, valKind);
            }
            code.invokestatic(CD_MVEL, "putMap",
                    MethodTypeDesc.of(CD_Object, CD_Map, CD_String, CD_Object));
        } else if ("setList".equals(methodName)) {
            // MVEL.setList(List context, int index, Object value) → Object
            // Arg 0: List (reference)
            emitExpression(code, mce.getArgument(0), slots, params);
            // Arg 1: int index (no boxing — stays as int)
            emitExpression(code, mce.getArgument(1), slots, params);
            // Arg 2: Object value (box if primitive)
            emitExpression(code, mce.getArgument(2), slots, params);
            TypeKind valKind = inferTypeKind(mce.getArgument(2), slots);
            if (valKind != TypeKind.REFERENCE) {
                ClassfileTypeUtils.emitBoxing(code, valKind);
            }
            code.invokestatic(CD_MVEL, "setList",
                    MethodTypeDesc.of(CD_Object, CD_List, CD_int, CD_Object));
        } else {
            throw new UnsupportedOperationException(
                    "Unsupported MVEL static method: " + methodName);
        }
    }

    // ── Field access emission ─────────────────────────────────────────────

    private static <C, W, O> void emitFieldAccessExpr(
            CodeBuilder code,
            FieldAccessExpr fae,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        // FieldAccessExpr is used as a class-name scope for static method calls
        // (e.g. java.lang.Math, org.mvel3.MVEL). These are NOT emitted as
        // field loads — the parent MethodCallExpr handles the invokestatic.
        // If we reach this point, the FieldAccessExpr is being used as a standalone
        // expression which shouldn't happen for class-name references.
        throw new UnsupportedOperationException(
                "Field access not yet supported: " + fae);
    }

    // ── Assignment emission ───────────────────────────────────────────────

    private static boolean isSupportedAssign(AssignExpr ae) {
        if (!(ae.getTarget() instanceof NameExpr)) return false;
        if (!isSupportedExpression(ae.getValue())) return false;
        return switch (ae.getOperator()) {
            case ASSIGN, PLUS, MINUS, MULTIPLY, DIVIDE, REMAINDER,
                 BINARY_AND, BINARY_OR, XOR,
                 LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT -> true;
            default -> false;
        };
    }

    private static <C, W, O> void emitAssignExpr(
            CodeBuilder code,
            AssignExpr ae,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String targetName;
        if (ae.getTarget() instanceof NameExpr ne) {
            targetName = ne.getNameAsString();
        } else {
            throw new UnsupportedOperationException(
                    "Unsupported assignment target: " + ae.getTarget());
        }

        if (!slots.contains(targetName)) {
            throw new UnsupportedOperationException(
                    "Assignment to unknown variable: " + targetName);
        }

        if (ae.getOperator() == AssignExpr.Operator.ASSIGN) {
            // Simple assignment: a = expr
            emitExpression(code, ae.getValue(), slots, params);
            slots.storeVar(code, targetName);
        } else {
            // Compound assignment: a op= expr → load a, emit expr, op, store a
            TypeKind kind = slots.typeKind(targetName);
            slots.loadVar(code, targetName);
            emitExpression(code, ae.getValue(), slots, params);
            switch (ae.getOperator()) {
                case PLUS -> emitAdd(code, kind);
                case MINUS -> emitSub(code, kind);
                case MULTIPLY -> emitMul(code, kind);
                case DIVIDE -> emitDiv(code, kind);
                case REMAINDER -> emitRem(code, kind);
                case BINARY_AND -> emitBitwiseAnd(code, kind);
                case BINARY_OR -> emitBitwiseOr(code, kind);
                case XOR -> emitBitwiseXor(code, kind);
                case LEFT_SHIFT -> emitShl(code, kind);
                case SIGNED_RIGHT_SHIFT -> emitShr(code, kind);
                case UNSIGNED_RIGHT_SHIFT -> emitUshr(code, kind);
                default -> throw new UnsupportedOperationException(
                        "Unsupported compound assignment: " + ae.getOperator());
            }
            slots.storeVar(code, targetName);
        }
    }

    // ── AST support checking ──────────────────────────────────────────────

    private static boolean isSupportedStatement(Statement stmt) {
        return switch (stmt) {
            case ReturnStmt rs -> rs.getExpression().map(ClassfileEvaluatorEmitter::isSupportedExpression).orElse(true);
            case ExpressionStmt es -> isSupportedExpression(es.getExpression());
            default -> false;
        };
    }

    private static boolean isSupportedExpression(Expression expr) {
        return switch (expr) {
            case IntegerLiteralExpr _ -> true;
            case LongLiteralExpr _ -> true;
            case DoubleLiteralExpr _ -> true;
            case BooleanLiteralExpr _ -> true;
            case StringLiteralExpr _ -> true;
            case NullLiteralExpr _ -> true;
            case CharLiteralExpr _ -> true;
            case NameExpr _ -> true;
            case EnclosedExpr ee -> isSupportedExpression(ee.getInner());
            case CastExpr ce -> isSupportedExpression(ce.getExpression());
            case UnaryExpr ue -> isSupportedUnary(ue);
            case BinaryExpr be -> isSupportedBinary(be);
            case MethodCallExpr mce -> isSupportedMethodCall(mce);
            case VariableDeclarationExpr vde ->
                    vde.getVariables().stream().allMatch(v ->
                            v.getInitializer().map(ClassfileEvaluatorEmitter::isSupportedExpression).orElse(true));
            case AssignExpr ae -> isSupportedAssign(ae);
            default -> false;
        };
    }

    private static boolean isSupportedUnary(UnaryExpr ue) {
        return switch (ue.getOperator()) {
            case LOGICAL_COMPLEMENT, MINUS, BITWISE_COMPLEMENT -> isSupportedExpression(ue.getExpression());
            default -> false;
        };
    }

    private static boolean isSupportedBinary(BinaryExpr be) {
        if (!isSupportedExpression(be.getLeft()) || !isSupportedExpression(be.getRight())) {
            return false;
        }
        return switch (be.getOperator()) {
            case PLUS, MINUS, MULTIPLY, DIVIDE, REMAINDER,
                 GREATER, LESS, GREATER_EQUALS, LESS_EQUALS,
                 EQUALS, NOT_EQUALS, AND, OR,
                 BINARY_AND, BINARY_OR, XOR,
                 LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT -> true;
            // String concatenation (+) is not yet supported in the emitter
            // (requires StringBuilder/StringConcatFactory). This is caught at emit
            // time by the arithmetic emitters which reject REFERENCE type.
            default -> false;
        };
    }

    private static boolean isSupportedMethodCall(MethodCallExpr mce) {
        if (mce.getScope().isEmpty()) return false;
        if (!mce.getArguments().stream().allMatch(ClassfileEvaluatorEmitter::isSupportedExpression)) {
            return false;
        }

        Expression scope = mce.getScope().get();

        // Static methods on java.lang.Math (sin, cos, pow, ceil, floor, etc.)
        if (isStaticMathScope(scope)) return true;

        // Static methods on org.mvel3.MVEL (putMap, setList)
        if (isStaticMvelScope(scope)) return true;

        // Instance method calls: scope must be a supported expression (variable access)
        return isSupportedExpression(scope);
    }

    private static boolean isStaticMathScope(Expression scope) {
        if (scope instanceof NameExpr ne) {
            return "Math".equals(ne.getNameAsString());
        }
        if (scope instanceof FieldAccessExpr fae) {
            String name = fae.toString();
            return "java.lang.Math".equals(name) || "Math".equals(name);
        }
        return false;
    }

    private static boolean isStaticMvelScope(Expression scope) {
        if (scope instanceof FieldAccessExpr fae) {
            String name = fae.toString();
            return "org.mvel3.MVEL".equals(name);
        }
        return false;
    }

    // ── Type inference helpers ─────────────────────────────────────────────

    /**
     * Infer the JVM TypeKind that an expression will leave on the stack.
     * This is a best-effort heuristic based on the AST structure.
     */
    static TypeKind inferTypeKind(Expression expr, LocalSlotTable slots) {
        return switch (expr) {
            case IntegerLiteralExpr _ -> TypeKind.INT;
            case LongLiteralExpr _ -> TypeKind.LONG;
            case DoubleLiteralExpr _ -> TypeKind.DOUBLE;
            case BooleanLiteralExpr _ -> TypeKind.INT; // booleans are int on JVM
            case StringLiteralExpr _ -> TypeKind.REFERENCE;
            case NullLiteralExpr _ -> TypeKind.REFERENCE;
            case CharLiteralExpr _ -> TypeKind.INT;
            case NameExpr ne -> {
                if (slots.contains(ne.getNameAsString())) {
                    yield slots.typeKind(ne.getNameAsString());
                }
                yield TypeKind.REFERENCE;
            }
            case EnclosedExpr ee -> inferTypeKind(ee.getInner(), slots);
            case CastExpr ce -> {
                if (ce.getType().isPrimitiveType()) {
                    yield ClassfileTypeUtils.toTypeKind(ce.getType());
                }
                yield TypeKind.REFERENCE;
            }
            case UnaryExpr ue -> {
                if (ue.getOperator() == UnaryExpr.Operator.LOGICAL_COMPLEMENT) {
                    yield TypeKind.INT; // boolean → int
                }
                if (ue.getOperator() == UnaryExpr.Operator.BITWISE_COMPLEMENT) {
                    yield inferTypeKind(ue.getExpression(), slots); // same type as operand
                }
                yield inferTypeKind(ue.getExpression(), slots);
            }
            case BinaryExpr be -> {
                BinaryExpr.Operator op = be.getOperator();
                if (op == BinaryExpr.Operator.AND || op == BinaryExpr.Operator.OR
                        || op == BinaryExpr.Operator.GREATER || op == BinaryExpr.Operator.LESS
                        || op == BinaryExpr.Operator.GREATER_EQUALS || op == BinaryExpr.Operator.LESS_EQUALS
                        || op == BinaryExpr.Operator.EQUALS || op == BinaryExpr.Operator.NOT_EQUALS) {
                    yield TypeKind.INT; // boolean result
                }
                // Bitwise and shift operations preserve the left operand type
                yield inferTypeKind(be.getLeft(), slots);
            }
            case MethodCallExpr mce -> {
                // Math static calls return double
                if (mce.getScope().isPresent() && isStaticMathScope(mce.getScope().get())) {
                    yield TypeKind.DOUBLE;
                }
                yield TypeKind.REFERENCE; // method calls return Object by default
            }
            default -> TypeKind.REFERENCE;
        };
    }

    /**
     * Infer a JavaParser Type from an expression, for use with var declarations.
     * Maps TypeKind back to PrimitiveType where possible, otherwise returns
     * a ClassOrInterfaceType matching the expression.
     */
    private static Type inferTypeFromExpression(Expression expr, LocalSlotTable slots) {
        // CastExpr: use the cast target type directly
        if (expr instanceof CastExpr ce) {
            return ce.getType();
        }
        TypeKind kind = inferTypeKind(expr, slots);
        return switch (kind) {
            case INT -> PrimitiveType.intType();
            case LONG -> PrimitiveType.longType();
            case DOUBLE -> PrimitiveType.doubleType();
            case FLOAT -> PrimitiveType.floatType();
            case BOOLEAN -> PrimitiveType.booleanType();
            case BYTE -> PrimitiveType.byteType();
            case CHAR -> PrimitiveType.charType();
            case SHORT -> PrimitiveType.shortType();
            default -> new ClassOrInterfaceType(null, "Object"); // reference fallback
        };
    }

    /**
     * For var declarations with a boxed initializer (CastExpr to a boxed type),
     * return the boxed type name for unboxing.
     */
    private static String inferBoxedNameFromExpression(Expression expr) {
        if (expr instanceof CastExpr ce && ce.getType().isClassOrInterfaceType()) {
            return ce.getType().asClassOrInterfaceType().getNameWithScope();
        }
        return "java.lang.Object";
    }

    /**
     * Infer the return type ClassDesc of a method call by examining the AST parent context.
     * For POJO getters used in variable declarations like {@code int x = ctx.getX()},
     * the return type is derived from the variable's declared type.
     */
    private static ClassDesc inferMethodReturnType(MethodCallExpr mce) {
        // Walk up: MethodCallExpr → VariableDeclarator → VariableDeclarationExpr
        if (mce.getParentNode().isPresent()) {
            var parent = mce.getParentNode().get();
            if (parent instanceof com.github.javaparser.ast.body.VariableDeclarator vd) {
                return ClassfileTypeUtils.toClassDesc(vd.getType());
            }
            // CastExpr parent — the cast target type
            if (parent instanceof CastExpr ce) {
                return ClassfileTypeUtils.toClassDesc(ce.getType());
            }
        }
        // Fallback: assume Object
        return CD_Object;
    }

    // ── Utility ───────────────────────────────────────────────────────────

    private static ClassDesc classDescForType(org.mvel3.Type<?> type) {
        if (type.isVoid()) return ClassDesc.of("java.lang.Void");
        Class<?> clazz = type.getClazz();
        if (clazz.isPrimitive()) {
            // Evaluator uses boxed types in generics
            return ClassfileTypeUtils.classDescFromName(clazz.getName());
        }
        return ClassDesc.of(clazz.getCanonicalName());
    }

    private static String boxedNameForPrimitive(PrimitiveType pt) {
        return switch (pt.getType()) {
            case INT -> "java.lang.Integer";
            case LONG -> "java.lang.Long";
            case DOUBLE -> "java.lang.Double";
            case FLOAT -> "java.lang.Float";
            case BOOLEAN -> "java.lang.Boolean";
            case BYTE -> "java.lang.Byte";
            case CHAR -> "java.lang.Character";
            case SHORT -> "java.lang.Short";
        };
    }

    /**
     * Supported AST node types for Phase 1 canEmit() check.
     */
    private static final Set<Class<? extends Expression>> SUPPORTED_EXPR_TYPES = Set.of(
            IntegerLiteralExpr.class, LongLiteralExpr.class, DoubleLiteralExpr.class,
            BooleanLiteralExpr.class, StringLiteralExpr.class, NullLiteralExpr.class,
            CharLiteralExpr.class, NameExpr.class, EnclosedExpr.class, CastExpr.class,
            UnaryExpr.class, BinaryExpr.class, MethodCallExpr.class,
            VariableDeclarationExpr.class, AssignExpr.class
    );

    // ── Phase 2 audit: diagnose why canEmit() rejected ───────────────────

    /**
     * Diagnose why {@link #canEmit(TranspiledResult)} returns false.
     * Returns a human-readable reason string, or null if emittable.
     */
    public static String diagnoseRejection(TranspiledResult result) {
        MethodDeclaration method = result.getUnit()
                .findFirst(MethodDeclaration.class)
                .orElse(null);
        if (method == null) return "no MethodDeclaration in AST";
        if (method.getBody().isEmpty()) return "method has no body";

        BlockStmt body = method.getBody().get();

        // Check statement support
        boolean hasReturn = false;
        for (Statement stmt : body.getStatements()) {
            if (!isSupportedStatement(stmt)) {
                return "unsupported statement: " + stmt.getClass().getSimpleName()
                        + " → " + stmt.toString().trim();
            }
            if (stmt instanceof ReturnStmt) hasReturn = true;
        }
        if (!hasReturn) return "no return statement";

        return null; // emittable
    }

    /**
     * Find the first unsupported expression node in a statement (for audit detail).
     */
    public static String diagnoseUnsupportedExpression(Statement stmt) {
        if (stmt instanceof ExpressionStmt es) {
            return findUnsupported(es.getExpression());
        } else if (stmt instanceof ReturnStmt rs && rs.getExpression().isPresent()) {
            return findUnsupported(rs.getExpression().get());
        }
        return stmt.getClass().getSimpleName();
    }

    private static String findUnsupported(Expression expr) {
        return switch (expr) {
            case IntegerLiteralExpr _, LongLiteralExpr _, DoubleLiteralExpr _,
                 BooleanLiteralExpr _, StringLiteralExpr _, NullLiteralExpr _,
                 CharLiteralExpr _, NameExpr _ -> null;
            case EnclosedExpr ee -> findUnsupported(ee.getInner());
            case CastExpr ce -> findUnsupported(ce.getExpression());
            case UnaryExpr ue -> {
                if (!isSupportedUnary(ue))
                    yield "UnaryExpr(" + ue.getOperator() + "): " + ue;
                yield findUnsupported(ue.getExpression());
            }
            case BinaryExpr be -> {
                if (!isSupportedBinary(be)) {
                    String left = findUnsupported(be.getLeft());
                    if (left != null) yield left;
                    String right = findUnsupported(be.getRight());
                    if (right != null) yield right;
                    yield "BinaryExpr(" + be.getOperator() + "): " + be;
                }
                yield null;
            }
            case MethodCallExpr mce -> {
                if (!isSupportedMethodCall(mce))
                    yield "MethodCallExpr: " + mce;
                yield null;
            }
            case VariableDeclarationExpr vde -> {
                for (var v : vde.getVariables()) {
                    if (v.getInitializer().isPresent()) {
                        String r = findUnsupported(v.getInitializer().get());
                        if (r != null) yield r;
                    }
                }
                yield null;
            }
            case AssignExpr ae -> {
                if (ae.getOperator() != AssignExpr.Operator.ASSIGN)
                    yield "AssignExpr(compound " + ae.getOperator() + "): " + ae;
                if (!(ae.getTarget() instanceof NameExpr))
                    yield "AssignExpr(non-name target " + ae.getTarget().getClass().getSimpleName() + "): " + ae;
                yield findUnsupported(ae.getValue());
            }
            default -> expr.getClass().getSimpleName() + ": " + expr;
        };
    }
}
