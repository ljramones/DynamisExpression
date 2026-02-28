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
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.LiteralExpr;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.metamodel.TemporalLiteralExprMetaModel;
import java.util.Optional;
import java.util.function.Consumer;
import static com.github.javaparser.utils.Utils.assertNotNull;
import com.github.javaparser.ast.Generated;

public class TemporalLiteralExpr extends LiteralExpr {

    private NodeList<TemporalChunkExpr> chunks;

    @AllFieldsConstructor
    public TemporalLiteralExpr(NodeList<TemporalChunkExpr> chunks) {
        this((TokenRange) null, chunks);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public TemporalLiteralExpr(TokenRange tokenRange, NodeList<TemporalChunkExpr> chunks) {
        super(tokenRange);
        setChunks(chunks);
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
    public NodeList<TemporalChunkExpr> getChunks() {
        return chunks;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTemporalLiteralExpr() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TemporalLiteralExpr asTemporalLiteralExpr() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TemporalLiteralExpr> toTemporalLiteralExpr() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTemporalLiteralExpr(Consumer<TemporalLiteralExpr> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public TemporalLiteralExpr setChunks(final NodeList<TemporalChunkExpr> chunks) {
        assertNotNull(chunks);
        if (chunks == this.chunks) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.CHUNKS, this.chunks, chunks);
        if (this.chunks != null)
            this.chunks.setParentNode(null);
        this.chunks = chunks;
        setAsParentNodeOf(chunks);
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.RemoveMethodGenerator")
    public boolean remove(Node node) {
        if (node == null) {
            return false;
        }
        for (int i = 0; i < chunks.size(); i++) {
            if (chunks.get(i) == node) {
                chunks.remove(i);
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
        for (int i = 0; i < chunks.size(); i++) {
            if (chunks.get(i) == node) {
                chunks.set(i, (TemporalChunkExpr) replacementNode);
                return true;
            }
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public TemporalLiteralExpr clone() {
        return (TemporalLiteralExpr) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public TemporalLiteralExprMetaModel getMetaModel() {
        return JavaParserMetaModel.temporalLiteralExprMetaModel;
    }
}
