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
import com.github.javaparser.metamodel.OOPathChunkMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;
import com.github.javaparser.metamodel.OptionalProperty;

public class OOPathChunk extends Expression {

    private SimpleName field;

    @OptionalProperty
    private SimpleName inlineCast;

    private NodeList<DrlxExpression> condition;

    private boolean singleValue;

    private boolean passive;

    @AllFieldsConstructor
    public OOPathChunk(SimpleName field, SimpleName inlineCast, NodeList<DrlxExpression> condition) {
        this(null, field, inlineCast, condition);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public OOPathChunk(TokenRange tokenRange, SimpleName field, SimpleName inlineCast, NodeList<DrlxExpression> condition) {
        super(tokenRange);
        setField(field);
        setInlineCast(inlineCast);
        setCondition(condition);
        customInitialization();
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public SimpleName getField() {
        return field;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Optional<SimpleName> getInlineCast() {
        return Optional.ofNullable(inlineCast);
    }

    public NodeList<DrlxExpression> getConditions() {
        return condition;
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

    public OOPathChunk singleValue() {
        singleValue = true;
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public boolean isSingleValue() {
        return singleValue;
    }

    public OOPathChunk passive() {
        passive = true;
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public boolean isPassive() {
        return passive;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isOOPathChunk() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public OOPathChunk asOOPathChunk() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<OOPathChunk> toOOPathChunk() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifOOPathChunk(Consumer<OOPathChunk> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public NodeList<DrlxExpression> getCondition() {
        return condition;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public OOPathChunk setCondition(final NodeList<DrlxExpression> condition) {
        assertNotNull(condition);
        if (condition == this.condition) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.CONDITION, this.condition, condition);
        if (this.condition != null)
            this.condition.setParentNode(null);
        this.condition = condition;
        setAsParentNodeOf(condition);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public OOPathChunk setField(final SimpleName field) {
        assertNotNull(field);
        if (field == this.field) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.FIELD, this.field, field);
        if (this.field != null)
            this.field.setParentNode(null);
        this.field = field;
        setAsParentNodeOf(field);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public OOPathChunk setInlineCast(final SimpleName inlineCast) {
        if (inlineCast == this.inlineCast) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.INLINE_CAST, this.inlineCast, inlineCast);
        if (this.inlineCast != null)
            this.inlineCast.setParentNode(null);
        this.inlineCast = inlineCast;
        setAsParentNodeOf(inlineCast);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public OOPathChunk setPassive(final boolean passive) {
        if (passive == this.passive) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.PASSIVE, this.passive, passive);
        this.passive = passive;
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public OOPathChunk setSingleValue(final boolean singleValue) {
        if (singleValue == this.singleValue) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.SINGLE_VALUE, this.singleValue, singleValue);
        this.singleValue = singleValue;
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.RemoveMethodGenerator")
    public boolean remove(Node node) {
        if (node == null) {
            return false;
        }
        for (int i = 0; i < condition.size(); i++) {
            if (condition.get(i) == node) {
                condition.remove(i);
                return true;
            }
        }
        if (inlineCast != null) {
            if (node == inlineCast) {
                removeInlineCast();
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
        for (int i = 0; i < condition.size(); i++) {
            if (condition.get(i) == node) {
                condition.set(i, (DrlxExpression) replacementNode);
                return true;
            }
        }
        if (node == field) {
            setField((SimpleName) replacementNode);
            return true;
        }
        if (inlineCast != null) {
            if (node == inlineCast) {
                setInlineCast((SimpleName) replacementNode);
                return true;
            }
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public OOPathChunk clone() {
        return (OOPathChunk) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public OOPathChunkMetaModel getMetaModel() {
        return JavaParserMetaModel.oOPathChunkMetaModel;
    }

    @Generated("com.github.javaparser.generator.core.node.RemoveMethodGenerator")
    public OOPathChunk removeInlineCast() {
        return setInlineCast((SimpleName) null);
    }
}
