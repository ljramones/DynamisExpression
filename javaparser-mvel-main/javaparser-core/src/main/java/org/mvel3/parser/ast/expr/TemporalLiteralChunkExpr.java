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

import java.util.concurrent.TimeUnit;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.observer.ObservableProperty;
import static com.github.javaparser.utils.Utils.assertNotNull;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.TemporalLiteralChunkExprMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

public class TemporalLiteralChunkExpr extends TemporalChunkExpr {

    private int value;

    private TimeUnit timeUnit;

    @AllFieldsConstructor
    public TemporalLiteralChunkExpr(String value, TimeUnit timeUnit) {
        this((TokenRange) null, value, timeUnit);
    }

    public TemporalLiteralChunkExpr(TokenRange tokenRange, String value) {
        super(tokenRange);
        this.value = Integer.parseInt(value);
        this.timeUnit = TimeUnit.MILLISECONDS;
    }

    public TemporalLiteralChunkExpr(TokenRange tokenRange, String value, TimeUnit timeUnit) {
        super(tokenRange);
        this.value = Integer.parseInt(value);
        this.timeUnit = timeUnit;
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
    public int getValue() {
        return value;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTemporalLiteralChunkExpr() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TemporalLiteralChunkExpr asTemporalLiteralChunkExpr() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TemporalLiteralChunkExpr> toTemporalLiteralChunkExpr() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTemporalLiteralChunkExpr(Consumer<TemporalLiteralChunkExpr> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public TemporalLiteralChunkExpr setTimeUnit(final TimeUnit timeUnit) {
        assertNotNull(timeUnit);
        if (timeUnit == this.timeUnit) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.TIME_UNIT, this.timeUnit, timeUnit);
        this.timeUnit = timeUnit;
        return this;
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public TemporalLiteralChunkExpr setValue(final int value) {
        if (value == this.value) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.VALUE, this.value, value);
        this.value = value;
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public TemporalLiteralChunkExpr clone() {
        return (TemporalLiteralChunkExpr) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public TemporalLiteralChunkExprMetaModel getMetaModel() {
        return JavaParserMetaModel.temporalLiteralChunkExprMetaModel;
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public TemporalLiteralChunkExpr(TokenRange tokenRange, int value, TimeUnit timeUnit) {
        super(tokenRange);
        setValue(value);
        setTimeUnit(timeUnit);
        customInitialization();
    }
}
