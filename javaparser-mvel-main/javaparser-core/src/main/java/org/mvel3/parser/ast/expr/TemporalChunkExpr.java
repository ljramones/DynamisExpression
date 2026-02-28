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
import com.github.javaparser.ast.expr.LiteralExpr;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.Generated;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.TemporalChunkExprMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;

public abstract class TemporalChunkExpr extends LiteralExpr {

    @AllFieldsConstructor
    public TemporalChunkExpr() {
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public TemporalChunkExpr(TokenRange tokenRange) {
        super(tokenRange);
        customInitialization();
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTemporalChunkExpr() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TemporalChunkExpr asTemporalChunkExpr() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TemporalChunkExpr> toTemporalChunkExpr() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTemporalChunkExpr(Consumer<TemporalChunkExpr> action) {
        action.accept(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public TemporalChunkExpr clone() {
        return (TemporalChunkExpr) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public TemporalChunkExprMetaModel getMetaModel() {
        return JavaParserMetaModel.temporalChunkExprMetaModel;
    }
}
