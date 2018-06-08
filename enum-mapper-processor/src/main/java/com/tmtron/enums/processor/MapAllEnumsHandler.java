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

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.tmtron.enums.EnumMappers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Will handle a single {@link EnumMappers} annotation.
 * Which may contain multiple Enum classes in the value class array.
 */
class MapAllEnumsHandler {

    // the EnumMappers class must have a member named value (array of Enum classes)
    private static final String ENUMS_ID = "value";
    private final ProcessingEnvironment processingEnvironment;
    private final Set<Element> annotatedElements;
    ;

    /**
     * Will process an element (e.g. a class) which must have the {@link EnumMappers} annotation.
     * Note: the {@link EnumMappers} annotation may contain multiple Enum classes in the enums class array.
     *
     * @param processingEnvironment the processing environment
     * @param annotatedElements     the element/s (e.g. class) which has/have the {@link EnumMappers} annotation
     */
    MapAllEnumsHandler(ProcessingEnvironment processingEnvironment, Set<Element> annotatedElements) {
        this.processingEnvironment = processingEnvironment;
        this.annotatedElements = annotatedElements;
    }

    /* Returns a map where the keys are all the unique enumerations and the values are the annotated classes
     * since V1.0.2 we can use the same enum in multiple @EnumMapper annotations
     * see issue #3 "duplicate enums in @EnumMappers should be ignored"
     * https://github.com/tmtron/enum-mapper/issues/3
     */
    private Map<AnnotationValueWrapper, Collection<Element>> getEnumAnnotationsMap() {
        ArrayListMultimap<AnnotationValueWrapper, Element> enumAnnotations = ArrayListMultimap.create();
        for (Element annotatedElement : annotatedElements) {
            Optional<AnnotationMirror> optMirror = MoreElements.getAnnotationMirror(annotatedElement, EnumMappers
                    .class);
            if (!optMirror.isPresent()) {
                throw new RuntimeException(EnumMappers.class.getSimpleName() + " annotation not found!");
            }
            final AnnotationMirror annotationMirrorMapAllEnums = optMirror.get();

            // get the "value" annotationValue (which is of type: array of classes)
            AnnotationValue annotationValue = AnnotationProcessingUtil.getRequiredAnnotationValue
                    (annotationMirrorMapAllEnums, ENUMS_ID);

            // the annotationValue is an array of classes
            // we convert it to a list where each item is the class
            // e.g. "value" -> {com.test.Dummy.ColorEnum.class, com.test.Dummy.BoolEnum.class}
            //      --> the enumsList will contain 2 items (ColorEnum and BoolEnum)
            List<? extends AnnotationValue> enumsList = AnnotationProcessingUtil.asList(annotationValue.getValue(),
                    AnnotationValue.class, ENUMS_ID);

            // loop over each (Enum-)class in the "value" array
            /*
             * NOTE: AnnotationValue
             */
            for (AnnotationValue enumsClassAnnotationValue : enumsList) {
                //noinspection ResultOfMethodCallIgnored
                enumAnnotations.put(new AnnotationValueWrapper(enumsClassAnnotationValue), annotatedElement);
            }
        }
        return enumAnnotations.asMap();
    }

    void work() {
        Map<AnnotationValueWrapper, Collection<Element>> enumAnnotationMap = getEnumAnnotationsMap();

        for (AnnotationValueWrapper enumsClassAnnotationValueWrapper : enumAnnotationMap.keySet()) {
            AnnotationValue enumsClassAnnotationValue = enumsClassAnnotationValueWrapper.annotationValue;
            TypeMirror enumsClassTypeMirror = (TypeMirror) enumsClassAnnotationValue.getValue();
            TypeElement enumsClassTypeElement = MoreTypes.asTypeElement(enumsClassTypeMirror);

            Collection<Element> originElements = enumAnnotationMap.get(enumsClassAnnotationValueWrapper);

            // now process each (Enum-)class
            // e.g. enumsClassTypeElement.getQualifiedName() "com.test.Dummy.BoolEnum.class"
            new MapEnumElement(processingEnvironment, originElements, enumsClassTypeElement).work();
        }
    }

    /**
     * Simple wrapper around an {@link AnnotationValue} that uses the toString() value for equals and hashCode.
     */
    private static class AnnotationValueWrapper {

        final AnnotationValue annotationValue;
        final String stringRep;

        private AnnotationValueWrapper(AnnotationValue annotationValue) {
            this.annotationValue = annotationValue;
            stringRep = annotationValue.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AnnotationValueWrapper that = (AnnotationValueWrapper) o;

            return stringRep.equals(that.stringRep);
        }

        @Override
        public int hashCode() {
            return stringRep.hashCode();
        }
    }

}
