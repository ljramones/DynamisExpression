/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mvel3.parser.ast.expr;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.observer.ObservableProperty;
import static com.github.javaparser.utils.Utils.assertNotNull;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.RuleJoinedPatternsMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class RuleJoinedPatterns extends RuleItem {

    private Type type;

    private NodeList<RuleItem> items;

    public enum Type {

        AND, OR
    }

    @AllFieldsConstructor
    public RuleJoinedPatterns(Type type, NodeList<RuleItem> items) {
        this(null, type, items);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public RuleJoinedPatterns(TokenRange tokenRange, Type type, NodeList<RuleItem> items) {
        super(tokenRange);
        setType(type);
        setItems(items);
        customInitialization();
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public Type getType() {
        return type;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public NodeList<RuleItem> getItems() {
        return items;
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
    public RuleJoinedPatterns setItems(final NodeList<RuleItem> items) {
        assertNotNull(items);
        if (items == this.items) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.ITEMS, this.items, items);
        if (this.items != null)
            this.items.setParentNode(null);
        this.items = items;
        setAsParentNodeOf(items);
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public RuleJoinedPatterns setType(final Type type) {
        assertNotNull(type);
        if (type == this.type) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.TYPE, this.type, type);
        this.type = type;
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.RemoveMethodGenerator")
    public boolean remove(Node node) {
        if (node == null) {
            return false;
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == node) {
                items.remove(i);
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
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == node) {
                items.set(i, (RuleItem) replacementNode);
                return true;
            }
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public RuleJoinedPatterns clone() {
        return (RuleJoinedPatterns) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public RuleJoinedPatternsMetaModel getMetaModel() {
        return JavaParserMetaModel.ruleJoinedPatternsMetaModel;
    }
}
