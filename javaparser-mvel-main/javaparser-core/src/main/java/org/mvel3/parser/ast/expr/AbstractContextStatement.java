package org.mvel3.parser.ast.expr;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import static com.github.javaparser.utils.Utils.assertNotNull;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.AbstractContextStatementMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class AbstractContextStatement<T extends AbstractContextStatement, R extends Expression> extends Statement {

    private Expression target;

    private NodeList<Statement> expressions;

    @AllFieldsConstructor
    public AbstractContextStatement(Expression target, NodeList<Statement> expressions) {
        this(null, target, expressions);
    }

    public AbstractContextStatement(TokenRange tokenRange) {
        this(tokenRange, null, new NodeList<>());
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public AbstractContextStatement(TokenRange tokenRange, Expression target, NodeList<Statement> expressions) {
        super(tokenRange);
        setTarget(target);
        setExpressions(expressions);
        customInitialization();
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Expression getTarget() {
        return target;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    @SuppressWarnings("unchecked")
    public T setTarget(final Expression target) {
        assertNotNull(target);
        if (target == this.target) {
            return (T) this;
        }
        notifyPropertyChange(ObservableProperty.TARGET, this.target, target);
        if (this.target != null)
            this.target.setParentNode(null);
        this.target = target;
        setAsParentNodeOf(target);
        return (T) this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public NodeList<Statement> getExpressions() {
        return expressions;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    @SuppressWarnings("unchecked")
    public T setExpressions(final NodeList<Statement> expressions) {
        assertNotNull(expressions);
        if (expressions == this.expressions) {
            return (T) this;
        }
        notifyPropertyChange(ObservableProperty.EXPRESSIONS, this.expressions, expressions);
        if (this.expressions != null)
            this.expressions.setParentNode(null);
        this.expressions = expressions;
        setAsParentNodeOf(expressions);
        return (T) this;
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

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isAbstractContextStatement() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public AbstractContextStatement asAbstractContextStatement() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<AbstractContextStatement> toAbstractContextStatement() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifAbstractContextStatement(Consumer<AbstractContextStatement> action) {
        action.accept(this);
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
                expressions.set(i, (Statement) replacementNode);
                return true;
            }
        }
        if (node == target) {
            setTarget((Expression) replacementNode);
            return true;
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public AbstractContextStatement<?, ?> clone() {
        return (AbstractContextStatement<?, ?>) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public AbstractContextStatementMetaModel getMetaModel() {
        return JavaParserMetaModel.abstractContextStatementMetaModel;
    }
}
