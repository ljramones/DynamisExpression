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
package org.mvel3.parser.ast.visitor;

import com.github.javaparser.ast.visitor.VoidVisitor;
import org.mvel3.parser.ast.expr.BigDecimalLiteralExpr;
import org.mvel3.parser.ast.expr.BigIntegerLiteralExpr;
import org.mvel3.parser.ast.expr.DrlNameExpr;
import org.mvel3.parser.ast.expr.DrlxExpression;
import org.mvel3.parser.ast.expr.FullyQualifiedInlineCastExpr;
import org.mvel3.parser.ast.expr.HalfBinaryExpr;
import org.mvel3.parser.ast.expr.HalfPointFreeExpr;
import org.mvel3.parser.ast.expr.InlineCastExpr;
import org.mvel3.parser.ast.expr.ListCreationLiteralExpression;
import org.mvel3.parser.ast.expr.ListCreationLiteralExpressionElement;
import org.mvel3.parser.ast.expr.MapCreationLiteralExpression;
import org.mvel3.parser.ast.expr.MapCreationLiteralExpressionKeyValuePair;
import org.mvel3.parser.ast.expr.ModifyStatement;
import org.mvel3.parser.ast.expr.NullSafeFieldAccessExpr;
import org.mvel3.parser.ast.expr.NullSafeMethodCallExpr;
import org.mvel3.parser.ast.expr.OOPathChunk;
import org.mvel3.parser.ast.expr.OOPathExpr;
import org.mvel3.parser.ast.expr.PointFreeExpr;
import org.mvel3.parser.ast.expr.RuleBody;
import org.mvel3.parser.ast.expr.RuleConsequence;
import org.mvel3.parser.ast.expr.RuleDeclaration;
import org.mvel3.parser.ast.expr.RuleJoinedPatterns;
import org.mvel3.parser.ast.expr.RulePattern;
import org.mvel3.parser.ast.expr.TemporalLiteralChunkExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralInfiniteChunkExpr;
import org.mvel3.parser.ast.expr.WithStatement;

public interface DrlVoidVisitor<A> extends VoidVisitor<A> {

    void visit(RuleDeclaration ruleDeclaration, A arg);

    void visit(RuleBody n, A arg);

    void visit(RulePattern n, A arg);

    void visit(RuleJoinedPatterns n, A arg);

    void visit(DrlxExpression n, A arg);

    void visit(OOPathExpr n, A arg);

    void visit(OOPathChunk n, A arg);

    void visit(RuleConsequence n, A arg);

    void visit(InlineCastExpr n, A arg);

    void visit(FullyQualifiedInlineCastExpr n, A arg);

    void visit(NullSafeFieldAccessExpr n, A arg);

    void visit(NullSafeMethodCallExpr n, A arg);

    void visit(PointFreeExpr n, A arg);

    void visit(TemporalLiteralExpr n, A arg);

    void visit(TemporalLiteralChunkExpr n, A arg);

    void visit(HalfBinaryExpr n, A arg);

    void visit(HalfPointFreeExpr n, A arg);

    void visit(BigDecimalLiteralExpr n, A arg);

    void visit(BigIntegerLiteralExpr n, A arg);

    void visit(TemporalLiteralInfiniteChunkExpr n, A arg);

    void visit(DrlNameExpr n, A arg);

    void visit(ModifyStatement n, A arg);

    void visit(MapCreationLiteralExpression n, A arg);

    void visit(MapCreationLiteralExpressionKeyValuePair n, A arg);

    void visit(ListCreationLiteralExpression n, A arg);

    void visit(ListCreationLiteralExpressionElement n, A arg);

    void visit(WithStatement withStatement, A arg);
}
