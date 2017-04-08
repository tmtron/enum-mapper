package com.tmtron.enums.processor;

import org.junit.Test;

public class Test2Enums extends AnnotationProcessorTest {
    @Test
    public void test() throws Exception {
        assertAboutEnumsProcessing(getJfoResource("TwoEnums_Source.java"))
                .compilesWithoutWarnings()
                .and()
                .generatesSources(
                        getJfoResource("BoolEnum_MapperFull.java"),
                        getJfoResource("ColorEnum_MapperFull.java"));
    }

}
