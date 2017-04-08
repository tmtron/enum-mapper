package com.tmtron.enums.processor;

import com.google.common.truth.Truth;
import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.tools.JavaFileObject;

public class AnnotationProcessorTest {

    private static final ZonedDateTime dateTimeForTesting =
            ZonedDateTime.of(1976, 12, 14
                    , 15, 16, 17, 234000000
                    , ZoneId.ofOffset("UTC", ZoneOffset.ofHours(2)));

    public AnnotationProcessorTest() {
        AnnotationProcessingUtil.setCurrentTimeProvider(
                () -> dateTimeForTesting
        );
    }

    /**
     * Will add "-Xlint:-processing" to the compile options, so that we don't get the warnings:
     * "No processor claimed any of these annotations..."
     * see {@link TestSingleEnum#testWarningMessage()}
     */
    CompileTester assertAboutEnumsProcessing(JavaFileObject source) {
        return Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(source)
                // otherwise we get this warning:
                // No processor claimed any of these annotations: com.tmtron.enums.annotation.MapAllEnums
                .withCompilerOptions("-Xlint:-processing")
                .processedWith(new EnumsAnnotationProcessor());
    }

    /**
     * Will load the given resource file from the test/resources subdirectory for the test-class
     *
     * @param fileNameAndExtension e.g. "LauncherAge_MapperFull.java"
     */
    JavaFileObject getJfoResource(String fileNameAndExtension) {
        String resourceUrl = this.getClass().getSimpleName() + "/" + fileNameAndExtension;
        return JavaFileObjects.forResource(resourceUrl);
    }

}

