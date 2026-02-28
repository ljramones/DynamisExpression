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

package org.mvel3.parser.printer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.printer.DefaultPrettyPrinterVisitor;
import com.github.javaparser.printer.configuration.ConfigurationOption;
import com.github.javaparser.printer.configuration.DefaultConfigurationOption;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration.ConfigOption;
import com.github.javaparser.printer.configuration.PrinterConfiguration;
import com.github.javaparser.resolution.types.ResolvedType;
import org.mvel3.parser.ast.expr.AbstractContextStatement;
import org.mvel3.parser.ast.expr.BigDecimalLiteralExpr;
import org.mvel3.parser.ast.expr.BigIntegerLiteralExpr;
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
import org.mvel3.parser.ast.expr.TemporalChunkExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralChunkExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralExpr;
import org.mvel3.parser.ast.expr.TemporalLiteralInfiniteChunkExpr;
import org.mvel3.parser.ast.expr.WithStatement;
import org.mvel3.parser.ast.visitor.DrlVoidVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.javaparser.utils.PositionUtils.sortByBeginPosition;

public class MVELPrintVisitor extends DefaultPrettyPrinterVisitor implements DrlVoidVisitor<Void> {


    public MVELPrintVisitor(PrinterConfiguration prettyPrinterConfiguration) {
        super(prettyPrinterConfiguration);
    }

    @Override
    public void visit( RuleDeclaration n, Void arg ) {
        printComment(n.getComment(), arg);

        for (AnnotationExpr ae : n.getAnnotations()) {
            ae.accept(this, arg);
            printer.print(" ");
        }

        printer.print("rule ");
        n.getName().accept(this, arg);
        printer.println(" {");
        n.getRuleBody().accept(this, arg);
        printer.println("}");
    }

    @Override
    public void visit(RuleBody ruleBody, Void arg) {
    }

    @Override
    public void visit(RulePattern n, Void arg) {
    }

    @Override
    public void visit(RuleJoinedPatterns n, Void arg) {
    }

    @Override
    public void visit(OOPathChunk n, Void arg) {
    }

    @Override
    public void visit(RuleConsequence n, Void arg) {
    }

    @Override
    public void visit( InlineCastExpr inlineCastExpr, Void arg ) {
        printComment(inlineCastExpr.getComment(), arg);
        inlineCastExpr.getExpression().accept( this, arg );
        printer.print( "#" );
        inlineCastExpr.getType().accept( this, arg );
        printer.print( "#" );
    }

    @Override
    public void visit( FullyQualifiedInlineCastExpr inlineCastExpr, Void arg ) {
    }

    @Override
    public void visit( NullSafeFieldAccessExpr nullSafeFieldAccessExpr, Void arg ) {
        printComment(nullSafeFieldAccessExpr.getComment(), arg);
        nullSafeFieldAccessExpr.getScope().accept( this, arg );
        printer.print( "!." );
        nullSafeFieldAccessExpr.getName().accept( this, arg );
    }

    @Override
    public void visit(NullSafeMethodCallExpr nullSafeMethodCallExpr, Void arg) {
        printComment(nullSafeMethodCallExpr.getComment(), arg);
        Optional<Expression> scopeExpression = nullSafeMethodCallExpr.getScope();
        if (scopeExpression.isPresent()) {
            scopeExpression.get().accept( this, arg );
            printer.print("!.");
        }
        printTypeArgs(nullSafeMethodCallExpr, arg);
        nullSafeMethodCallExpr.getName().accept( this, arg );
        printArguments(nullSafeMethodCallExpr.getArguments(), arg);
    }

    @Override
    public void visit( PointFreeExpr pointFreeExpr, Void arg ) {
        printComment(pointFreeExpr.getComment(), arg);
        pointFreeExpr.getLeft().accept( this, arg );
        if(pointFreeExpr.isNegated()) {
            printer.print(" not");
        }
        printer.print(" ");
        pointFreeExpr.getOperator().accept( this, arg );
        if (pointFreeExpr.getArg1() != null) {
            printer.print("[");
            pointFreeExpr.getArg1().accept( this, arg );
            if (pointFreeExpr.getArg2() != null) {
                printer.print(",");
                pointFreeExpr.getArg2().accept( this, arg );
            }
            if (pointFreeExpr.getArg3() != null) {
                printer.print(",");
                pointFreeExpr.getArg3().accept( this, arg );
            }
            if (pointFreeExpr.getArg4() != null) {
                printer.print(",");
                pointFreeExpr.getArg4().accept( this, arg );
            }
            printer.print("]");
        }
        printer.print(" ");
        NodeList<Expression> rightExprs = pointFreeExpr.getRight();
        if (rightExprs.size() == 1) {
            rightExprs.get(0).accept( this, arg );
        } else {
            printer.print("(");
            if(rightExprs.isNonEmpty()) {
                rightExprs.get(0).accept(this, arg);
            }
            for (int i = 1; i < rightExprs.size(); i++) {
                printer.print(", ");
                rightExprs.get(i).accept( this, arg );
            }
            printer.print(")");
        }
    }

