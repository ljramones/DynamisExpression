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
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import org.mvel3.parser.ast.visitor.DrlVoidVisitor;
import org.mvel3.parser.ast.visitor.DrlVoidVisitorAdapter;
import java.util.Optional;
import java.util.function.Consumer;
import com.github.javaparser.ast.observer.ObservableProperty;
import static com.github.javaparser.utils.Utils.assertNotNull;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.metamodel.RuleDeclarationMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.ast.Generated;

/**
 * Represents a rule declaration in a DRL (Drools Rule Language) file.
 * Note that this is not tested in mvel3 project. Will be tested in drlx-parser project.
 */
public class RuleDeclaration extends TypeDeclaration<RuleDeclaration> {

    private RuleBody ruleBody;

    // This is the original constructor
    public RuleDeclaration(TokenRange range, NodeList<AnnotationExpr> annotations, SimpleName name, RuleBody ruleBody) {
        this(range, new NodeList<>(), annotations, name, new NodeList<>(), ruleBody);
    }

    // This is added for generator
    @AllFieldsConstructor
    public RuleDeclaration(NodeList<Modifier> modifiers, NodeList<AnnotationExpr> annotations, SimpleName name, NodeList<BodyDeclaration<?>> members, RuleBody ruleBody) {
        this(null, modifiers, annotations, name, members, ruleBody);
    }

    /**
     * This constructor is used by the parser and is considered private.
     */
    @Generated("com.github.javaparser.generator.core.node.MainConstructorGenerator")
    public RuleDeclaration(TokenRange tokenRange, NodeList<Modifier> modifiers, NodeList<AnnotationExpr> annotations, SimpleName name, NodeList<BodyDeclaration<?>> members, RuleBody ruleBody) {
        super(tokenRange, modifiers, annotations, name, members);
        setRuleBody(ruleBody);
        customInitialization();
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.AcceptGenerator")
    public <R, A> R accept(final GenericVisitor<R, A> v, final A arg) {
        return v.visit(this, arg);
    }

    public static <A> VoidVisitor<A> getDrlVoidVisitor(VoidVisitor<A> v) {
        if (!(v instanceof DrlVoidVisitor)) {
            v = new DrlVoidVisitorAdapter<>(v);
        }
        return v;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.AcceptGenerator")
    public <A> void accept(final VoidVisitor<A> v, final A arg) {
        v.visit(this, arg);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public RuleBody getRuleBody() {
        return ruleBody;
    }

    @Override
    public ResolvedReferenceTypeDeclaration resolve() {
        return null;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public boolean isRuleDeclaration() {
        return true;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public RuleDeclaration asRuleDeclaration() {
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public Optional<RuleDeclaration> toRuleDeclaration() {
        return Optional.of(this);
    }

    @Generated("com.github.javaparser.generator.core.node.TypeCastingGenerator")
    public void ifRuleDeclaration(Consumer<RuleDeclaration> action) {
        action.accept(this);
    }

    @Generated("com.github.javaparser.generator.core.node.PropertyGenerator")
    public RuleDeclaration setRuleBody(final RuleBody ruleBody) {
        assertNotNull(ruleBody);
        if (ruleBody == this.ruleBody) {
            return this;
        }
        notifyPropertyChange(ObservableProperty.RULE_BODY, this.ruleBody, ruleBody);
        if (this.ruleBody != null)
            this.ruleBody.setParentNode(null);
        this.ruleBody = ruleBody;
        setAsParentNodeOf(ruleBody);
        return this;
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.ReplaceMethodGenerator")
    public boolean replace(Node node, Node replacementNode) {
        if (node == null) {
            return false;
        }
        if (node == ruleBody) {
            setRuleBody((RuleBody) replacementNode);
            return true;
        }
        return super.replace(node, replacementNode);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public RuleDeclaration clone() {
        return (RuleDeclaration) accept(new CloneVisitor(), null);
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.GetMetaModelGenerator")
    public RuleDeclarationMetaModel getMetaModel() {
        return JavaParserMetaModel.ruleDeclarationMetaModel;
    }
}
