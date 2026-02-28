/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package org.mvel3.parser.ast.expr;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.observer.ObservableProperty;
import static com.github.javaparser.utils.Utils.assertNotNull;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.DrlxExpressionMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class DrlxExpression extends Expression {

    private SimpleName bind;

    private Expression expr;

    @AllFieldsConstructor
    public DrlxExpression(SimpleName bind, Expression expr) {
        super(bind != null ? new TokenRange(bind.getTokenRange().orElseThrow(() -> new IllegalStateException("Bind doesn't contain token range! " + bind)).getBegin(), expr.getTokenRange().orElseThrow(() -> new IllegalStateException("Expression doesn't contain token range! " + expr.toString())).getEnd()) : expr.getTokenRange().orElseThrow(() -> new IllegalStateException("Expression doesn't contain token range! " + expr.toString())));
        this.bind = bind;
        this.expr = expr;
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
    public SimpleName getBind() {
        return bind;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Expression getExpr() {
        return expr;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isDrlxExpression() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public DrlxExpression asDrlxExpression() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<DrlxExpression> toDrlxExpression() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifDrlxExpression(Consumer<DrlxExpression> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public DrlxExpression setBind(final SimpleName bind) {
        assertNotNull(bind);
        if (bind == this.bind) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.BIND, this.bind, bind);
        if (this.bind != null)
            this.bind.setParentNode(null);
        this.bind = bind;
        setAsParentNodeOf(bind);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public DrlxExpression setExpr(final Expression expr) {
        assertNotNull(expr);
        if (expr == this.expr) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.EXPR, this.expr, expr);
        if (this.expr != null)
            this.expr.setParentNode(null);
        this.expr = expr;
        setAsParentNodeOf(expr);
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.ReplaceMethodGenerator")
    public boolean replace(Node node, Node replacementNode) {
        if (node == null) {
            return false;
        }
        if (node == bind) {
            setBind((SimpleName) replacementNode);
            return true;
        }
        if (node == expr) {
            setExpr((Expression) replacementNode);
            return true;
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public DrlxExpression clone() {
        return (DrlxExpression) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public DrlxExpressionMetaModel getMetaModel() {
        return JavaParserMetaModel.drlxExpressionMetaModel;
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public DrlxExpression(TokenRange tokenRange, SimpleName bind, Expression expr) {
        super(tokenRange);
        setBind(bind);
        setExpr(expr);
        customInitialization();
    }
}
