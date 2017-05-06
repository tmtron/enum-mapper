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
package com.tmtron.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation can be used on an Enum. It will generate a full-enum-mapper {@link EnumMapperFull}
 * which will make sure that all enum values are mapped. If you forget to map an enum, you get a compile error.
 * </p>
 * Example usage of the annotation:
 * <pre><code>
 * {@literal @}EnumMapper public enum Seasons {
 *     SPRING, SUMMER, FALL, WINTER
 *  }
 * </code></pre>
 * Example usage of the generated full-enum-mapper:
 * <pre><code>
 * EnumMapperFull&lt;Seasons, String&gt; germanSeasons = Seasons_MapperFull
 *     .setSPRING("Fruehling")
 *     .setSUMMER("Sommer")
 *     .setFALL("Herbst")
 *     .setWINTER("Winter");
 *
 * String germanSummer = germanSeasons.getValue(Seasons.SUMMER); // returns "Sommer"
 * </code></pre>
 * <p>
 * <p>
 * When you cannot apply the {@link EnumMapper} annotation
 * directly to the Enum (e.g. when the Enum is defined in a 3rd party library), you can use the
 * {@link EnumMappers} annotation instead (note the plural form)
 * </p>
 *
 * @see EnumMappers
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumMapper {
}
