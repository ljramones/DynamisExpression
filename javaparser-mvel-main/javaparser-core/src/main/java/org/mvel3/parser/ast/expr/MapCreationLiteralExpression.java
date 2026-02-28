package org.mvel3.parser.ast.expr;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.observer.ObservableProperty;
import static com.github.javaparser.utils.Utils.assertNotNull;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.MapCreationLiteralExpressionMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class MapCreationLiteralExpression extends Expression {

    private NodeList<Expression> expressions;

    @AllFieldsConstructor
    public MapCreationLiteralExpression(NodeList<Expression> expressions) {
        this(null, expressions);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public MapCreationLiteralExpression(TokenRange tokenRange, NodeList<Expression> expressions) {
        super(tokenRange);
        setExpressions(expressions);
        customInitialization();
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.AcceptGenerator")
    public <R, A> R accept(final GenericVisitor<R, A> v, final A arg) {
        return v.visit(this, arg);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.AcceptGenerator")
    public <A> void accept(final VoidVisitor<A> v, final A arg) {
        v.visit(this, arg);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public NodeList<Expression> getExpressions() {
        return expressions;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isMapCreationLiteralExpression() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public MapCreationLiteralExpression asMapCreationLiteralExpression() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<MapCreationLiteralExpression> toMapCreationLiteralExpression() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifMapCreationLiteralExpression(Consumer<MapCreationLiteralExpression> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public MapCreationLiteralExpression setExpressions(final NodeList<Expression> expressions) {
        assertNotNull(expressions);
        if (expressions == this.expressions) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.EXPRESSIONS, this.expressions, expressions);
        if (this.expressions != null)
            this.expressions.setParentNode(null);
        this.expressions = expressions;
        setAsParentNodeOf(expressions);
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.RemoveMethodGenerator")
    public boolean remove(Node node) {
        if (node == null) {
            return false;
        }
        for (int i = 0; i < expressions.size(); i++) {
            if (expressions.get(i) == node) {
                expressions.remove(i);
                return true;
            }
        }
        return super.remove(node);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.ReplaceMethodGenerator")
    public boolean replace(Node node, Node replacementNode) {
        if (node == null) {
            return false;
        }
        for (int i = 0; i < expressions.size(); i++) {
            if (expressions.get(i) == node) {
                expressions.set(i, (Expression) replacementNode);
                return true;
            }
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public MapCreationLiteralExpression clone() {
        return (MapCreationLiteralExpression) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public MapCreationLiteralExpressionMetaModel getMetaModel() {
        return JavaParserMetaModel.mapCreationLiteralExpressionMetaModel;
    }
}
