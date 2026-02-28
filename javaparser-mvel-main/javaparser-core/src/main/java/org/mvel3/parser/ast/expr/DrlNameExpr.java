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
import com.github.javaparser.ast.Generated;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.resolution.Resolvable;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.DrlNameExprMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;

/**
 * Whenever a SimpleName is used in an expression, it is wrapped in DrlNameExpr.
 * <br/>In <code>int x = a + 3;</code> a is a SimpleName inside a DrlNameExpr.
 * @author Julio Vilmar Gesser
 */
public class DrlNameExpr extends NameExpr implements NodeWithSimpleName<NameExpr>, Resolvable<ResolvedValueDeclaration> {

    private int backReferencesCount;

    public DrlNameExpr() {
        this(null, new SimpleName(), 0);
    }

    public DrlNameExpr(final String name) {
        this(null, new SimpleName(name), 0);
    }

    @AllFieldsConstructor
    public DrlNameExpr(final SimpleName name, int backReferencesCount) {
        this(name.getTokenRange().orElse(null), name, backReferencesCount);
        setRange(name.getRange().orElse(null));
    }

    public DrlNameExpr(final SimpleName name) {
        this(name, 0);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public DrlNameExpr(TokenRange tokenRange, SimpleName name, int backReferencesCount) {
        super(tokenRange, name);
        setBackReferencesCount(backReferencesCount);
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
    public int getBackReferencesCount() {
        return backReferencesCount;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isDrlNameExpr() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public DrlNameExpr asDrlNameExpr() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<DrlNameExpr> toDrlNameExpr() {
        return Optional.of(this);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifDrlNameExpr(Consumer<DrlNameExpr> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public DrlNameExpr setBackReferencesCount(final int backReferencesCount) {
        if (backReferencesCount == this.backReferencesCount) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.BACK_REFERENCES_COUNT, this.backReferencesCount, backReferencesCount);
        this.backReferencesCount = backReferencesCount;
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public DrlNameExpr clone() {
        return (DrlNameExpr) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public DrlNameExprMetaModel getMetaModel() {
        return JavaParserMetaModel.drlNameExprMetaModel;
    }
}
