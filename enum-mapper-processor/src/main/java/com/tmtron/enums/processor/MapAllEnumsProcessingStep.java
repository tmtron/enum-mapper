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

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.common.MoreTypes;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.tmtron.enums.EnumMapper;
import com.tmtron.enums.EnumMappers;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * Processing step for the {@link EnumMappers} annotation
 */
public class MapAllEnumsProcessingStep implements BasicAnnotationProcessor.ProcessingStep {

    private final ProcessingEnvironment processingEnvironment;

    MapAllEnumsProcessingStep(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return ImmutableSet.of(EnumMapper.class, EnumMappers.class);
    }

    @Override
    public Set<Element> process(SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        try {
            Map<Class<? extends Annotation>, Collection<Element>> annotationsMap =
                    elementsByAnnotation.asMap();
            // the annotations() method tells the framework which annotations to accept
            if (annotationsMap.keySet().size() > 2) throw new RuntimeException("Too many annotations");

            for (Class<? extends Annotation> annotation : annotationsMap.keySet()) {
                // the elements which are annotated with the annotation
                // e.g. the EnumMappers annotation may be present on multiple classes of packages
                Set<Element> annotatedElements = elementsByAnnotation.get(annotation);
                String annotationName = annotation.getName();
                if (annotationName.equals(EnumMappers.class.getCanonicalName())) {
                    processEnumMappersAnnotation(annotatedElements);
                } else if (annotationName.equals(EnumMapper.class.getCanonicalName())) {
                    processEnumMapperAnnotation(annotatedElements);
                } else {
                    throw new RuntimeException("Unexpected annotation class found: '" + annotationName + "'");
                }
            }
        } catch (Exception e) {
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "Annotation processing error: " + e.getClass().getSimpleName() + "-" + e.getMessage());
        }

        return Collections.emptySet();
    }

    private void processEnumMapperAnnotation(Set<Element> annotatedElements) {
        // the EnumMapper annotation may be present on multiple enums in the project
        for (Element element : annotatedElements) {
            try {
                if (!ElementKind.ENUM.equals(element.getKind())) {
                    throw new Exception(EnumMapper.class + " annotation must only be used on Enums");
                }

                TypeMirror enumsClassTypeMirror = element.asType();
                TypeElement enumsClassTypeElement = MoreTypes.asTypeElement(enumsClassTypeMirror);
                // now process each (Enum-)class
                // e.g. enumsClassTypeElement.getQualifiedName() "com.test.Dummy.BoolEnum.class"
                new MapEnumElement(processingEnvironment, Collections.singletonList(element), enumsClassTypeElement)
                        .work();

            } catch (Exception e) {
                processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR
                        , "Annotation processing error: " + e.getClass().getSimpleName() + "-" + e.getMessage()
                        , element);
            }
        }
    }

    private void processEnumMappersAnnotation(Set<Element> annotatedElements) {
        try {
            new MapAllEnumsHandler(processingEnvironment, annotatedElements).work();
        } catch (Exception e) {
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR
                    , "Annotation processing error: " + e.getClass().getSimpleName() + "-" + e.getMessage());
        }
    }
}
