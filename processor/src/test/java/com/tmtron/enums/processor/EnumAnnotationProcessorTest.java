package com.tmtron.enums.processor;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import com.tmtron.enums.annotation.MapAllEnums;

import org.junit.Test;

import javax.tools.JavaFileObject;

public class EnumAnnotationProcessorTest extends AnnotationProcessorTest {

    private final JavaFileObject source = JavaFileObjects.forSourceString(
            "test.Dummy"
            , "// comment"
                    + "\npackage com.test;"
                    + "\nimport " + MapAllEnums.class.getCanonicalName() + ";"
                    + "\n@MapAllEnums(enums = Dummy.ColorEnum.class)"
                    + "\npublic class Dummy {"
                    + "\n    enum ColorEnum { RED, BLUE }"
                    + "\n}"
    );

    /**
     * We are not sure why we keep getting the warning message.
     * Use {@link #assertAboutEnumsProcessing(JavaFileObject)} to suppress this error.
     */
    @Test
    public void testWarningMessage() {
        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(source)
                .processedWith(new EnumsAnnotationProcessor())
                .compilesWithoutError()
                .withWarningCount(1)
                .withWarningContaining(
                        "No processor claimed any of these annotations: com.tmtron.enums.annotation" +
                                ".MapAllEnums");
    }

    @Test
    public void test() throws Exception {
        JavaFileObject genColorEnum_MapperFull = JavaFileObjects.forSourceString("test.ColorEnum_MapperFull"
                , "// GENERATED"
                        + "\npackage com.test;"
                        + "\npublic final class ColorEnum_MapperFull {"
                        + "\n    public int RED;"
                        + "\n    public int BLUE;"
                        + "\n}"
        );

        assertAboutEnumsProcessing(source)
                .compilesWithoutWarnings()
                .and()
                .generatesSources(genColorEnum_MapperFull);
    }

}
