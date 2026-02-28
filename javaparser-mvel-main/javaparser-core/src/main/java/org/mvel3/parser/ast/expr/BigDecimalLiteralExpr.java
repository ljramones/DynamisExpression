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

import java.math.BigDecimal;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.metamodel.BigDecimalLiteralExprMetaModel;
import com.github.javaparser.ast.Generated;

public class BigDecimalLiteralExpr extends LiteralStringValueExpr {

    public BigDecimalLiteralExpr() {
        this(null, "0");
    }

    @AllFieldsConstructor
    public BigDecimalLiteralExpr(final String value) {
        this(null, value);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public BigDecimalLiteralExpr(TokenRange tokenRange, String value) {
        super(tokenRange, value);
        customInitialization();
    }

    public BigDecimalLiteralExpr(final BigDecimal value) {
        this(null, value.toString());
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

    /**
     * @return the literal value as an long while respecting different number representations
     */
    public BigDecimal asBigDecimal() {
        return new BigDecimal(getValue());
    }

    public String getValue() {
        String result = value.replaceAll("_", "");
        char lastChar = result.charAt(result.length() - 1);
        if (lastChar == 'B') {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public BigDecimalLiteralExpr setLong(long value) {
        this.value = String.valueOf(value);
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public BigDecimalLiteralExpr clone() {
        return (BigDecimalLiteralExpr) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public BigDecimalLiteralExprMetaModel getMetaModel() {
        return JavaParserMetaModel.bigDecimalLiteralExprMetaModel;
    }

    public ObjectCreationExpr convertToObjectCreationExpr() {
        return new ObjectCreationExpr(null, new ClassOrInterfaceType(null, BigDecimal.class.getCanonicalName()), NodeList.nodeList(new StringLiteralExpr(getValue())));
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isBigDecimalLiteralExpr() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public BigDecimalLiteralExpr asBigDecimalLiteralExpr() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<BigDecimalLiteralExpr> toBigDecimalLiteralExpr() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifBigDecimalLiteralExpr(Consumer<BigDecimalLiteralExpr> action) {
        action.accept(this);
    }
}
