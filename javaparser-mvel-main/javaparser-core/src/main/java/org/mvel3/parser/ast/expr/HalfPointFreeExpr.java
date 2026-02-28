/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2016 The JavaParser Team.
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * Modified by Red Hat, Inc.
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
import com.github.javaparser.metamodel.HalfPointFreeExprMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class HalfPointFreeExpr extends Expression {

    private NodeList<Expression> right;

    private SimpleName operator;

    private boolean negated;

    private Expression arg1;

    private Expression arg2;

    private Expression arg3;

    private Expression arg4;

    @AllFieldsConstructor
    public HalfPointFreeExpr(NodeList<Expression> right, SimpleName operator, boolean negated, Expression arg1, Expression arg2, Expression arg3, Expression arg4) {
        this(null, right, operator, negated, arg1, arg2, arg3, arg4);
    }

    public HalfPointFreeExpr(TokenRange tokenRange, NodeList<Expression> right, SimpleName operator, Boolean negated, Expression arg1, Expression arg2, Expression arg3, Expression arg4) {
        super(tokenRange);
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
    public boolean isHalfPointFreeExpr() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public HalfPointFreeExpr asHalfPointFreeExpr() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<HalfPointFreeExpr> toHalfPointFreeExpr() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifHalfPointFreeExpr(Consumer<HalfPointFreeExpr> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public HalfPointFreeExpr setArg1(final Expression arg1) {
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
    public HalfPointFreeExpr setArg2(final Expression arg2) {
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
    public HalfPointFreeExpr setArg3(final Expression arg3) {
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
    public HalfPointFreeExpr setArg4(final Expression arg4) {
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
    public HalfPointFreeExpr setNegated(final boolean negated) {
        if (negated == this.negated) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.NEGATED, this.negated, negated);
        this.negated = negated;
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public HalfPointFreeExpr setOperator(final SimpleName operator) {
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
    public HalfPointFreeExpr setRight(final NodeList<Expression> right) {
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
    public HalfPointFreeExpr clone() {
        return (HalfPointFreeExpr) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public HalfPointFreeExprMetaModel getMetaModel() {
        return JavaParserMetaModel.halfPointFreeExprMetaModel;
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public HalfPointFreeExpr(TokenRange tokenRange, NodeList<Expression> right, SimpleName operator, boolean negated, Expression arg1, Expression arg2, Expression arg3, Expression arg4) {
        super(tokenRange);
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
