package com.tmtron.enums.processor;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;

import org.junit.Test;

import javax.tools.JavaFileObject;

public class EnumAnnotationProcessorTest {
    @Test
    public void test() throws Exception {
        JavaFileObject source = JavaFileObjects.forSourceString(
                "test.Dummy"
                , "// comment"
                        + "\npackage test;"
                        + "\nimport com.tmtron.enums.annotation.MapAllEnums;"
                        + "\n@MapAllEnums(enums = {Dummy.ColorEnum.class, Dummy.BoolEnum.class})"
                        + "\npublic class Dummy {"
                        + "\n    enum ColorEnum { RED, BLUE }"
                        + "\n    enum BoolEnum { OFF, ON }"
                        + "\n}"
        );
        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(source)
                .processedWith(new EnumsAnnotationProcessor())
                .compilesWithoutWarnings();
    }

}
