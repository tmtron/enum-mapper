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
import com.google.auto.common.MoreTypes;
import com.google.common.base.Optional;
import com.tmtron.enums.MapAllEnums;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Will handle a single {@link com.tmtron.enums.MapAllEnums} annotation.
 * Which may contain multiple Enum classes in the enums class array.
 */
public class MapAllEnumsHandler {

    // the MapAllEnums class must have a member named enums (array of Enum classes)
    private static final String ENUMS_ID = "enums";
    private final AnnotationMirror annotationMirrorMapAllEnums;
    private final ProcessingEnvironment processingEnvironment;
    private final Element annotatedElement;

    /**
     * Will process an element (e.g. a class) which must have the {@link com.tmtron.enums.MapAllEnums} annotation.
     * Note: the {@link MapAllEnums} annotation may contain multiple Enum classes in the enums class array.
     *
     * @param processingEnvironment the processing environment
     * @param annotatedElement      the element (e.g. class) which has the {@link MapAllEnums} annotation
     */
    public MapAllEnumsHandler(ProcessingEnvironment processingEnvironment,
                              Element annotatedElement) {
        this.processingEnvironment = processingEnvironment;
        this.annotatedElement = annotatedElement;

        Optional<AnnotationMirror> optMirror = MoreElements.getAnnotationMirror(annotatedElement, MapAllEnums.class);
        if (!optMirror.isPresent()) {
            throw new RuntimeException(MapAllEnums.class.getSimpleName() + " annotation not found!");
        }
        this.annotationMirrorMapAllEnums = optMirror.get();
    }

    public void work() {
        AnnotationValue annotationValue = AnnotationProcessingUtil.getRequiredAnnotationValue
                (annotationMirrorMapAllEnums, ENUMS_ID);
        // e.g. "enums" -> {com.test.Dummy.ColorEnum.class, com.test.Dummy.BoolEnum.class}

        // the "enums" member is an array of classes
        List<? extends AnnotationValue> enumsList = AnnotationProcessingUtil.asList(annotationValue.getValue(),
                AnnotationValue.class, ENUMS_ID);

        // loop over each class in the "enums" array
        for (AnnotationValue enumsClassAnnotationValue : enumsList) {
            TypeMirror enumsClassTypeMirror = (TypeMirror) enumsClassAnnotationValue.getValue();
            TypeElement enumsClassTypeElement = MoreTypes.asTypeElement(enumsClassTypeMirror);
            // e.g. enumsClassTypeElement.getQualifiedName() "com.test.Dummy.BoolEnum.class"
            new MapEnumElement(processingEnvironment, annotatedElement, enumsClassTypeElement).work();
        }
    }

}
