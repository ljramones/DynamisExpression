/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2023 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */
package com.github.javaparser.ast.expr;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.Generated;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithOptionalScope;
import com.github.javaparser.ast.nodeTypes.NodeWithTypeArguments;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.ExpressionMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.resolution.types.ResolvedType;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import static com.github.javaparser.utils.CodeGenerationUtils.f;
import org.mvel3.parser.ast.expr.InlineCastExpr;
import org.mvel3.parser.ast.expr.BigDecimalLiteralExpr;
import org.mvel3.parser.ast.expr.BigIntegerLiteralExpr;
import org.mvel3.parser.ast.expr.DrlNameExpr;
import org.mvel3.parser.ast.expr.DrlxExpression;
import org.mvel3.parser.ast.expr.FullyQualifiedInlineCastExpr;
import org.mvel3.parser.ast.expr.HalfBinaryExpr;
import org.mvel3.parser.ast.expr.HalfPointFreeExpr;
import org.mvel3.parser.ast.expr.PointFreeExpr;
import org.mvel3.parser.ast.expr.ListCreationLiteralExpressionElement;
import org.mvel3.parser.ast.expr.ListCreationLiteralExpression;
import org.mvel3.parser.ast.expr.MapCreationLiteralExpressionKeyValuePair;
import org.mvel3.parser.ast.expr.MapCreationLiteralExpression;
import org.mvel3.parser.ast.expr.NullSafeFieldAccessExpr;
import org.mvel3.parser.ast.expr.NullSafeMethodCallExpr;
import org.mvel3.parser.ast.expr.TemporalChunkExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralChunkExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralInfiniteChunkExpr;
import org.mvel3.parser.ast.expr.OOPathChunk;
import org.mvel3.parser.ast.expr.OOPathExpr;

/**
 * A base class for all expressions.
 *
 * @author Julio Vilmar Gesser
 */
public abstract class Expression extends Node {

    /**
     * Returns {@code true} when the Node to be tested is not an
     * {@link EnclosedExpr}, {@code false} otherwise.
     */
    public static final Predicate<Node> IS_NOT_ENCLOSED_EXPR = n -> !(n instanceof EnclosedExpr);

    /**
     * A {@link Function} that returns its argument (an {@link Expression}) when
     * the argument is not an {@link EnclosedExpr}, otherwise the first
     * {@link Expression} down the argument's 'inner' path that is not an
     * {@link EnclosedExpr}.
     */
    public static final Function<Expression, Expression> EXCLUDE_ENCLOSED_EXPR = expr -> {
        while (expr.isEnclosedExpr()) {
            expr = expr.asEnclosedExpr().getInner();
        }
        return expr;
    };

