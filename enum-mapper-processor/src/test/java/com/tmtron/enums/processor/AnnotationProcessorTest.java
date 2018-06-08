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

import com.google.common.truth.Truth;
import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.tools.JavaFileObject;

/**
 * Base class for annotation processor tests.
 * <p>
 * The base class will setup the date-time handling for the tests and it
 * provides some useful helper methods.
 * </p>
 */
class AnnotationProcessorTest {

    /* we use a fixed date/time which is the same as in the resource test files
     */
    private static final ZonedDateTime dateTimeForTesting =
            ZonedDateTime.of(1976, 12, 14
                    , 15, 16, 17, 234000000
                    , ZoneId.ofOffset("UTC", ZoneOffset.ofHours(2)));

    AnnotationProcessorTest() {
        AnnotationProcessingUtil.setCurrentTimeProvider(
                () -> dateTimeForTesting
        );
    }

    /**
     * Will add "-Xlint:-processing" to the compile options, so that we don't get the warnings:
     * "No processor claimed any of these annotations..."
     * see {@link TestMappers4SingleEnum#testWarningMessage()}
     */
    CompileTester assertAboutEnumsProcessing(JavaFileObject source) {
        return Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(source)
                // otherwise we get this warning:
                // No processor claimed any of these annotations: com.tmtron.enums.annotation.EnumMappers
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

