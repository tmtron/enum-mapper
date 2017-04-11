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
import com.google.common.collect.SetMultimap;
import com.tmtron.enums.MapAllEnums;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Processing step for the {@link MapAllEnums} annotation
 */
public class MapAllEnumsProcessingStep implements BasicAnnotationProcessor.ProcessingStep {

    private final ProcessingEnvironment processingEnvironment;

    MapAllEnumsProcessingStep(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(MapAllEnums.class);
    }

    @Override
    public Set<Element> process(SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        try {
            // the annotations method tells the framework to only accept the MapAllEnums annotation
            if (elementsByAnnotation.keys().size() != 1) throw new RuntimeException("Exactly one annotation expected");
            Class<? extends Annotation> annotation = elementsByAnnotation.keys().iterator().next();
            if (!annotation.getName().equals(MapAllEnums.class.getCanonicalName())) {
                throw new RuntimeException("Unexpected class found: '" + annotation.getName() + "'"
                        + " Expected class: " + MapAllEnums.class.getCanonicalName());
            }

            // the MapAllEnums annotation may be present on multiple classes
            for (Element element : elementsByAnnotation.get(annotation)) {
                try {
                    new MapAllEnumsHandler(processingEnvironment, element).work();
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
}
