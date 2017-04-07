package com.tmtron.enums.processor;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.common.MoreElements;
import com.google.common.base.Optional;
import com.google.common.collect.SetMultimap;
import com.tmtron.enums.annotation.MapAllEnums;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class MapAllEnumsProcessingStep implements BasicAnnotationProcessor.ProcessingStep {

    private final ProcessingEnvironment processingEnvironment;

    public MapAllEnumsProcessingStep(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(MapAllEnums.class);
    }

    @Override
    public Set<Element> process(SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        try {
            if (elementsByAnnotation.keys().size() != 1) throw new RuntimeException("Exactly one annotation expected");
            Class<? extends Annotation> annotation = elementsByAnnotation.keys().iterator().next();
            if (!annotation.getName().equals(MapAllEnums.class.getCanonicalName())) {
                throw new RuntimeException("Unexpected class found: '" + annotation.getName() + "'"
                        + " Expected class: " + MapAllEnums.class.getCanonicalName());
            }
            // the annotation may be present on multiple classes
            for (Element element : elementsByAnnotation.get(annotation)) {
                try {
                    processElement(element);
                } catch (Exception e) {
                    processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR
                            , "Annotation processing error: " + e.getClass().getSimpleName() + "-" + e.getMessage()
                            , element);
                }
            }

        } catch (Exception e) {
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "Annotation processing error: " + e.getClass().getSimpleName() + "-" + e.getMessage());
        }

        return Collections.emptySet();
    }

    /**
     * the element must be annotated with the {@link MapAllEnums} annotation
     */
    private void processElement(Element element) {
        Optional<AnnotationMirror> optMirror = MoreElements.getAnnotationMirror(element, MapAllEnums.class);
        if (!optMirror.isPresent()) {
            throw new RuntimeException(MapAllEnums.class.getSimpleName() + " annotation not found!");
        }

        new MapAllEnumsHandler(processingEnvironment, optMirror.get()).work();
    }
}
