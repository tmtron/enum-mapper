/*
 * Copyright © 2018 Martin Trummer (martin.trummer@tmtron.com)
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

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeVariableName;
import com.tmtron.enums.EnumMappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * This class will create the "_MapperFull.java" file (including the staged builder) for one Enum-class
 */
class MapEnumElement {

    private final ProcessingEnvironment processingEnvironment;
    private final Collection<Element> annotatedElements;
    private final TypeElement enumsClassTypeElement;
    private final List<CodeGenEnumConst> enumConstants = new ArrayList<>();
    private final TypeVariableName typeVariableName4Value;

    /**
     * Will process a single Enum class of the {@link EnumMappers} annotation.
     *
     * @param processingEnvironment the processing environment
     * @param annotatedElements     the element/s (e.g. classe) which has/have the {@link EnumMappers} annotation
     * @param enumsClassTypeElement a single Enum class from the "values" array of the {@link EnumMappers} annotation
     */
    MapEnumElement(ProcessingEnvironment processingEnvironment, Collection<Element> annotatedElements,
                   TypeElement enumsClassTypeElement) {
        this.processingEnvironment = processingEnvironment;
        this.annotatedElements = annotatedElements;
        this.enumsClassTypeElement = enumsClassTypeElement;
        typeVariableName4Value = TypeVariableName.get("V");
    }

    void work() {
        // e.g."com.test.Dummy.BoolEnum.class"
        String msg = "creating MapEnumElement mapper for: " + enumsClassTypeElement.getQualifiedName();
        /* NOTE: the android shrinker treats NOTE messages as errors and stops processing with an error!
         * processingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
         */
        if (!ElementKind.ENUM.equals(enumsClassTypeElement.getKind())) {
            throw new RuntimeException(enumsClassTypeElement.toString() + " is not an ENUM kind");
        }

        for (Element element : enumsClassTypeElement.getEnclosedElements()) {
            if (ElementKind.ENUM_CONSTANT.equals(element.getKind())) {
                // element.getSimpleName().toString();
                // e.g. RED, BLUE / ON, OFF
                enumConstants.add(new CodeGenEnumConst(typeVariableName4Value, element));
            }
        }
        if (enumConstants.size() < 2) throw new RuntimeException("The Enum must have at least 2 constants!");

        WriteMapperFull writeMapperFull = new WriteMapperFull(annotatedElements, enumsClassTypeElement
                , enumConstants, typeVariableName4Value);
        try {
            JavaFile.builder(getPackageName(), writeMapperFull.work())
                    .skipJavaLangImports(true)
                    .build()
                    .writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getPackageName() {
        return processingEnvironment.getElementUtils().getPackageOf(enumsClassTypeElement).getQualifiedName()
                .toString();
    }

}
