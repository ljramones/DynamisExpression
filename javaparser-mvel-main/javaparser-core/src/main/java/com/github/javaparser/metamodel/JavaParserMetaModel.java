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
package com.github.javaparser.metamodel;

import com.github.javaparser.ast.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Warning: The content of this class is partially or completely generated - manual edits risk being overwritten.
 */
public final class JavaParserMetaModel {

    private JavaParserMetaModel() {
    }

    private static final List<BaseNodeMetaModel> nodeMetaModels = new ArrayList<>();

    @Generated("com.github.javaparser.generator.metamodel.MetaModelGenerator")
    private static void initializeConstructorParameters() {
        bodyDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        callableDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.modifiersPropertyMetaModel);
        callableDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        callableDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.typeParametersPropertyMetaModel);
        callableDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.namePropertyMetaModel);
        callableDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.parametersPropertyMetaModel);
        callableDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.thrownExceptionsPropertyMetaModel);
        callableDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.receiverParameterPropertyMetaModel);
        typeMetaModel.getConstructorParameters().add(typeMetaModel.annotationsPropertyMetaModel);
        annotationExprMetaModel.getConstructorParameters().add(annotationExprMetaModel.namePropertyMetaModel);
        referenceTypeMetaModel.getConstructorParameters().add(typeMetaModel.annotationsPropertyMetaModel);
        typeDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.modifiersPropertyMetaModel);
        typeDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        typeDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.namePropertyMetaModel);
        typeDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.membersPropertyMetaModel);
        literalStringValueExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        stringLiteralExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        moduleDeclarationMetaModel.getConstructorParameters().add(moduleDeclarationMetaModel.annotationsPropertyMetaModel);
        moduleDeclarationMetaModel.getConstructorParameters().add(moduleDeclarationMetaModel.namePropertyMetaModel);
        moduleDeclarationMetaModel.getConstructorParameters().add(moduleDeclarationMetaModel.isOpenPropertyMetaModel);
        moduleDeclarationMetaModel.getConstructorParameters().add(moduleDeclarationMetaModel.directivesPropertyMetaModel);
        arrayCreationLevelMetaModel.getConstructorParameters().add(arrayCreationLevelMetaModel.dimensionPropertyMetaModel);
        arrayCreationLevelMetaModel.getConstructorParameters().add(arrayCreationLevelMetaModel.annotationsPropertyMetaModel);
        compilationUnitMetaModel.getConstructorParameters().add(compilationUnitMetaModel.packageDeclarationPropertyMetaModel);
        compilationUnitMetaModel.getConstructorParameters().add(compilationUnitMetaModel.importsPropertyMetaModel);
        compilationUnitMetaModel.getConstructorParameters().add(compilationUnitMetaModel.typesPropertyMetaModel);
        compilationUnitMetaModel.getConstructorParameters().add(compilationUnitMetaModel.modulePropertyMetaModel);
        importDeclarationMetaModel.getConstructorParameters().add(importDeclarationMetaModel.namePropertyMetaModel);
        importDeclarationMetaModel.getConstructorParameters().add(importDeclarationMetaModel.isStaticPropertyMetaModel);
        importDeclarationMetaModel.getConstructorParameters().add(importDeclarationMetaModel.isAsteriskPropertyMetaModel);
        modifierMetaModel.getConstructorParameters().add(modifierMetaModel.keywordPropertyMetaModel);
        packageDeclarationMetaModel.getConstructorParameters().add(packageDeclarationMetaModel.annotationsPropertyMetaModel);
        packageDeclarationMetaModel.getConstructorParameters().add(packageDeclarationMetaModel.namePropertyMetaModel);
        annotationDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.modifiersPropertyMetaModel);
        annotationDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        annotationDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.namePropertyMetaModel);
        annotationDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.membersPropertyMetaModel);
        annotationMemberDeclarationMetaModel.getConstructorParameters().add(annotationMemberDeclarationMetaModel.modifiersPropertyMetaModel);
        annotationMemberDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        annotationMemberDeclarationMetaModel.getConstructorParameters().add(annotationMemberDeclarationMetaModel.typePropertyMetaModel);
        annotationMemberDeclarationMetaModel.getConstructorParameters().add(annotationMemberDeclarationMetaModel.namePropertyMetaModel);
        annotationMemberDeclarationMetaModel.getConstructorParameters().add(annotationMemberDeclarationMetaModel.defaultValuePropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.modifiersPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(classOrInterfaceDeclarationMetaModel.isInterfacePropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.namePropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(classOrInterfaceDeclarationMetaModel.typeParametersPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(classOrInterfaceDeclarationMetaModel.extendedTypesPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(classOrInterfaceDeclarationMetaModel.implementedTypesPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(classOrInterfaceDeclarationMetaModel.permittedTypesPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.membersPropertyMetaModel);
        constructorDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.modifiersPropertyMetaModel);
        constructorDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        constructorDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.typeParametersPropertyMetaModel);
        constructorDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.namePropertyMetaModel);
        constructorDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.parametersPropertyMetaModel);
        constructorDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.thrownExceptionsPropertyMetaModel);
        constructorDeclarationMetaModel.getConstructorParameters().add(constructorDeclarationMetaModel.bodyPropertyMetaModel);
        constructorDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.receiverParameterPropertyMetaModel);
        enumConstantDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        enumConstantDeclarationMetaModel.getConstructorParameters().add(enumConstantDeclarationMetaModel.namePropertyMetaModel);
        enumConstantDeclarationMetaModel.getConstructorParameters().add(enumConstantDeclarationMetaModel.argumentsPropertyMetaModel);
        enumConstantDeclarationMetaModel.getConstructorParameters().add(enumConstantDeclarationMetaModel.classBodyPropertyMetaModel);
        enumDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.modifiersPropertyMetaModel);
        enumDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        enumDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.namePropertyMetaModel);
        enumDeclarationMetaModel.getConstructorParameters().add(enumDeclarationMetaModel.implementedTypesPropertyMetaModel);
        enumDeclarationMetaModel.getConstructorParameters().add(enumDeclarationMetaModel.entriesPropertyMetaModel);
        enumDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.membersPropertyMetaModel);
        fieldDeclarationMetaModel.getConstructorParameters().add(fieldDeclarationMetaModel.modifiersPropertyMetaModel);
        fieldDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        fieldDeclarationMetaModel.getConstructorParameters().add(fieldDeclarationMetaModel.variablesPropertyMetaModel);
        initializerDeclarationMetaModel.getConstructorParameters().add(initializerDeclarationMetaModel.isStaticPropertyMetaModel);
        initializerDeclarationMetaModel.getConstructorParameters().add(initializerDeclarationMetaModel.bodyPropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.modifiersPropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.typeParametersPropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(methodDeclarationMetaModel.typePropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.namePropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.parametersPropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.thrownExceptionsPropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(methodDeclarationMetaModel.bodyPropertyMetaModel);
        methodDeclarationMetaModel.getConstructorParameters().add(callableDeclarationMetaModel.receiverParameterPropertyMetaModel);
        parameterMetaModel.getConstructorParameters().add(parameterMetaModel.modifiersPropertyMetaModel);
        parameterMetaModel.getConstructorParameters().add(parameterMetaModel.annotationsPropertyMetaModel);
        parameterMetaModel.getConstructorParameters().add(parameterMetaModel.typePropertyMetaModel);
        parameterMetaModel.getConstructorParameters().add(parameterMetaModel.isVarArgsPropertyMetaModel);
        parameterMetaModel.getConstructorParameters().add(parameterMetaModel.varArgsAnnotationsPropertyMetaModel);
        parameterMetaModel.getConstructorParameters().add(parameterMetaModel.namePropertyMetaModel);
        receiverParameterMetaModel.getConstructorParameters().add(receiverParameterMetaModel.annotationsPropertyMetaModel);
        receiverParameterMetaModel.getConstructorParameters().add(receiverParameterMetaModel.typePropertyMetaModel);
        receiverParameterMetaModel.getConstructorParameters().add(receiverParameterMetaModel.namePropertyMetaModel);
        recordDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.modifiersPropertyMetaModel);
        recordDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        recordDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.namePropertyMetaModel);
        recordDeclarationMetaModel.getConstructorParameters().add(recordDeclarationMetaModel.parametersPropertyMetaModel);
        recordDeclarationMetaModel.getConstructorParameters().add(recordDeclarationMetaModel.typeParametersPropertyMetaModel);
        recordDeclarationMetaModel.getConstructorParameters().add(recordDeclarationMetaModel.implementedTypesPropertyMetaModel);
        recordDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.membersPropertyMetaModel);
        recordDeclarationMetaModel.getConstructorParameters().add(recordDeclarationMetaModel.receiverParameterPropertyMetaModel);
        compactConstructorDeclarationMetaModel.getConstructorParameters().add(compactConstructorDeclarationMetaModel.modifiersPropertyMetaModel);
        compactConstructorDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        compactConstructorDeclarationMetaModel.getConstructorParameters().add(compactConstructorDeclarationMetaModel.typeParametersPropertyMetaModel);
        compactConstructorDeclarationMetaModel.getConstructorParameters().add(compactConstructorDeclarationMetaModel.namePropertyMetaModel);
        compactConstructorDeclarationMetaModel.getConstructorParameters().add(compactConstructorDeclarationMetaModel.thrownExceptionsPropertyMetaModel);
        compactConstructorDeclarationMetaModel.getConstructorParameters().add(compactConstructorDeclarationMetaModel.bodyPropertyMetaModel);
        variableDeclaratorMetaModel.getConstructorParameters().add(variableDeclaratorMetaModel.typePropertyMetaModel);
        variableDeclaratorMetaModel.getConstructorParameters().add(variableDeclaratorMetaModel.namePropertyMetaModel);
        variableDeclaratorMetaModel.getConstructorParameters().add(variableDeclaratorMetaModel.initializerPropertyMetaModel);
        commentMetaModel.getConstructorParameters().add(commentMetaModel.contentPropertyMetaModel);
        blockCommentMetaModel.getConstructorParameters().add(commentMetaModel.contentPropertyMetaModel);
        javadocCommentMetaModel.getConstructorParameters().add(commentMetaModel.contentPropertyMetaModel);
        lineCommentMetaModel.getConstructorParameters().add(commentMetaModel.contentPropertyMetaModel);
        arrayAccessExprMetaModel.getConstructorParameters().add(arrayAccessExprMetaModel.namePropertyMetaModel);
        arrayAccessExprMetaModel.getConstructorParameters().add(arrayAccessExprMetaModel.indexPropertyMetaModel);
        arrayCreationExprMetaModel.getConstructorParameters().add(arrayCreationExprMetaModel.elementTypePropertyMetaModel);
        arrayCreationExprMetaModel.getConstructorParameters().add(arrayCreationExprMetaModel.levelsPropertyMetaModel);
        arrayCreationExprMetaModel.getConstructorParameters().add(arrayCreationExprMetaModel.initializerPropertyMetaModel);
        arrayInitializerExprMetaModel.getConstructorParameters().add(arrayInitializerExprMetaModel.valuesPropertyMetaModel);
        assignExprMetaModel.getConstructorParameters().add(assignExprMetaModel.targetPropertyMetaModel);
        assignExprMetaModel.getConstructorParameters().add(assignExprMetaModel.valuePropertyMetaModel);
        assignExprMetaModel.getConstructorParameters().add(assignExprMetaModel.operatorPropertyMetaModel);
        binaryExprMetaModel.getConstructorParameters().add(binaryExprMetaModel.leftPropertyMetaModel);
        binaryExprMetaModel.getConstructorParameters().add(binaryExprMetaModel.rightPropertyMetaModel);
        binaryExprMetaModel.getConstructorParameters().add(binaryExprMetaModel.operatorPropertyMetaModel);
        booleanLiteralExprMetaModel.getConstructorParameters().add(booleanLiteralExprMetaModel.valuePropertyMetaModel);
        castExprMetaModel.getConstructorParameters().add(castExprMetaModel.typePropertyMetaModel);
        castExprMetaModel.getConstructorParameters().add(castExprMetaModel.expressionPropertyMetaModel);
        charLiteralExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        classExprMetaModel.getConstructorParameters().add(classExprMetaModel.typePropertyMetaModel);
        conditionalExprMetaModel.getConstructorParameters().add(conditionalExprMetaModel.conditionPropertyMetaModel);
        conditionalExprMetaModel.getConstructorParameters().add(conditionalExprMetaModel.thenExprPropertyMetaModel);
        conditionalExprMetaModel.getConstructorParameters().add(conditionalExprMetaModel.elseExprPropertyMetaModel);
        doubleLiteralExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        enclosedExprMetaModel.getConstructorParameters().add(enclosedExprMetaModel.innerPropertyMetaModel);
        fieldAccessExprMetaModel.getConstructorParameters().add(fieldAccessExprMetaModel.scopePropertyMetaModel);
        fieldAccessExprMetaModel.getConstructorParameters().add(fieldAccessExprMetaModel.typeArgumentsPropertyMetaModel);
        fieldAccessExprMetaModel.getConstructorParameters().add(fieldAccessExprMetaModel.namePropertyMetaModel);
        instanceOfExprMetaModel.getConstructorParameters().add(instanceOfExprMetaModel.expressionPropertyMetaModel);
        instanceOfExprMetaModel.getConstructorParameters().add(instanceOfExprMetaModel.typePropertyMetaModel);
        instanceOfExprMetaModel.getConstructorParameters().add(instanceOfExprMetaModel.patternPropertyMetaModel);
        integerLiteralExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        lambdaExprMetaModel.getConstructorParameters().add(lambdaExprMetaModel.parametersPropertyMetaModel);
        lambdaExprMetaModel.getConstructorParameters().add(lambdaExprMetaModel.bodyPropertyMetaModel);
        lambdaExprMetaModel.getConstructorParameters().add(lambdaExprMetaModel.isEnclosingParametersPropertyMetaModel);
        longLiteralExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        markerAnnotationExprMetaModel.getConstructorParameters().add(annotationExprMetaModel.namePropertyMetaModel);
        memberValuePairMetaModel.getConstructorParameters().add(memberValuePairMetaModel.namePropertyMetaModel);
        memberValuePairMetaModel.getConstructorParameters().add(memberValuePairMetaModel.valuePropertyMetaModel);
        methodCallExprMetaModel.getConstructorParameters().add(methodCallExprMetaModel.scopePropertyMetaModel);
        methodCallExprMetaModel.getConstructorParameters().add(methodCallExprMetaModel.typeArgumentsPropertyMetaModel);
        methodCallExprMetaModel.getConstructorParameters().add(methodCallExprMetaModel.namePropertyMetaModel);
        methodCallExprMetaModel.getConstructorParameters().add(methodCallExprMetaModel.argumentsPropertyMetaModel);
        methodReferenceExprMetaModel.getConstructorParameters().add(methodReferenceExprMetaModel.scopePropertyMetaModel);
        methodReferenceExprMetaModel.getConstructorParameters().add(methodReferenceExprMetaModel.typeArgumentsPropertyMetaModel);
        methodReferenceExprMetaModel.getConstructorParameters().add(methodReferenceExprMetaModel.identifierPropertyMetaModel);
        nameExprMetaModel.getConstructorParameters().add(nameExprMetaModel.namePropertyMetaModel);
        nameMetaModel.getConstructorParameters().add(nameMetaModel.qualifierPropertyMetaModel);
        nameMetaModel.getConstructorParameters().add(nameMetaModel.identifierPropertyMetaModel);
        normalAnnotationExprMetaModel.getConstructorParameters().add(annotationExprMetaModel.namePropertyMetaModel);
        normalAnnotationExprMetaModel.getConstructorParameters().add(normalAnnotationExprMetaModel.pairsPropertyMetaModel);
        objectCreationExprMetaModel.getConstructorParameters().add(objectCreationExprMetaModel.scopePropertyMetaModel);
        objectCreationExprMetaModel.getConstructorParameters().add(objectCreationExprMetaModel.typePropertyMetaModel);
        objectCreationExprMetaModel.getConstructorParameters().add(objectCreationExprMetaModel.typeArgumentsPropertyMetaModel);
        objectCreationExprMetaModel.getConstructorParameters().add(objectCreationExprMetaModel.argumentsPropertyMetaModel);
        objectCreationExprMetaModel.getConstructorParameters().add(objectCreationExprMetaModel.anonymousClassBodyPropertyMetaModel);
        patternExprMetaModel.getConstructorParameters().add(patternExprMetaModel.modifiersPropertyMetaModel);
        patternExprMetaModel.getConstructorParameters().add(patternExprMetaModel.typePropertyMetaModel);
        patternExprMetaModel.getConstructorParameters().add(patternExprMetaModel.namePropertyMetaModel);
        singleMemberAnnotationExprMetaModel.getConstructorParameters().add(annotationExprMetaModel.namePropertyMetaModel);
        singleMemberAnnotationExprMetaModel.getConstructorParameters().add(singleMemberAnnotationExprMetaModel.memberValuePropertyMetaModel);
        simpleNameMetaModel.getConstructorParameters().add(simpleNameMetaModel.identifierPropertyMetaModel);
        superExprMetaModel.getConstructorParameters().add(superExprMetaModel.typeNamePropertyMetaModel);
        switchExprMetaModel.getConstructorParameters().add(switchExprMetaModel.selectorPropertyMetaModel);
        switchExprMetaModel.getConstructorParameters().add(switchExprMetaModel.entriesPropertyMetaModel);
        textBlockLiteralExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        thisExprMetaModel.getConstructorParameters().add(thisExprMetaModel.typeNamePropertyMetaModel);
        typeExprMetaModel.getConstructorParameters().add(typeExprMetaModel.typePropertyMetaModel);
        unaryExprMetaModel.getConstructorParameters().add(unaryExprMetaModel.expressionPropertyMetaModel);
        unaryExprMetaModel.getConstructorParameters().add(unaryExprMetaModel.operatorPropertyMetaModel);
        variableDeclarationExprMetaModel.getConstructorParameters().add(variableDeclarationExprMetaModel.modifiersPropertyMetaModel);
        variableDeclarationExprMetaModel.getConstructorParameters().add(variableDeclarationExprMetaModel.annotationsPropertyMetaModel);
        variableDeclarationExprMetaModel.getConstructorParameters().add(variableDeclarationExprMetaModel.variablesPropertyMetaModel);
        assertStmtMetaModel.getConstructorParameters().add(assertStmtMetaModel.checkPropertyMetaModel);
        assertStmtMetaModel.getConstructorParameters().add(assertStmtMetaModel.messagePropertyMetaModel);
        blockStmtMetaModel.getConstructorParameters().add(blockStmtMetaModel.statementsPropertyMetaModel);
        breakStmtMetaModel.getConstructorParameters().add(breakStmtMetaModel.labelPropertyMetaModel);
        catchClauseMetaModel.getConstructorParameters().add(catchClauseMetaModel.parameterPropertyMetaModel);
        catchClauseMetaModel.getConstructorParameters().add(catchClauseMetaModel.bodyPropertyMetaModel);
        continueStmtMetaModel.getConstructorParameters().add(continueStmtMetaModel.labelPropertyMetaModel);
        doStmtMetaModel.getConstructorParameters().add(doStmtMetaModel.bodyPropertyMetaModel);
        doStmtMetaModel.getConstructorParameters().add(doStmtMetaModel.conditionPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.getConstructorParameters().add(explicitConstructorInvocationStmtMetaModel.typeArgumentsPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.getConstructorParameters().add(explicitConstructorInvocationStmtMetaModel.isThisPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.getConstructorParameters().add(explicitConstructorInvocationStmtMetaModel.expressionPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.getConstructorParameters().add(explicitConstructorInvocationStmtMetaModel.argumentsPropertyMetaModel);
        expressionStmtMetaModel.getConstructorParameters().add(expressionStmtMetaModel.expressionPropertyMetaModel);
        forEachStmtMetaModel.getConstructorParameters().add(forEachStmtMetaModel.variablePropertyMetaModel);
        forEachStmtMetaModel.getConstructorParameters().add(forEachStmtMetaModel.iterablePropertyMetaModel);
        forEachStmtMetaModel.getConstructorParameters().add(forEachStmtMetaModel.bodyPropertyMetaModel);
        forStmtMetaModel.getConstructorParameters().add(forStmtMetaModel.initializationPropertyMetaModel);
        forStmtMetaModel.getConstructorParameters().add(forStmtMetaModel.comparePropertyMetaModel);
        forStmtMetaModel.getConstructorParameters().add(forStmtMetaModel.updatePropertyMetaModel);
        forStmtMetaModel.getConstructorParameters().add(forStmtMetaModel.bodyPropertyMetaModel);
        ifStmtMetaModel.getConstructorParameters().add(ifStmtMetaModel.conditionPropertyMetaModel);
        ifStmtMetaModel.getConstructorParameters().add(ifStmtMetaModel.thenStmtPropertyMetaModel);
        ifStmtMetaModel.getConstructorParameters().add(ifStmtMetaModel.elseStmtPropertyMetaModel);
        labeledStmtMetaModel.getConstructorParameters().add(labeledStmtMetaModel.labelPropertyMetaModel);
        labeledStmtMetaModel.getConstructorParameters().add(labeledStmtMetaModel.statementPropertyMetaModel);
        localClassDeclarationStmtMetaModel.getConstructorParameters().add(localClassDeclarationStmtMetaModel.classDeclarationPropertyMetaModel);
        localRecordDeclarationStmtMetaModel.getConstructorParameters().add(localRecordDeclarationStmtMetaModel.recordDeclarationPropertyMetaModel);
        returnStmtMetaModel.getConstructorParameters().add(returnStmtMetaModel.expressionPropertyMetaModel);
        switchEntryMetaModel.getConstructorParameters().add(switchEntryMetaModel.labelsPropertyMetaModel);
        switchEntryMetaModel.getConstructorParameters().add(switchEntryMetaModel.typePropertyMetaModel);
        switchEntryMetaModel.getConstructorParameters().add(switchEntryMetaModel.statementsPropertyMetaModel);
        switchStmtMetaModel.getConstructorParameters().add(switchStmtMetaModel.selectorPropertyMetaModel);
        switchStmtMetaModel.getConstructorParameters().add(switchStmtMetaModel.entriesPropertyMetaModel);
        synchronizedStmtMetaModel.getConstructorParameters().add(synchronizedStmtMetaModel.expressionPropertyMetaModel);
        synchronizedStmtMetaModel.getConstructorParameters().add(synchronizedStmtMetaModel.bodyPropertyMetaModel);
        throwStmtMetaModel.getConstructorParameters().add(throwStmtMetaModel.expressionPropertyMetaModel);
        tryStmtMetaModel.getConstructorParameters().add(tryStmtMetaModel.resourcesPropertyMetaModel);
        tryStmtMetaModel.getConstructorParameters().add(tryStmtMetaModel.tryBlockPropertyMetaModel);
        tryStmtMetaModel.getConstructorParameters().add(tryStmtMetaModel.catchClausesPropertyMetaModel);
        tryStmtMetaModel.getConstructorParameters().add(tryStmtMetaModel.finallyBlockPropertyMetaModel);
        whileStmtMetaModel.getConstructorParameters().add(whileStmtMetaModel.conditionPropertyMetaModel);
        whileStmtMetaModel.getConstructorParameters().add(whileStmtMetaModel.bodyPropertyMetaModel);
        yieldStmtMetaModel.getConstructorParameters().add(yieldStmtMetaModel.expressionPropertyMetaModel);
        arrayTypeMetaModel.getConstructorParameters().add(arrayTypeMetaModel.componentTypePropertyMetaModel);
        arrayTypeMetaModel.getConstructorParameters().add(arrayTypeMetaModel.originPropertyMetaModel);
        arrayTypeMetaModel.getConstructorParameters().add(typeMetaModel.annotationsPropertyMetaModel);
        classOrInterfaceTypeMetaModel.getConstructorParameters().add(classOrInterfaceTypeMetaModel.scopePropertyMetaModel);
        classOrInterfaceTypeMetaModel.getConstructorParameters().add(classOrInterfaceTypeMetaModel.namePropertyMetaModel);
        classOrInterfaceTypeMetaModel.getConstructorParameters().add(classOrInterfaceTypeMetaModel.typeArgumentsPropertyMetaModel);
        classOrInterfaceTypeMetaModel.getConstructorParameters().add(typeMetaModel.annotationsPropertyMetaModel);
        intersectionTypeMetaModel.getConstructorParameters().add(intersectionTypeMetaModel.elementsPropertyMetaModel);
        primitiveTypeMetaModel.getConstructorParameters().add(primitiveTypeMetaModel.typePropertyMetaModel);
        primitiveTypeMetaModel.getConstructorParameters().add(typeMetaModel.annotationsPropertyMetaModel);
        typeParameterMetaModel.getConstructorParameters().add(typeParameterMetaModel.namePropertyMetaModel);
        typeParameterMetaModel.getConstructorParameters().add(typeParameterMetaModel.typeBoundPropertyMetaModel);
        typeParameterMetaModel.getConstructorParameters().add(typeMetaModel.annotationsPropertyMetaModel);
        unionTypeMetaModel.getConstructorParameters().add(unionTypeMetaModel.elementsPropertyMetaModel);
        wildcardTypeMetaModel.getConstructorParameters().add(wildcardTypeMetaModel.extendedTypePropertyMetaModel);
        wildcardTypeMetaModel.getConstructorParameters().add(wildcardTypeMetaModel.superTypePropertyMetaModel);
        wildcardTypeMetaModel.getConstructorParameters().add(typeMetaModel.annotationsPropertyMetaModel);
        moduleExportsDirectiveMetaModel.getConstructorParameters().add(moduleExportsDirectiveMetaModel.namePropertyMetaModel);
        moduleExportsDirectiveMetaModel.getConstructorParameters().add(moduleExportsDirectiveMetaModel.moduleNamesPropertyMetaModel);
        moduleOpensDirectiveMetaModel.getConstructorParameters().add(moduleOpensDirectiveMetaModel.namePropertyMetaModel);
        moduleOpensDirectiveMetaModel.getConstructorParameters().add(moduleOpensDirectiveMetaModel.moduleNamesPropertyMetaModel);
        moduleProvidesDirectiveMetaModel.getConstructorParameters().add(moduleProvidesDirectiveMetaModel.namePropertyMetaModel);
        moduleProvidesDirectiveMetaModel.getConstructorParameters().add(moduleProvidesDirectiveMetaModel.withPropertyMetaModel);
        moduleRequiresDirectiveMetaModel.getConstructorParameters().add(moduleRequiresDirectiveMetaModel.modifiersPropertyMetaModel);
        moduleRequiresDirectiveMetaModel.getConstructorParameters().add(moduleRequiresDirectiveMetaModel.namePropertyMetaModel);
        moduleUsesDirectiveMetaModel.getConstructorParameters().add(moduleUsesDirectiveMetaModel.namePropertyMetaModel);
        inlineCastExprMetaModel.getConstructorParameters().add(inlineCastExprMetaModel.typePropertyMetaModel);
        inlineCastExprMetaModel.getConstructorParameters().add(inlineCastExprMetaModel.expressionPropertyMetaModel);
        fullyQualifiedInlineCastExprMetaModel.getConstructorParameters().add(fullyQualifiedInlineCastExprMetaModel.expressionPropertyMetaModel);
        fullyQualifiedInlineCastExprMetaModel.getConstructorParameters().add(fullyQualifiedInlineCastExprMetaModel.typePropertyMetaModel);
        bigDecimalLiteralExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        bigIntegerLiteralExprMetaModel.getConstructorParameters().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        drlNameExprMetaModel.getConstructorParameters().add(nameExprMetaModel.namePropertyMetaModel);
        drlNameExprMetaModel.getConstructorParameters().add(drlNameExprMetaModel.backReferencesCountPropertyMetaModel);
        drlxExpressionMetaModel.getConstructorParameters().add(drlxExpressionMetaModel.bindPropertyMetaModel);
        drlxExpressionMetaModel.getConstructorParameters().add(drlxExpressionMetaModel.exprPropertyMetaModel);
        halfBinaryExprMetaModel.getConstructorParameters().add(halfBinaryExprMetaModel.rightPropertyMetaModel);
        halfBinaryExprMetaModel.getConstructorParameters().add(halfBinaryExprMetaModel.operatorPropertyMetaModel);
        pointFreeExprMetaModel.getConstructorParameters().add(pointFreeExprMetaModel.leftPropertyMetaModel);
        pointFreeExprMetaModel.getConstructorParameters().add(pointFreeExprMetaModel.rightPropertyMetaModel);
        pointFreeExprMetaModel.getConstructorParameters().add(pointFreeExprMetaModel.operatorPropertyMetaModel);
        pointFreeExprMetaModel.getConstructorParameters().add(pointFreeExprMetaModel.negatedPropertyMetaModel);
        pointFreeExprMetaModel.getConstructorParameters().add(pointFreeExprMetaModel.arg1PropertyMetaModel);
        pointFreeExprMetaModel.getConstructorParameters().add(pointFreeExprMetaModel.arg2PropertyMetaModel);
        pointFreeExprMetaModel.getConstructorParameters().add(pointFreeExprMetaModel.arg3PropertyMetaModel);
        pointFreeExprMetaModel.getConstructorParameters().add(pointFreeExprMetaModel.arg4PropertyMetaModel);
        halfPointFreeExprMetaModel.getConstructorParameters().add(halfPointFreeExprMetaModel.rightPropertyMetaModel);
        halfPointFreeExprMetaModel.getConstructorParameters().add(halfPointFreeExprMetaModel.operatorPropertyMetaModel);
        halfPointFreeExprMetaModel.getConstructorParameters().add(halfPointFreeExprMetaModel.negatedPropertyMetaModel);
        halfPointFreeExprMetaModel.getConstructorParameters().add(halfPointFreeExprMetaModel.arg1PropertyMetaModel);
        halfPointFreeExprMetaModel.getConstructorParameters().add(halfPointFreeExprMetaModel.arg2PropertyMetaModel);
        halfPointFreeExprMetaModel.getConstructorParameters().add(halfPointFreeExprMetaModel.arg3PropertyMetaModel);
        halfPointFreeExprMetaModel.getConstructorParameters().add(halfPointFreeExprMetaModel.arg4PropertyMetaModel);
        listCreationLiteralExpressionMetaModel.getConstructorParameters().add(listCreationLiteralExpressionMetaModel.expressionsPropertyMetaModel);
        listCreationLiteralExpressionElementMetaModel.getConstructorParameters().add(listCreationLiteralExpressionElementMetaModel.valuePropertyMetaModel);
        mapCreationLiteralExpressionMetaModel.getConstructorParameters().add(mapCreationLiteralExpressionMetaModel.expressionsPropertyMetaModel);
        mapCreationLiteralExpressionKeyValuePairMetaModel.getConstructorParameters().add(mapCreationLiteralExpressionKeyValuePairMetaModel.keyPropertyMetaModel);
        mapCreationLiteralExpressionKeyValuePairMetaModel.getConstructorParameters().add(mapCreationLiteralExpressionKeyValuePairMetaModel.valuePropertyMetaModel);
        nullSafeFieldAccessExprMetaModel.getConstructorParameters().add(nullSafeFieldAccessExprMetaModel.scopePropertyMetaModel);
        nullSafeFieldAccessExprMetaModel.getConstructorParameters().add(nullSafeFieldAccessExprMetaModel.typeArgumentsPropertyMetaModel);
        nullSafeFieldAccessExprMetaModel.getConstructorParameters().add(nullSafeFieldAccessExprMetaModel.namePropertyMetaModel);
        nullSafeMethodCallExprMetaModel.getConstructorParameters().add(nullSafeMethodCallExprMetaModel.scopePropertyMetaModel);
        nullSafeMethodCallExprMetaModel.getConstructorParameters().add(nullSafeMethodCallExprMetaModel.typeArgumentsPropertyMetaModel);
        nullSafeMethodCallExprMetaModel.getConstructorParameters().add(nullSafeMethodCallExprMetaModel.namePropertyMetaModel);
        nullSafeMethodCallExprMetaModel.getConstructorParameters().add(nullSafeMethodCallExprMetaModel.argumentsPropertyMetaModel);
        temporalLiteralChunkExprMetaModel.getConstructorParameters().add(temporalLiteralChunkExprMetaModel.valuePropertyMetaModel);
        temporalLiteralChunkExprMetaModel.getConstructorParameters().add(temporalLiteralChunkExprMetaModel.timeUnitPropertyMetaModel);
        temporalLiteralExprMetaModel.getConstructorParameters().add(temporalLiteralExprMetaModel.chunksPropertyMetaModel);
        temporalLiteralInfiniteChunkExprMetaModel.getConstructorParameters().add(temporalLiteralInfiniteChunkExprMetaModel.valuePropertyMetaModel);
        temporalLiteralInfiniteChunkExprMetaModel.getConstructorParameters().add(temporalLiteralInfiniteChunkExprMetaModel.timeUnitPropertyMetaModel);
        abstractContextStatementMetaModel.getConstructorParameters().add(abstractContextStatementMetaModel.targetPropertyMetaModel);
        abstractContextStatementMetaModel.getConstructorParameters().add(abstractContextStatementMetaModel.expressionsPropertyMetaModel);
        modifyStatementMetaModel.getConstructorParameters().add(abstractContextStatementMetaModel.targetPropertyMetaModel);
        modifyStatementMetaModel.getConstructorParameters().add(abstractContextStatementMetaModel.expressionsPropertyMetaModel);
        withStatementMetaModel.getConstructorParameters().add(abstractContextStatementMetaModel.targetPropertyMetaModel);
        withStatementMetaModel.getConstructorParameters().add(abstractContextStatementMetaModel.expressionsPropertyMetaModel);
        oOPathChunkMetaModel.getConstructorParameters().add(oOPathChunkMetaModel.fieldPropertyMetaModel);
        oOPathChunkMetaModel.getConstructorParameters().add(oOPathChunkMetaModel.inlineCastPropertyMetaModel);
        oOPathChunkMetaModel.getConstructorParameters().add(oOPathChunkMetaModel.conditionPropertyMetaModel);
        oOPathExprMetaModel.getConstructorParameters().add(oOPathExprMetaModel.chunksPropertyMetaModel);
        rulePatternMetaModel.getConstructorParameters().add(rulePatternMetaModel.typePropertyMetaModel);
        rulePatternMetaModel.getConstructorParameters().add(rulePatternMetaModel.bindPropertyMetaModel);
        rulePatternMetaModel.getConstructorParameters().add(rulePatternMetaModel.exprPropertyMetaModel);
        ruleJoinedPatternsMetaModel.getConstructorParameters().add(ruleJoinedPatternsMetaModel.typePropertyMetaModel);
        ruleJoinedPatternsMetaModel.getConstructorParameters().add(ruleJoinedPatternsMetaModel.itemsPropertyMetaModel);
        ruleConsequenceMetaModel.getConstructorParameters().add(ruleConsequenceMetaModel.statementPropertyMetaModel);
        ruleBodyMetaModel.getConstructorParameters().add(ruleBodyMetaModel.itemsPropertyMetaModel);
        ruleDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.modifiersPropertyMetaModel);
        ruleDeclarationMetaModel.getConstructorParameters().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        ruleDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.namePropertyMetaModel);
        ruleDeclarationMetaModel.getConstructorParameters().add(typeDeclarationMetaModel.membersPropertyMetaModel);
        ruleDeclarationMetaModel.getConstructorParameters().add(ruleDeclarationMetaModel.ruleBodyPropertyMetaModel);
    }

    public static List<BaseNodeMetaModel> getNodeMetaModels() {
        return nodeMetaModels;
    }

    @Generated("com.github.javaparser.generator.metamodel.MetaModelGenerator")
    private static void initializeNodeMetaModels() {
        nodeMetaModels.add(abstractContextStatementMetaModel);
        nodeMetaModels.add(annotationDeclarationMetaModel);
        nodeMetaModels.add(annotationExprMetaModel);
        nodeMetaModels.add(annotationMemberDeclarationMetaModel);
        nodeMetaModels.add(arrayAccessExprMetaModel);
        nodeMetaModels.add(arrayCreationExprMetaModel);
        nodeMetaModels.add(arrayCreationLevelMetaModel);
        nodeMetaModels.add(arrayInitializerExprMetaModel);
        nodeMetaModels.add(arrayTypeMetaModel);
        nodeMetaModels.add(assertStmtMetaModel);
        nodeMetaModels.add(assignExprMetaModel);
        nodeMetaModels.add(bigDecimalLiteralExprMetaModel);
        nodeMetaModels.add(bigIntegerLiteralExprMetaModel);
        nodeMetaModels.add(binaryExprMetaModel);
        nodeMetaModels.add(blockCommentMetaModel);
        nodeMetaModels.add(blockStmtMetaModel);
        nodeMetaModels.add(bodyDeclarationMetaModel);
        nodeMetaModels.add(booleanLiteralExprMetaModel);
        nodeMetaModels.add(breakStmtMetaModel);
        nodeMetaModels.add(callableDeclarationMetaModel);
        nodeMetaModels.add(castExprMetaModel);
        nodeMetaModels.add(catchClauseMetaModel);
        nodeMetaModels.add(charLiteralExprMetaModel);
        nodeMetaModels.add(classExprMetaModel);
        nodeMetaModels.add(classOrInterfaceDeclarationMetaModel);
        nodeMetaModels.add(classOrInterfaceTypeMetaModel);
        nodeMetaModels.add(commentMetaModel);
        nodeMetaModels.add(compactConstructorDeclarationMetaModel);
        nodeMetaModels.add(compilationUnitMetaModel);
        nodeMetaModels.add(conditionalExprMetaModel);
        nodeMetaModels.add(constructorDeclarationMetaModel);
        nodeMetaModels.add(continueStmtMetaModel);
        nodeMetaModels.add(doStmtMetaModel);
        nodeMetaModels.add(doubleLiteralExprMetaModel);
        nodeMetaModels.add(drlNameExprMetaModel);
        nodeMetaModels.add(drlxExpressionMetaModel);
        nodeMetaModels.add(emptyStmtMetaModel);
        nodeMetaModels.add(enclosedExprMetaModel);
        nodeMetaModels.add(enumConstantDeclarationMetaModel);
        nodeMetaModels.add(enumDeclarationMetaModel);
        nodeMetaModels.add(explicitConstructorInvocationStmtMetaModel);
        nodeMetaModels.add(expressionMetaModel);
        nodeMetaModels.add(expressionStmtMetaModel);
        nodeMetaModels.add(fieldAccessExprMetaModel);
        nodeMetaModels.add(fieldDeclarationMetaModel);
        nodeMetaModels.add(forEachStmtMetaModel);
        nodeMetaModels.add(forStmtMetaModel);
        nodeMetaModels.add(fullyQualifiedInlineCastExprMetaModel);
        nodeMetaModels.add(halfBinaryExprMetaModel);
        nodeMetaModels.add(halfPointFreeExprMetaModel);
        nodeMetaModels.add(ifStmtMetaModel);
        nodeMetaModels.add(importDeclarationMetaModel);
        nodeMetaModels.add(initializerDeclarationMetaModel);
        nodeMetaModels.add(inlineCastExprMetaModel);
        nodeMetaModels.add(instanceOfExprMetaModel);
        nodeMetaModels.add(integerLiteralExprMetaModel);
        nodeMetaModels.add(intersectionTypeMetaModel);
        nodeMetaModels.add(javadocCommentMetaModel);
        nodeMetaModels.add(labeledStmtMetaModel);
        nodeMetaModels.add(lambdaExprMetaModel);
        nodeMetaModels.add(lineCommentMetaModel);
        nodeMetaModels.add(listCreationLiteralExpressionElementMetaModel);
        nodeMetaModels.add(listCreationLiteralExpressionMetaModel);
        nodeMetaModels.add(literalExprMetaModel);
        nodeMetaModels.add(literalStringValueExprMetaModel);
        nodeMetaModels.add(localClassDeclarationStmtMetaModel);
        nodeMetaModels.add(localRecordDeclarationStmtMetaModel);
        nodeMetaModels.add(longLiteralExprMetaModel);
        nodeMetaModels.add(mapCreationLiteralExpressionKeyValuePairMetaModel);
        nodeMetaModels.add(mapCreationLiteralExpressionMetaModel);
        nodeMetaModels.add(markerAnnotationExprMetaModel);
        nodeMetaModels.add(memberValuePairMetaModel);
        nodeMetaModels.add(methodCallExprMetaModel);
        nodeMetaModels.add(methodDeclarationMetaModel);
        nodeMetaModels.add(methodReferenceExprMetaModel);
        nodeMetaModels.add(modifierMetaModel);
        nodeMetaModels.add(modifyStatementMetaModel);
        nodeMetaModels.add(moduleDeclarationMetaModel);
        nodeMetaModels.add(moduleDirectiveMetaModel);
        nodeMetaModels.add(moduleExportsDirectiveMetaModel);
        nodeMetaModels.add(moduleOpensDirectiveMetaModel);
        nodeMetaModels.add(moduleProvidesDirectiveMetaModel);
        nodeMetaModels.add(moduleRequiresDirectiveMetaModel);
        nodeMetaModels.add(moduleUsesDirectiveMetaModel);
        nodeMetaModels.add(nameExprMetaModel);
        nodeMetaModels.add(nameMetaModel);
        nodeMetaModels.add(nodeMetaModel);
        nodeMetaModels.add(normalAnnotationExprMetaModel);
        nodeMetaModels.add(nullLiteralExprMetaModel);
        nodeMetaModels.add(nullSafeFieldAccessExprMetaModel);
        nodeMetaModels.add(nullSafeMethodCallExprMetaModel);
        nodeMetaModels.add(oOPathChunkMetaModel);
        nodeMetaModels.add(oOPathExprMetaModel);
        nodeMetaModels.add(objectCreationExprMetaModel);
        nodeMetaModels.add(packageDeclarationMetaModel);
        nodeMetaModels.add(parameterMetaModel);
        nodeMetaModels.add(patternExprMetaModel);
        nodeMetaModels.add(pointFreeExprMetaModel);
        nodeMetaModels.add(primitiveTypeMetaModel);
        nodeMetaModels.add(receiverParameterMetaModel);
        nodeMetaModels.add(recordDeclarationMetaModel);
        nodeMetaModels.add(referenceTypeMetaModel);
        nodeMetaModels.add(returnStmtMetaModel);
        nodeMetaModels.add(ruleBodyMetaModel);
        nodeMetaModels.add(ruleConsequenceMetaModel);
        nodeMetaModels.add(ruleDeclarationMetaModel);
        nodeMetaModels.add(ruleItemMetaModel);
        nodeMetaModels.add(ruleJoinedPatternsMetaModel);
        nodeMetaModels.add(rulePatternMetaModel);
        nodeMetaModels.add(simpleNameMetaModel);
        nodeMetaModels.add(singleMemberAnnotationExprMetaModel);
        nodeMetaModels.add(statementMetaModel);
        nodeMetaModels.add(stringLiteralExprMetaModel);
        nodeMetaModels.add(superExprMetaModel);
        nodeMetaModels.add(switchEntryMetaModel);
        nodeMetaModels.add(switchExprMetaModel);
        nodeMetaModels.add(switchStmtMetaModel);
        nodeMetaModels.add(synchronizedStmtMetaModel);
        nodeMetaModels.add(temporalChunkExprMetaModel);
        nodeMetaModels.add(temporalLiteralChunkExprMetaModel);
        nodeMetaModels.add(temporalLiteralExprMetaModel);
        nodeMetaModels.add(temporalLiteralInfiniteChunkExprMetaModel);
        nodeMetaModels.add(textBlockLiteralExprMetaModel);
        nodeMetaModels.add(thisExprMetaModel);
        nodeMetaModels.add(throwStmtMetaModel);
        nodeMetaModels.add(tryStmtMetaModel);
        nodeMetaModels.add(typeDeclarationMetaModel);
        nodeMetaModels.add(typeExprMetaModel);
        nodeMetaModels.add(typeMetaModel);
        nodeMetaModels.add(typeParameterMetaModel);
        nodeMetaModels.add(unaryExprMetaModel);
        nodeMetaModels.add(unionTypeMetaModel);
        nodeMetaModels.add(unknownTypeMetaModel);
        nodeMetaModels.add(unparsableStmtMetaModel);
        nodeMetaModels.add(varTypeMetaModel);
        nodeMetaModels.add(variableDeclarationExprMetaModel);
        nodeMetaModels.add(variableDeclaratorMetaModel);
        nodeMetaModels.add(voidTypeMetaModel);
        nodeMetaModels.add(whileStmtMetaModel);
        nodeMetaModels.add(wildcardTypeMetaModel);
        nodeMetaModels.add(withStatementMetaModel);
        nodeMetaModels.add(yieldStmtMetaModel);
    }

    @Generated("com.github.javaparser.generator.metamodel.MetaModelGenerator")
    private static void initializePropertyMetaModels() {
        nodeMetaModel.commentPropertyMetaModel = new PropertyMetaModel(nodeMetaModel, "comment", com.github.javaparser.ast.comments.Comment.class, Optional.of(commentMetaModel), true, false, false, false);
        nodeMetaModel.getDeclaredPropertyMetaModels().add(nodeMetaModel.commentPropertyMetaModel);
        bodyDeclarationMetaModel.annotationsPropertyMetaModel = new PropertyMetaModel(bodyDeclarationMetaModel, "annotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        bodyDeclarationMetaModel.getDeclaredPropertyMetaModels().add(bodyDeclarationMetaModel.annotationsPropertyMetaModel);
        callableDeclarationMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(callableDeclarationMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        callableDeclarationMetaModel.getDeclaredPropertyMetaModels().add(callableDeclarationMetaModel.modifiersPropertyMetaModel);
        callableDeclarationMetaModel.namePropertyMetaModel = new PropertyMetaModel(callableDeclarationMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        callableDeclarationMetaModel.getDeclaredPropertyMetaModels().add(callableDeclarationMetaModel.namePropertyMetaModel);
        callableDeclarationMetaModel.parametersPropertyMetaModel = new PropertyMetaModel(callableDeclarationMetaModel, "parameters", com.github.javaparser.ast.body.Parameter.class, Optional.of(parameterMetaModel), false, false, true, false);
        callableDeclarationMetaModel.getDeclaredPropertyMetaModels().add(callableDeclarationMetaModel.parametersPropertyMetaModel);
        callableDeclarationMetaModel.receiverParameterPropertyMetaModel = new PropertyMetaModel(callableDeclarationMetaModel, "receiverParameter", com.github.javaparser.ast.body.ReceiverParameter.class, Optional.of(receiverParameterMetaModel), true, false, false, false);
        callableDeclarationMetaModel.getDeclaredPropertyMetaModels().add(callableDeclarationMetaModel.receiverParameterPropertyMetaModel);
        callableDeclarationMetaModel.thrownExceptionsPropertyMetaModel = new PropertyMetaModel(callableDeclarationMetaModel, "thrownExceptions", com.github.javaparser.ast.type.ReferenceType.class, Optional.of(referenceTypeMetaModel), false, false, true, false);
        callableDeclarationMetaModel.getDeclaredPropertyMetaModels().add(callableDeclarationMetaModel.thrownExceptionsPropertyMetaModel);
        callableDeclarationMetaModel.typeParametersPropertyMetaModel = new PropertyMetaModel(callableDeclarationMetaModel, "typeParameters", com.github.javaparser.ast.type.TypeParameter.class, Optional.of(typeParameterMetaModel), false, false, true, false);
        callableDeclarationMetaModel.getDeclaredPropertyMetaModels().add(callableDeclarationMetaModel.typeParametersPropertyMetaModel);
        typeMetaModel.annotationsPropertyMetaModel = new PropertyMetaModel(typeMetaModel, "annotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        typeMetaModel.getDeclaredPropertyMetaModels().add(typeMetaModel.annotationsPropertyMetaModel);
        annotationExprMetaModel.namePropertyMetaModel = new PropertyMetaModel(annotationExprMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        annotationExprMetaModel.getDeclaredPropertyMetaModels().add(annotationExprMetaModel.namePropertyMetaModel);
        typeDeclarationMetaModel.membersPropertyMetaModel = new PropertyMetaModel(typeDeclarationMetaModel, "members", com.github.javaparser.ast.body.BodyDeclaration.class, Optional.of(bodyDeclarationMetaModel), false, false, true, true);
        typeDeclarationMetaModel.getDeclaredPropertyMetaModels().add(typeDeclarationMetaModel.membersPropertyMetaModel);
        typeDeclarationMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(typeDeclarationMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        typeDeclarationMetaModel.getDeclaredPropertyMetaModels().add(typeDeclarationMetaModel.modifiersPropertyMetaModel);
        typeDeclarationMetaModel.namePropertyMetaModel = new PropertyMetaModel(typeDeclarationMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        typeDeclarationMetaModel.getDeclaredPropertyMetaModels().add(typeDeclarationMetaModel.namePropertyMetaModel);
        literalStringValueExprMetaModel.valuePropertyMetaModel = new PropertyMetaModel(literalStringValueExprMetaModel, "value", java.lang.String.class, Optional.empty(), false, false, false, false);
        literalStringValueExprMetaModel.getDeclaredPropertyMetaModels().add(literalStringValueExprMetaModel.valuePropertyMetaModel);
        moduleDeclarationMetaModel.annotationsPropertyMetaModel = new PropertyMetaModel(moduleDeclarationMetaModel, "annotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        moduleDeclarationMetaModel.getDeclaredPropertyMetaModels().add(moduleDeclarationMetaModel.annotationsPropertyMetaModel);
        moduleDeclarationMetaModel.directivesPropertyMetaModel = new PropertyMetaModel(moduleDeclarationMetaModel, "directives", com.github.javaparser.ast.modules.ModuleDirective.class, Optional.of(moduleDirectiveMetaModel), false, false, true, false);
        moduleDeclarationMetaModel.getDeclaredPropertyMetaModels().add(moduleDeclarationMetaModel.directivesPropertyMetaModel);
        moduleDeclarationMetaModel.isOpenPropertyMetaModel = new PropertyMetaModel(moduleDeclarationMetaModel, "isOpen", boolean.class, Optional.empty(), false, false, false, false);
        moduleDeclarationMetaModel.getDeclaredPropertyMetaModels().add(moduleDeclarationMetaModel.isOpenPropertyMetaModel);
        moduleDeclarationMetaModel.namePropertyMetaModel = new PropertyMetaModel(moduleDeclarationMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        moduleDeclarationMetaModel.getDeclaredPropertyMetaModels().add(moduleDeclarationMetaModel.namePropertyMetaModel);
        arrayCreationLevelMetaModel.annotationsPropertyMetaModel = new PropertyMetaModel(arrayCreationLevelMetaModel, "annotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        arrayCreationLevelMetaModel.getDeclaredPropertyMetaModels().add(arrayCreationLevelMetaModel.annotationsPropertyMetaModel);
        arrayCreationLevelMetaModel.dimensionPropertyMetaModel = new PropertyMetaModel(arrayCreationLevelMetaModel, "dimension", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        arrayCreationLevelMetaModel.getDeclaredPropertyMetaModels().add(arrayCreationLevelMetaModel.dimensionPropertyMetaModel);
        compilationUnitMetaModel.importsPropertyMetaModel = new PropertyMetaModel(compilationUnitMetaModel, "imports", com.github.javaparser.ast.ImportDeclaration.class, Optional.of(importDeclarationMetaModel), false, false, true, false);
        compilationUnitMetaModel.getDeclaredPropertyMetaModels().add(compilationUnitMetaModel.importsPropertyMetaModel);
        compilationUnitMetaModel.modulePropertyMetaModel = new PropertyMetaModel(compilationUnitMetaModel, "module", com.github.javaparser.ast.modules.ModuleDeclaration.class, Optional.of(moduleDeclarationMetaModel), true, false, false, false);
        compilationUnitMetaModel.getDeclaredPropertyMetaModels().add(compilationUnitMetaModel.modulePropertyMetaModel);
        compilationUnitMetaModel.packageDeclarationPropertyMetaModel = new PropertyMetaModel(compilationUnitMetaModel, "packageDeclaration", com.github.javaparser.ast.PackageDeclaration.class, Optional.of(packageDeclarationMetaModel), true, false, false, false);
        compilationUnitMetaModel.getDeclaredPropertyMetaModels().add(compilationUnitMetaModel.packageDeclarationPropertyMetaModel);
        compilationUnitMetaModel.typesPropertyMetaModel = new PropertyMetaModel(compilationUnitMetaModel, "types", com.github.javaparser.ast.body.TypeDeclaration.class, Optional.of(typeDeclarationMetaModel), false, false, true, true);
        compilationUnitMetaModel.getDeclaredPropertyMetaModels().add(compilationUnitMetaModel.typesPropertyMetaModel);
        importDeclarationMetaModel.isAsteriskPropertyMetaModel = new PropertyMetaModel(importDeclarationMetaModel, "isAsterisk", boolean.class, Optional.empty(), false, false, false, false);
        importDeclarationMetaModel.getDeclaredPropertyMetaModels().add(importDeclarationMetaModel.isAsteriskPropertyMetaModel);
        importDeclarationMetaModel.isStaticPropertyMetaModel = new PropertyMetaModel(importDeclarationMetaModel, "isStatic", boolean.class, Optional.empty(), false, false, false, false);
        importDeclarationMetaModel.getDeclaredPropertyMetaModels().add(importDeclarationMetaModel.isStaticPropertyMetaModel);
        importDeclarationMetaModel.namePropertyMetaModel = new PropertyMetaModel(importDeclarationMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        importDeclarationMetaModel.getDeclaredPropertyMetaModels().add(importDeclarationMetaModel.namePropertyMetaModel);
        modifierMetaModel.keywordPropertyMetaModel = new PropertyMetaModel(modifierMetaModel, "keyword", com.github.javaparser.ast.Modifier.Keyword.class, Optional.empty(), false, false, false, false);
        modifierMetaModel.getDeclaredPropertyMetaModels().add(modifierMetaModel.keywordPropertyMetaModel);
        packageDeclarationMetaModel.annotationsPropertyMetaModel = new PropertyMetaModel(packageDeclarationMetaModel, "annotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        packageDeclarationMetaModel.getDeclaredPropertyMetaModels().add(packageDeclarationMetaModel.annotationsPropertyMetaModel);
        packageDeclarationMetaModel.namePropertyMetaModel = new PropertyMetaModel(packageDeclarationMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        packageDeclarationMetaModel.getDeclaredPropertyMetaModels().add(packageDeclarationMetaModel.namePropertyMetaModel);
        annotationMemberDeclarationMetaModel.defaultValuePropertyMetaModel = new PropertyMetaModel(annotationMemberDeclarationMetaModel, "defaultValue", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        annotationMemberDeclarationMetaModel.getDeclaredPropertyMetaModels().add(annotationMemberDeclarationMetaModel.defaultValuePropertyMetaModel);
        annotationMemberDeclarationMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(annotationMemberDeclarationMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        annotationMemberDeclarationMetaModel.getDeclaredPropertyMetaModels().add(annotationMemberDeclarationMetaModel.modifiersPropertyMetaModel);
        annotationMemberDeclarationMetaModel.namePropertyMetaModel = new PropertyMetaModel(annotationMemberDeclarationMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        annotationMemberDeclarationMetaModel.getDeclaredPropertyMetaModels().add(annotationMemberDeclarationMetaModel.namePropertyMetaModel);
        annotationMemberDeclarationMetaModel.typePropertyMetaModel = new PropertyMetaModel(annotationMemberDeclarationMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        annotationMemberDeclarationMetaModel.getDeclaredPropertyMetaModels().add(annotationMemberDeclarationMetaModel.typePropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.extendedTypesPropertyMetaModel = new PropertyMetaModel(classOrInterfaceDeclarationMetaModel, "extendedTypes", com.github.javaparser.ast.type.ClassOrInterfaceType.class, Optional.of(classOrInterfaceTypeMetaModel), false, false, true, false);
        classOrInterfaceDeclarationMetaModel.getDeclaredPropertyMetaModels().add(classOrInterfaceDeclarationMetaModel.extendedTypesPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.implementedTypesPropertyMetaModel = new PropertyMetaModel(classOrInterfaceDeclarationMetaModel, "implementedTypes", com.github.javaparser.ast.type.ClassOrInterfaceType.class, Optional.of(classOrInterfaceTypeMetaModel), false, false, true, false);
        classOrInterfaceDeclarationMetaModel.getDeclaredPropertyMetaModels().add(classOrInterfaceDeclarationMetaModel.implementedTypesPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.isInterfacePropertyMetaModel = new PropertyMetaModel(classOrInterfaceDeclarationMetaModel, "isInterface", boolean.class, Optional.empty(), false, false, false, false);
        classOrInterfaceDeclarationMetaModel.getDeclaredPropertyMetaModels().add(classOrInterfaceDeclarationMetaModel.isInterfacePropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.permittedTypesPropertyMetaModel = new PropertyMetaModel(classOrInterfaceDeclarationMetaModel, "permittedTypes", com.github.javaparser.ast.type.ClassOrInterfaceType.class, Optional.of(classOrInterfaceTypeMetaModel), false, false, true, false);
        classOrInterfaceDeclarationMetaModel.getDeclaredPropertyMetaModels().add(classOrInterfaceDeclarationMetaModel.permittedTypesPropertyMetaModel);
        classOrInterfaceDeclarationMetaModel.typeParametersPropertyMetaModel = new PropertyMetaModel(classOrInterfaceDeclarationMetaModel, "typeParameters", com.github.javaparser.ast.type.TypeParameter.class, Optional.of(typeParameterMetaModel), false, false, true, false);
        classOrInterfaceDeclarationMetaModel.getDeclaredPropertyMetaModels().add(classOrInterfaceDeclarationMetaModel.typeParametersPropertyMetaModel);
        constructorDeclarationMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(constructorDeclarationMetaModel, "body", com.github.javaparser.ast.stmt.BlockStmt.class, Optional.of(blockStmtMetaModel), false, false, false, false);
        constructorDeclarationMetaModel.getDeclaredPropertyMetaModels().add(constructorDeclarationMetaModel.bodyPropertyMetaModel);
        enumConstantDeclarationMetaModel.argumentsPropertyMetaModel = new PropertyMetaModel(enumConstantDeclarationMetaModel, "arguments", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        enumConstantDeclarationMetaModel.getDeclaredPropertyMetaModels().add(enumConstantDeclarationMetaModel.argumentsPropertyMetaModel);
        enumConstantDeclarationMetaModel.classBodyPropertyMetaModel = new PropertyMetaModel(enumConstantDeclarationMetaModel, "classBody", com.github.javaparser.ast.body.BodyDeclaration.class, Optional.of(bodyDeclarationMetaModel), false, false, true, true);
        enumConstantDeclarationMetaModel.getDeclaredPropertyMetaModels().add(enumConstantDeclarationMetaModel.classBodyPropertyMetaModel);
        enumConstantDeclarationMetaModel.namePropertyMetaModel = new PropertyMetaModel(enumConstantDeclarationMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        enumConstantDeclarationMetaModel.getDeclaredPropertyMetaModels().add(enumConstantDeclarationMetaModel.namePropertyMetaModel);
        enumDeclarationMetaModel.entriesPropertyMetaModel = new PropertyMetaModel(enumDeclarationMetaModel, "entries", com.github.javaparser.ast.body.EnumConstantDeclaration.class, Optional.of(enumConstantDeclarationMetaModel), false, false, true, false);
        enumDeclarationMetaModel.getDeclaredPropertyMetaModels().add(enumDeclarationMetaModel.entriesPropertyMetaModel);
        enumDeclarationMetaModel.implementedTypesPropertyMetaModel = new PropertyMetaModel(enumDeclarationMetaModel, "implementedTypes", com.github.javaparser.ast.type.ClassOrInterfaceType.class, Optional.of(classOrInterfaceTypeMetaModel), false, false, true, false);
        enumDeclarationMetaModel.getDeclaredPropertyMetaModels().add(enumDeclarationMetaModel.implementedTypesPropertyMetaModel);
        fieldDeclarationMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(fieldDeclarationMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        fieldDeclarationMetaModel.getDeclaredPropertyMetaModels().add(fieldDeclarationMetaModel.modifiersPropertyMetaModel);
        fieldDeclarationMetaModel.variablesPropertyMetaModel = new PropertyMetaModel(fieldDeclarationMetaModel, "variables", com.github.javaparser.ast.body.VariableDeclarator.class, Optional.of(variableDeclaratorMetaModel), false, true, true, false);
        fieldDeclarationMetaModel.getDeclaredPropertyMetaModels().add(fieldDeclarationMetaModel.variablesPropertyMetaModel);
        fieldDeclarationMetaModel.maximumCommonTypePropertyMetaModel = new PropertyMetaModel(fieldDeclarationMetaModel, "maximumCommonType", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, false, false);
        fieldDeclarationMetaModel.getDerivedPropertyMetaModels().add(fieldDeclarationMetaModel.maximumCommonTypePropertyMetaModel);
        initializerDeclarationMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(initializerDeclarationMetaModel, "body", com.github.javaparser.ast.stmt.BlockStmt.class, Optional.of(blockStmtMetaModel), false, false, false, false);
        initializerDeclarationMetaModel.getDeclaredPropertyMetaModels().add(initializerDeclarationMetaModel.bodyPropertyMetaModel);
        initializerDeclarationMetaModel.isStaticPropertyMetaModel = new PropertyMetaModel(initializerDeclarationMetaModel, "isStatic", boolean.class, Optional.empty(), false, false, false, false);
        initializerDeclarationMetaModel.getDeclaredPropertyMetaModels().add(initializerDeclarationMetaModel.isStaticPropertyMetaModel);
        methodDeclarationMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(methodDeclarationMetaModel, "body", com.github.javaparser.ast.stmt.BlockStmt.class, Optional.of(blockStmtMetaModel), true, false, false, false);
        methodDeclarationMetaModel.getDeclaredPropertyMetaModels().add(methodDeclarationMetaModel.bodyPropertyMetaModel);
        methodDeclarationMetaModel.typePropertyMetaModel = new PropertyMetaModel(methodDeclarationMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        methodDeclarationMetaModel.getDeclaredPropertyMetaModels().add(methodDeclarationMetaModel.typePropertyMetaModel);
        parameterMetaModel.annotationsPropertyMetaModel = new PropertyMetaModel(parameterMetaModel, "annotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        parameterMetaModel.getDeclaredPropertyMetaModels().add(parameterMetaModel.annotationsPropertyMetaModel);
        parameterMetaModel.isVarArgsPropertyMetaModel = new PropertyMetaModel(parameterMetaModel, "isVarArgs", boolean.class, Optional.empty(), false, false, false, false);
        parameterMetaModel.getDeclaredPropertyMetaModels().add(parameterMetaModel.isVarArgsPropertyMetaModel);
        parameterMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(parameterMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        parameterMetaModel.getDeclaredPropertyMetaModels().add(parameterMetaModel.modifiersPropertyMetaModel);
        parameterMetaModel.namePropertyMetaModel = new PropertyMetaModel(parameterMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        parameterMetaModel.getDeclaredPropertyMetaModels().add(parameterMetaModel.namePropertyMetaModel);
        parameterMetaModel.typePropertyMetaModel = new PropertyMetaModel(parameterMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        parameterMetaModel.getDeclaredPropertyMetaModels().add(parameterMetaModel.typePropertyMetaModel);
        parameterMetaModel.varArgsAnnotationsPropertyMetaModel = new PropertyMetaModel(parameterMetaModel, "varArgsAnnotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        parameterMetaModel.getDeclaredPropertyMetaModels().add(parameterMetaModel.varArgsAnnotationsPropertyMetaModel);
        receiverParameterMetaModel.annotationsPropertyMetaModel = new PropertyMetaModel(receiverParameterMetaModel, "annotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        receiverParameterMetaModel.getDeclaredPropertyMetaModels().add(receiverParameterMetaModel.annotationsPropertyMetaModel);
        receiverParameterMetaModel.namePropertyMetaModel = new PropertyMetaModel(receiverParameterMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        receiverParameterMetaModel.getDeclaredPropertyMetaModels().add(receiverParameterMetaModel.namePropertyMetaModel);
        receiverParameterMetaModel.typePropertyMetaModel = new PropertyMetaModel(receiverParameterMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        receiverParameterMetaModel.getDeclaredPropertyMetaModels().add(receiverParameterMetaModel.typePropertyMetaModel);
        recordDeclarationMetaModel.implementedTypesPropertyMetaModel = new PropertyMetaModel(recordDeclarationMetaModel, "implementedTypes", com.github.javaparser.ast.type.ClassOrInterfaceType.class, Optional.of(classOrInterfaceTypeMetaModel), false, false, true, false);
        recordDeclarationMetaModel.getDeclaredPropertyMetaModels().add(recordDeclarationMetaModel.implementedTypesPropertyMetaModel);
        recordDeclarationMetaModel.parametersPropertyMetaModel = new PropertyMetaModel(recordDeclarationMetaModel, "parameters", com.github.javaparser.ast.body.Parameter.class, Optional.of(parameterMetaModel), false, false, true, false);
        recordDeclarationMetaModel.getDeclaredPropertyMetaModels().add(recordDeclarationMetaModel.parametersPropertyMetaModel);
        recordDeclarationMetaModel.receiverParameterPropertyMetaModel = new PropertyMetaModel(recordDeclarationMetaModel, "receiverParameter", com.github.javaparser.ast.body.ReceiverParameter.class, Optional.of(receiverParameterMetaModel), true, false, false, false);
        recordDeclarationMetaModel.getDeclaredPropertyMetaModels().add(recordDeclarationMetaModel.receiverParameterPropertyMetaModel);
        recordDeclarationMetaModel.typeParametersPropertyMetaModel = new PropertyMetaModel(recordDeclarationMetaModel, "typeParameters", com.github.javaparser.ast.type.TypeParameter.class, Optional.of(typeParameterMetaModel), false, false, true, false);
        recordDeclarationMetaModel.getDeclaredPropertyMetaModels().add(recordDeclarationMetaModel.typeParametersPropertyMetaModel);
        compactConstructorDeclarationMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(compactConstructorDeclarationMetaModel, "body", com.github.javaparser.ast.stmt.BlockStmt.class, Optional.of(blockStmtMetaModel), false, false, false, false);
        compactConstructorDeclarationMetaModel.getDeclaredPropertyMetaModels().add(compactConstructorDeclarationMetaModel.bodyPropertyMetaModel);
        compactConstructorDeclarationMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(compactConstructorDeclarationMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        compactConstructorDeclarationMetaModel.getDeclaredPropertyMetaModels().add(compactConstructorDeclarationMetaModel.modifiersPropertyMetaModel);
        compactConstructorDeclarationMetaModel.namePropertyMetaModel = new PropertyMetaModel(compactConstructorDeclarationMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        compactConstructorDeclarationMetaModel.getDeclaredPropertyMetaModels().add(compactConstructorDeclarationMetaModel.namePropertyMetaModel);
        compactConstructorDeclarationMetaModel.thrownExceptionsPropertyMetaModel = new PropertyMetaModel(compactConstructorDeclarationMetaModel, "thrownExceptions", com.github.javaparser.ast.type.ReferenceType.class, Optional.of(referenceTypeMetaModel), false, false, true, false);
        compactConstructorDeclarationMetaModel.getDeclaredPropertyMetaModels().add(compactConstructorDeclarationMetaModel.thrownExceptionsPropertyMetaModel);
        compactConstructorDeclarationMetaModel.typeParametersPropertyMetaModel = new PropertyMetaModel(compactConstructorDeclarationMetaModel, "typeParameters", com.github.javaparser.ast.type.TypeParameter.class, Optional.of(typeParameterMetaModel), false, false, true, false);
        compactConstructorDeclarationMetaModel.getDeclaredPropertyMetaModels().add(compactConstructorDeclarationMetaModel.typeParametersPropertyMetaModel);
        variableDeclaratorMetaModel.initializerPropertyMetaModel = new PropertyMetaModel(variableDeclaratorMetaModel, "initializer", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, true, false, false);
        variableDeclaratorMetaModel.getDeclaredPropertyMetaModels().add(variableDeclaratorMetaModel.initializerPropertyMetaModel);
        variableDeclaratorMetaModel.namePropertyMetaModel = new PropertyMetaModel(variableDeclaratorMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        variableDeclaratorMetaModel.getDeclaredPropertyMetaModels().add(variableDeclaratorMetaModel.namePropertyMetaModel);
        variableDeclaratorMetaModel.typePropertyMetaModel = new PropertyMetaModel(variableDeclaratorMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        variableDeclaratorMetaModel.getDeclaredPropertyMetaModels().add(variableDeclaratorMetaModel.typePropertyMetaModel);
        commentMetaModel.contentPropertyMetaModel = new PropertyMetaModel(commentMetaModel, "content", java.lang.String.class, Optional.empty(), false, false, false, false);
        commentMetaModel.getDeclaredPropertyMetaModels().add(commentMetaModel.contentPropertyMetaModel);
        arrayAccessExprMetaModel.indexPropertyMetaModel = new PropertyMetaModel(arrayAccessExprMetaModel, "index", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        arrayAccessExprMetaModel.getDeclaredPropertyMetaModels().add(arrayAccessExprMetaModel.indexPropertyMetaModel);
        arrayAccessExprMetaModel.namePropertyMetaModel = new PropertyMetaModel(arrayAccessExprMetaModel, "name", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        arrayAccessExprMetaModel.getDeclaredPropertyMetaModels().add(arrayAccessExprMetaModel.namePropertyMetaModel);
        arrayCreationExprMetaModel.elementTypePropertyMetaModel = new PropertyMetaModel(arrayCreationExprMetaModel, "elementType", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        arrayCreationExprMetaModel.getDeclaredPropertyMetaModels().add(arrayCreationExprMetaModel.elementTypePropertyMetaModel);
        arrayCreationExprMetaModel.initializerPropertyMetaModel = new PropertyMetaModel(arrayCreationExprMetaModel, "initializer", com.github.javaparser.ast.expr.ArrayInitializerExpr.class, Optional.of(arrayInitializerExprMetaModel), true, false, false, false);
        arrayCreationExprMetaModel.getDeclaredPropertyMetaModels().add(arrayCreationExprMetaModel.initializerPropertyMetaModel);
        arrayCreationExprMetaModel.levelsPropertyMetaModel = new PropertyMetaModel(arrayCreationExprMetaModel, "levels", com.github.javaparser.ast.ArrayCreationLevel.class, Optional.of(arrayCreationLevelMetaModel), false, true, true, false);
        arrayCreationExprMetaModel.getDeclaredPropertyMetaModels().add(arrayCreationExprMetaModel.levelsPropertyMetaModel);
        arrayInitializerExprMetaModel.valuesPropertyMetaModel = new PropertyMetaModel(arrayInitializerExprMetaModel, "values", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        arrayInitializerExprMetaModel.getDeclaredPropertyMetaModels().add(arrayInitializerExprMetaModel.valuesPropertyMetaModel);
        assignExprMetaModel.operatorPropertyMetaModel = new PropertyMetaModel(assignExprMetaModel, "operator", com.github.javaparser.ast.expr.AssignExpr.Operator.class, Optional.empty(), false, false, false, false);
        assignExprMetaModel.getDeclaredPropertyMetaModels().add(assignExprMetaModel.operatorPropertyMetaModel);
        assignExprMetaModel.targetPropertyMetaModel = new PropertyMetaModel(assignExprMetaModel, "target", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        assignExprMetaModel.getDeclaredPropertyMetaModels().add(assignExprMetaModel.targetPropertyMetaModel);
        assignExprMetaModel.valuePropertyMetaModel = new PropertyMetaModel(assignExprMetaModel, "value", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        assignExprMetaModel.getDeclaredPropertyMetaModels().add(assignExprMetaModel.valuePropertyMetaModel);
        binaryExprMetaModel.leftPropertyMetaModel = new PropertyMetaModel(binaryExprMetaModel, "left", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        binaryExprMetaModel.getDeclaredPropertyMetaModels().add(binaryExprMetaModel.leftPropertyMetaModel);
        binaryExprMetaModel.operatorPropertyMetaModel = new PropertyMetaModel(binaryExprMetaModel, "operator", com.github.javaparser.ast.expr.BinaryExpr.Operator.class, Optional.empty(), false, false, false, false);
        binaryExprMetaModel.getDeclaredPropertyMetaModels().add(binaryExprMetaModel.operatorPropertyMetaModel);
        binaryExprMetaModel.rightPropertyMetaModel = new PropertyMetaModel(binaryExprMetaModel, "right", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        binaryExprMetaModel.getDeclaredPropertyMetaModels().add(binaryExprMetaModel.rightPropertyMetaModel);
        booleanLiteralExprMetaModel.valuePropertyMetaModel = new PropertyMetaModel(booleanLiteralExprMetaModel, "value", boolean.class, Optional.empty(), false, false, false, false);
        booleanLiteralExprMetaModel.getDeclaredPropertyMetaModels().add(booleanLiteralExprMetaModel.valuePropertyMetaModel);
        castExprMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(castExprMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        castExprMetaModel.getDeclaredPropertyMetaModels().add(castExprMetaModel.expressionPropertyMetaModel);
        castExprMetaModel.typePropertyMetaModel = new PropertyMetaModel(castExprMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        castExprMetaModel.getDeclaredPropertyMetaModels().add(castExprMetaModel.typePropertyMetaModel);
        classExprMetaModel.typePropertyMetaModel = new PropertyMetaModel(classExprMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        classExprMetaModel.getDeclaredPropertyMetaModels().add(classExprMetaModel.typePropertyMetaModel);
        conditionalExprMetaModel.conditionPropertyMetaModel = new PropertyMetaModel(conditionalExprMetaModel, "condition", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        conditionalExprMetaModel.getDeclaredPropertyMetaModels().add(conditionalExprMetaModel.conditionPropertyMetaModel);
        conditionalExprMetaModel.elseExprPropertyMetaModel = new PropertyMetaModel(conditionalExprMetaModel, "elseExpr", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        conditionalExprMetaModel.getDeclaredPropertyMetaModels().add(conditionalExprMetaModel.elseExprPropertyMetaModel);
        conditionalExprMetaModel.thenExprPropertyMetaModel = new PropertyMetaModel(conditionalExprMetaModel, "thenExpr", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        conditionalExprMetaModel.getDeclaredPropertyMetaModels().add(conditionalExprMetaModel.thenExprPropertyMetaModel);
        enclosedExprMetaModel.innerPropertyMetaModel = new PropertyMetaModel(enclosedExprMetaModel, "inner", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        enclosedExprMetaModel.getDeclaredPropertyMetaModels().add(enclosedExprMetaModel.innerPropertyMetaModel);
        fieldAccessExprMetaModel.namePropertyMetaModel = new PropertyMetaModel(fieldAccessExprMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        fieldAccessExprMetaModel.getDeclaredPropertyMetaModels().add(fieldAccessExprMetaModel.namePropertyMetaModel);
        fieldAccessExprMetaModel.scopePropertyMetaModel = new PropertyMetaModel(fieldAccessExprMetaModel, "scope", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        fieldAccessExprMetaModel.getDeclaredPropertyMetaModels().add(fieldAccessExprMetaModel.scopePropertyMetaModel);
        fieldAccessExprMetaModel.typeArgumentsPropertyMetaModel = new PropertyMetaModel(fieldAccessExprMetaModel, "typeArguments", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, true, false);
        fieldAccessExprMetaModel.getDeclaredPropertyMetaModels().add(fieldAccessExprMetaModel.typeArgumentsPropertyMetaModel);
        fieldAccessExprMetaModel.usingDiamondOperatorPropertyMetaModel = new PropertyMetaModel(fieldAccessExprMetaModel, "usingDiamondOperator", boolean.class, Optional.empty(), false, false, false, false);
        fieldAccessExprMetaModel.getDerivedPropertyMetaModels().add(fieldAccessExprMetaModel.usingDiamondOperatorPropertyMetaModel);
        instanceOfExprMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(instanceOfExprMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        instanceOfExprMetaModel.getDeclaredPropertyMetaModels().add(instanceOfExprMetaModel.expressionPropertyMetaModel);
        instanceOfExprMetaModel.patternPropertyMetaModel = new PropertyMetaModel(instanceOfExprMetaModel, "pattern", com.github.javaparser.ast.expr.PatternExpr.class, Optional.of(patternExprMetaModel), true, false, false, false);
        instanceOfExprMetaModel.getDeclaredPropertyMetaModels().add(instanceOfExprMetaModel.patternPropertyMetaModel);
        instanceOfExprMetaModel.typePropertyMetaModel = new PropertyMetaModel(instanceOfExprMetaModel, "type", com.github.javaparser.ast.type.ReferenceType.class, Optional.of(referenceTypeMetaModel), false, false, false, false);
        instanceOfExprMetaModel.getDeclaredPropertyMetaModels().add(instanceOfExprMetaModel.typePropertyMetaModel);
        lambdaExprMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(lambdaExprMetaModel, "body", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, false, false);
        lambdaExprMetaModel.getDeclaredPropertyMetaModels().add(lambdaExprMetaModel.bodyPropertyMetaModel);
        lambdaExprMetaModel.isEnclosingParametersPropertyMetaModel = new PropertyMetaModel(lambdaExprMetaModel, "isEnclosingParameters", boolean.class, Optional.empty(), false, false, false, false);
        lambdaExprMetaModel.getDeclaredPropertyMetaModels().add(lambdaExprMetaModel.isEnclosingParametersPropertyMetaModel);
        lambdaExprMetaModel.parametersPropertyMetaModel = new PropertyMetaModel(lambdaExprMetaModel, "parameters", com.github.javaparser.ast.body.Parameter.class, Optional.of(parameterMetaModel), false, false, true, false);
        lambdaExprMetaModel.getDeclaredPropertyMetaModels().add(lambdaExprMetaModel.parametersPropertyMetaModel);
        lambdaExprMetaModel.expressionBodyPropertyMetaModel = new PropertyMetaModel(lambdaExprMetaModel, "expressionBody", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        lambdaExprMetaModel.getDerivedPropertyMetaModels().add(lambdaExprMetaModel.expressionBodyPropertyMetaModel);
        memberValuePairMetaModel.namePropertyMetaModel = new PropertyMetaModel(memberValuePairMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        memberValuePairMetaModel.getDeclaredPropertyMetaModels().add(memberValuePairMetaModel.namePropertyMetaModel);
        memberValuePairMetaModel.valuePropertyMetaModel = new PropertyMetaModel(memberValuePairMetaModel, "value", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        memberValuePairMetaModel.getDeclaredPropertyMetaModels().add(memberValuePairMetaModel.valuePropertyMetaModel);
        methodCallExprMetaModel.argumentsPropertyMetaModel = new PropertyMetaModel(methodCallExprMetaModel, "arguments", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        methodCallExprMetaModel.getDeclaredPropertyMetaModels().add(methodCallExprMetaModel.argumentsPropertyMetaModel);
        methodCallExprMetaModel.namePropertyMetaModel = new PropertyMetaModel(methodCallExprMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        methodCallExprMetaModel.getDeclaredPropertyMetaModels().add(methodCallExprMetaModel.namePropertyMetaModel);
        methodCallExprMetaModel.scopePropertyMetaModel = new PropertyMetaModel(methodCallExprMetaModel, "scope", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        methodCallExprMetaModel.getDeclaredPropertyMetaModels().add(methodCallExprMetaModel.scopePropertyMetaModel);
        methodCallExprMetaModel.typeArgumentsPropertyMetaModel = new PropertyMetaModel(methodCallExprMetaModel, "typeArguments", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, true, false);
        methodCallExprMetaModel.getDeclaredPropertyMetaModels().add(methodCallExprMetaModel.typeArgumentsPropertyMetaModel);
        methodCallExprMetaModel.usingDiamondOperatorPropertyMetaModel = new PropertyMetaModel(methodCallExprMetaModel, "usingDiamondOperator", boolean.class, Optional.empty(), false, false, false, false);
        methodCallExprMetaModel.getDerivedPropertyMetaModels().add(methodCallExprMetaModel.usingDiamondOperatorPropertyMetaModel);
        methodReferenceExprMetaModel.identifierPropertyMetaModel = new PropertyMetaModel(methodReferenceExprMetaModel, "identifier", java.lang.String.class, Optional.empty(), false, true, false, false);
        methodReferenceExprMetaModel.getDeclaredPropertyMetaModels().add(methodReferenceExprMetaModel.identifierPropertyMetaModel);
        methodReferenceExprMetaModel.scopePropertyMetaModel = new PropertyMetaModel(methodReferenceExprMetaModel, "scope", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        methodReferenceExprMetaModel.getDeclaredPropertyMetaModels().add(methodReferenceExprMetaModel.scopePropertyMetaModel);
        methodReferenceExprMetaModel.typeArgumentsPropertyMetaModel = new PropertyMetaModel(methodReferenceExprMetaModel, "typeArguments", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, true, false);
        methodReferenceExprMetaModel.getDeclaredPropertyMetaModels().add(methodReferenceExprMetaModel.typeArgumentsPropertyMetaModel);
        methodReferenceExprMetaModel.usingDiamondOperatorPropertyMetaModel = new PropertyMetaModel(methodReferenceExprMetaModel, "usingDiamondOperator", boolean.class, Optional.empty(), false, false, false, false);
        methodReferenceExprMetaModel.getDerivedPropertyMetaModels().add(methodReferenceExprMetaModel.usingDiamondOperatorPropertyMetaModel);
        nameExprMetaModel.namePropertyMetaModel = new PropertyMetaModel(nameExprMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        nameExprMetaModel.getDeclaredPropertyMetaModels().add(nameExprMetaModel.namePropertyMetaModel);
        nameMetaModel.identifierPropertyMetaModel = new PropertyMetaModel(nameMetaModel, "identifier", java.lang.String.class, Optional.empty(), false, true, false, false);
        nameMetaModel.getDeclaredPropertyMetaModels().add(nameMetaModel.identifierPropertyMetaModel);
        nameMetaModel.qualifierPropertyMetaModel = new PropertyMetaModel(nameMetaModel, "qualifier", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), true, false, false, false);
        nameMetaModel.getDeclaredPropertyMetaModels().add(nameMetaModel.qualifierPropertyMetaModel);
        normalAnnotationExprMetaModel.pairsPropertyMetaModel = new PropertyMetaModel(normalAnnotationExprMetaModel, "pairs", com.github.javaparser.ast.expr.MemberValuePair.class, Optional.of(memberValuePairMetaModel), false, false, true, false);
        normalAnnotationExprMetaModel.getDeclaredPropertyMetaModels().add(normalAnnotationExprMetaModel.pairsPropertyMetaModel);
        objectCreationExprMetaModel.anonymousClassBodyPropertyMetaModel = new PropertyMetaModel(objectCreationExprMetaModel, "anonymousClassBody", com.github.javaparser.ast.body.BodyDeclaration.class, Optional.of(bodyDeclarationMetaModel), true, false, true, true);
        objectCreationExprMetaModel.getDeclaredPropertyMetaModels().add(objectCreationExprMetaModel.anonymousClassBodyPropertyMetaModel);
        objectCreationExprMetaModel.argumentsPropertyMetaModel = new PropertyMetaModel(objectCreationExprMetaModel, "arguments", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        objectCreationExprMetaModel.getDeclaredPropertyMetaModels().add(objectCreationExprMetaModel.argumentsPropertyMetaModel);
        objectCreationExprMetaModel.scopePropertyMetaModel = new PropertyMetaModel(objectCreationExprMetaModel, "scope", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        objectCreationExprMetaModel.getDeclaredPropertyMetaModels().add(objectCreationExprMetaModel.scopePropertyMetaModel);
        objectCreationExprMetaModel.typePropertyMetaModel = new PropertyMetaModel(objectCreationExprMetaModel, "type", com.github.javaparser.ast.type.ClassOrInterfaceType.class, Optional.of(classOrInterfaceTypeMetaModel), false, false, false, false);
        objectCreationExprMetaModel.getDeclaredPropertyMetaModels().add(objectCreationExprMetaModel.typePropertyMetaModel);
        objectCreationExprMetaModel.typeArgumentsPropertyMetaModel = new PropertyMetaModel(objectCreationExprMetaModel, "typeArguments", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, true, false);
        objectCreationExprMetaModel.getDeclaredPropertyMetaModels().add(objectCreationExprMetaModel.typeArgumentsPropertyMetaModel);
        objectCreationExprMetaModel.usingDiamondOperatorPropertyMetaModel = new PropertyMetaModel(objectCreationExprMetaModel, "usingDiamondOperator", boolean.class, Optional.empty(), false, false, false, false);
        objectCreationExprMetaModel.getDerivedPropertyMetaModels().add(objectCreationExprMetaModel.usingDiamondOperatorPropertyMetaModel);
        patternExprMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(patternExprMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        patternExprMetaModel.getDeclaredPropertyMetaModels().add(patternExprMetaModel.modifiersPropertyMetaModel);
        patternExprMetaModel.namePropertyMetaModel = new PropertyMetaModel(patternExprMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        patternExprMetaModel.getDeclaredPropertyMetaModels().add(patternExprMetaModel.namePropertyMetaModel);
        patternExprMetaModel.typePropertyMetaModel = new PropertyMetaModel(patternExprMetaModel, "type", com.github.javaparser.ast.type.ReferenceType.class, Optional.of(referenceTypeMetaModel), false, false, false, false);
        patternExprMetaModel.getDeclaredPropertyMetaModels().add(patternExprMetaModel.typePropertyMetaModel);
        singleMemberAnnotationExprMetaModel.memberValuePropertyMetaModel = new PropertyMetaModel(singleMemberAnnotationExprMetaModel, "memberValue", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        singleMemberAnnotationExprMetaModel.getDeclaredPropertyMetaModels().add(singleMemberAnnotationExprMetaModel.memberValuePropertyMetaModel);
        simpleNameMetaModel.identifierPropertyMetaModel = new PropertyMetaModel(simpleNameMetaModel, "identifier", java.lang.String.class, Optional.empty(), false, true, false, false);
        simpleNameMetaModel.getDeclaredPropertyMetaModels().add(simpleNameMetaModel.identifierPropertyMetaModel);
        superExprMetaModel.typeNamePropertyMetaModel = new PropertyMetaModel(superExprMetaModel, "typeName", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), true, false, false, false);
        superExprMetaModel.getDeclaredPropertyMetaModels().add(superExprMetaModel.typeNamePropertyMetaModel);
        switchExprMetaModel.entriesPropertyMetaModel = new PropertyMetaModel(switchExprMetaModel, "entries", com.github.javaparser.ast.stmt.SwitchEntry.class, Optional.of(switchEntryMetaModel), false, false, true, false);
        switchExprMetaModel.getDeclaredPropertyMetaModels().add(switchExprMetaModel.entriesPropertyMetaModel);
        switchExprMetaModel.selectorPropertyMetaModel = new PropertyMetaModel(switchExprMetaModel, "selector", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        switchExprMetaModel.getDeclaredPropertyMetaModels().add(switchExprMetaModel.selectorPropertyMetaModel);
        thisExprMetaModel.typeNamePropertyMetaModel = new PropertyMetaModel(thisExprMetaModel, "typeName", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), true, false, false, false);
        thisExprMetaModel.getDeclaredPropertyMetaModels().add(thisExprMetaModel.typeNamePropertyMetaModel);
        typeExprMetaModel.typePropertyMetaModel = new PropertyMetaModel(typeExprMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        typeExprMetaModel.getDeclaredPropertyMetaModels().add(typeExprMetaModel.typePropertyMetaModel);
        unaryExprMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(unaryExprMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        unaryExprMetaModel.getDeclaredPropertyMetaModels().add(unaryExprMetaModel.expressionPropertyMetaModel);
        unaryExprMetaModel.operatorPropertyMetaModel = new PropertyMetaModel(unaryExprMetaModel, "operator", com.github.javaparser.ast.expr.UnaryExpr.Operator.class, Optional.empty(), false, false, false, false);
        unaryExprMetaModel.getDeclaredPropertyMetaModels().add(unaryExprMetaModel.operatorPropertyMetaModel);
        unaryExprMetaModel.postfixPropertyMetaModel = new PropertyMetaModel(unaryExprMetaModel, "postfix", boolean.class, Optional.empty(), false, false, false, false);
        unaryExprMetaModel.getDerivedPropertyMetaModels().add(unaryExprMetaModel.postfixPropertyMetaModel);
        unaryExprMetaModel.prefixPropertyMetaModel = new PropertyMetaModel(unaryExprMetaModel, "prefix", boolean.class, Optional.empty(), false, false, false, false);
        unaryExprMetaModel.getDerivedPropertyMetaModels().add(unaryExprMetaModel.prefixPropertyMetaModel);
        variableDeclarationExprMetaModel.annotationsPropertyMetaModel = new PropertyMetaModel(variableDeclarationExprMetaModel, "annotations", com.github.javaparser.ast.expr.AnnotationExpr.class, Optional.of(annotationExprMetaModel), false, false, true, false);
        variableDeclarationExprMetaModel.getDeclaredPropertyMetaModels().add(variableDeclarationExprMetaModel.annotationsPropertyMetaModel);
        variableDeclarationExprMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(variableDeclarationExprMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        variableDeclarationExprMetaModel.getDeclaredPropertyMetaModels().add(variableDeclarationExprMetaModel.modifiersPropertyMetaModel);
        variableDeclarationExprMetaModel.variablesPropertyMetaModel = new PropertyMetaModel(variableDeclarationExprMetaModel, "variables", com.github.javaparser.ast.body.VariableDeclarator.class, Optional.of(variableDeclaratorMetaModel), false, true, true, false);
        variableDeclarationExprMetaModel.getDeclaredPropertyMetaModels().add(variableDeclarationExprMetaModel.variablesPropertyMetaModel);
        variableDeclarationExprMetaModel.maximumCommonTypePropertyMetaModel = new PropertyMetaModel(variableDeclarationExprMetaModel, "maximumCommonType", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, false, false);
        variableDeclarationExprMetaModel.getDerivedPropertyMetaModels().add(variableDeclarationExprMetaModel.maximumCommonTypePropertyMetaModel);
        assertStmtMetaModel.checkPropertyMetaModel = new PropertyMetaModel(assertStmtMetaModel, "check", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        assertStmtMetaModel.getDeclaredPropertyMetaModels().add(assertStmtMetaModel.checkPropertyMetaModel);
        assertStmtMetaModel.messagePropertyMetaModel = new PropertyMetaModel(assertStmtMetaModel, "message", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        assertStmtMetaModel.getDeclaredPropertyMetaModels().add(assertStmtMetaModel.messagePropertyMetaModel);
        blockStmtMetaModel.statementsPropertyMetaModel = new PropertyMetaModel(blockStmtMetaModel, "statements", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, true, false);
        blockStmtMetaModel.getDeclaredPropertyMetaModels().add(blockStmtMetaModel.statementsPropertyMetaModel);
        breakStmtMetaModel.labelPropertyMetaModel = new PropertyMetaModel(breakStmtMetaModel, "label", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), true, false, false, false);
        breakStmtMetaModel.getDeclaredPropertyMetaModels().add(breakStmtMetaModel.labelPropertyMetaModel);
        catchClauseMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(catchClauseMetaModel, "body", com.github.javaparser.ast.stmt.BlockStmt.class, Optional.of(blockStmtMetaModel), false, false, false, false);
        catchClauseMetaModel.getDeclaredPropertyMetaModels().add(catchClauseMetaModel.bodyPropertyMetaModel);
        catchClauseMetaModel.parameterPropertyMetaModel = new PropertyMetaModel(catchClauseMetaModel, "parameter", com.github.javaparser.ast.body.Parameter.class, Optional.of(parameterMetaModel), false, false, false, false);
        catchClauseMetaModel.getDeclaredPropertyMetaModels().add(catchClauseMetaModel.parameterPropertyMetaModel);
        continueStmtMetaModel.labelPropertyMetaModel = new PropertyMetaModel(continueStmtMetaModel, "label", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), true, false, false, false);
        continueStmtMetaModel.getDeclaredPropertyMetaModels().add(continueStmtMetaModel.labelPropertyMetaModel);
        doStmtMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(doStmtMetaModel, "body", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, false, false);
        doStmtMetaModel.getDeclaredPropertyMetaModels().add(doStmtMetaModel.bodyPropertyMetaModel);
        doStmtMetaModel.conditionPropertyMetaModel = new PropertyMetaModel(doStmtMetaModel, "condition", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        doStmtMetaModel.getDeclaredPropertyMetaModels().add(doStmtMetaModel.conditionPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.argumentsPropertyMetaModel = new PropertyMetaModel(explicitConstructorInvocationStmtMetaModel, "arguments", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        explicitConstructorInvocationStmtMetaModel.getDeclaredPropertyMetaModels().add(explicitConstructorInvocationStmtMetaModel.argumentsPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(explicitConstructorInvocationStmtMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        explicitConstructorInvocationStmtMetaModel.getDeclaredPropertyMetaModels().add(explicitConstructorInvocationStmtMetaModel.expressionPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.isThisPropertyMetaModel = new PropertyMetaModel(explicitConstructorInvocationStmtMetaModel, "isThis", boolean.class, Optional.empty(), false, false, false, false);
        explicitConstructorInvocationStmtMetaModel.getDeclaredPropertyMetaModels().add(explicitConstructorInvocationStmtMetaModel.isThisPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.typeArgumentsPropertyMetaModel = new PropertyMetaModel(explicitConstructorInvocationStmtMetaModel, "typeArguments", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, true, false);
        explicitConstructorInvocationStmtMetaModel.getDeclaredPropertyMetaModels().add(explicitConstructorInvocationStmtMetaModel.typeArgumentsPropertyMetaModel);
        explicitConstructorInvocationStmtMetaModel.usingDiamondOperatorPropertyMetaModel = new PropertyMetaModel(explicitConstructorInvocationStmtMetaModel, "usingDiamondOperator", boolean.class, Optional.empty(), false, false, false, false);
        explicitConstructorInvocationStmtMetaModel.getDerivedPropertyMetaModels().add(explicitConstructorInvocationStmtMetaModel.usingDiamondOperatorPropertyMetaModel);
        expressionStmtMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(expressionStmtMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        expressionStmtMetaModel.getDeclaredPropertyMetaModels().add(expressionStmtMetaModel.expressionPropertyMetaModel);
        forEachStmtMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(forEachStmtMetaModel, "body", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, false, false);
        forEachStmtMetaModel.getDeclaredPropertyMetaModels().add(forEachStmtMetaModel.bodyPropertyMetaModel);
        forEachStmtMetaModel.iterablePropertyMetaModel = new PropertyMetaModel(forEachStmtMetaModel, "iterable", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        forEachStmtMetaModel.getDeclaredPropertyMetaModels().add(forEachStmtMetaModel.iterablePropertyMetaModel);
        forEachStmtMetaModel.variablePropertyMetaModel = new PropertyMetaModel(forEachStmtMetaModel, "variable", com.github.javaparser.ast.expr.VariableDeclarationExpr.class, Optional.of(variableDeclarationExprMetaModel), false, false, false, false);
        forEachStmtMetaModel.getDeclaredPropertyMetaModels().add(forEachStmtMetaModel.variablePropertyMetaModel);
        forStmtMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(forStmtMetaModel, "body", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, false, false);
        forStmtMetaModel.getDeclaredPropertyMetaModels().add(forStmtMetaModel.bodyPropertyMetaModel);
        forStmtMetaModel.comparePropertyMetaModel = new PropertyMetaModel(forStmtMetaModel, "compare", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        forStmtMetaModel.getDeclaredPropertyMetaModels().add(forStmtMetaModel.comparePropertyMetaModel);
        forStmtMetaModel.initializationPropertyMetaModel = new PropertyMetaModel(forStmtMetaModel, "initialization", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        forStmtMetaModel.getDeclaredPropertyMetaModels().add(forStmtMetaModel.initializationPropertyMetaModel);
        forStmtMetaModel.updatePropertyMetaModel = new PropertyMetaModel(forStmtMetaModel, "update", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        forStmtMetaModel.getDeclaredPropertyMetaModels().add(forStmtMetaModel.updatePropertyMetaModel);
        ifStmtMetaModel.conditionPropertyMetaModel = new PropertyMetaModel(ifStmtMetaModel, "condition", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        ifStmtMetaModel.getDeclaredPropertyMetaModels().add(ifStmtMetaModel.conditionPropertyMetaModel);
        ifStmtMetaModel.elseStmtPropertyMetaModel = new PropertyMetaModel(ifStmtMetaModel, "elseStmt", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), true, false, false, false);
        ifStmtMetaModel.getDeclaredPropertyMetaModels().add(ifStmtMetaModel.elseStmtPropertyMetaModel);
        ifStmtMetaModel.thenStmtPropertyMetaModel = new PropertyMetaModel(ifStmtMetaModel, "thenStmt", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, false, false);
        ifStmtMetaModel.getDeclaredPropertyMetaModels().add(ifStmtMetaModel.thenStmtPropertyMetaModel);
        ifStmtMetaModel.cascadingIfStmtPropertyMetaModel = new PropertyMetaModel(ifStmtMetaModel, "cascadingIfStmt", boolean.class, Optional.empty(), false, false, false, false);
        ifStmtMetaModel.getDerivedPropertyMetaModels().add(ifStmtMetaModel.cascadingIfStmtPropertyMetaModel);
        ifStmtMetaModel.elseBlockPropertyMetaModel = new PropertyMetaModel(ifStmtMetaModel, "elseBlock", boolean.class, Optional.empty(), false, false, false, false);
        ifStmtMetaModel.getDerivedPropertyMetaModels().add(ifStmtMetaModel.elseBlockPropertyMetaModel);
        ifStmtMetaModel.elseBranchPropertyMetaModel = new PropertyMetaModel(ifStmtMetaModel, "elseBranch", boolean.class, Optional.empty(), false, false, false, false);
        ifStmtMetaModel.getDerivedPropertyMetaModels().add(ifStmtMetaModel.elseBranchPropertyMetaModel);
        ifStmtMetaModel.thenBlockPropertyMetaModel = new PropertyMetaModel(ifStmtMetaModel, "thenBlock", boolean.class, Optional.empty(), false, false, false, false);
        ifStmtMetaModel.getDerivedPropertyMetaModels().add(ifStmtMetaModel.thenBlockPropertyMetaModel);
        labeledStmtMetaModel.labelPropertyMetaModel = new PropertyMetaModel(labeledStmtMetaModel, "label", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        labeledStmtMetaModel.getDeclaredPropertyMetaModels().add(labeledStmtMetaModel.labelPropertyMetaModel);
        labeledStmtMetaModel.statementPropertyMetaModel = new PropertyMetaModel(labeledStmtMetaModel, "statement", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, false, false);
        labeledStmtMetaModel.getDeclaredPropertyMetaModels().add(labeledStmtMetaModel.statementPropertyMetaModel);
        localClassDeclarationStmtMetaModel.classDeclarationPropertyMetaModel = new PropertyMetaModel(localClassDeclarationStmtMetaModel, "classDeclaration", com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class, Optional.of(classOrInterfaceDeclarationMetaModel), false, false, false, false);
        localClassDeclarationStmtMetaModel.getDeclaredPropertyMetaModels().add(localClassDeclarationStmtMetaModel.classDeclarationPropertyMetaModel);
        localRecordDeclarationStmtMetaModel.recordDeclarationPropertyMetaModel = new PropertyMetaModel(localRecordDeclarationStmtMetaModel, "recordDeclaration", com.github.javaparser.ast.body.RecordDeclaration.class, Optional.of(recordDeclarationMetaModel), false, false, false, false);
        localRecordDeclarationStmtMetaModel.getDeclaredPropertyMetaModels().add(localRecordDeclarationStmtMetaModel.recordDeclarationPropertyMetaModel);
        returnStmtMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(returnStmtMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        returnStmtMetaModel.getDeclaredPropertyMetaModels().add(returnStmtMetaModel.expressionPropertyMetaModel);
        switchEntryMetaModel.labelsPropertyMetaModel = new PropertyMetaModel(switchEntryMetaModel, "labels", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        switchEntryMetaModel.getDeclaredPropertyMetaModels().add(switchEntryMetaModel.labelsPropertyMetaModel);
        switchEntryMetaModel.statementsPropertyMetaModel = new PropertyMetaModel(switchEntryMetaModel, "statements", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, true, false);
        switchEntryMetaModel.getDeclaredPropertyMetaModels().add(switchEntryMetaModel.statementsPropertyMetaModel);
        switchEntryMetaModel.typePropertyMetaModel = new PropertyMetaModel(switchEntryMetaModel, "type", com.github.javaparser.ast.stmt.SwitchEntry.Type.class, Optional.empty(), false, false, false, false);
        switchEntryMetaModel.getDeclaredPropertyMetaModels().add(switchEntryMetaModel.typePropertyMetaModel);
        switchStmtMetaModel.entriesPropertyMetaModel = new PropertyMetaModel(switchStmtMetaModel, "entries", com.github.javaparser.ast.stmt.SwitchEntry.class, Optional.of(switchEntryMetaModel), false, false, true, false);
        switchStmtMetaModel.getDeclaredPropertyMetaModels().add(switchStmtMetaModel.entriesPropertyMetaModel);
        switchStmtMetaModel.selectorPropertyMetaModel = new PropertyMetaModel(switchStmtMetaModel, "selector", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        switchStmtMetaModel.getDeclaredPropertyMetaModels().add(switchStmtMetaModel.selectorPropertyMetaModel);
        synchronizedStmtMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(synchronizedStmtMetaModel, "body", com.github.javaparser.ast.stmt.BlockStmt.class, Optional.of(blockStmtMetaModel), false, false, false, false);
        synchronizedStmtMetaModel.getDeclaredPropertyMetaModels().add(synchronizedStmtMetaModel.bodyPropertyMetaModel);
        synchronizedStmtMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(synchronizedStmtMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        synchronizedStmtMetaModel.getDeclaredPropertyMetaModels().add(synchronizedStmtMetaModel.expressionPropertyMetaModel);
        throwStmtMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(throwStmtMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        throwStmtMetaModel.getDeclaredPropertyMetaModels().add(throwStmtMetaModel.expressionPropertyMetaModel);
        tryStmtMetaModel.catchClausesPropertyMetaModel = new PropertyMetaModel(tryStmtMetaModel, "catchClauses", com.github.javaparser.ast.stmt.CatchClause.class, Optional.of(catchClauseMetaModel), false, false, true, false);
        tryStmtMetaModel.getDeclaredPropertyMetaModels().add(tryStmtMetaModel.catchClausesPropertyMetaModel);
        tryStmtMetaModel.finallyBlockPropertyMetaModel = new PropertyMetaModel(tryStmtMetaModel, "finallyBlock", com.github.javaparser.ast.stmt.BlockStmt.class, Optional.of(blockStmtMetaModel), true, false, false, false);
        tryStmtMetaModel.getDeclaredPropertyMetaModels().add(tryStmtMetaModel.finallyBlockPropertyMetaModel);
        tryStmtMetaModel.resourcesPropertyMetaModel = new PropertyMetaModel(tryStmtMetaModel, "resources", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        tryStmtMetaModel.getDeclaredPropertyMetaModels().add(tryStmtMetaModel.resourcesPropertyMetaModel);
        tryStmtMetaModel.tryBlockPropertyMetaModel = new PropertyMetaModel(tryStmtMetaModel, "tryBlock", com.github.javaparser.ast.stmt.BlockStmt.class, Optional.of(blockStmtMetaModel), false, false, false, false);
        tryStmtMetaModel.getDeclaredPropertyMetaModels().add(tryStmtMetaModel.tryBlockPropertyMetaModel);
        whileStmtMetaModel.bodyPropertyMetaModel = new PropertyMetaModel(whileStmtMetaModel, "body", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, false, false);
        whileStmtMetaModel.getDeclaredPropertyMetaModels().add(whileStmtMetaModel.bodyPropertyMetaModel);
        whileStmtMetaModel.conditionPropertyMetaModel = new PropertyMetaModel(whileStmtMetaModel, "condition", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        whileStmtMetaModel.getDeclaredPropertyMetaModels().add(whileStmtMetaModel.conditionPropertyMetaModel);
        yieldStmtMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(yieldStmtMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        yieldStmtMetaModel.getDeclaredPropertyMetaModels().add(yieldStmtMetaModel.expressionPropertyMetaModel);
        arrayTypeMetaModel.componentTypePropertyMetaModel = new PropertyMetaModel(arrayTypeMetaModel, "componentType", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        arrayTypeMetaModel.getDeclaredPropertyMetaModels().add(arrayTypeMetaModel.componentTypePropertyMetaModel);
        arrayTypeMetaModel.originPropertyMetaModel = new PropertyMetaModel(arrayTypeMetaModel, "origin", com.github.javaparser.ast.type.ArrayType.Origin.class, Optional.empty(), false, false, false, false);
        arrayTypeMetaModel.getDeclaredPropertyMetaModels().add(arrayTypeMetaModel.originPropertyMetaModel);
        classOrInterfaceTypeMetaModel.namePropertyMetaModel = new PropertyMetaModel(classOrInterfaceTypeMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        classOrInterfaceTypeMetaModel.getDeclaredPropertyMetaModels().add(classOrInterfaceTypeMetaModel.namePropertyMetaModel);
        classOrInterfaceTypeMetaModel.scopePropertyMetaModel = new PropertyMetaModel(classOrInterfaceTypeMetaModel, "scope", com.github.javaparser.ast.type.ClassOrInterfaceType.class, Optional.of(classOrInterfaceTypeMetaModel), true, false, false, false);
        classOrInterfaceTypeMetaModel.getDeclaredPropertyMetaModels().add(classOrInterfaceTypeMetaModel.scopePropertyMetaModel);
        classOrInterfaceTypeMetaModel.typeArgumentsPropertyMetaModel = new PropertyMetaModel(classOrInterfaceTypeMetaModel, "typeArguments", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, true, false);
        classOrInterfaceTypeMetaModel.getDeclaredPropertyMetaModels().add(classOrInterfaceTypeMetaModel.typeArgumentsPropertyMetaModel);
        classOrInterfaceTypeMetaModel.usingDiamondOperatorPropertyMetaModel = new PropertyMetaModel(classOrInterfaceTypeMetaModel, "usingDiamondOperator", boolean.class, Optional.empty(), false, false, false, false);
        classOrInterfaceTypeMetaModel.getDerivedPropertyMetaModels().add(classOrInterfaceTypeMetaModel.usingDiamondOperatorPropertyMetaModel);
        intersectionTypeMetaModel.elementsPropertyMetaModel = new PropertyMetaModel(intersectionTypeMetaModel, "elements", com.github.javaparser.ast.type.ReferenceType.class, Optional.of(referenceTypeMetaModel), false, true, true, false);
        intersectionTypeMetaModel.getDeclaredPropertyMetaModels().add(intersectionTypeMetaModel.elementsPropertyMetaModel);
        primitiveTypeMetaModel.typePropertyMetaModel = new PropertyMetaModel(primitiveTypeMetaModel, "type", com.github.javaparser.ast.type.PrimitiveType.Primitive.class, Optional.empty(), false, false, false, false);
        primitiveTypeMetaModel.getDeclaredPropertyMetaModels().add(primitiveTypeMetaModel.typePropertyMetaModel);
        typeParameterMetaModel.namePropertyMetaModel = new PropertyMetaModel(typeParameterMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        typeParameterMetaModel.getDeclaredPropertyMetaModels().add(typeParameterMetaModel.namePropertyMetaModel);
        typeParameterMetaModel.typeBoundPropertyMetaModel = new PropertyMetaModel(typeParameterMetaModel, "typeBound", com.github.javaparser.ast.type.ClassOrInterfaceType.class, Optional.of(classOrInterfaceTypeMetaModel), false, false, true, false);
        typeParameterMetaModel.getDeclaredPropertyMetaModels().add(typeParameterMetaModel.typeBoundPropertyMetaModel);
        unionTypeMetaModel.elementsPropertyMetaModel = new PropertyMetaModel(unionTypeMetaModel, "elements", com.github.javaparser.ast.type.ReferenceType.class, Optional.of(referenceTypeMetaModel), false, true, true, false);
        unionTypeMetaModel.getDeclaredPropertyMetaModels().add(unionTypeMetaModel.elementsPropertyMetaModel);
        wildcardTypeMetaModel.extendedTypePropertyMetaModel = new PropertyMetaModel(wildcardTypeMetaModel, "extendedType", com.github.javaparser.ast.type.ReferenceType.class, Optional.of(referenceTypeMetaModel), true, false, false, false);
        wildcardTypeMetaModel.getDeclaredPropertyMetaModels().add(wildcardTypeMetaModel.extendedTypePropertyMetaModel);
        wildcardTypeMetaModel.superTypePropertyMetaModel = new PropertyMetaModel(wildcardTypeMetaModel, "superType", com.github.javaparser.ast.type.ReferenceType.class, Optional.of(referenceTypeMetaModel), true, false, false, false);
        wildcardTypeMetaModel.getDeclaredPropertyMetaModels().add(wildcardTypeMetaModel.superTypePropertyMetaModel);
        moduleExportsDirectiveMetaModel.moduleNamesPropertyMetaModel = new PropertyMetaModel(moduleExportsDirectiveMetaModel, "moduleNames", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, true, false);
        moduleExportsDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleExportsDirectiveMetaModel.moduleNamesPropertyMetaModel);
        moduleExportsDirectiveMetaModel.namePropertyMetaModel = new PropertyMetaModel(moduleExportsDirectiveMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        moduleExportsDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleExportsDirectiveMetaModel.namePropertyMetaModel);
        moduleOpensDirectiveMetaModel.moduleNamesPropertyMetaModel = new PropertyMetaModel(moduleOpensDirectiveMetaModel, "moduleNames", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, true, false);
        moduleOpensDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleOpensDirectiveMetaModel.moduleNamesPropertyMetaModel);
        moduleOpensDirectiveMetaModel.namePropertyMetaModel = new PropertyMetaModel(moduleOpensDirectiveMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        moduleOpensDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleOpensDirectiveMetaModel.namePropertyMetaModel);
        moduleProvidesDirectiveMetaModel.namePropertyMetaModel = new PropertyMetaModel(moduleProvidesDirectiveMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        moduleProvidesDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleProvidesDirectiveMetaModel.namePropertyMetaModel);
        moduleProvidesDirectiveMetaModel.withPropertyMetaModel = new PropertyMetaModel(moduleProvidesDirectiveMetaModel, "with", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, true, false);
        moduleProvidesDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleProvidesDirectiveMetaModel.withPropertyMetaModel);
        moduleRequiresDirectiveMetaModel.modifiersPropertyMetaModel = new PropertyMetaModel(moduleRequiresDirectiveMetaModel, "modifiers", com.github.javaparser.ast.Modifier.class, Optional.of(modifierMetaModel), false, false, true, false);
        moduleRequiresDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleRequiresDirectiveMetaModel.modifiersPropertyMetaModel);
        moduleRequiresDirectiveMetaModel.namePropertyMetaModel = new PropertyMetaModel(moduleRequiresDirectiveMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        moduleRequiresDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleRequiresDirectiveMetaModel.namePropertyMetaModel);
        moduleUsesDirectiveMetaModel.namePropertyMetaModel = new PropertyMetaModel(moduleUsesDirectiveMetaModel, "name", com.github.javaparser.ast.expr.Name.class, Optional.of(nameMetaModel), false, false, false, false);
        moduleUsesDirectiveMetaModel.getDeclaredPropertyMetaModels().add(moduleUsesDirectiveMetaModel.namePropertyMetaModel);
        inlineCastExprMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(inlineCastExprMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        inlineCastExprMetaModel.getDeclaredPropertyMetaModels().add(inlineCastExprMetaModel.expressionPropertyMetaModel);
        inlineCastExprMetaModel.typePropertyMetaModel = new PropertyMetaModel(inlineCastExprMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        inlineCastExprMetaModel.getDeclaredPropertyMetaModels().add(inlineCastExprMetaModel.typePropertyMetaModel);
        fullyQualifiedInlineCastExprMetaModel.expressionPropertyMetaModel = new PropertyMetaModel(fullyQualifiedInlineCastExprMetaModel, "expression", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        fullyQualifiedInlineCastExprMetaModel.getDeclaredPropertyMetaModels().add(fullyQualifiedInlineCastExprMetaModel.expressionPropertyMetaModel);
        fullyQualifiedInlineCastExprMetaModel.typePropertyMetaModel = new PropertyMetaModel(fullyQualifiedInlineCastExprMetaModel, "type", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), false, false, false, false);
        fullyQualifiedInlineCastExprMetaModel.getDeclaredPropertyMetaModels().add(fullyQualifiedInlineCastExprMetaModel.typePropertyMetaModel);
        drlNameExprMetaModel.backReferencesCountPropertyMetaModel = new PropertyMetaModel(drlNameExprMetaModel, "backReferencesCount", int.class, Optional.empty(), false, false, false, false);
        drlNameExprMetaModel.getDeclaredPropertyMetaModels().add(drlNameExprMetaModel.backReferencesCountPropertyMetaModel);
        drlxExpressionMetaModel.bindPropertyMetaModel = new PropertyMetaModel(drlxExpressionMetaModel, "bind", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        drlxExpressionMetaModel.getDeclaredPropertyMetaModels().add(drlxExpressionMetaModel.bindPropertyMetaModel);
        drlxExpressionMetaModel.exprPropertyMetaModel = new PropertyMetaModel(drlxExpressionMetaModel, "expr", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        drlxExpressionMetaModel.getDeclaredPropertyMetaModels().add(drlxExpressionMetaModel.exprPropertyMetaModel);
        halfBinaryExprMetaModel.operatorPropertyMetaModel = new PropertyMetaModel(halfBinaryExprMetaModel, "operator", org.mvel3.parser.ast.expr.HalfBinaryExpr.Operator.class, Optional.empty(), false, false, false, false);
        halfBinaryExprMetaModel.getDeclaredPropertyMetaModels().add(halfBinaryExprMetaModel.operatorPropertyMetaModel);
        halfBinaryExprMetaModel.rightPropertyMetaModel = new PropertyMetaModel(halfBinaryExprMetaModel, "right", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        halfBinaryExprMetaModel.getDeclaredPropertyMetaModels().add(halfBinaryExprMetaModel.rightPropertyMetaModel);
        pointFreeExprMetaModel.arg1PropertyMetaModel = new PropertyMetaModel(pointFreeExprMetaModel, "arg1", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        pointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(pointFreeExprMetaModel.arg1PropertyMetaModel);
        pointFreeExprMetaModel.arg2PropertyMetaModel = new PropertyMetaModel(pointFreeExprMetaModel, "arg2", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        pointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(pointFreeExprMetaModel.arg2PropertyMetaModel);
        pointFreeExprMetaModel.arg3PropertyMetaModel = new PropertyMetaModel(pointFreeExprMetaModel, "arg3", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        pointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(pointFreeExprMetaModel.arg3PropertyMetaModel);
        pointFreeExprMetaModel.arg4PropertyMetaModel = new PropertyMetaModel(pointFreeExprMetaModel, "arg4", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        pointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(pointFreeExprMetaModel.arg4PropertyMetaModel);
        pointFreeExprMetaModel.leftPropertyMetaModel = new PropertyMetaModel(pointFreeExprMetaModel, "left", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        pointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(pointFreeExprMetaModel.leftPropertyMetaModel);
        pointFreeExprMetaModel.negatedPropertyMetaModel = new PropertyMetaModel(pointFreeExprMetaModel, "negated", boolean.class, Optional.empty(), false, false, false, false);
        pointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(pointFreeExprMetaModel.negatedPropertyMetaModel);
        pointFreeExprMetaModel.operatorPropertyMetaModel = new PropertyMetaModel(pointFreeExprMetaModel, "operator", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        pointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(pointFreeExprMetaModel.operatorPropertyMetaModel);
        pointFreeExprMetaModel.rightPropertyMetaModel = new PropertyMetaModel(pointFreeExprMetaModel, "right", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        pointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(pointFreeExprMetaModel.rightPropertyMetaModel);
        halfPointFreeExprMetaModel.arg1PropertyMetaModel = new PropertyMetaModel(halfPointFreeExprMetaModel, "arg1", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        halfPointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(halfPointFreeExprMetaModel.arg1PropertyMetaModel);
        halfPointFreeExprMetaModel.arg2PropertyMetaModel = new PropertyMetaModel(halfPointFreeExprMetaModel, "arg2", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        halfPointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(halfPointFreeExprMetaModel.arg2PropertyMetaModel);
        halfPointFreeExprMetaModel.arg3PropertyMetaModel = new PropertyMetaModel(halfPointFreeExprMetaModel, "arg3", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        halfPointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(halfPointFreeExprMetaModel.arg3PropertyMetaModel);
        halfPointFreeExprMetaModel.arg4PropertyMetaModel = new PropertyMetaModel(halfPointFreeExprMetaModel, "arg4", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        halfPointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(halfPointFreeExprMetaModel.arg4PropertyMetaModel);
        halfPointFreeExprMetaModel.negatedPropertyMetaModel = new PropertyMetaModel(halfPointFreeExprMetaModel, "negated", boolean.class, Optional.empty(), false, false, false, false);
        halfPointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(halfPointFreeExprMetaModel.negatedPropertyMetaModel);
        halfPointFreeExprMetaModel.operatorPropertyMetaModel = new PropertyMetaModel(halfPointFreeExprMetaModel, "operator", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        halfPointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(halfPointFreeExprMetaModel.operatorPropertyMetaModel);
        halfPointFreeExprMetaModel.rightPropertyMetaModel = new PropertyMetaModel(halfPointFreeExprMetaModel, "right", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        halfPointFreeExprMetaModel.getDeclaredPropertyMetaModels().add(halfPointFreeExprMetaModel.rightPropertyMetaModel);
        listCreationLiteralExpressionMetaModel.expressionsPropertyMetaModel = new PropertyMetaModel(listCreationLiteralExpressionMetaModel, "expressions", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        listCreationLiteralExpressionMetaModel.getDeclaredPropertyMetaModels().add(listCreationLiteralExpressionMetaModel.expressionsPropertyMetaModel);
        listCreationLiteralExpressionElementMetaModel.valuePropertyMetaModel = new PropertyMetaModel(listCreationLiteralExpressionElementMetaModel, "value", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        listCreationLiteralExpressionElementMetaModel.getDeclaredPropertyMetaModels().add(listCreationLiteralExpressionElementMetaModel.valuePropertyMetaModel);
        mapCreationLiteralExpressionMetaModel.expressionsPropertyMetaModel = new PropertyMetaModel(mapCreationLiteralExpressionMetaModel, "expressions", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        mapCreationLiteralExpressionMetaModel.getDeclaredPropertyMetaModels().add(mapCreationLiteralExpressionMetaModel.expressionsPropertyMetaModel);
        mapCreationLiteralExpressionKeyValuePairMetaModel.keyPropertyMetaModel = new PropertyMetaModel(mapCreationLiteralExpressionKeyValuePairMetaModel, "key", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        mapCreationLiteralExpressionKeyValuePairMetaModel.getDeclaredPropertyMetaModels().add(mapCreationLiteralExpressionKeyValuePairMetaModel.keyPropertyMetaModel);
        mapCreationLiteralExpressionKeyValuePairMetaModel.valuePropertyMetaModel = new PropertyMetaModel(mapCreationLiteralExpressionKeyValuePairMetaModel, "value", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        mapCreationLiteralExpressionKeyValuePairMetaModel.getDeclaredPropertyMetaModels().add(mapCreationLiteralExpressionKeyValuePairMetaModel.valuePropertyMetaModel);
        nullSafeFieldAccessExprMetaModel.namePropertyMetaModel = new PropertyMetaModel(nullSafeFieldAccessExprMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        nullSafeFieldAccessExprMetaModel.getDeclaredPropertyMetaModels().add(nullSafeFieldAccessExprMetaModel.namePropertyMetaModel);
        nullSafeFieldAccessExprMetaModel.scopePropertyMetaModel = new PropertyMetaModel(nullSafeFieldAccessExprMetaModel, "scope", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        nullSafeFieldAccessExprMetaModel.getDeclaredPropertyMetaModels().add(nullSafeFieldAccessExprMetaModel.scopePropertyMetaModel);
        nullSafeFieldAccessExprMetaModel.typeArgumentsPropertyMetaModel = new PropertyMetaModel(nullSafeFieldAccessExprMetaModel, "typeArguments", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, true, false);
        nullSafeFieldAccessExprMetaModel.getDeclaredPropertyMetaModels().add(nullSafeFieldAccessExprMetaModel.typeArgumentsPropertyMetaModel);
        nullSafeFieldAccessExprMetaModel.usingDiamondOperatorPropertyMetaModel = new PropertyMetaModel(nullSafeFieldAccessExprMetaModel, "usingDiamondOperator", boolean.class, Optional.empty(), false, false, false, false);
        nullSafeFieldAccessExprMetaModel.getDerivedPropertyMetaModels().add(nullSafeFieldAccessExprMetaModel.usingDiamondOperatorPropertyMetaModel);
        nullSafeMethodCallExprMetaModel.argumentsPropertyMetaModel = new PropertyMetaModel(nullSafeMethodCallExprMetaModel, "arguments", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, true, false);
        nullSafeMethodCallExprMetaModel.getDeclaredPropertyMetaModels().add(nullSafeMethodCallExprMetaModel.argumentsPropertyMetaModel);
        nullSafeMethodCallExprMetaModel.namePropertyMetaModel = new PropertyMetaModel(nullSafeMethodCallExprMetaModel, "name", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        nullSafeMethodCallExprMetaModel.getDeclaredPropertyMetaModels().add(nullSafeMethodCallExprMetaModel.namePropertyMetaModel);
        nullSafeMethodCallExprMetaModel.scopePropertyMetaModel = new PropertyMetaModel(nullSafeMethodCallExprMetaModel, "scope", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), true, false, false, false);
        nullSafeMethodCallExprMetaModel.getDeclaredPropertyMetaModels().add(nullSafeMethodCallExprMetaModel.scopePropertyMetaModel);
        nullSafeMethodCallExprMetaModel.typeArgumentsPropertyMetaModel = new PropertyMetaModel(nullSafeMethodCallExprMetaModel, "typeArguments", com.github.javaparser.ast.type.Type.class, Optional.of(typeMetaModel), true, false, true, false);
        nullSafeMethodCallExprMetaModel.getDeclaredPropertyMetaModels().add(nullSafeMethodCallExprMetaModel.typeArgumentsPropertyMetaModel);
        nullSafeMethodCallExprMetaModel.usingDiamondOperatorPropertyMetaModel = new PropertyMetaModel(nullSafeMethodCallExprMetaModel, "usingDiamondOperator", boolean.class, Optional.empty(), false, false, false, false);
        nullSafeMethodCallExprMetaModel.getDerivedPropertyMetaModels().add(nullSafeMethodCallExprMetaModel.usingDiamondOperatorPropertyMetaModel);
        temporalLiteralChunkExprMetaModel.timeUnitPropertyMetaModel = new PropertyMetaModel(temporalLiteralChunkExprMetaModel, "timeUnit", java.util.concurrent.TimeUnit.class, Optional.empty(), false, false, false, false);
        temporalLiteralChunkExprMetaModel.getDeclaredPropertyMetaModels().add(temporalLiteralChunkExprMetaModel.timeUnitPropertyMetaModel);
        temporalLiteralChunkExprMetaModel.valuePropertyMetaModel = new PropertyMetaModel(temporalLiteralChunkExprMetaModel, "value", int.class, Optional.empty(), false, false, false, false);
        temporalLiteralChunkExprMetaModel.getDeclaredPropertyMetaModels().add(temporalLiteralChunkExprMetaModel.valuePropertyMetaModel);
        temporalLiteralExprMetaModel.chunksPropertyMetaModel = new PropertyMetaModel(temporalLiteralExprMetaModel, "chunks", org.mvel3.parser.ast.expr.TemporalChunkExpr.class, Optional.of(temporalChunkExprMetaModel), false, false, true, false);
        temporalLiteralExprMetaModel.getDeclaredPropertyMetaModels().add(temporalLiteralExprMetaModel.chunksPropertyMetaModel);
        temporalLiteralInfiniteChunkExprMetaModel.timeUnitPropertyMetaModel = new PropertyMetaModel(temporalLiteralInfiniteChunkExprMetaModel, "timeUnit", java.util.concurrent.TimeUnit.class, Optional.empty(), false, false, false, false);
        temporalLiteralInfiniteChunkExprMetaModel.getDeclaredPropertyMetaModels().add(temporalLiteralInfiniteChunkExprMetaModel.timeUnitPropertyMetaModel);
        temporalLiteralInfiniteChunkExprMetaModel.valuePropertyMetaModel = new PropertyMetaModel(temporalLiteralInfiniteChunkExprMetaModel, "value", java.lang.String.class, Optional.empty(), false, false, false, false);
        temporalLiteralInfiniteChunkExprMetaModel.getDeclaredPropertyMetaModels().add(temporalLiteralInfiniteChunkExprMetaModel.valuePropertyMetaModel);
        abstractContextStatementMetaModel.expressionsPropertyMetaModel = new PropertyMetaModel(abstractContextStatementMetaModel, "expressions", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, true, false);
        abstractContextStatementMetaModel.getDeclaredPropertyMetaModels().add(abstractContextStatementMetaModel.expressionsPropertyMetaModel);
        abstractContextStatementMetaModel.targetPropertyMetaModel = new PropertyMetaModel(abstractContextStatementMetaModel, "target", com.github.javaparser.ast.expr.Expression.class, Optional.of(expressionMetaModel), false, false, false, false);
        abstractContextStatementMetaModel.getDeclaredPropertyMetaModels().add(abstractContextStatementMetaModel.targetPropertyMetaModel);
        oOPathChunkMetaModel.conditionPropertyMetaModel = new PropertyMetaModel(oOPathChunkMetaModel, "condition", org.mvel3.parser.ast.expr.DrlxExpression.class, Optional.of(drlxExpressionMetaModel), false, false, true, false);
        oOPathChunkMetaModel.getDeclaredPropertyMetaModels().add(oOPathChunkMetaModel.conditionPropertyMetaModel);
        oOPathChunkMetaModel.fieldPropertyMetaModel = new PropertyMetaModel(oOPathChunkMetaModel, "field", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        oOPathChunkMetaModel.getDeclaredPropertyMetaModels().add(oOPathChunkMetaModel.fieldPropertyMetaModel);
        oOPathChunkMetaModel.inlineCastPropertyMetaModel = new PropertyMetaModel(oOPathChunkMetaModel, "inlineCast", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), true, false, false, false);
        oOPathChunkMetaModel.getDeclaredPropertyMetaModels().add(oOPathChunkMetaModel.inlineCastPropertyMetaModel);
        oOPathChunkMetaModel.passivePropertyMetaModel = new PropertyMetaModel(oOPathChunkMetaModel, "passive", boolean.class, Optional.empty(), false, false, false, false);
        oOPathChunkMetaModel.getDeclaredPropertyMetaModels().add(oOPathChunkMetaModel.passivePropertyMetaModel);
        oOPathChunkMetaModel.singleValuePropertyMetaModel = new PropertyMetaModel(oOPathChunkMetaModel, "singleValue", boolean.class, Optional.empty(), false, false, false, false);
        oOPathChunkMetaModel.getDeclaredPropertyMetaModels().add(oOPathChunkMetaModel.singleValuePropertyMetaModel);
        oOPathExprMetaModel.chunksPropertyMetaModel = new PropertyMetaModel(oOPathExprMetaModel, "chunks", org.mvel3.parser.ast.expr.OOPathChunk.class, Optional.of(oOPathChunkMetaModel), false, false, true, false);
        oOPathExprMetaModel.getDeclaredPropertyMetaModels().add(oOPathExprMetaModel.chunksPropertyMetaModel);
        rulePatternMetaModel.bindPropertyMetaModel = new PropertyMetaModel(rulePatternMetaModel, "bind", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        rulePatternMetaModel.getDeclaredPropertyMetaModels().add(rulePatternMetaModel.bindPropertyMetaModel);
        rulePatternMetaModel.exprPropertyMetaModel = new PropertyMetaModel(rulePatternMetaModel, "expr", org.mvel3.parser.ast.expr.OOPathExpr.class, Optional.of(oOPathExprMetaModel), false, false, false, false);
        rulePatternMetaModel.getDeclaredPropertyMetaModels().add(rulePatternMetaModel.exprPropertyMetaModel);
        rulePatternMetaModel.typePropertyMetaModel = new PropertyMetaModel(rulePatternMetaModel, "type", com.github.javaparser.ast.expr.SimpleName.class, Optional.of(simpleNameMetaModel), false, false, false, false);
        rulePatternMetaModel.getDeclaredPropertyMetaModels().add(rulePatternMetaModel.typePropertyMetaModel);
        ruleJoinedPatternsMetaModel.itemsPropertyMetaModel = new PropertyMetaModel(ruleJoinedPatternsMetaModel, "items", org.mvel3.parser.ast.expr.RuleItem.class, Optional.of(ruleItemMetaModel), false, false, true, false);
        ruleJoinedPatternsMetaModel.getDeclaredPropertyMetaModels().add(ruleJoinedPatternsMetaModel.itemsPropertyMetaModel);
        ruleJoinedPatternsMetaModel.typePropertyMetaModel = new PropertyMetaModel(ruleJoinedPatternsMetaModel, "type", org.mvel3.parser.ast.expr.RuleJoinedPatterns.Type.class, Optional.empty(), false, false, false, false);
        ruleJoinedPatternsMetaModel.getDeclaredPropertyMetaModels().add(ruleJoinedPatternsMetaModel.typePropertyMetaModel);
        ruleConsequenceMetaModel.statementPropertyMetaModel = new PropertyMetaModel(ruleConsequenceMetaModel, "statement", com.github.javaparser.ast.stmt.Statement.class, Optional.of(statementMetaModel), false, false, false, false);
        ruleConsequenceMetaModel.getDeclaredPropertyMetaModels().add(ruleConsequenceMetaModel.statementPropertyMetaModel);
        ruleBodyMetaModel.itemsPropertyMetaModel = new PropertyMetaModel(ruleBodyMetaModel, "items", org.mvel3.parser.ast.expr.RuleItem.class, Optional.of(ruleItemMetaModel), false, false, true, false);
        ruleBodyMetaModel.getDeclaredPropertyMetaModels().add(ruleBodyMetaModel.itemsPropertyMetaModel);
        ruleDeclarationMetaModel.ruleBodyPropertyMetaModel = new PropertyMetaModel(ruleDeclarationMetaModel, "ruleBody", org.mvel3.parser.ast.expr.RuleBody.class, Optional.of(ruleBodyMetaModel), false, false, false, false);
        ruleDeclarationMetaModel.getDeclaredPropertyMetaModels().add(ruleDeclarationMetaModel.ruleBodyPropertyMetaModel);
    }

    public static Optional<BaseNodeMetaModel> getNodeMetaModel(Class<?> c) {
        for (BaseNodeMetaModel nodeMetaModel : nodeMetaModels) {
            if (nodeMetaModel.getTypeName().equals(c.getSimpleName())) {
                return Optional.of(nodeMetaModel);
            }
        }
        return Optional.empty();
    }

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final NodeMetaModel nodeMetaModel = new NodeMetaModel(Optional.empty());

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final BodyDeclarationMetaModel bodyDeclarationMetaModel = new BodyDeclarationMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final CallableDeclarationMetaModel callableDeclarationMetaModel = new CallableDeclarationMetaModel(Optional.of(bodyDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ExpressionMetaModel expressionMetaModel = new ExpressionMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final StatementMetaModel statementMetaModel = new StatementMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TypeMetaModel typeMetaModel = new TypeMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final AnnotationExprMetaModel annotationExprMetaModel = new AnnotationExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ReferenceTypeMetaModel referenceTypeMetaModel = new ReferenceTypeMetaModel(Optional.of(typeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TypeDeclarationMetaModel typeDeclarationMetaModel = new TypeDeclarationMetaModel(Optional.of(bodyDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final LiteralExprMetaModel literalExprMetaModel = new LiteralExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final LiteralStringValueExprMetaModel literalStringValueExprMetaModel = new LiteralStringValueExprMetaModel(Optional.of(literalExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final StringLiteralExprMetaModel stringLiteralExprMetaModel = new StringLiteralExprMetaModel(Optional.of(literalStringValueExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModuleDeclarationMetaModel moduleDeclarationMetaModel = new ModuleDeclarationMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModuleDirectiveMetaModel moduleDirectiveMetaModel = new ModuleDirectiveMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ArrayCreationLevelMetaModel arrayCreationLevelMetaModel = new ArrayCreationLevelMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final CompilationUnitMetaModel compilationUnitMetaModel = new CompilationUnitMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ImportDeclarationMetaModel importDeclarationMetaModel = new ImportDeclarationMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModifierMetaModel modifierMetaModel = new ModifierMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final PackageDeclarationMetaModel packageDeclarationMetaModel = new PackageDeclarationMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final AnnotationDeclarationMetaModel annotationDeclarationMetaModel = new AnnotationDeclarationMetaModel(Optional.of(typeDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final AnnotationMemberDeclarationMetaModel annotationMemberDeclarationMetaModel = new AnnotationMemberDeclarationMetaModel(Optional.of(bodyDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ClassOrInterfaceDeclarationMetaModel classOrInterfaceDeclarationMetaModel = new ClassOrInterfaceDeclarationMetaModel(Optional.of(typeDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ConstructorDeclarationMetaModel constructorDeclarationMetaModel = new ConstructorDeclarationMetaModel(Optional.of(callableDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final EnumConstantDeclarationMetaModel enumConstantDeclarationMetaModel = new EnumConstantDeclarationMetaModel(Optional.of(bodyDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final EnumDeclarationMetaModel enumDeclarationMetaModel = new EnumDeclarationMetaModel(Optional.of(typeDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final FieldDeclarationMetaModel fieldDeclarationMetaModel = new FieldDeclarationMetaModel(Optional.of(bodyDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final InitializerDeclarationMetaModel initializerDeclarationMetaModel = new InitializerDeclarationMetaModel(Optional.of(bodyDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final MethodDeclarationMetaModel methodDeclarationMetaModel = new MethodDeclarationMetaModel(Optional.of(callableDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ParameterMetaModel parameterMetaModel = new ParameterMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ReceiverParameterMetaModel receiverParameterMetaModel = new ReceiverParameterMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final RecordDeclarationMetaModel recordDeclarationMetaModel = new RecordDeclarationMetaModel(Optional.of(typeDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final CompactConstructorDeclarationMetaModel compactConstructorDeclarationMetaModel = new CompactConstructorDeclarationMetaModel(Optional.of(bodyDeclarationMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final VariableDeclaratorMetaModel variableDeclaratorMetaModel = new VariableDeclaratorMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final CommentMetaModel commentMetaModel = new CommentMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final BlockCommentMetaModel blockCommentMetaModel = new BlockCommentMetaModel(Optional.of(commentMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final JavadocCommentMetaModel javadocCommentMetaModel = new JavadocCommentMetaModel(Optional.of(commentMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final LineCommentMetaModel lineCommentMetaModel = new LineCommentMetaModel(Optional.of(commentMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ArrayAccessExprMetaModel arrayAccessExprMetaModel = new ArrayAccessExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ArrayCreationExprMetaModel arrayCreationExprMetaModel = new ArrayCreationExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ArrayInitializerExprMetaModel arrayInitializerExprMetaModel = new ArrayInitializerExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final AssignExprMetaModel assignExprMetaModel = new AssignExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final BinaryExprMetaModel binaryExprMetaModel = new BinaryExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final BooleanLiteralExprMetaModel booleanLiteralExprMetaModel = new BooleanLiteralExprMetaModel(Optional.of(literalExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final CastExprMetaModel castExprMetaModel = new CastExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final CharLiteralExprMetaModel charLiteralExprMetaModel = new CharLiteralExprMetaModel(Optional.of(literalStringValueExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ClassExprMetaModel classExprMetaModel = new ClassExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ConditionalExprMetaModel conditionalExprMetaModel = new ConditionalExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final DoubleLiteralExprMetaModel doubleLiteralExprMetaModel = new DoubleLiteralExprMetaModel(Optional.of(literalStringValueExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final EnclosedExprMetaModel enclosedExprMetaModel = new EnclosedExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final FieldAccessExprMetaModel fieldAccessExprMetaModel = new FieldAccessExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final InstanceOfExprMetaModel instanceOfExprMetaModel = new InstanceOfExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final IntegerLiteralExprMetaModel integerLiteralExprMetaModel = new IntegerLiteralExprMetaModel(Optional.of(literalStringValueExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final LambdaExprMetaModel lambdaExprMetaModel = new LambdaExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final LongLiteralExprMetaModel longLiteralExprMetaModel = new LongLiteralExprMetaModel(Optional.of(literalStringValueExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final MarkerAnnotationExprMetaModel markerAnnotationExprMetaModel = new MarkerAnnotationExprMetaModel(Optional.of(annotationExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final MemberValuePairMetaModel memberValuePairMetaModel = new MemberValuePairMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final MethodCallExprMetaModel methodCallExprMetaModel = new MethodCallExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final MethodReferenceExprMetaModel methodReferenceExprMetaModel = new MethodReferenceExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final NameExprMetaModel nameExprMetaModel = new NameExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final NameMetaModel nameMetaModel = new NameMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final NormalAnnotationExprMetaModel normalAnnotationExprMetaModel = new NormalAnnotationExprMetaModel(Optional.of(annotationExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final NullLiteralExprMetaModel nullLiteralExprMetaModel = new NullLiteralExprMetaModel(Optional.of(literalExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ObjectCreationExprMetaModel objectCreationExprMetaModel = new ObjectCreationExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final PatternExprMetaModel patternExprMetaModel = new PatternExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final SingleMemberAnnotationExprMetaModel singleMemberAnnotationExprMetaModel = new SingleMemberAnnotationExprMetaModel(Optional.of(annotationExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final SimpleNameMetaModel simpleNameMetaModel = new SimpleNameMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final SuperExprMetaModel superExprMetaModel = new SuperExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final SwitchExprMetaModel switchExprMetaModel = new SwitchExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TextBlockLiteralExprMetaModel textBlockLiteralExprMetaModel = new TextBlockLiteralExprMetaModel(Optional.of(literalStringValueExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ThisExprMetaModel thisExprMetaModel = new ThisExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TypeExprMetaModel typeExprMetaModel = new TypeExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final UnaryExprMetaModel unaryExprMetaModel = new UnaryExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final VariableDeclarationExprMetaModel variableDeclarationExprMetaModel = new VariableDeclarationExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final AssertStmtMetaModel assertStmtMetaModel = new AssertStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final BlockStmtMetaModel blockStmtMetaModel = new BlockStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final BreakStmtMetaModel breakStmtMetaModel = new BreakStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final CatchClauseMetaModel catchClauseMetaModel = new CatchClauseMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ContinueStmtMetaModel continueStmtMetaModel = new ContinueStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final DoStmtMetaModel doStmtMetaModel = new DoStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final EmptyStmtMetaModel emptyStmtMetaModel = new EmptyStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ExplicitConstructorInvocationStmtMetaModel explicitConstructorInvocationStmtMetaModel = new ExplicitConstructorInvocationStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ExpressionStmtMetaModel expressionStmtMetaModel = new ExpressionStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ForEachStmtMetaModel forEachStmtMetaModel = new ForEachStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ForStmtMetaModel forStmtMetaModel = new ForStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final IfStmtMetaModel ifStmtMetaModel = new IfStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final LabeledStmtMetaModel labeledStmtMetaModel = new LabeledStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final LocalClassDeclarationStmtMetaModel localClassDeclarationStmtMetaModel = new LocalClassDeclarationStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final LocalRecordDeclarationStmtMetaModel localRecordDeclarationStmtMetaModel = new LocalRecordDeclarationStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ReturnStmtMetaModel returnStmtMetaModel = new ReturnStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final SwitchEntryMetaModel switchEntryMetaModel = new SwitchEntryMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final SwitchStmtMetaModel switchStmtMetaModel = new SwitchStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final SynchronizedStmtMetaModel synchronizedStmtMetaModel = new SynchronizedStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ThrowStmtMetaModel throwStmtMetaModel = new ThrowStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TryStmtMetaModel tryStmtMetaModel = new TryStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final UnparsableStmtMetaModel unparsableStmtMetaModel = new UnparsableStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final WhileStmtMetaModel whileStmtMetaModel = new WhileStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final YieldStmtMetaModel yieldStmtMetaModel = new YieldStmtMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ArrayTypeMetaModel arrayTypeMetaModel = new ArrayTypeMetaModel(Optional.of(referenceTypeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ClassOrInterfaceTypeMetaModel classOrInterfaceTypeMetaModel = new ClassOrInterfaceTypeMetaModel(Optional.of(referenceTypeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final IntersectionTypeMetaModel intersectionTypeMetaModel = new IntersectionTypeMetaModel(Optional.of(typeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final PrimitiveTypeMetaModel primitiveTypeMetaModel = new PrimitiveTypeMetaModel(Optional.of(typeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TypeParameterMetaModel typeParameterMetaModel = new TypeParameterMetaModel(Optional.of(referenceTypeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final UnionTypeMetaModel unionTypeMetaModel = new UnionTypeMetaModel(Optional.of(typeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final UnknownTypeMetaModel unknownTypeMetaModel = new UnknownTypeMetaModel(Optional.of(typeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final VarTypeMetaModel varTypeMetaModel = new VarTypeMetaModel(Optional.of(typeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final VoidTypeMetaModel voidTypeMetaModel = new VoidTypeMetaModel(Optional.of(typeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final WildcardTypeMetaModel wildcardTypeMetaModel = new WildcardTypeMetaModel(Optional.of(typeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModuleExportsDirectiveMetaModel moduleExportsDirectiveMetaModel = new ModuleExportsDirectiveMetaModel(Optional.of(moduleDirectiveMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModuleOpensDirectiveMetaModel moduleOpensDirectiveMetaModel = new ModuleOpensDirectiveMetaModel(Optional.of(moduleDirectiveMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModuleProvidesDirectiveMetaModel moduleProvidesDirectiveMetaModel = new ModuleProvidesDirectiveMetaModel(Optional.of(moduleDirectiveMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModuleRequiresDirectiveMetaModel moduleRequiresDirectiveMetaModel = new ModuleRequiresDirectiveMetaModel(Optional.of(moduleDirectiveMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModuleUsesDirectiveMetaModel moduleUsesDirectiveMetaModel = new ModuleUsesDirectiveMetaModel(Optional.of(moduleDirectiveMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final InlineCastExprMetaModel inlineCastExprMetaModel = new InlineCastExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final FullyQualifiedInlineCastExprMetaModel fullyQualifiedInlineCastExprMetaModel = new FullyQualifiedInlineCastExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final BigDecimalLiteralExprMetaModel bigDecimalLiteralExprMetaModel = new BigDecimalLiteralExprMetaModel(Optional.of(literalStringValueExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final BigIntegerLiteralExprMetaModel bigIntegerLiteralExprMetaModel = new BigIntegerLiteralExprMetaModel(Optional.of(literalStringValueExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final DrlNameExprMetaModel drlNameExprMetaModel = new DrlNameExprMetaModel(Optional.of(nameExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final DrlxExpressionMetaModel drlxExpressionMetaModel = new DrlxExpressionMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final HalfBinaryExprMetaModel halfBinaryExprMetaModel = new HalfBinaryExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final PointFreeExprMetaModel pointFreeExprMetaModel = new PointFreeExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final HalfPointFreeExprMetaModel halfPointFreeExprMetaModel = new HalfPointFreeExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ListCreationLiteralExpressionMetaModel listCreationLiteralExpressionMetaModel = new ListCreationLiteralExpressionMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ListCreationLiteralExpressionElementMetaModel listCreationLiteralExpressionElementMetaModel = new ListCreationLiteralExpressionElementMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final MapCreationLiteralExpressionMetaModel mapCreationLiteralExpressionMetaModel = new MapCreationLiteralExpressionMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final MapCreationLiteralExpressionKeyValuePairMetaModel mapCreationLiteralExpressionKeyValuePairMetaModel = new MapCreationLiteralExpressionKeyValuePairMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final NullSafeFieldAccessExprMetaModel nullSafeFieldAccessExprMetaModel = new NullSafeFieldAccessExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final NullSafeMethodCallExprMetaModel nullSafeMethodCallExprMetaModel = new NullSafeMethodCallExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TemporalChunkExprMetaModel temporalChunkExprMetaModel = new TemporalChunkExprMetaModel(Optional.of(literalExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TemporalLiteralChunkExprMetaModel temporalLiteralChunkExprMetaModel = new TemporalLiteralChunkExprMetaModel(Optional.of(temporalChunkExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TemporalLiteralExprMetaModel temporalLiteralExprMetaModel = new TemporalLiteralExprMetaModel(Optional.of(literalExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final TemporalLiteralInfiniteChunkExprMetaModel temporalLiteralInfiniteChunkExprMetaModel = new TemporalLiteralInfiniteChunkExprMetaModel(Optional.of(temporalChunkExprMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final AbstractContextStatementMetaModel abstractContextStatementMetaModel = new AbstractContextStatementMetaModel(Optional.of(statementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final ModifyStatementMetaModel modifyStatementMetaModel = new ModifyStatementMetaModel(Optional.of(abstractContextStatementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final WithStatementMetaModel withStatementMetaModel = new WithStatementMetaModel(Optional.of(abstractContextStatementMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final OOPathChunkMetaModel oOPathChunkMetaModel = new OOPathChunkMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final OOPathExprMetaModel oOPathExprMetaModel = new OOPathExprMetaModel(Optional.of(expressionMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final RuleItemMetaModel ruleItemMetaModel = new RuleItemMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final RulePatternMetaModel rulePatternMetaModel = new RulePatternMetaModel(Optional.of(ruleItemMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final RuleJoinedPatternsMetaModel ruleJoinedPatternsMetaModel = new RuleJoinedPatternsMetaModel(Optional.of(ruleItemMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final RuleConsequenceMetaModel ruleConsequenceMetaModel = new RuleConsequenceMetaModel(Optional.of(ruleItemMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final RuleBodyMetaModel ruleBodyMetaModel = new RuleBodyMetaModel(Optional.of(nodeMetaModel));

    @Generated("com.github.javaparser.generator.metamodel.NodeMetaModelGenerator")
    public static final RuleDeclarationMetaModel ruleDeclarationMetaModel = new RuleDeclarationMetaModel(Optional.of(typeDeclarationMetaModel));

    static {
        initializeNodeMetaModels();
        initializePropertyMetaModels();
        initializeConstructorParameters();
    }
}
