package com.tmtron.enums.processor;

import com.google.testing.compile.JavaFileObjects;
import com.tmtron.enums.annotation.MapAllEnums;

import org.junit.Test;

import javax.tools.JavaFileObject;

public class EnumAnnotationProcessorTest2Enums extends AnnotationProcessorTest {
    @Test
    public void test() throws Exception {
        JavaFileObject source = JavaFileObjects.forSourceString(
                "test.Dummy"
                , "// comment"
                        + "\npackage com.test;"
                        + "\nimport " + MapAllEnums.class.getCanonicalName() + ";"
                        + "\n@MapAllEnums(enums = {Dummy.ColorEnum.class, Dummy.BoolEnum.class})"
                        + "\npublic class Dummy {"
                        + "\n    enum ColorEnum { RED, BLUE }"
                        + "\n    enum BoolEnum { OFF, ON }"
                        + "\n}"
        );
        JavaFileObject genBoolEnum_MapperFull = JavaFileObjects.forSourceString("test.BoolEnum_MapperFull"
                , "// GENERATED"
                        + "\npackage com.test;"
                        + "\npublic final class BoolEnum_MapperFull {"
                        + "\n    public int OFF;"
                        + "\n    public int ON;"
                        + "\n}"
        );

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
                .generatesSources(genBoolEnum_MapperFull, genColorEnum_MapperFull);
    }

}
