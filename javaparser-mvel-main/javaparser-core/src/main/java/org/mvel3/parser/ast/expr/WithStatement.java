package org.mvel3.parser.ast.expr;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.WithStatementMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class WithStatement extends AbstractContextStatement<WithStatement, Expression> {

    @AllFieldsConstructor
    public WithStatement(Expression target, NodeList<Statement> expressions) {
        this(null, target, expressions);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public WithStatement(TokenRange tokenRange, Expression target, NodeList<Statement> expressions) {
        super(tokenRange, target, expressions);
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

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isWithStatement() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public WithStatement asWithStatement() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<WithStatement> toWithStatement() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifWithStatement(Consumer<WithStatement> action) {
        action.accept(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public WithStatement clone() {
        return (WithStatement) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public WithStatementMetaModel getMetaModel() {
        return JavaParserMetaModel.withStatementMetaModel;
    }
}
