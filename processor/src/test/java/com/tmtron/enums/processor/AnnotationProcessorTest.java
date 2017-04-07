package com.tmtron.enums.processor;

import com.google.common.truth.Truth;
import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaSourceSubjectFactory;

import javax.tools.JavaFileObject;

public class AnnotationProcessorTest {

    CompileTester assertAboutEnumsProcessing(JavaFileObject source) {
        return Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(source)
                // otherwise we get this warning:
                // No processor claimed any of these annotations: com.tmtron.enums.annotation.MapAllEnums
                .withCompilerOptions("-Xlint:-processing")
                .processedWith(new EnumsAnnotationProcessor());
    }

}

