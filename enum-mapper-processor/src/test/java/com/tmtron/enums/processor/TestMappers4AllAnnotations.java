/*
 * Copyright © 2018 Martin Trummer (martin.trummer@tmtron.com)
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

import org.junit.Test;

/**
 * test for <a href="https://github.com/tmtron/enum-mapper/issues/1">
 * Issue 1: Cannot use @EnumMapper and @EnumMappers in the same project</a>
 */
public class TestMappers4AllAnnotations extends AnnotationProcessorTest {
    @Test
    public void test() throws Exception {
        assertAboutEnumsProcessing(getJfoResource("AllAnnotations_Source.java"))
                .compilesWithoutWarnings()
                .and()
                .generatesSources(
                        getJfoResource("Seasons_MapperFull.java"),
                        getJfoResource("BoolEnum_MapperFull.java"),
                        getJfoResource("ColorEnum_MapperFull.java"));
    }

}
