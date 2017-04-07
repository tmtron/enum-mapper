package com.tmtron.enums.processor;

import com.google.auto.common.MoreTypes;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Will handle a single {@link com.tmtron.enums.annotation.MapAllEnums} annotation.
 * Which may contain multiple Enum classes in the enums class array.
 */
public class MapAllEnumsHandler {

    // the MapAllEnums class must have a member named enums (array of Enum classes)
    private static final String ENUMS_ID = "enums";
    private final AnnotationMirror annotationMirrorMapAllEnums;
    private final ProcessingEnvironment processingEnvironment;

    public MapAllEnumsHandler(ProcessingEnvironment processingEnvironment, AnnotationMirror
            annotationMirrorMapAllEnums) {
        this.annotationMirrorMapAllEnums = annotationMirrorMapAllEnums;
        this.processingEnvironment = processingEnvironment;
    }

    public void work() {
        AnnotationValue annotationValue = AnnoationProcessingUtil.getRequiredAnnotationValue
                (annotationMirrorMapAllEnums, ENUMS_ID);
        // e.g. "enums" -> {com.test.Dummy.ColorEnum.class, com.test.Dummy.BoolEnum.class}

        // the "enums" member is an array of classes
        List<? extends AnnotationValue> enumsList = AnnoationProcessingUtil.asList(annotationValue.getValue(),
                AnnotationValue.class, ENUMS_ID);

        // loop over each class in the "enums" array
        for (AnnotationValue enumsClassAnnotationValue : enumsList) {
            TypeMirror enumsClassTypeMirror = (TypeMirror) enumsClassAnnotationValue.getValue();
            TypeElement enumsClassTypeElement = MoreTypes.asTypeElement(enumsClassTypeMirror);
            // e.g. enumsClassTypeElement.getQualifiedName() "com.test.Dummy.BoolEnum.class"
            new MapEnumElement(processingEnvironment, enumsClassTypeElement).work();
        }
    }

}
