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

import com.google.auto.common.MoreElements;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.tmtron.enums.EnumMapper;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Generated;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

class WriteMapperFull {
    private final Element annotatedElement;
    private final TypeElement enumsClassTypeElement;
    private final List<CodeGenEnumConst> enumConstants;
    private final TypeVariableName typeVariableName4Value;

    WriteMapperFull(Element annotatedElement, TypeElement enumsClassTypeElement, List<CodeGenEnumConst>
            enumConstants, TypeVariableName typeVariableName4Value) {
        this.annotatedElement = annotatedElement;
        this.enumsClassTypeElement = enumsClassTypeElement;
        this.enumConstants = enumConstants;
        this.typeVariableName4Value = typeVariableName4Value;
    }

    /**
     * Creates a Generated annotation
     * <pre><code>
     *  {@literal @}Generated(
     *    value = "com.tmtron.enums.processor.EnumsAnnotationProcessor",
     *    date = "1976-12-14T15:16:17.234+02:00",
     *    comments = "origin=com.test.TwoEnums_Source"
     *    )
     * </code></pre>
     *
     * @param annotationProcessorClass the class-name will be used for the value item
     */
    private AnnotationSpec createGeneratedAnnotation(Class<?> annotationProcessorClass) {
        String dateString = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(AnnotationProcessingUtil.now());
        AnnotationSpec.Builder annotationSpecBuilder = AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", annotationProcessorClass.getCanonicalName())
                .addMember("date", "$S", dateString);

        String commentsString = "origin=" + MoreElements.asType(annotatedElement).getQualifiedName().toString();
        annotationSpecBuilder.addMember("comments", "$S", commentsString);

        return annotationSpecBuilder.build();
    }

    /**
     * Will add the required interfaces for the build-stages to the mapperFullTypeBuilder.
     * <ul>
     * <li>we need no interface for the first enum-constant, becuase we create a static method for this</li>
     * <li>the last interface will return the fully initialized mapper</li>
     * </ul>
     *
     * @param mapperFullTypeBuilder  the interfaces will be added to this builder
     * @param typeVariableName4Value the type-variable name: e.g. "V"
     * @param lastReturnType         the return type for the last stage: e.g. EnumMapper<LauncherAge, V>
     */
    private void addStagedBuilderInterfaces(TypeSpec.Builder mapperFullTypeBuilder
            , TypeVariableName typeVariableName4Value, TypeName lastReturnType) {

        TypeName returnType = lastReturnType;
        // iterate backwards and skip the first - no interface needed
        for (int i = enumConstants.size() - 1; i > 0; i--) {
            CodeGenEnumConst enumConst = enumConstants.get(i);

            TypeSpec interfaceTypeSpec = TypeSpec.interfaceBuilder(enumConst.interfaceClassName)
                    .addTypeVariable(typeVariableName4Value)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(MethodSpec.methodBuilder(enumConst.setterName)
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .addParameter(typeVariableName4Value, "value")
                            .returns(returnType)
                            .build()
                    ).build();
            mapperFullTypeBuilder.addType(interfaceTypeSpec);

            // set return type for next lower enum-interface
            returnType = enumConst.interfaceTypeName;
        }
    }

    /**
     * Will create the TypeSpec.Builder for the "_MapperFull" class including the {@link Generated} annotation
     */
    private TypeSpec.Builder createFullMapperTypeSpecBuilder() {
        // LauncherAge_MapperFull
        ClassName className = ClassName.bestGuess(enumsClassTypeElement.getQualifiedName() + "_MapperFull");
        TypeSpec.Builder result = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC,
                Modifier.FINAL);
        // @Generated annotation
        result.addAnnotation(
                createGeneratedAnnotation(EnumsAnnotationProcessor.class)
        );
        return result;
    }

    /**
     * will return a type spec for the "_MapperFull" class (including the staged builder and interfaces)
     */
    TypeSpec work() {
        /* Example:
         *   enum LauncherAge {
         *     ONLINE, OLD, OFFLINE
         *   }
         */

        TypeSpec.Builder mapperFullTypeBuilder = createFullMapperTypeSpecBuilder();

        // package.LauncherAge
        ClassName enumClassName = ClassName.bestGuess(enumsClassTypeElement.toString());

        ClassName enumMapperClassName = ClassName.get(EnumMapper.class);
        // EnumMapper<LauncherAge, V>
        TypeName lastReturnType = ParameterizedTypeName.get(enumMapperClassName
                , enumClassName.withoutAnnotations(), typeVariableName4Value);
        addStagedBuilderInterfaces(mapperFullTypeBuilder, typeVariableName4Value, lastReturnType);

        // add StagedBuilder as inner class
        StagedBuilder stagedBuilder = new StagedBuilder(typeVariableName4Value
                , enumClassName, enumConstants, lastReturnType);
        mapperFullTypeBuilder.addType(stagedBuilder.getTypeSpec());

        // add the staged-builder entry method (for the first enum constant)
        // it will create a staged-builder instance and return it (as the interface for the next stage)
        CodeGenEnumConst firstEnumConst = enumConstants.get(0);
        CodeGenEnumConst secondEnumConst = enumConstants.get(1);
        MethodSpec setFirstEnum = MethodSpec.methodBuilder(firstEnumConst.setterName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(typeVariableName4Value)
                .returns(secondEnumConst.interfaceTypeName)
                .addParameter(typeVariableName4Value, "value")
                .addStatement("$T result = new $T<>()", stagedBuilder.getFullType(), stagedBuilder.getClassName())
                .addStatement("result." + StagedBuilder.STATEMENT_PUT_CONSTANT_TO_ENUM_MAPPER, enumClassName,
                        firstEnumConst
                                .identifier)
                .addStatement("return result")
                .build();
        mapperFullTypeBuilder.addMethod(setFirstEnum);

        return mapperFullTypeBuilder.build();
    }
}
