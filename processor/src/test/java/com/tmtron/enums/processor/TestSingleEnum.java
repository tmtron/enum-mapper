package com.tmtron.enums.processor;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaSourceSubjectFactory;

import org.junit.Test;

import javax.tools.JavaFileObject;

public class TestSingleEnum extends AnnotationProcessorTest {

    private final JavaFileObject source = getJfoResource("LauncherAge_Source.java");

    /**
     * We are not sure why we get these warning messages.
     * Use {@link #assertAboutEnumsProcessing(JavaFileObject)} to suppress this error.
     */
    @Test
    public void testWarningMessage() {
        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(source)
                .processedWith(new EnumsAnnotationProcessor())
                .compilesWithoutError()
                .withWarningContaining(
                        "No processor claimed any of these annotations: com.tmtron.enums.MapAllEnums");
    }

    @Test
    public void testLauncherAgeMappingFull() throws Exception {
        assertAboutEnumsProcessing(source)
                .compilesWithoutWarnings()
                .and()
                .generatesSources(getJfoResource("LauncherAge_MapperFull.java"));
    }

}
