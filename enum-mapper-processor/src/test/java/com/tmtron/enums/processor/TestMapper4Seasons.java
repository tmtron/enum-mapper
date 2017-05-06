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

import org.junit.Test;

/**
 * Test the {@link com.tmtron.enums.EnumMapper} annotation which is directly on the enum
 */
public class TestMapper4Seasons extends AnnotationProcessorTest {
    @Test
    public void test() throws Exception {
        assertAboutEnumsProcessing(getJfoResource("Seasons.java"))
                .compilesWithoutWarnings()
                .and()
                .generatesSources(
                        getJfoResource("Seasons_MapperFull.java"));
    }

}