    @AllFieldsConstructor
    public Expression() {
        this(null);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public Expression(TokenRange tokenRange) {
        super(tokenRange);
        customInitialization();
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public Expression clone() {
        return (Expression) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public ExpressionMetaModel getMetaModel() {
        return JavaParserMetaModel.expressionMetaModel;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isAnnotationExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public AnnotationExpr asAnnotationExpr() {
        throw new IllegalStateException(f("%s is not AnnotationExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isArrayAccessExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ArrayAccessExpr asArrayAccessExpr() {
        throw new IllegalStateException(f("%s is not ArrayAccessExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isArrayCreationExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ArrayCreationExpr asArrayCreationExpr() {
        throw new IllegalStateException(f("%s is not ArrayCreationExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isArrayInitializerExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ArrayInitializerExpr asArrayInitializerExpr() {
        throw new IllegalStateException(f("%s is not ArrayInitializerExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isAssignExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public AssignExpr asAssignExpr() {
        throw new IllegalStateException(f("%s is not AssignExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isBinaryExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public BinaryExpr asBinaryExpr() {
        throw new IllegalStateException(f("%s is not BinaryExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isBooleanLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public BooleanLiteralExpr asBooleanLiteralExpr() {
        throw new IllegalStateException(f("%s is not BooleanLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isCastExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public CastExpr asCastExpr() {
        throw new IllegalStateException(f("%s is not CastExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isCharLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public CharLiteralExpr asCharLiteralExpr() {
        throw new IllegalStateException(f("%s is not CharLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isClassExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ClassExpr asClassExpr() {
        throw new IllegalStateException(f("%s is not ClassExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isConditionalExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ConditionalExpr asConditionalExpr() {
        throw new IllegalStateException(f("%s is not ConditionalExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isDoubleLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public DoubleLiteralExpr asDoubleLiteralExpr() {
        throw new IllegalStateException(f("%s is not DoubleLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isEnclosedExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public EnclosedExpr asEnclosedExpr() {
        throw new IllegalStateException(f("%s is not EnclosedExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isFieldAccessExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public FieldAccessExpr asFieldAccessExpr() {
        throw new IllegalStateException(f("%s is not FieldAccessExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isInstanceOfExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public InstanceOfExpr asInstanceOfExpr() {
        throw new IllegalStateException(f("%s is not InstanceOfExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isIntegerLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public IntegerLiteralExpr asIntegerLiteralExpr() {
        throw new IllegalStateException(f("%s is not IntegerLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isLambdaExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public LambdaExpr asLambdaExpr() {
        throw new IllegalStateException(f("%s is not LambdaExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public LiteralExpr asLiteralExpr() {
        throw new IllegalStateException(f("%s is not LiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isLiteralStringValueExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public LiteralStringValueExpr asLiteralStringValueExpr() {
        throw new IllegalStateException(f("%s is not LiteralStringValueExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isLongLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public LongLiteralExpr asLongLiteralExpr() {
        throw new IllegalStateException(f("%s is not LongLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isMarkerAnnotationExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public MarkerAnnotationExpr asMarkerAnnotationExpr() {
        throw new IllegalStateException(f("%s is not MarkerAnnotationExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isMethodCallExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public MethodCallExpr asMethodCallExpr() {
        throw new IllegalStateException(f("%s is not MethodCallExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isMethodReferenceExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public MethodReferenceExpr asMethodReferenceExpr() {
        throw new IllegalStateException(f("%s is not MethodReferenceExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isNameExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public NameExpr asNameExpr() {
        throw new IllegalStateException(f("%s is not NameExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isNormalAnnotationExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public NormalAnnotationExpr asNormalAnnotationExpr() {
        throw new IllegalStateException(f("%s is not NormalAnnotationExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isNullLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public NullLiteralExpr asNullLiteralExpr() {
        throw new IllegalStateException(f("%s is not NullLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isObjectCreationExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ObjectCreationExpr asObjectCreationExpr() {
        throw new IllegalStateException(f("%s is not ObjectCreationExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isSingleMemberAnnotationExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public SingleMemberAnnotationExpr asSingleMemberAnnotationExpr() {
        throw new IllegalStateException(f("%s is not SingleMemberAnnotationExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isStringLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public StringLiteralExpr asStringLiteralExpr() {
        throw new IllegalStateException(f("%s is not StringLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isSuperExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public SuperExpr asSuperExpr() {
        throw new IllegalStateException(f("%s is not SuperExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isThisExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ThisExpr asThisExpr() {
        throw new IllegalStateException(f("%s is not ThisExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTypeExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TypeExpr asTypeExpr() {
        throw new IllegalStateException(f("%s is not TypeExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isUnaryExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public UnaryExpr asUnaryExpr() {
        throw new IllegalStateException(f("%s is not UnaryExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isVariableDeclarationExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public VariableDeclarationExpr asVariableDeclarationExpr() {
        throw new IllegalStateException(f("%s is not VariableDeclarationExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifAnnotationExpr(Consumer<AnnotationExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifArrayAccessExpr(Consumer<ArrayAccessExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifArrayCreationExpr(Consumer<ArrayCreationExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifArrayInitializerExpr(Consumer<ArrayInitializerExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifAssignExpr(Consumer<AssignExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifBinaryExpr(Consumer<BinaryExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifBooleanLiteralExpr(Consumer<BooleanLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifCastExpr(Consumer<CastExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifCharLiteralExpr(Consumer<CharLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifClassExpr(Consumer<ClassExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifConditionalExpr(Consumer<ConditionalExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifDoubleLiteralExpr(Consumer<DoubleLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifEnclosedExpr(Consumer<EnclosedExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifFieldAccessExpr(Consumer<FieldAccessExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifInstanceOfExpr(Consumer<InstanceOfExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifIntegerLiteralExpr(Consumer<IntegerLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifLambdaExpr(Consumer<LambdaExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifLiteralExpr(Consumer<LiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifLiteralStringValueExpr(Consumer<LiteralStringValueExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifLongLiteralExpr(Consumer<LongLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifMarkerAnnotationExpr(Consumer<MarkerAnnotationExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifMethodCallExpr(Consumer<MethodCallExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifMethodReferenceExpr(Consumer<MethodReferenceExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifNameExpr(Consumer<NameExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifNormalAnnotationExpr(Consumer<NormalAnnotationExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifNullLiteralExpr(Consumer<NullLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifObjectCreationExpr(Consumer<ObjectCreationExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifSingleMemberAnnotationExpr(Consumer<SingleMemberAnnotationExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifStringLiteralExpr(Consumer<StringLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifSuperExpr(Consumer<SuperExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifThisExpr(Consumer<ThisExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTypeExpr(Consumer<TypeExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifUnaryExpr(Consumer<UnaryExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifVariableDeclarationExpr(Consumer<VariableDeclarationExpr> action) {
    }

    /**
     * returns the type associated with the node.
     */
    public ResolvedType calculateResolvedType() {
        return getSymbolResolver().calculateType(this);
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<AnnotationExpr> toAnnotationExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ArrayAccessExpr> toArrayAccessExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ArrayCreationExpr> toArrayCreationExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ArrayInitializerExpr> toArrayInitializerExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<AssignExpr> toAssignExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<BinaryExpr> toBinaryExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<BooleanLiteralExpr> toBooleanLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<CastExpr> toCastExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<CharLiteralExpr> toCharLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ClassExpr> toClassExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ConditionalExpr> toConditionalExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<DoubleLiteralExpr> toDoubleLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<EnclosedExpr> toEnclosedExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<FieldAccessExpr> toFieldAccessExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<InstanceOfExpr> toInstanceOfExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<IntegerLiteralExpr> toIntegerLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<LambdaExpr> toLambdaExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<LiteralExpr> toLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<LiteralStringValueExpr> toLiteralStringValueExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<LongLiteralExpr> toLongLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<MarkerAnnotationExpr> toMarkerAnnotationExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<MethodCallExpr> toMethodCallExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<MethodReferenceExpr> toMethodReferenceExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<NameExpr> toNameExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<NormalAnnotationExpr> toNormalAnnotationExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<NullLiteralExpr> toNullLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ObjectCreationExpr> toObjectCreationExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<SingleMemberAnnotationExpr> toSingleMemberAnnotationExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<StringLiteralExpr> toStringLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<SuperExpr> toSuperExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ThisExpr> toThisExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TypeExpr> toTypeExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<UnaryExpr> toUnaryExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<VariableDeclarationExpr> toVariableDeclarationExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isSwitchExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public SwitchExpr asSwitchExpr() {
        throw new IllegalStateException(f("%s is not SwitchExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<SwitchExpr> toSwitchExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifSwitchExpr(Consumer<SwitchExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTextBlockLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TextBlockLiteralExpr asTextBlockLiteralExpr() {
        throw new IllegalStateException(f("%s is not TextBlockLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TextBlockLiteralExpr> toTextBlockLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTextBlockLiteralExpr(Consumer<TextBlockLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isPatternExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public PatternExpr asPatternExpr() {
        throw new IllegalStateException(f("%s is not PatternExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<PatternExpr> toPatternExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifPatternExpr(Consumer<PatternExpr> action) {
    }

    /**
     * See https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.2
     * @return true if the expression is a standalone expression
     */
    public boolean isStandaloneExpression() {
        return !isPolyExpression();
    }

    /**
     * See https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.2
     * @return true if the expression is a poly expression
     */
    public boolean isPolyExpression() {
        return false;
    }

    /*
     * 6.5.6.2. Qualified Expression Names
     * https://docs.oracle.com/javase/specs/jls/se7/html/jls-6.html#jls-6.5.6.2
     */
    public boolean isQualified() {
        return hasScope();
    }

    /*
     * Verify if the parent node is an assignment context.
     */
    public final boolean appearsInAssignmentContext() {
        if (getParentNode().isPresent() && getParentNode().get() instanceof Expression) {
            return ((Expression) getParentNode().get()).isAssignmentContext();
        }
        return false;
    }

    /*
     * Returns true if the expression is an assignment context. Default is false.
     * https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.2
     * 5.2. Assignment Contexts: Assignment contexts allow the value of an expression to be assigned (§15.26) to a variable;...
     */
    protected boolean isAssignmentContext() {
        return false;
    }

    /*
     * Verify if the parent node is an invocation context.
     */
    public final boolean appearsInInvocationContext() {
        if (getParentNode().isPresent() && getParentNode().get() instanceof Expression) {
            return ((Expression) getParentNode().get()).isInvocationContext();
        }
        return false;
    }

    /*
     * Returns true if the expression is an invocation context. Default is false.
     * https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.3
     * 5.3. Invocation Contexts
     */
    protected boolean isInvocationContext() {
        return false;
    }

    /*
     * returns true if the scope of this expression does not define an type argument or if the expression has not a scope (the expression is not qualified)
     * or if there is a scope it uses <> to elide class type arguments
     * For exemple :
     * m()      ==> true because there is no scope
     * a.m()    ==> true because the scope has no type arguments
     * a<>.m()  ==> true because the type argument is elided
     * a<T>.m() ==> false because the type argument is not elided
     */
    public final boolean elidesTypeArguments() {
        if (!(hasScope() && this instanceof NodeWithTypeArguments)) {
            return true;
        }
        Expression scope = (Expression) ((NodeWithOptionalScope) this).getScope().get();
        NodeWithTypeArguments nwta = (NodeWithTypeArguments) this;
        return scope.elidesTypeArguments() && (!nwta.getTypeArguments().isPresent() || nwta.isUsingDiamondOperator());
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isInlineCastExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public InlineCastExpr asInlineCastExpr() {
        throw new IllegalStateException(f("%s is not InlineCastExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<InlineCastExpr> toInlineCastExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifInlineCastExpr(Consumer<InlineCastExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isBigDecimalLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public BigDecimalLiteralExpr asBigDecimalLiteralExpr() {
        throw new IllegalStateException(f("%s is not BigDecimalLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<BigDecimalLiteralExpr> toBigDecimalLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifBigDecimalLiteralExpr(Consumer<BigDecimalLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isBigIntegerLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public BigIntegerLiteralExpr asBigIntegerLiteralExpr() {
        throw new IllegalStateException(f("%s is not BigIntegerLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<BigIntegerLiteralExpr> toBigIntegerLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifBigIntegerLiteralExpr(Consumer<BigIntegerLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isDrlNameExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public DrlNameExpr asDrlNameExpr() {
        throw new IllegalStateException(f("%s is not DrlNameExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<DrlNameExpr> toDrlNameExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifDrlNameExpr(Consumer<DrlNameExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isDrlxExpression() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public DrlxExpression asDrlxExpression() {
        throw new IllegalStateException(f("%s is not DrlxExpression, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<DrlxExpression> toDrlxExpression() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifDrlxExpression(Consumer<DrlxExpression> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isFullyQualifiedInlineCastExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public FullyQualifiedInlineCastExpr asFullyQualifiedInlineCastExpr() {
        throw new IllegalStateException(f("%s is not FullyQualifiedInlineCastExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<FullyQualifiedInlineCastExpr> toFullyQualifiedInlineCastExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifFullyQualifiedInlineCastExpr(Consumer<FullyQualifiedInlineCastExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isHalfBinaryExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public HalfBinaryExpr asHalfBinaryExpr() {
        throw new IllegalStateException(f("%s is not HalfBinaryExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<HalfBinaryExpr> toHalfBinaryExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifHalfBinaryExpr(Consumer<HalfBinaryExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isHalfPointFreeExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public HalfPointFreeExpr asHalfPointFreeExpr() {
        throw new IllegalStateException(f("%s is not HalfPointFreeExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<HalfPointFreeExpr> toHalfPointFreeExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifHalfPointFreeExpr(Consumer<HalfPointFreeExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isPointFreeExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public PointFreeExpr asPointFreeExpr() {
        throw new IllegalStateException(f("%s is not PointFreeExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<PointFreeExpr> toPointFreeExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifPointFreeExpr(Consumer<PointFreeExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isListCreationLiteralExpressionElement() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ListCreationLiteralExpressionElement asListCreationLiteralExpressionElement() {
        throw new IllegalStateException(f("%s is not ListCreationLiteralExpressionElement, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ListCreationLiteralExpressionElement> toListCreationLiteralExpressionElement() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifListCreationLiteralExpressionElement(Consumer<ListCreationLiteralExpressionElement> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isListCreationLiteralExpression() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public ListCreationLiteralExpression asListCreationLiteralExpression() {
        throw new IllegalStateException(f("%s is not ListCreationLiteralExpression, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<ListCreationLiteralExpression> toListCreationLiteralExpression() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifListCreationLiteralExpression(Consumer<ListCreationLiteralExpression> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isMapCreationLiteralExpressionKeyValuePair() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public MapCreationLiteralExpressionKeyValuePair asMapCreationLiteralExpressionKeyValuePair() {
        throw new IllegalStateException(f("%s is not MapCreationLiteralExpressionKeyValuePair, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<MapCreationLiteralExpressionKeyValuePair> toMapCreationLiteralExpressionKeyValuePair() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifMapCreationLiteralExpressionKeyValuePair(Consumer<MapCreationLiteralExpressionKeyValuePair> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isMapCreationLiteralExpression() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public MapCreationLiteralExpression asMapCreationLiteralExpression() {
        throw new IllegalStateException(f("%s is not MapCreationLiteralExpression, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<MapCreationLiteralExpression> toMapCreationLiteralExpression() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifMapCreationLiteralExpression(Consumer<MapCreationLiteralExpression> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isNullSafeFieldAccessExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public NullSafeFieldAccessExpr asNullSafeFieldAccessExpr() {
        throw new IllegalStateException(f("%s is not NullSafeFieldAccessExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<NullSafeFieldAccessExpr> toNullSafeFieldAccessExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifNullSafeFieldAccessExpr(Consumer<NullSafeFieldAccessExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isNullSafeMethodCallExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public NullSafeMethodCallExpr asNullSafeMethodCallExpr() {
        throw new IllegalStateException(f("%s is not NullSafeMethodCallExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<NullSafeMethodCallExpr> toNullSafeMethodCallExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifNullSafeMethodCallExpr(Consumer<NullSafeMethodCallExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTemporalChunkExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TemporalChunkExpr asTemporalChunkExpr() {
        throw new IllegalStateException(f("%s is not TemporalChunkExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TemporalChunkExpr> toTemporalChunkExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTemporalChunkExpr(Consumer<TemporalChunkExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTemporalLiteralChunkExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TemporalLiteralChunkExpr asTemporalLiteralChunkExpr() {
        throw new IllegalStateException(f("%s is not TemporalLiteralChunkExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TemporalLiteralChunkExpr> toTemporalLiteralChunkExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTemporalLiteralChunkExpr(Consumer<TemporalLiteralChunkExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTemporalLiteralExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TemporalLiteralExpr asTemporalLiteralExpr() {
        throw new IllegalStateException(f("%s is not TemporalLiteralExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TemporalLiteralExpr> toTemporalLiteralExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTemporalLiteralExpr(Consumer<TemporalLiteralExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isTemporalLiteralInfiniteChunkExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public TemporalLiteralInfiniteChunkExpr asTemporalLiteralInfiniteChunkExpr() {
        throw new IllegalStateException(f("%s is not TemporalLiteralInfiniteChunkExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<TemporalLiteralInfiniteChunkExpr> toTemporalLiteralInfiniteChunkExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifTemporalLiteralInfiniteChunkExpr(Consumer<TemporalLiteralInfiniteChunkExpr> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isOOPathChunk() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public OOPathChunk asOOPathChunk() {
        throw new IllegalStateException(f("%s is not OOPathChunk, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<OOPathChunk> toOOPathChunk() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifOOPathChunk(Consumer<OOPathChunk> action) {
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isOOPathExpr() {
        return false;
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public OOPathExpr asOOPathExpr() {
        throw new IllegalStateException(f("%s is not OOPathExpr, it is %s", this, this.getClass().getSimpleName()));
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<OOPathExpr> toOOPathExpr() {
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifOOPathExpr(Consumer<OOPathExpr> action) {
    }
}
