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
package com.test;

/*
 * Test if we can put the @EnumMappers annotation on 2 different classes
 * did not work in V1.0.1
 * see issue #2: "@EnumMappers can only be used once in the project"
 *  https://github.com/tmtron/enum-mapper/issues/2
 */

import com.tmtron.enums.EnumMappers;

public class TwoClasses_Source {
    enum BoolEnum {OFF, ON}

    enum ColorEnum {RED, BLUE}

    @EnumMappers({TwoClasses_Source.BoolEnum.class})
    public static class AnnotatedClassA {

    }

    @EnumMappers({TwoClasses_Source.ColorEnum.class})
    public static class AnnotatedClassB {

    }

}