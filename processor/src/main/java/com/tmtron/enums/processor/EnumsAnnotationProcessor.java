package com.tmtron.enums.processor;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;

import java.util.Collections;

import javax.annotation.processing.Processor;

@AutoService(Processor.class)
public class EnumsAnnotationProcessor extends BasicAnnotationProcessor {

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        return Collections.singletonList(new MapAllEnumsProcessingStep(processingEnv));
    }
}
