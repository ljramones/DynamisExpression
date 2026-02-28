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

package org.mvel3.parser.util;

import java.util.Map;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.type.Type;
import org.mvel3.parser.ast.expr.DrlxExpression;
import org.mvel3.parser.ast.expr.HalfBinaryExpr;
import org.mvel3.parser.ast.expr.NullSafeFieldAccessExpr;
import org.mvel3.parser.ast.expr.NullSafeMethodCallExpr;

public class AstUtils {

    private static final Map<String, BinaryExpr.Operator> OPERATOR_MAP = Map.ofEntries(
            Map.entry("==", BinaryExpr.Operator.EQUALS),
            Map.entry("!=", BinaryExpr.Operator.NOT_EQUALS),
            Map.entry("<", BinaryExpr.Operator.LESS),
            Map.entry(">", BinaryExpr.Operator.GREATER),
            Map.entry("<=", BinaryExpr.Operator.LESS_EQUALS),
            Map.entry(">=", BinaryExpr.Operator.GREATER_EQUALS),
            Map.entry("&&", BinaryExpr.Operator.AND),
            Map.entry("||", BinaryExpr.Operator.OR),
            Map.entry("+", BinaryExpr.Operator.PLUS),
            Map.entry("-", BinaryExpr.Operator.MINUS),
            Map.entry("*", BinaryExpr.Operator.MULTIPLY),
            Map.entry("/", BinaryExpr.Operator.DIVIDE),
            Map.entry("%", BinaryExpr.Operator.REMAINDER),
            Map.entry("&", BinaryExpr.Operator.BINARY_AND),
            Map.entry("|", BinaryExpr.Operator.BINARY_OR),
            Map.entry("^", BinaryExpr.Operator.XOR),
            Map.entry("<<", BinaryExpr.Operator.LEFT_SHIFT),
            Map.entry(">>", BinaryExpr.Operator.SIGNED_RIGHT_SHIFT),
            Map.entry(">>>", BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT)
    );

    public static BinaryExpr.Operator getBinaryExprOperator(String operatorText) {
        BinaryExpr.Operator operator = OPERATOR_MAP.get(operatorText);
        if (operator == null) {
            throw new IllegalArgumentException("Unknown binary operator: " + operatorText);
        }
        return operator;
    }

