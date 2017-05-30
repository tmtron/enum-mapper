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
 * <p>This annotation can be used on any type or package. It will generate a full-enum-mapper {@link EnumMapperFull}
 * for each of the specified enum-classes.
 * </p>
 * <p>
 * You need to use this annotation only when you cannot apply the {@link EnumMapper} annotation (note the singular form)
 * directly to the Enum (e.g. when the Enum is defined in a 3rd party library).
 * </p>
 * Example usage of the annotation:
 * <pre><code>
 * {@literal @}EnumMappers({Seasons.class, ColorEnum.class})
 *  public class AppUtil {}
 * </code></pre>
 * When you only have one class to map you do not need the curly braces:
 * <pre><code>
 * {@literal @}EnumMappers(Seasons.class)
 *  public class AppUtil {}
 * </code></pre>
 * For an example how to use the generated full-enum-mapper see {@link EnumMapper}
 *
 * @see EnumMapper
 */
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumMappers {
    /**
     * @return the enum-class/es for which you want to generate a full-enum-mapper {@link EnumMapperFull}
     */
    Class<? extends Enum<?>>[] value();
}
