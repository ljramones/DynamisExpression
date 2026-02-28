/*
 * Copyright (C) 2015-2016 Federico Tomassetti
 * Copyright (C) 2017-2023 The JavaParser Team.
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

package com.github.javaparser.symbolsolver.javaparsermodel;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.modules.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.resolution.types.ResolvedType;
import org.mvel3.parser.ast.expr.BigDecimalLiteralExpr;
import org.mvel3.parser.ast.expr.BigIntegerLiteralExpr;
import org.mvel3.parser.ast.expr.DrlNameExpr;
import org.mvel3.parser.ast.expr.DrlxExpression;
import org.mvel3.parser.ast.expr.FullyQualifiedInlineCastExpr;
import org.mvel3.parser.ast.expr.HalfBinaryExpr;
import org.mvel3.parser.ast.expr.HalfPointFreeExpr;
import org.mvel3.parser.ast.expr.InlineCastExpr;
import org.mvel3.parser.ast.expr.PointFreeExpr;
import org.mvel3.parser.ast.expr.ListCreationLiteralExpression;
import org.mvel3.parser.ast.expr.AbstractContextStatement;
import org.mvel3.parser.ast.expr.ListCreationLiteralExpressionElement;
import org.mvel3.parser.ast.expr.MapCreationLiteralExpression;
import org.mvel3.parser.ast.expr.MapCreationLiteralExpressionKeyValuePair;
import org.mvel3.parser.ast.expr.ModifyStatement;
import org.mvel3.parser.ast.expr.NullSafeFieldAccessExpr;
import org.mvel3.parser.ast.expr.NullSafeMethodCallExpr;
import org.mvel3.parser.ast.expr.OOPathChunk;
import org.mvel3.parser.ast.expr.OOPathExpr;
import org.mvel3.parser.ast.expr.RuleBody;
import org.mvel3.parser.ast.expr.RuleConsequence;
import org.mvel3.parser.ast.expr.RuleDeclaration;
import org.mvel3.parser.ast.expr.RuleJoinedPatterns;
import org.mvel3.parser.ast.expr.RulePattern;
import org.mvel3.parser.ast.expr.TemporalLiteralChunkExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralInfiniteChunkExpr;
import org.mvel3.parser.ast.expr.WithStatement;

public class DefaultVisitorAdapter implements GenericVisitor<ResolvedType, Boolean> {
    @Override
    public ResolvedType visit(CompilationUnit node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(PackageDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(TypeParameter node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(LineComment node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(BlockComment node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ClassOrInterfaceDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(EnumDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(EnumConstantDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(AnnotationDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(AnnotationMemberDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(FieldDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(VariableDeclarator node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ConstructorDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(MethodDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(Parameter node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(InitializerDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(JavadocComment node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ClassOrInterfaceType node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(PrimitiveType node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ArrayType node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ArrayCreationLevel node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(IntersectionType node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(UnionType node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(VoidType node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(WildcardType node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(UnknownType node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ArrayAccessExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ArrayCreationExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ArrayInitializerExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(AssignExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(BinaryExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(CastExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(InlineCastExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(FullyQualifiedInlineCastExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(HalfBinaryExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(HalfPointFreeExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(PointFreeExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ListCreationLiteralExpression node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ListCreationLiteralExpressionElement node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(MapCreationLiteralExpression node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(MapCreationLiteralExpressionKeyValuePair node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(NullSafeFieldAccessExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(NullSafeMethodCallExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(OOPathExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(OOPathChunk node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(RuleDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(RuleBody node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(RulePattern node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(RuleJoinedPatterns node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(RuleConsequence node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(TemporalLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(TemporalLiteralChunkExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(TemporalLiteralInfiniteChunkExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(AbstractContextStatement<?, ?> node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ModifyStatement node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(WithStatement node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ClassExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ConditionalExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(EnclosedExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(FieldAccessExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(InstanceOfExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(PatternExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(StringLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(IntegerLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(LongLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(CharLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(DoubleLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(BigIntegerLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(BigDecimalLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(DrlNameExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(DrlxExpression node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(BooleanLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(NullLiteralExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(MethodCallExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(NameExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ObjectCreationExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ThisExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(SuperExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(UnaryExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(VariableDeclarationExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(MarkerAnnotationExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(SingleMemberAnnotationExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(NormalAnnotationExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(MemberValuePair node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ExplicitConstructorInvocationStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(LocalClassDeclarationStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(LocalRecordDeclarationStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(AssertStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(BlockStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(LabeledStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(EmptyStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ExpressionStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(SwitchStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(SwitchEntry node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(BreakStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ReturnStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(IfStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(WhileStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ContinueStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(DoStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ForEachStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ForStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ThrowStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(SynchronizedStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(TryStmt node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(CatchClause node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(LambdaExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(MethodReferenceExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(TypeExpr node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(NodeList node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(Name node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(SimpleName node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ImportDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ModuleDeclaration node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ModuleRequiresDirective node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ModuleExportsDirective node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ModuleProvidesDirective node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ModuleUsesDirective node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ModuleOpensDirective node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(UnparsableStmt node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(ReceiverParameter node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(VarType node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(Modifier node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(SwitchExpr node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(YieldStmt node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(TextBlockLiteralExpr node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(RecordDeclaration node, Boolean arg) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }

    @Override
    public ResolvedType visit(CompactConstructorDeclaration node, Boolean aBoolean) {
        throw new UnsupportedOperationException(node.getClass().getCanonicalName());
    }
}