    @Override
    public void visit(TemporalLiteralExpr temporalLiteralExpr, Void arg) {
        printComment(temporalLiteralExpr.getComment(), arg);
        NodeList<TemporalChunkExpr> chunks = temporalLiteralExpr.getChunks();
        for (TemporalChunkExpr c : chunks) {
            c.accept(this, arg);
        }
    }

    @Override
    public void visit(TemporalLiteralChunkExpr temporalLiteralExpr, Void arg) {
        printComment(temporalLiteralExpr.getComment(), arg);
        printer.print("" + temporalLiteralExpr.getValue());
        switch (temporalLiteralExpr.getTimeUnit()) {
            case MILLISECONDS:
                printer.print("ms");
                break;
            case SECONDS:
                printer.print("s");
                break;
            case MINUTES:
                printer.print("m");
                break;
            case HOURS:
                printer.print("h");
                break;
            case DAYS:
                printer.print("d");
                break;
        }
    }

    @Override
    public void visit(TemporalLiteralInfiniteChunkExpr temporalLiteralInfiniteChunkExpr, Void arg) {
        printer.print("*");
    }

    @Override
    public void visit(DrlxExpression expr, Void arg) {
        if (expr.getBind() != null) {
            expr.getBind().accept( this, arg );
            printer.print( " : " );
        }
        expr.getExpr().accept(this, arg);
    }

    @Override
    public void visit(OOPathExpr oopathExpr, Void arg) {
        printComment(oopathExpr.getComment(), arg);
        NodeList<OOPathChunk> chunks = oopathExpr.getChunks();
        for (int i = 0; i <  chunks.size(); i++) {
            final OOPathChunk chunk = chunks.get(i);
            printer.print(chunk.isSingleValue() ? "." : "/");
            chunk.accept(this, arg);
            printer.print(chunk.getField().toString());

            if (chunk.getInlineCast().isPresent()) {
                printer.print("#");
                chunk.getInlineCast().get().accept( this, arg );
            }

            List<DrlxExpression> condition = chunk.getConditions();
            final Iterator<DrlxExpression> iterator = condition.iterator();
            if (!condition.isEmpty()) {
                printer.print("[");
                DrlxExpression first = iterator.next();
                first.accept(this, arg);
                while(iterator.hasNext()) {
                    printer.print(",");
                    iterator.next().accept(this, arg);
                }
                printer.print("]");
            }
        }
    }

    @Override
    public void visit(HalfBinaryExpr n, Void arg) {
        printComment(n.getComment(), arg);
        printer.print(n.getOperator().asString());
        printer.print(" ");
        n.getRight().accept(this, arg);
    }

    @Override
    public void visit(HalfPointFreeExpr pointFreeExpr, Void arg) {
        printComment(pointFreeExpr.getComment(), arg);
        if(pointFreeExpr.isNegated()) {
            printer.print("not ");
        }
        pointFreeExpr.getOperator().accept( this, arg );
        if (pointFreeExpr.getArg1() != null) {
            printer.print("[");
            pointFreeExpr.getArg1().accept( this, arg );
            if (pointFreeExpr.getArg2() != null) {
                printer.print(",");
                pointFreeExpr.getArg2().accept( this, arg );
            }
            if (pointFreeExpr.getArg3() != null) {
                printer.print(",");
                pointFreeExpr.getArg3().accept( this, arg );
            }
            if (pointFreeExpr.getArg4() != null) {
                printer.print(",");
                pointFreeExpr.getArg4().accept( this, arg );
            }
            printer.print("]");
        }
        printer.print(" ");
        NodeList<Expression> rightExprs = pointFreeExpr.getRight();
        if (rightExprs.size() == 1) {
            rightExprs.get(0).accept( this, arg );
        } else {
            printer.print("(");
            rightExprs.get(0).accept( this, arg );
            for (int i = 1; i < rightExprs.size(); i++) {
                printer.print(", ");
                rightExprs.get(i).accept( this, arg );
            }
            printer.print(")");
        }
    }

    @Override
    public void visit(BigDecimalLiteralExpr bigDecimalLiteralExpr, Void arg) {
        printer.print(bigDecimalLiteralExpr.getValue() + "B");
    }

    @Override
    public void visit(BigIntegerLiteralExpr bigIntegerLiteralExpr, Void arg) {
        printer.print(bigIntegerLiteralExpr.getValue() + "I");
    }

    @Override
    public void visit(ModifyStatement modifyExpression, Void arg) {
        printer.print("modify (");
        visitContextStatement(modifyExpression, arg);
    }

    @Override
    public void visit(WithStatement withExpression, Void arg) {
        printer.print("with (");
        visitContextStatement(withExpression, arg);
    }

