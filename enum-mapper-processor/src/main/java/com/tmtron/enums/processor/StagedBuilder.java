/*
 * Copyright © 2017 Martin Trummer (martin.trummer@tmtron.com)
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
 */
package com.tmtron.enums.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.tmtron.enums.EnumMapperFull;

import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * The java-poet code for the StagedBuilder.
 */
class StagedBuilder {
    static final String STATEMENT_PUT_CONSTANT_TO_ENUM_MAPPER = "enumMapperBuilder.put($T.$L, value)";
    private final TypeVariableName typeVariableName4Value;
    private final ClassName enumClassName;
    private final List<CodeGenEnumConst> enumConstants;
    private final TypeName lastReturnType;
    private final ParameterizedTypeName stagedBuilderType;
    private final ClassName stagedBuilderClassName;

    /**
     * Creates a new StagedBuilder
     *
     * @param typeVariableName4Value the type-variable name: e.g. "V"
     * @param enumClassName          e.g package.LauncherAge
     * @param enumConstants          all enum constants
     * @param lastReturnType         the return type for the last stage: e.g. EnumMapperFull<LauncherAge, V>
     */
    StagedBuilder(TypeVariableName typeVariableName4Value, ClassName enumClassName
            , List<CodeGenEnumConst> enumConstants, TypeName lastReturnType) {
        this.typeVariableName4Value = typeVariableName4Value;
        this.enumClassName = enumClassName;
        this.enumConstants = enumConstants;
        this.lastReturnType = lastReturnType;

        stagedBuilderClassName = ClassName.get("", "StagedBuilder");
        stagedBuilderType = ParameterizedTypeName.get(stagedBuilderClassName, typeVariableName4Value);
    }

    // StagedBuilder<V>
    ParameterizedTypeName getFullType() {
        return stagedBuilderType;
    }

    // StagedBuilder
    ClassName getClassName() {
        return stagedBuilderClassName;
    }

    /**
     * returns the full type spec for the staged builder (including all setter methods)
     */
    TypeSpec getTypeSpec() {
        // type for StagedBuilder<V> class
        TypeSpec.Builder stagedBuilderTypeBuilder = TypeSpec.classBuilder(stagedBuilderClassName)
                .addTypeVariable(typeVariableName4Value)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC);

        // add the implemented interfaces
        // note: not needed for the first enum-constant
        for (int i = 1; i < enumConstants.size(); i++) {
            CodeGenEnumConst enumConst = enumConstants.get(i);
            stagedBuilderTypeBuilder.addSuperinterface(enumConst.interfaceTypeName);
        }

        // add the field for the enumMapperBuilder instance
        stagedBuilderTypeBuilder.addField(getEnumMapperBuilderFieldSpec());

        addSetterMethods(stagedBuilderTypeBuilder);

        return stagedBuilderTypeBuilder.build();
    }

    private void addSetterMethods(TypeSpec.Builder stagedBuilderTypeBuilder) {
        // skip the first
        for (int i = 1; i < enumConstants.size(); i++) {
            CodeGenEnumConst enumConst = enumConstants.get(i);
            MethodSpec.Builder interfaceMethodImplBuilder = MethodSpec.methodBuilder(enumConst.setterName)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(typeVariableName4Value, "value")
                    .addStatement(STATEMENT_PUT_CONSTANT_TO_ENUM_MAPPER, enumClassName, enumConst.identifier);

            if (i == enumConstants.size() - 1) {
                // the last interface
                interfaceMethodImplBuilder
                        .addStatement("return enumMapperBuilder.build()")
                        .returns(lastReturnType);
            } else {
                interfaceMethodImplBuilder
                        .addStatement("return this")
                        .returns(enumConstants.get(i + 1).interfaceTypeName);
            }
            stagedBuilderTypeBuilder.addMethod(interfaceMethodImplBuilder.build());
        }

    }

    //private final EnumMapperFull.Builder<LauncherAge, V> enumMapperBuilder = EnumMapperFull.builder(LauncherAge
    // .class);
    private FieldSpec getEnumMapperBuilderFieldSpec() {
        ClassName enumMapperBuilderClassName = ClassName.get(EnumMapperFull.Builder.class);
        TypeName enumMapperBuilderTypeName = ParameterizedTypeName.get(enumMapperBuilderClassName
                , enumClassName.withoutAnnotations(), typeVariableName4Value);
        return FieldSpec.builder(enumMapperBuilderTypeName, "enumMapperBuilder")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("EnumMapperFull.builder($T.class)", enumClassName)
                .build();
    }

}