    public static String getStringFromLiteral(String value) {
        value = value.trim();
        String result = value.replaceAll("_", "");
        char lastChar = result.charAt(result.length() - 1);
        if (!Character.isDigit(lastChar)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static boolean hasChildOfType( Node node, Class<?> nodeType ) {
        if (nodeType.isInstance( node )) {
            return true;
        }
        for (Node child : node.getChildNodes()) {
            if (hasChildOfType( child, nodeType )) {
                return true;
            }
        }
        return false;
    }

    public static Expression parseThisExprOrHalfBinary(TokenRange tokenRange, ThisExpr thisExpr, NodeList<Expression> args ) {
        return args.size() == 1 && isHalfBinaryArg( args.get( 0 ) ) ?
                transformHalfBinaryArg( tokenRange, null, thisExpr, args.get( 0 ), false) :
                new MethodCallExpr(tokenRange, null, null, new SimpleName( "this" ), args);
    }

    public static Expression parseMethodExprOrHalfBinary( TokenRange tokenRange, SimpleName name, NodeList<Expression> args ) {
        return parseMethodExprOrHalfBinary(tokenRange, null, null, name, args, false);
    }

    public static Expression parseMethodExprOrHalfBinary(TokenRange tokenRange, Expression scope, NodeList<Type> typeArguments, SimpleName name, NodeList<Expression> args, boolean nullSafe ) {
        return args.size() == 1 && isHalfBinaryArg( args.get( 0 ) ) ?
                transformHalfBinaryArg( tokenRange, scope, new NameExpr( name ), args.get( 0 ), nullSafe) :
                (nullSafe ? new NullSafeMethodCallExpr(tokenRange, scope, typeArguments, name, args) : new MethodCallExpr(tokenRange, scope, typeArguments, name, args));
    }

    private static Expression transformHalfBinaryArg(TokenRange tokenRange, Expression scope, Expression name, Expression expr, boolean nullSafe) {
        return switch (expr) {
            case HalfBinaryExpr halfBinary -> {
                Expression left = scope == null ? name : (nullSafe ? new NullSafeFieldAccessExpr(scope, null, name.asNameExpr().getName()) : new FieldAccessExpr(scope, null, name.asNameExpr().getName()));
                yield new BinaryExpr(tokenRange, left, halfBinary.getRight(), halfBinary.getOperator().toBinaryExprOperator());
            }
            case EnclosedExpr enclosed -> transformHalfBinaryArg(tokenRange, scope, name, enclosed.getInner(), nullSafe);
            case BinaryExpr binary -> {
                Expression rewrittenLeft = transformHalfBinaryArg(tokenRange, scope, name, binary.getLeft(), nullSafe);
                Expression rewrittenRight = binary.getRight() instanceof HalfBinaryExpr && !(binary.getLeft() instanceof EnclosedExpr) ?
                        binary.getRight() :
                        transformHalfBinaryArg(tokenRange, scope, name, binary.getRight(), nullSafe);
                rewrittenRight.setParentNode(rewrittenLeft);
                yield new BinaryExpr(tokenRange, rewrittenLeft, rewrittenRight, binary.getOperator());
            }
            default -> throw new IllegalStateException();
        };
    }

    private static boolean isHalfBinaryArg(Expression expr) {
        return switch (expr) {
            case HalfBinaryExpr _ -> true;
            case BinaryExpr binary -> isHalfBinaryArg(binary.getLeft());
            case EnclosedExpr enclosed -> isHalfBinaryArg(enclosed.getInner());
            default -> false;
        };
    }

    public static DrlxExpression parseBindingAfterAndOr(TokenRange tokenRange, DrlxExpression leftExpr, Expression rightExpr) {
        // This is intended to parse and adjust the AST of expressions with a binding on the right side of an AND like
        //     $n : name == "Mario" && $a : age > 20
        // In the case the parser originally produces the following
        //     leftExpr = DrlxExpression( "$n", BinaryExpr("name == \"Mario\"", AND, "$a") )
        //     rightExpr = "age > 20"
        // and this method combine these 2 expressions into
        //     DrlxExpression( BinaryExpr( DrlxExpression("$n", "name == \"Mario\""), AND, DrlxExpression("$a", "age > 20") ) )

        if (leftExpr.getExpr() instanceof BinaryExpr leftBinary) {
            BinaryExpr.Operator operator = leftBinary.getOperator();
            if (isLogicalOperator(operator)) {
                if (leftBinary.getRight() instanceof NameExpr rightNameExpr) {
                    DrlxExpression newLeft = new DrlxExpression(leftExpr.getBind(), leftBinary.getLeft());
                    SimpleName rightName = rightNameExpr.getName();
                    DrlxExpression newRight = new DrlxExpression(rightName, rightExpr);
                    return new DrlxExpression(null, new BinaryExpr(tokenRange, newLeft, newRight, operator));
                }

                if (leftBinary.getRight() instanceof DrlxExpression rightDrlx) {
                    Expression first = leftBinary.getLeft();
                    DrlxExpression innerRight = parseBindingAfterAndOr(tokenRange, rightDrlx, rightExpr);
                    BinaryExpr innerRightBinary = (BinaryExpr) innerRight.getExpr();
                    Expression second = innerRightBinary.getLeft();
                    Expression third = innerRightBinary.getRight();
                    BinaryExpr.Operator innerRightOperator = innerRightBinary.getOperator();
                    if (operator == BinaryExpr.Operator.OR && innerRightOperator == BinaryExpr.Operator.AND) {
                        return new DrlxExpression(null, new BinaryExpr(tokenRange, first, new BinaryExpr(tokenRange, second, third, innerRightOperator), operator));
                    } else {
                        return new DrlxExpression(null, new BinaryExpr(tokenRange, new BinaryExpr(tokenRange, first, second, operator), third, innerRightOperator));
                    }
                }
            }
        }
        throw new IllegalStateException("leftExpr has to be a BinaryExpr with LogicalOperator");
    }

    public static boolean isLogicalOperator( BinaryExpr.Operator operator ) {
        return operator == BinaryExpr.Operator.AND || operator == BinaryExpr.Operator.OR;
    }

}