    public <T extends AbstractContextStatement, R extends Expression> void visitContextStatement(AbstractContextStatement<T, R> contextExpression, Void arg) {
        contextExpression.getTarget().accept(this, arg);
        printer.print(") { ");

        String expressionWithComma = contextExpression.getExpressions()
                                                   .stream()
                                                   .filter(Objects::nonNull)
                                                   .filter(Statement::isExpressionStmt)
                                                   .map(n -> PrintUtil.printNode(n.asExpressionStmt().getExpression()))
                                                   .collect(Collectors.joining("; "));

        printer.print(expressionWithComma);
        if (!contextExpression.getExpressions().isEmpty()) {
            printer.print(";");
        }
        printer.print(" }");
    }

    public void printComment(final Optional<Comment> comment, final Void arg) {
        comment.ifPresent(c -> c.accept(this, arg));
    }

    @Override
    public void visit(final CharLiteralExpr n, final Void arg) {
        printOrphanCommentsBeforeThisChildNode(n);
        printComment(n.getComment(), arg);

        printString(n.getValue(), "'");
    }

    private void printString(String value, String quotes) {
        printer.print(quotes + value + quotes);
    }

    @Override
    public void visit(final DoubleLiteralExpr n, final Void arg) {
        printOrphanCommentsBeforeThisChildNode(n);
        printComment(n.getComment(), arg);
        printLiteral(n.getValue());
    }

    @Override
    public void visit(final StringLiteralExpr n, final Void arg) {
        printOrphanCommentsBeforeThisChildNode(n);
        printComment(n.getComment(), arg);

        printString(n.getValue(), "\"");
    }

    private void printLiteral(String value) {
        printer.print(value);
    }

    @Override
    public void visit(final IntegerLiteralExpr n, final Void arg) {
        printOrphanCommentsBeforeThisChildNode(n);
        printComment(n.getComment(), arg);
        printLiteral(n.getValue());
    }

    @Override
    public void visit(final LongLiteralExpr n, final Void arg) {
        printOrphanCommentsBeforeThisChildNode(n);
        printComment(n.getComment(), arg);
        printLiteral(n.getValue());
    }

    @Override
    public void visit(final BinaryExpr n, final Void arg) {
        super.visit(n, arg);
    }

    @Override
    public void visit(final ArrayAccessExpr n, final Void arg) {
        printOrphanCommentsBeforeThisChildNode(n);
        printComment(n.getComment(), arg);

        n.getName().accept(this, arg);

        ResolvedType type = n.getName().calculateResolvedType();


        if (type.isArray()) {
            printer.print("[");
            n.getIndex().accept(this, arg);
            printer.print("]");
        } else {
            printer.print(".get(");
            n.getIndex().accept(this, arg);
            printer.print(")");
        }
    }

    @Override
    public void visit(MapCreationLiteralExpression n, Void arg) {
        printer.print("[");

        Iterator<Expression> expressions = n.getExpressions().iterator();
        while(expressions.hasNext()) {
            expressions.next().accept(this, arg);
            if(expressions.hasNext()) {
                printer.print(", ");
            }
        }
        printer.print("]");
    }

    @Override
    public void visit(MapCreationLiteralExpressionKeyValuePair n, Void arg) {
        n.getKey().accept(this, arg);
        printer.print(" : ");
        n.getValue().accept(this, arg);
    }

    @Override
    public void visit(ListCreationLiteralExpression n, Void arg) {
        printer.print("[");

        Iterator<Expression> expressions = n.getExpressions().iterator();
        while(expressions.hasNext()) {
            expressions.next().accept(this, arg);
            if(expressions.hasNext()) {
                printer.print(", ");
            }
        }
        printer.print("]");
    }

    @Override
    public void visit(ListCreationLiteralExpressionElement n, Void arg) {
        n.getValue().accept(this, arg);
    }

    private Optional<ConfigurationOption> getOption(ConfigOption cOption) {
        return configuration.get(new DefaultConfigurationOption(cOption));
    }

    protected void printOrphanCommentsBeforeThisChildNode(final Node node) {
        if (!getOption(ConfigOption.PRINT_COMMENTS).isPresent()) return;
        if (node instanceof Comment) return;

        Node parent = node.getParentNode().orElse(null);
        if (parent == null) return;
        List<Node> everything = new ArrayList<>(parent.getChildNodes());
        sortByBeginPosition(everything);
        int positionOfTheChild = -1;
        for (int i = 0; i < everything.size(); ++i) { // indexOf is by equality, so this is used to index by identity
            if (everything.get(i) == node) {
                positionOfTheChild = i;
                break;
            }
        }
        if (positionOfTheChild == -1) {
            throw new AssertionError("I am not a child of my parent.");
        }
        int positionOfPreviousChild = -1;
        for (int i = positionOfTheChild - 1; i >= 0 && positionOfPreviousChild == -1; i--) {
            if (!(everything.get(i) instanceof Comment)) positionOfPreviousChild = i;
        }
        for (int i = positionOfPreviousChild + 1; i < positionOfTheChild; i++) {
            Node nodeToPrint = everything.get(i);
            if (!(nodeToPrint instanceof Comment))
                throw new RuntimeException(
                        "Expected comment, instead " + nodeToPrint.getClass() + ". Position of previous child: "
                        + positionOfPreviousChild + ", position of child " + positionOfTheChild);
            nodeToPrint.accept(this, null);
        }
    }

}
