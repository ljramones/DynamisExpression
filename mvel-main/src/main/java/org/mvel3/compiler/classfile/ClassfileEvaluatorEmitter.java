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
import org.mvel3.transpiler.context.Declaration;

import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.classfile.Opcode;
import java.lang.classfile.TypeKind;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
    private static final ClassDesc CD_BigDecimal = ClassDesc.of("java.math.BigDecimal");
    private static final ClassDesc CD_MathContext = ClassDesc.of("java.math.MathContext");

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
        for (Statement stmt : body.getStatements()) {
            if (!isSupportedStatement(stmt)) return false;
        }
        return containsReturn(body);
    }

    /**
     * Recursively check whether a statement or block contains at least one return statement.
     */
    private static boolean containsReturn(Statement stmt) {
        return switch (stmt) {
            case ReturnStmt _ -> true;
            case BlockStmt bs -> bs.getStatements().stream().anyMatch(ClassfileEvaluatorEmitter::containsReturn);
            case IfStmt is -> containsReturn(is.getThenStmt())
                    || is.getElseStmt().map(ClassfileEvaluatorEmitter::containsReturn).orElse(false);
            default -> false;
        };
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
            case BlockStmt bs -> emitBlockStmt(code, bs, slots, params, outClass);
            case IfStmt is -> emitIfStmt(code, is, slots, params, outClass);
            case EmptyStmt _ -> {} // no-op: trailing semicolons
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
        TypeKind exprKind = inferTypeKind(expr, slots, params);
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

    // ── Block statement emission ───────────────────────────────────────────

    private static <C, W, O> void emitBlockStmt(
            CodeBuilder code,
            BlockStmt bs,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params,
            Class<?> outClass) {

        for (Statement stmt : bs.getStatements()) {
            emitStatement(code, stmt, slots, params, outClass);
        }
    }

    // ── If statement emission ────────────────────────────────────────────

    private static <C, W, O> void emitIfStmt(
            CodeBuilder code,
            IfStmt is,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params,
            Class<?> outClass) {

        if (is.getElseStmt().isPresent()) {
            var elseLabel = code.newLabel();

            // Emit condition; result is int (0=false, nonzero=true)
            emitExpression(code, is.getCondition(), slots, params);
            code.ifeq(elseLabel);       // if false, jump to else

            // Then branch
            emitStatement(code, is.getThenStmt(), slots, params, outClass);

            if (endsWithReturn(is.getThenStmt())) {
                // Both branches return — no goto or endLabel needed after then
                code.labelBinding(elseLabel);
                emitStatement(code, is.getElseStmt().get(), slots, params, outClass);
            } else {
                // Then branch falls through — need goto to skip else
                var endLabel = code.newLabel();
                code.goto_(endLabel);

                code.labelBinding(elseLabel);
                emitStatement(code, is.getElseStmt().get(), slots, params, outClass);

                code.labelBinding(endLabel);
            }
        } else {
            // if-then (no else)
            var endLabel = code.newLabel();

            emitExpression(code, is.getCondition(), slots, params);
            code.ifeq(endLabel);        // if false, skip then block

            emitStatement(code, is.getThenStmt(), slots, params, outClass);

            code.labelBinding(endLabel);
        }
    }

    /**
     * Check whether a statement ends with a return (directly or in its last sub-statement).
     * Used to avoid emitting dead code after returning branches.
     */
    private static boolean endsWithReturn(Statement stmt) {
        return switch (stmt) {
            case ReturnStmt _ -> true;
            case BlockStmt bs -> {
                List<Statement> stmts = bs.getStatements();
                yield !stmts.isEmpty() && endsWithReturn(stmts.getLast());
            }
            case IfStmt is -> endsWithReturn(is.getThenStmt())
                    && is.getElseStmt().map(ClassfileEvaluatorEmitter::endsWithReturn).orElse(false);
            default -> false;
        };
    }

    // ── Expression emission ──────────────────────────────────────────────

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
            case ObjectCreationExpr oce -> emitObjectCreationExpr(code, oce, slots, params);
            case AssignExpr ae -> emitAssignExprAsExpression(code, ae, slots, params);
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
                TypeKind kind = inferTypeKind(ue.getExpression(), slots, params);
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
                TypeKind kind = inferTypeKind(ue.getExpression(), slots, params);
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

        // Comparison operators (==, !=, >, <, >=, <=) — supports int, long, double, float
        if (isComparisonOp(op)) {
            emitComparison(code, be, slots, params);
            return;
        }

        // Arithmetic/bitwise operators: determine the widened type, emit with widening
        TypeKind leftKind = inferTypeKind(be.getLeft(), slots, params);
        TypeKind rightKind = inferTypeKind(be.getRight(), slots, params);
        TypeKind opKind = widenTypeKind(leftKind, rightKind);

        emitExpression(code, be.getLeft(), slots, params);
        emitTypeWidening(code, leftKind, opKind);
        emitExpression(code, be.getRight(), slots, params);
        emitTypeWidening(code, rightKind, opKind);

        switch (op) {
            case PLUS -> emitAdd(code, opKind);
            case MINUS -> emitSub(code, opKind);
            case MULTIPLY -> emitMul(code, opKind);
            case DIVIDE -> emitDiv(code, opKind);
            case REMAINDER -> emitRem(code, opKind);
            case BINARY_AND -> emitBitwiseAnd(code, opKind);
            case BINARY_OR -> emitBitwiseOr(code, opKind);
            case XOR -> emitBitwiseXor(code, opKind);
            case LEFT_SHIFT -> emitShl(code, opKind);
            case SIGNED_RIGHT_SHIFT -> emitShr(code, opKind);
            case UNSIGNED_RIGHT_SHIFT -> emitUshr(code, opKind);
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

    private static boolean isComparisonOp(BinaryExpr.Operator op) {
        return switch (op) {
            case GREATER, LESS, GREATER_EQUALS, LESS_EQUALS, EQUALS, NOT_EQUALS -> true;
            default -> false;
        };
    }

    /**
     * Emit a comparison expression for any numeric type (int, long, double, float).
     * For int: uses if_icmpXX instructions directly.
     * For long/double/float: emits lcmp/dcmpg/fcmpg first, then ifeq/ifne/etc.
     */
    private static <C, W, O> void emitComparison(
            CodeBuilder code,
            BinaryExpr be,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        TypeKind leftKind = inferTypeKind(be.getLeft(), slots, params);
        TypeKind rightKind = inferTypeKind(be.getRight(), slots, params);
        TypeKind cmpKind = widenTypeKind(leftKind, rightKind);

        var trueLabel = code.newLabel();
        var endLabel = code.newLabel();

        emitExpression(code, be.getLeft(), slots, params);
        emitTypeWidening(code, leftKind, cmpKind);
        emitExpression(code, be.getRight(), slots, params);
        emitTypeWidening(code, rightKind, cmpKind);

        switch (cmpKind) {
            case INT, BOOLEAN -> {
                // Direct int comparison with if_icmpXX
                switch (be.getOperator()) {
                    case GREATER -> code.if_icmpgt(trueLabel);
                    case LESS -> code.if_icmplt(trueLabel);
                    case GREATER_EQUALS -> code.if_icmpge(trueLabel);
                    case LESS_EQUALS -> code.if_icmple(trueLabel);
                    case EQUALS -> code.if_icmpeq(trueLabel);
                    case NOT_EQUALS -> code.if_icmpne(trueLabel);
                    default -> throw new IllegalStateException("Not a comparison: " + be.getOperator());
                }
            }
            case LONG -> {
                // lcmp pushes -1/0/+1, then branch on that int
                code.lcmp();
                emitIntBranchForComparison(code, be.getOperator(), trueLabel);
            }
            case DOUBLE -> {
                // dcmpg for NaN safety (NaN produces 1, so < returns false correctly)
                code.dcmpg();
                emitIntBranchForComparison(code, be.getOperator(), trueLabel);
            }
            case FLOAT -> {
                code.fcmpg();
                emitIntBranchForComparison(code, be.getOperator(), trueLabel);
            }
            default -> throw new UnsupportedOperationException(
                    "Cannot compare type: " + cmpKind);
        }

        code.iconst_0();               // false
        code.goto_(endLabel);

        code.labelBinding(trueLabel);
        code.iconst_1();               // true

        code.labelBinding(endLabel);
    }

    /**
     * After lcmp/dcmpg/fcmpg, the stack has int -1/0/+1.
     * Emit the appropriate branch for the comparison operator.
     */
    private static void emitIntBranchForComparison(CodeBuilder code,
                                                    BinaryExpr.Operator op,
                                                    java.lang.classfile.Label trueLabel) {
        switch (op) {
            case GREATER -> code.ifgt(trueLabel);
            case LESS -> code.iflt(trueLabel);
            case GREATER_EQUALS -> code.ifge(trueLabel);
            case LESS_EQUALS -> code.ifle(trueLabel);
            case EQUALS -> code.ifeq(trueLabel);
            case NOT_EQUALS -> code.ifne(trueLabel);
            default -> throw new IllegalStateException("Not a comparison: " + op);
        }
    }

    // ── Type widening helpers ─────────────────────────────────────────────

    /**
     * Determine the wider TypeKind for binary operations between two types.
     * Rules: double > float > long > int (byte/short/char/boolean widen to int).
     */
    private static TypeKind widenTypeKind(TypeKind left, TypeKind right) {
        if (left == TypeKind.DOUBLE || right == TypeKind.DOUBLE) return TypeKind.DOUBLE;
        if (left == TypeKind.FLOAT || right == TypeKind.FLOAT) return TypeKind.FLOAT;
        if (left == TypeKind.LONG || right == TypeKind.LONG) return TypeKind.LONG;
        return TypeKind.INT;
    }

    /**
     * Emit a widening conversion from one primitive type to another.
     * No-op if the types are the same.
     */
    private static void emitTypeWidening(CodeBuilder code, TypeKind from, TypeKind to) {
        if (from == to) return;
        switch (from) {
            case INT, BOOLEAN, BYTE, CHAR, SHORT -> {
                switch (to) {
                    case LONG -> code.i2l();
                    case FLOAT -> code.i2f();
                    case DOUBLE -> code.i2d();
                    default -> {}
                }
            }
            case LONG -> {
                switch (to) {
                    case FLOAT -> code.l2f();
                    case DOUBLE -> code.l2d();
                    default -> {}
                }
            }
            case FLOAT -> {
                if (to == TypeKind.DOUBLE) code.f2d();
            }
            default -> {}
        }
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

        // Static method calls on BigDecimal/BigInteger (valueOf, etc.)
        if (mce.getScope().isPresent() && isStaticBigDecimalScope(mce.getScope().get())) {
            emitBigDecimalStaticCall(code, mce, slots, params);
            return;
        }

        // Static method calls on known JDK classes (Integer.rotateLeft, Double.parseDouble, etc.)
        if (mce.getScope().isPresent() && isStaticKnownClassScope(mce.getScope().get())) {
            emitKnownClassStaticCall(code, mce, slots, params);
            return;
        }

        // POJO instance method call: scope.methodName(args...)
        // Use reflection to resolve the actual method descriptor.
        if (mce.getScope().isPresent()) {
            Expression scope = mce.getScope().get();
            if (scope instanceof NameExpr scopeName && slots.contains(scopeName.getNameAsString())) {
                Class<?> scopeClass = resolveVariableClassWithLocals(scopeName.getNameAsString(), slots, params);
                if (scopeClass != null) {
                    emitReflectedMethodCall(code, mce, scopeClass, scopeName, slots, params);
                    return;
                }
                // Fallback: no class info, use heuristic-based emission
                emitHeuristicMethodCall(code, mce, scopeName, methodName, slots, params);
                return;
            }

            // Chained method call: scope is itself a MethodCallExpr (e.g., _this.getSalary().add(...))
            Class<?> scopeReturnClass = resolveExpressionType(scope, slots, params);
            if (scopeReturnClass != null) {
                emitChainedMethodCall(code, mce, scope, scopeReturnClass, slots, params);
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
            TypeKind kind = inferTypeKind(arg, slots, params);
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
            TypeKind valKind = inferTypeKind(mce.getArgument(2), slots, params);
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
            TypeKind valKind = inferTypeKind(mce.getArgument(2), slots, params);
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

    // ── BigDecimal / BigInteger static method emission ─────────────────────

    /**
     * Emit a static method call on BigDecimal or BigInteger (e.g., BigDecimal.valueOf(10)).
     */
    private static <C, W, O> void emitBigDecimalStaticCall(
            CodeBuilder code,
            MethodCallExpr mce,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        Expression scope = mce.getScope().get();
        String className = scope instanceof NameExpr ne ? ne.getNameAsString() : scope.toString();
        boolean isBigInteger = className.contains("BigInteger");
        ClassDesc targetClass = isBigInteger
                ? ClassDesc.of("java.math.BigInteger")
                : CD_BigDecimal;

        String methodName = mce.getNameAsString();

        if ("valueOf".equals(methodName) && mce.getArguments().size() == 1) {
            Expression arg = mce.getArgument(0);
            emitExpression(code, arg, slots, params);
            TypeKind argKind = inferTypeKind(arg, slots, params);
            // BigDecimal.valueOf(long) and BigInteger.valueOf(long) both take long
            if (argKind == TypeKind.INT || argKind == TypeKind.BOOLEAN || argKind == TypeKind.BYTE
                    || argKind == TypeKind.SHORT || argKind == TypeKind.CHAR) {
                code.i2l();
            }
            code.invokestatic(targetClass, "valueOf", MethodTypeDesc.of(targetClass, CD_long));
            return;
        }

        // Generic fallback for other static methods: use reflection
        emitReflectedStaticCall(code, targetClass, className, mce, slots, params);
    }

    /**
     * Emit a static method call on a known JDK class using reflection to resolve the descriptor.
     */
    private static <C, W, O> void emitKnownClassStaticCall(
            CodeBuilder code,
            MethodCallExpr mce,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String className = ((NameExpr) mce.getScope().get()).getNameAsString();
        ClassDesc targetClass = ClassfileTypeUtils.classDescFromName(className);
        emitReflectedStaticCall(code, targetClass, className, mce, slots, params);
    }

    /**
     * Generic reflection-based static method call emitter.
     */
    private static <C, W, O> void emitReflectedStaticCall(
            CodeBuilder code,
            ClassDesc targetClassDesc,
            String className,
            MethodCallExpr mce,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String methodName = mce.getNameAsString();
        int argCount = mce.getArguments().size();

        // Resolve the class and find the static method
        Class<?> clazz = resolveClassName(className);
        if (clazz == null) {
            throw new UnsupportedOperationException(
                    "Cannot resolve class for static call: " + className + "." + methodName);
        }

        Method method = findStaticMethodByNameAndArgCount(clazz, methodName, argCount);
        if (method == null) {
            throw new UnsupportedOperationException(
                    "Cannot find static method: " + className + "." + methodName + " with " + argCount + " arg(s)");
        }

        // Emit arguments with type conversion
        Class<?>[] paramTypes = method.getParameterTypes();
        ClassDesc[] paramDescs = new ClassDesc[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            paramDescs[i] = classDescForJavaClass(paramTypes[i]);
            Expression arg = mce.getArgument(i);
            emitExpression(code, arg, slots, params);
            TypeKind argKind = inferTypeKind(arg, slots, params);
            // Widen primitive if needed (e.g., int → long, int → double)
            if (paramTypes[i].isPrimitive() && argKind != TypeKind.REFERENCE) {
                TypeKind targetKind = typeKindForJavaClass(paramTypes[i]);
                emitTypeWidening(code, argKind, targetKind);
            } else if (!paramTypes[i].isPrimitive() && argKind != TypeKind.REFERENCE) {
                // Method takes Object, we have a primitive → box it
                ClassfileTypeUtils.emitBoxing(code, argKind);
            }
        }

        ClassDesc returnDesc = classDescForJavaClass(method.getReturnType());
        code.invokestatic(targetClassDesc, methodName,
                MethodTypeDesc.of(returnDesc, paramDescs));
    }

    /**
     * Emit a chained method call where the scope is a complex expression
     * (e.g., _this.getSalary().add(...) where scope is the MethodCallExpr _this.getSalary()).
     */
    private static <C, W, O> void emitChainedMethodCall(
            CodeBuilder code,
            MethodCallExpr mce,
            Expression scope,
            Class<?> scopeReturnClass,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String methodName = mce.getNameAsString();
        int argCount = mce.getArguments().size();

        Method method = findMethodByNameAndArgCount(scopeReturnClass, methodName, argCount);
        if (method == null) {
            throw new UnsupportedOperationException(
                    "Cannot resolve chained method: " + scopeReturnClass.getName() + "." + methodName
                    + " with " + argCount + " arg(s)");
        }

        // Emit the scope expression — puts the receiver object on the stack
        emitExpression(code, scope, slots, params);

        ClassDesc ownerDesc = classDescForJavaClass(scopeReturnClass);

        // Emit arguments with type conversion
        Class<?>[] paramTypes = method.getParameterTypes();
        ClassDesc[] paramDescs = new ClassDesc[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            paramDescs[i] = classDescForJavaClass(paramTypes[i]);
            Expression arg = mce.getArgument(i);
            emitExpression(code, arg, slots, params);
            TypeKind argKind = inferTypeKind(arg, slots, params);
            if (paramTypes[i] == Object.class && argKind != TypeKind.REFERENCE) {
                ClassfileTypeUtils.emitBoxing(code, argKind);
            } else if (paramTypes[i].isPrimitive() && argKind != TypeKind.REFERENCE) {
                TypeKind targetKind = typeKindForJavaClass(paramTypes[i]);
                emitTypeWidening(code, argKind, targetKind);
            }
        }

        ClassDesc returnDesc = classDescForJavaClass(method.getReturnType());

        // Use invokeinterface for interface types, invokevirtual for classes
        if (scopeReturnClass.isInterface()) {
            code.invokeinterface(ownerDesc, methodName,
                    MethodTypeDesc.of(returnDesc, paramDescs));
        } else {
            code.invokevirtual(ownerDesc, methodName,
                    MethodTypeDesc.of(returnDesc, paramDescs));
        }
    }

    // ── Object creation emission ──────────────────────────────────────────

    private static <C, W, O> void emitObjectCreationExpr(
            CodeBuilder code,
            ObjectCreationExpr oce,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String typeName = oce.getType().getNameWithScope();
        Class<?> clazz = resolveClassName(typeName);
        if (clazz == null) {
            throw new UnsupportedOperationException("Cannot resolve class: " + typeName);
        }

        ClassDesc classDesc = classDescForJavaClass(clazz);
        code.new_(classDesc);
        code.dup();

        // Find matching constructor by argument count
        int argCount = oce.getArguments().size();
        java.lang.reflect.Constructor<?> ctor = findConstructorByArgCount(clazz, argCount);
        if (ctor == null) {
            throw new UnsupportedOperationException(
                    "Cannot find constructor for " + typeName + " with " + argCount + " arg(s)");
        }

        Class<?>[] paramTypes = ctor.getParameterTypes();
        ClassDesc[] paramDescs = new ClassDesc[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            paramDescs[i] = classDescForJavaClass(paramTypes[i]);
            Expression arg = oce.getArgument(i);
            emitExpression(code, arg, slots, params);
            TypeKind argKind = inferTypeKind(arg, slots, params);
            if (!paramTypes[i].isPrimitive() && argKind != TypeKind.REFERENCE) {
                ClassfileTypeUtils.emitBoxing(code, argKind);
            } else if (paramTypes[i].isPrimitive() && argKind != TypeKind.REFERENCE) {
                TypeKind targetKind = typeKindForJavaClass(paramTypes[i]);
                emitTypeWidening(code, argKind, targetKind);
            }
        }

        code.invokespecial(classDesc, INIT_NAME,
                MethodTypeDesc.of(CD_void, paramDescs));
    }

    // ── AssignExpr as expression (e.g., in return stmts) ──────────────────

    /**
     * Emit an AssignExpr used as an expression (not a statement).
     * E.g., {@code return x += 1;} — performs the assignment and leaves the result on the stack.
     */
    private static <C, W, O> void emitAssignExprAsExpression(
            CodeBuilder code,
            AssignExpr ae,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        // Perform the assignment (same as emitAssignExpr)
        emitAssignExpr(code, ae, slots, params);
        // Load the result back onto the stack
        if (ae.getTarget() instanceof NameExpr ne) {
            slots.loadVar(code, ne.getNameAsString());
        }
    }

    // ── Field access emission ─────────────────────────────────────────────

    private static <C, W, O> void emitFieldAccessExpr(
            CodeBuilder code,
            FieldAccessExpr fae,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String full = fae.toString();

        // Static field constants
        if ("java.math.MathContext.DECIMAL128".equals(full)) {
            code.getstatic(CD_MathContext, "DECIMAL128", CD_MathContext);
            return;
        }

        // FieldAccessExpr is also used as a class-name scope for static method calls
        // (e.g. java.lang.Math, org.mvel3.MVEL). Those are handled by the parent
        // MethodCallExpr. If we reach here, it's a standalone field access.
        throw new UnsupportedOperationException(
                "Field access not yet supported: " + fae);
    }

    // ── Reflection-based POJO method call ────────────────────────────────

    /**
     * Resolve the actual Java Class for a variable by searching CompilerParameters declarations.
     */
    private static <C, W, O> Class<?> resolveVariableClass(String varName, CompilerParameters<C, W, O> params) {
        // Check context variable
        if (params.contextDeclaration().name().equals(varName) && !params.contextDeclaration().type().isVoid()) {
            return params.contextDeclaration().type().getClazz();
        }
        // Check declared variables
        for (Declaration<?> decl : params.variableDeclarations()) {
            if (decl.name().equals(varName)) {
                return decl.type().getClazz();
            }
        }
        return null;
    }

    /**
     * Resolve the actual Java Class for a variable, also checking local slot types.
     */
    private static <C, W, O> Class<?> resolveVariableClassWithLocals(
            String varName, LocalSlotTable slots, CompilerParameters<C, W, O> params) {
        Class<?> fromParams = resolveVariableClass(varName, params);
        if (fromParams != null) return fromParams;
        if (slots.contains(varName)) {
            Type slotType = slots.type(varName);
            if (slotType.isClassOrInterfaceType()) {
                return resolveClassName(slotType.asClassOrInterfaceType().getNameWithScope());
            }
        }
        return null;
    }

    /**
     * Emit a POJO method call using reflection to resolve the actual method descriptor.
     * This ensures the invokevirtual descriptor matches the real method signature.
     */
    private static <C, W, O> void emitReflectedMethodCall(
            CodeBuilder code,
            MethodCallExpr mce,
            Class<?> scopeClass,
            NameExpr scopeName,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        String methodName = mce.getNameAsString();
        int argCount = mce.getArguments().size();

        // Find the method by name and argument count
        Method method = findMethodByNameAndArgCount(scopeClass, methodName, argCount);
        if (method == null) {
            throw new UnsupportedOperationException(
                    "Cannot resolve method: " + scopeClass.getName() + "." + methodName
                    + " with " + argCount + " arg(s)");
        }

        ClassDesc scopeDesc = ClassDesc.of(scopeClass.getCanonicalName());

        // Emit receiver
        emitExpression(code, scopeName, slots, params);

        // Build parameter descriptors from the actual method signature
        Class<?>[] paramTypes = method.getParameterTypes();
        ClassDesc[] paramDescs = new ClassDesc[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            paramDescs[i] = classDescForJavaClass(paramTypes[i]);

            // Emit argument
            Expression arg = mce.getArgument(i);
            emitExpression(code, arg, slots, params);

            // Convert the emitted value to match the parameter type
            TypeKind argKind = inferTypeKind(arg, slots, params);
            if (paramTypes[i] == Object.class && argKind != TypeKind.REFERENCE) {
                // Method takes Object, we have a primitive → box it
                ClassfileTypeUtils.emitBoxing(code, argKind);
            }
            // If method takes a primitive and we emitted a primitive, no conversion needed
            // (assuming matching types — int→int, etc.)
        }

        // Build the method type descriptor
        Class<?> returnType = method.getReturnType();
        ClassDesc returnDesc = classDescForJavaClass(returnType);
        MethodTypeDesc mtd = MethodTypeDesc.of(returnDesc, paramDescs);

        // Use invokeinterface for interface types, invokevirtual for classes
        if (scopeClass.isInterface()) {
            code.invokeinterface(scopeDesc, methodName, mtd);
        } else {
            code.invokevirtual(scopeDesc, methodName, mtd);
        }
    }

    /**
     * Fallback for when we can't resolve the class via reflection.
     * Uses heuristic-based method descriptors (all Object args, Object return).
     */
    private static <C, W, O> void emitHeuristicMethodCall(
            CodeBuilder code,
            MethodCallExpr mce,
            NameExpr scopeName,
            String methodName,
            LocalSlotTable slots,
            CompilerParameters<C, W, O> params) {

        Type scopeType = slots.type(scopeName.getNameAsString());
        ClassDesc scopeDesc = ClassfileTypeUtils.toClassDesc(scopeType);

        emitExpression(code, scopeName, slots, params);

        if (mce.getArguments().isEmpty()) {
            // No-arg method: infer return type from parent context
            ClassDesc returnDesc = inferMethodReturnType(mce);
            code.invokevirtual(scopeDesc, methodName, MethodTypeDesc.of(returnDesc));
        } else {
            // Method with args: box all to Object
            ClassDesc[] argDescs = new ClassDesc[mce.getArguments().size()];
            for (int i = 0; i < mce.getArguments().size(); i++) {
                Expression arg = mce.getArgument(i);
                emitExpression(code, arg, slots, params);
                TypeKind argKind = inferTypeKind(arg, slots, params);
                if (argKind != TypeKind.REFERENCE) {
                    ClassfileTypeUtils.emitBoxing(code, argKind);
                }
                argDescs[i] = CD_Object;
            }
            ClassDesc returnDesc = inferMethodReturnType(mce);
            code.invokevirtual(scopeDesc, methodName,
                    MethodTypeDesc.of(returnDesc, argDescs));
        }
    }

    /**
     * Find a public method on a class by name and argument count.
     * For overloaded methods, returns the first match.
     */
    private static Method findMethodByNameAndArgCount(Class<?> clazz, String methodName, int argCount) {
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(methodName) && m.getParameterCount() == argCount) {
                return m;
            }
        }
        return null;
    }

    /**
     * Find a public static method on a class by name and argument count.
     */
    private static Method findStaticMethodByNameAndArgCount(Class<?> clazz, String methodName, int argCount) {
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(methodName) && m.getParameterCount() == argCount
                    && Modifier.isStatic(m.getModifiers())) {
                return m;
            }
        }
        return null;
    }

    /**
     * Find a public constructor by argument count.
     */
    private static java.lang.reflect.Constructor<?> findConstructorByArgCount(Class<?> clazz, int argCount) {
        for (var ctor : clazz.getConstructors()) {
            if (ctor.getParameterCount() == argCount) {
                return ctor;
            }
        }
        return null;
    }

    /**
     * Resolve a simple or fully-qualified class name to a Class object.
     */
    private static Class<?> resolveClassName(String name) {
        // Try as-is (fully qualified)
        try { return Class.forName(name); } catch (ClassNotFoundException _) {}
        // Try java.lang package
        try { return Class.forName("java.lang." + name); } catch (ClassNotFoundException _) {}
        // Try java.math package (BigDecimal, BigInteger, MathContext)
        try { return Class.forName("java.math." + name); } catch (ClassNotFoundException _) {}
        // Try java.util package (List, Map, etc.)
        try { return Class.forName("java.util." + name); } catch (ClassNotFoundException _) {}
        return null;
    }

    /**
     * Resolve the Java Class that an expression evaluates to at runtime.
     * Used for chained method calls where we need to know the intermediate type.
     */
    private static <C, W, O> Class<?> resolveExpressionType(
            Expression expr, LocalSlotTable slots, CompilerParameters<C, W, O> params) {
        return switch (expr) {
            case NameExpr ne -> {
                // Check compiler params first (context + declared variables)
                Class<?> fromParams = resolveVariableClass(ne.getNameAsString(), params);
                if (fromParams != null) yield fromParams;
                // Check local slot table
                if (slots.contains(ne.getNameAsString())) {
                    Type slotType = slots.type(ne.getNameAsString());
                    if (slotType.isClassOrInterfaceType()) {
                        yield resolveClassName(slotType.asClassOrInterfaceType().getNameWithScope());
                    }
                }
                yield null;
            }
            case MethodCallExpr mce -> {
                if (mce.getScope().isEmpty()) yield null;
                Expression scope = mce.getScope().get();
                // Static method calls: resolve return type directly
                if (isStaticBigDecimalScope(scope)) {
                    String className = scope instanceof NameExpr ne ? ne.getNameAsString() : scope.toString();
                    Class<?> clazz = resolveClassName(className);
                    if (clazz != null) {
                        Method m = findStaticMethodByNameAndArgCount(clazz, mce.getNameAsString(), mce.getArguments().size());
                        if (m != null) yield m.getReturnType();
                    }
                }
                // Instance method calls: resolve the scope type, then find the method
                Class<?> scopeClass = resolveExpressionType(scope, slots, params);
                if (scopeClass != null) {
                    Method m = findMethodByNameAndArgCount(scopeClass, mce.getNameAsString(), mce.getArguments().size());
                    if (m != null) yield m.getReturnType();
                }
                yield null;
            }
            case CastExpr ce -> {
                Type castType = ce.getType();
                if (castType.isClassOrInterfaceType()) {
                    yield resolveClassName(castType.asClassOrInterfaceType().getNameWithScope());
                }
                yield null;
            }
            default -> null;
        };
    }

    /**
     * Convert a Java Class to a ClassDesc.
     */
    private static ClassDesc classDescForJavaClass(Class<?> clazz) {
        if (clazz == void.class) return CD_void;
        if (clazz == int.class) return CD_int;
        if (clazz == long.class) return CD_long;
        if (clazz == double.class) return CD_double;
        if (clazz == float.class) return CD_float;
        if (clazz == boolean.class) return CD_boolean;
        if (clazz == byte.class) return CD_byte;
        if (clazz == char.class) return CD_char;
        if (clazz == short.class) return CD_short;
        if (clazz.isArray()) {
            return classDescForJavaClass(clazz.getComponentType()).arrayType();
        }
        return ClassDesc.of(clazz.getCanonicalName());
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
            case BlockStmt bs -> bs.getStatements().stream().allMatch(ClassfileEvaluatorEmitter::isSupportedStatement);
            case IfStmt is -> isSupportedExpression(is.getCondition())
                    && isSupportedStatement(is.getThenStmt())
                    && is.getElseStmt().map(ClassfileEvaluatorEmitter::isSupportedStatement).orElse(true);
            case EmptyStmt _ -> true;
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
            case FieldAccessExpr fae -> isSupportedFieldAccess(fae);
            case ObjectCreationExpr oce -> isSupportedObjectCreation(oce);
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

        // Static methods on BigDecimal/BigInteger (valueOf, etc.)
        if (isStaticBigDecimalScope(scope)) return true;

        // Static methods on other known classes (Integer.rotateLeft, Double.parseDouble, etc.)
        if (isStaticKnownClassScope(scope)) return true;

        // Instance method calls: scope must be a supported expression (variable access or chained call)
        return isSupportedExpression(scope);
    }

    private static boolean isSupportedFieldAccess(FieldAccessExpr fae) {
        String full = fae.toString();
        // Static field constants (e.g., java.math.MathContext.DECIMAL128)
        if ("java.math.MathContext.DECIMAL128".equals(full)) return true;
        return false;
    }

    private static boolean isSupportedObjectCreation(ObjectCreationExpr oce) {
        String typeName = oce.getType().getNameWithScope();
        if ("BigDecimal".equals(typeName) || "java.math.BigDecimal".equals(typeName)) {
            return oce.getArguments().stream().allMatch(ClassfileEvaluatorEmitter::isSupportedExpression);
        }
        return false;
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

    private static boolean isStaticBigDecimalScope(Expression scope) {
        if (scope instanceof NameExpr ne) {
            return "BigDecimal".equals(ne.getNameAsString())
                    || "BigInteger".equals(ne.getNameAsString());
        }
        if (scope instanceof FieldAccessExpr fae) {
            String name = fae.toString();
            return "java.math.BigDecimal".equals(name) || "java.math.BigInteger".equals(name);
        }
        return false;
    }

    /**
     * Recognize static method scopes for well-known JDK classes (Integer, Double, etc.).
     * These are NameExpr references to class names that are NOT local variables.
     */
    private static boolean isStaticKnownClassScope(Expression scope) {
        if (scope instanceof NameExpr ne) {
            return switch (ne.getNameAsString()) {
                case "Integer", "Long", "Double", "Float", "Short", "Byte",
                     "Character", "Boolean", "String" -> true;
                default -> false;
            };
        }
        return false;
    }

    // ── Type inference helpers ─────────────────────────────────────────────

    /**
     * Infer the JVM TypeKind that an expression will leave on the stack.
     * This is a best-effort heuristic based on the AST structure.
     */
    static TypeKind inferTypeKind(Expression expr, LocalSlotTable slots) {
        return inferTypeKind(expr, slots, null);
    }

    /**
     * Infer the JVM TypeKind that an expression will leave on the stack.
     * When params is non-null, uses reflection to resolve POJO method return types.
     */
    static <C, W, O> TypeKind inferTypeKind(Expression expr, LocalSlotTable slots,
                                             CompilerParameters<C, W, O> params) {
        return switch (expr) {
            case IntegerLiteralExpr _ -> TypeKind.INT;
            case LongLiteralExpr _ -> TypeKind.LONG;
            case DoubleLiteralExpr _ -> TypeKind.DOUBLE;
            case BooleanLiteralExpr _ -> TypeKind.BOOLEAN;
            case StringLiteralExpr _ -> TypeKind.REFERENCE;
            case NullLiteralExpr _ -> TypeKind.REFERENCE;
            case CharLiteralExpr _ -> TypeKind.INT;
            case NameExpr ne -> {
                if (slots.contains(ne.getNameAsString())) {
                    yield slots.typeKind(ne.getNameAsString());
                }
                // May be a class name reference (BigDecimal, Integer, etc.)
                yield TypeKind.REFERENCE;
            }
            case EnclosedExpr ee -> inferTypeKind(ee.getInner(), slots, params);
            case CastExpr ce -> {
                if (ce.getType().isPrimitiveType()) {
                    yield ClassfileTypeUtils.toTypeKind(ce.getType());
                }
                yield TypeKind.REFERENCE;
            }
            case UnaryExpr ue -> {
                if (ue.getOperator() == UnaryExpr.Operator.LOGICAL_COMPLEMENT) {
                    yield TypeKind.BOOLEAN;
                }
                if (ue.getOperator() == UnaryExpr.Operator.BITWISE_COMPLEMENT) {
                    yield inferTypeKind(ue.getExpression(), slots, params);
                }
                yield inferTypeKind(ue.getExpression(), slots, params);
            }
            case BinaryExpr be -> {
                BinaryExpr.Operator op = be.getOperator();
                if (op == BinaryExpr.Operator.AND || op == BinaryExpr.Operator.OR
                        || op == BinaryExpr.Operator.GREATER || op == BinaryExpr.Operator.LESS
                        || op == BinaryExpr.Operator.GREATER_EQUALS || op == BinaryExpr.Operator.LESS_EQUALS
                        || op == BinaryExpr.Operator.EQUALS || op == BinaryExpr.Operator.NOT_EQUALS) {
                    yield TypeKind.BOOLEAN;
                }
                // Arithmetic/bitwise: return the widened type of both operands
                TypeKind left = inferTypeKind(be.getLeft(), slots, params);
                TypeKind right = inferTypeKind(be.getRight(), slots, params);
                yield widenTypeKind(left, right);
            }
            case MethodCallExpr mce -> {
                // Math static calls return double
                if (mce.getScope().isPresent() && isStaticMathScope(mce.getScope().get())) {
                    yield TypeKind.DOUBLE;
                }
                // Use reflection to resolve method return types
                if (params != null && mce.getScope().isPresent()) {
                    Expression scope = mce.getScope().get();
                    // Direct variable scope: resolve via compiler params + locals
                    if (scope instanceof NameExpr scopeName) {
                        Class<?> scopeClass = resolveVariableClassWithLocals(scopeName.getNameAsString(), slots, params);
                        if (scopeClass != null) {
                            Method m = findMethodByNameAndArgCount(scopeClass, mce.getNameAsString(),
                                                                   mce.getArguments().size());
                            if (m != null) {
                                yield typeKindForJavaClass(m.getReturnType());
                            }
                        }
                    }
                    // Chained calls or static methods: resolve expression type
                    Class<?> scopeType = resolveExpressionType(scope, slots, params);
                    if (scopeType != null) {
                        Method m = findMethodByNameAndArgCount(scopeType, mce.getNameAsString(),
                                                               mce.getArguments().size());
                        if (m != null) {
                            yield typeKindForJavaClass(m.getReturnType());
                        }
                    }
                    // Static BigDecimal/BigInteger methods
                    if (isStaticBigDecimalScope(scope)) {
                        String className = scope instanceof NameExpr ne ? ne.getNameAsString() : scope.toString();
                        Class<?> clazz = resolveClassName(className);
                        if (clazz != null) {
                            Method m = findStaticMethodByNameAndArgCount(clazz, mce.getNameAsString(),
                                                                         mce.getArguments().size());
                            if (m != null) yield typeKindForJavaClass(m.getReturnType());
                        }
                    }
                    // Known class static methods
                    if (isStaticKnownClassScope(scope)) {
                        String className = ((NameExpr) scope).getNameAsString();
                        Class<?> clazz = resolveClassName(className);
                        if (clazz != null) {
                            Method m = findStaticMethodByNameAndArgCount(clazz, mce.getNameAsString(),
                                                                         mce.getArguments().size());
                            if (m != null) yield typeKindForJavaClass(m.getReturnType());
                        }
                    }
                }
                yield TypeKind.REFERENCE;
            }
            case FieldAccessExpr _ -> TypeKind.REFERENCE;
            case ObjectCreationExpr _ -> TypeKind.REFERENCE;
            case AssignExpr ae -> {
                if (ae.getTarget() instanceof NameExpr ne && slots.contains(ne.getNameAsString())) {
                    yield slots.typeKind(ne.getNameAsString());
                }
                yield inferTypeKind(ae.getValue(), slots, params);
            }
            default -> TypeKind.REFERENCE;
        };
    }

    /**
     * Map a Java Class to its JVM TypeKind.
     */
    private static TypeKind typeKindForJavaClass(Class<?> clazz) {
        if (clazz == int.class) return TypeKind.INT;
        if (clazz == long.class) return TypeKind.LONG;
        if (clazz == double.class) return TypeKind.DOUBLE;
        if (clazz == float.class) return TypeKind.FLOAT;
        if (clazz == boolean.class) return TypeKind.INT; // booleans are int on JVM
        if (clazz == byte.class) return TypeKind.BYTE;
        if (clazz == char.class) return TypeKind.CHAR;
        if (clazz == short.class) return TypeKind.SHORT;
        if (clazz == void.class) return TypeKind.VOID;
        return TypeKind.REFERENCE;
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
        for (Statement stmt : body.getStatements()) {
            if (!isSupportedStatement(stmt)) {
                return "unsupported statement: " + stmt.getClass().getSimpleName()
                        + " → " + stmt.toString().trim();
            }
        }
        if (!containsReturn(body)) return "no return statement";

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
