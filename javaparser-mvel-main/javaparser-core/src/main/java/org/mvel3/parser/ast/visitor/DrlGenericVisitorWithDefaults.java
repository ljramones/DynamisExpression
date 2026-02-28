package org.mvel3.parser.ast.visitor;

import com.github.javaparser.ast.visitor.GenericVisitorWithDefaults;
import org.mvel3.parser.ast.expr.BigDecimalLiteralExpr;
import org.mvel3.parser.ast.expr.BigIntegerLiteralExpr;
import org.mvel3.parser.ast.expr.DrlxExpression;
import org.mvel3.parser.ast.expr.FullyQualifiedInlineCastExpr;
import org.mvel3.parser.ast.expr.HalfBinaryExpr;
import org.mvel3.parser.ast.expr.InlineCastExpr;
import org.mvel3.parser.ast.expr.ListCreationLiteralExpression;
import org.mvel3.parser.ast.expr.ListCreationLiteralExpressionElement;
import org.mvel3.parser.ast.expr.MapCreationLiteralExpression;
import org.mvel3.parser.ast.expr.MapCreationLiteralExpressionKeyValuePair;
import org.mvel3.parser.ast.expr.ModifyStatement;
import org.mvel3.parser.ast.expr.NullSafeFieldAccessExpr;
import org.mvel3.parser.ast.expr.NullSafeMethodCallExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralChunkExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralInfiniteChunkExpr;
import org.mvel3.parser.ast.expr.WithStatement;

public abstract class DrlGenericVisitorWithDefaults<R, A> extends GenericVisitorWithDefaults<R, A> implements DrlGenericVisitor<R, A> {

    public R visit(DrlxExpression n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(InlineCastExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(FullyQualifiedInlineCastExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(NullSafeFieldAccessExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(NullSafeMethodCallExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(TemporalLiteralExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(TemporalLiteralChunkExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(HalfBinaryExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(BigDecimalLiteralExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(BigIntegerLiteralExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(TemporalLiteralInfiniteChunkExpr n, A arg) {
        return defaultAction(n, arg);
    }

    public R visit(ModifyStatement n, A arg) {
        return defaultAction(n, arg);
    }

    @Override
    public R visit(MapCreationLiteralExpression n, A arg) {
        return defaultAction(n, arg);
    }

    @Override
    public R visit(MapCreationLiteralExpressionKeyValuePair n, A arg) {
        return defaultAction(n, arg);
    }

    @Override
    public R visit(ListCreationLiteralExpression n, A arg) {
        return defaultAction(n, arg);
    }

    @Override
    public R visit(ListCreationLiteralExpressionElement n, A arg) {
        return defaultAction(n, arg);
    }

    @Override
    public R visit(WithStatement n, A arg) {
        return defaultAction(n, arg);
    }
}
