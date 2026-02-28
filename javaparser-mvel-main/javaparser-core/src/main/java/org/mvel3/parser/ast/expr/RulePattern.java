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
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.observer.ObservableProperty;
import static com.github.javaparser.utils.Utils.assertNotNull;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.RulePatternMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class RulePattern extends RuleItem {

    private SimpleName type;

    private SimpleName bind;

    private OOPathExpr expr;

    @AllFieldsConstructor
    public RulePattern(SimpleName type, SimpleName bind, OOPathExpr expr) {
        this(null, type, bind, expr);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public RulePattern(TokenRange tokenRange, SimpleName type, SimpleName bind, OOPathExpr expr) {
        super(tokenRange);
        setType(type);
        setBind(bind);
        setExpr(expr);
        customInitialization();
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public SimpleName getType() {
        return type;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public SimpleName getBind() {
        return bind;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public OOPathExpr getExpr() {
        return expr;
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
    public RulePattern setBind(final SimpleName bind) {
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
    public RulePattern setExpr(final OOPathExpr expr) {
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

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public RulePattern setType(final SimpleName type) {
        assertNotNull(type);
        if (type == this.type) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.TYPE, this.type, type);
        if (this.type != null)
            this.type.setParentNode(null);
        this.type = type;
        setAsParentNodeOf(type);
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
            setExpr((OOPathExpr) replacementNode);
            return true;
        }
        if (node == type) {
            setType((SimpleName) replacementNode);
            return true;
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public RulePattern clone() {
        return (RulePattern) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public RulePatternMetaModel getMetaModel() {
        return JavaParserMetaModel.rulePatternMetaModel;
    }
}
