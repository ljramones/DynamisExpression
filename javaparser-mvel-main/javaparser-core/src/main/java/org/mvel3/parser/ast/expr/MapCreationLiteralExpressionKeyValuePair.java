package org.mvel3.parser.ast.expr;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.observer.ObservableProperty;
import static com.github.javaparser.utils.Utils.assertNotNull;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.MapCreationLiteralExpressionKeyValuePairMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class MapCreationLiteralExpressionKeyValuePair extends Expression {

    private Expression key;

    private Expression value;

    @AllFieldsConstructor
    public MapCreationLiteralExpressionKeyValuePair(Expression key, Expression value) {
        this(null, key, value);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public MapCreationLiteralExpressionKeyValuePair(TokenRange tokenRange, Expression key, Expression value) {
        super(tokenRange);
        setKey(key);
        setValue(value);
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
    public Expression getKey() {
        return key;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Expression getValue() {
        return value;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isMapCreationLiteralExpressionKeyValuePair() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public MapCreationLiteralExpressionKeyValuePair asMapCreationLiteralExpressionKeyValuePair() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<MapCreationLiteralExpressionKeyValuePair> toMapCreationLiteralExpressionKeyValuePair() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifMapCreationLiteralExpressionKeyValuePair(Consumer<MapCreationLiteralExpressionKeyValuePair> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public MapCreationLiteralExpressionKeyValuePair setKey(final Expression key) {
        assertNotNull(key);
        if (key == this.key) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.KEY, this.key, key);
        if (this.key != null)
            this.key.setParentNode(null);
        this.key = key;
        setAsParentNodeOf(key);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public MapCreationLiteralExpressionKeyValuePair setValue(final Expression value) {
        assertNotNull(value);
        if (value == this.value) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.VALUE, this.value, value);
        if (this.value != null)
            this.value.setParentNode(null);
        this.value = value;
        setAsParentNodeOf(value);
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.ReplaceMethodGenerator")
    public boolean replace(Node node, Node replacementNode) {
        if (node == null) {
            return false;
        }
        if (node == key) {
            setKey((Expression) replacementNode);
            return true;
        }
        if (node == value) {
            setValue((Expression) replacementNode);
            return true;
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public MapCreationLiteralExpressionKeyValuePair clone() {
        return (MapCreationLiteralExpressionKeyValuePair) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public MapCreationLiteralExpressionKeyValuePairMetaModel getMetaModel() {
        return JavaParserMetaModel.mapCreationLiteralExpressionKeyValuePairMetaModel;
    }
}
