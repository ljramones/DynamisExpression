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
import com.github.javaparser.ast.NodeList;
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
import com.github.javaparser.metamodel.PointFreeExprMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class PointFreeExpr extends Expression {

    private Expression left;

    private NodeList<Expression> right;

    private SimpleName operator;

    private boolean negated;

    private Expression arg1;

    private Expression arg2;

    private Expression arg3;

    private Expression arg4;

    @AllFieldsConstructor
    public PointFreeExpr(Expression left, NodeList<Expression> right, SimpleName operator, boolean negated, Expression arg1, Expression arg2, Expression arg3, Expression arg4) {
        this(null, left, right, operator, negated, arg1, arg2, arg3, arg4);
    }

    public PointFreeExpr(TokenRange tokenRange, Expression left, NodeList<Expression> right, SimpleName operator, Boolean negated, Expression arg1, Expression arg2, Expression arg3, Expression arg4) {
        super(tokenRange);
        this.left = left;
        this.right = right;
        this.operator = operator;
        this.negated = negated;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.arg4 = arg4;
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
    public Expression getLeft() {
        return left;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public NodeList<Expression> getRight() {
        return right;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public SimpleName getOperator() {
        return operator;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public boolean isNegated() {
        return negated;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Expression getArg1() {
        return arg1;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Expression getArg2() {
        return arg2;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Expression getArg3() {
        return arg3;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Expression getArg4() {
        return arg4;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isPointFreeExpr() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public PointFreeExpr asPointFreeExpr() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<PointFreeExpr> toPointFreeExpr() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifPointFreeExpr(Consumer<PointFreeExpr> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public PointFreeExpr setArg1(final Expression arg1) {
        assertNotNull(arg1);
        if (arg1 == this.arg1) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.ARG1, this.arg1, arg1);
        if (this.arg1 != null)
            this.arg1.setParentNode(null);
        this.arg1 = arg1;
        setAsParentNodeOf(arg1);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public PointFreeExpr setArg2(final Expression arg2) {
        assertNotNull(arg2);
        if (arg2 == this.arg2) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.ARG2, this.arg2, arg2);
        if (this.arg2 != null)
            this.arg2.setParentNode(null);
        this.arg2 = arg2;
        setAsParentNodeOf(arg2);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public PointFreeExpr setArg3(final Expression arg3) {
        assertNotNull(arg3);
        if (arg3 == this.arg3) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.ARG3, this.arg3, arg3);
        if (this.arg3 != null)
            this.arg3.setParentNode(null);
        this.arg3 = arg3;
        setAsParentNodeOf(arg3);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public PointFreeExpr setArg4(final Expression arg4) {
        assertNotNull(arg4);
        if (arg4 == this.arg4) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.ARG4, this.arg4, arg4);
        if (this.arg4 != null)
            this.arg4.setParentNode(null);
        this.arg4 = arg4;
        setAsParentNodeOf(arg4);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public PointFreeExpr setLeft(final Expression left) {
        assertNotNull(left);
        if (left == this.left) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.LEFT, this.left, left);
        if (this.left != null)
            this.left.setParentNode(null);
        this.left = left;
        setAsParentNodeOf(left);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public PointFreeExpr setNegated(final boolean negated) {
        if (negated == this.negated) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.NEGATED, this.negated, negated);
        this.negated = negated;
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public PointFreeExpr setOperator(final SimpleName operator) {
        assertNotNull(operator);
        if (operator == this.operator) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.OPERATOR, this.operator, operator);
        if (this.operator != null)
            this.operator.setParentNode(null);
        this.operator = operator;
        setAsParentNodeOf(operator);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public PointFreeExpr setRight(final NodeList<Expression> right) {
        assertNotNull(right);
        if (right == this.right) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.RIGHT, this.right, right);
        if (this.right != null)
            this.right.setParentNode(null);
        this.right = right;
        setAsParentNodeOf(right);
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.RemoveMethodGenerator")
    public boolean remove(Node node) {
        if (node == null) {
            return false;
        }
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i) == node) {
                right.remove(i);
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
        if (node == arg1) {
            setArg1((Expression) replacementNode);
            return true;
        }
        if (node == arg2) {
            setArg2((Expression) replacementNode);
            return true;
        }
        if (node == arg3) {
            setArg3((Expression) replacementNode);
            return true;
        }
        if (node == arg4) {
            setArg4((Expression) replacementNode);
            return true;
        }
        if (node == left) {
            setLeft((Expression) replacementNode);
            return true;
        }
        if (node == operator) {
            setOperator((SimpleName) replacementNode);
            return true;
        }
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i) == node) {
                right.set(i, (Expression) replacementNode);
                return true;
            }
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public PointFreeExpr clone() {
        return (PointFreeExpr) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public PointFreeExprMetaModel getMetaModel() {
        return JavaParserMetaModel.pointFreeExprMetaModel;
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public PointFreeExpr(TokenRange tokenRange, Expression left, NodeList<Expression> right, SimpleName operator, boolean negated, Expression arg1, Expression arg2, Expression arg3, Expression arg4) {
        super(tokenRange);
        setLeft(left);
        setRight(right);
        setOperator(operator);
        setNegated(negated);
        setArg1(arg1);
        setArg2(arg2);
        setArg3(arg3);
        setArg4(arg4);
        customInitialization();
    }
}
