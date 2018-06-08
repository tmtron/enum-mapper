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

import com.tmtron.enums.EnumMapperFull;

import javax.annotation.Generated;

@Generated(
        value = "com.tmtron.enums.processor.EnumsAnnotationProcessor",
        date = "1976-12-14T15:16:17.234+02:00",
        comments = "origin=com.test.DuplicateEnums_Source.AnnotatedClassB"
)
public final class ColorEnum_MapperFull {
    public static <V> IsetBLUE<V> setRED(V value) {
        StagedBuilder<V> result = new StagedBuilder<>();
        result.enumMapperBuilder.put(DuplicateEnums_Source.ColorEnum.RED, value);
        return result;
    }

    public interface IsetBLUE<V> {
        EnumMapperFull<DuplicateEnums_Source.ColorEnum, V> setBLUE(V value);
    }

    private static class StagedBuilder<V> implements IsetBLUE<V> {
        private final EnumMapperFull.Builder<DuplicateEnums_Source.ColorEnum, V> enumMapperBuilder = EnumMapperFull
                .builder
                        (DuplicateEnums_Source.ColorEnum.class);

        @Override
        public EnumMapperFull<DuplicateEnums_Source.ColorEnum, V> setBLUE(V value) {
            enumMapperBuilder.put(DuplicateEnums_Source.ColorEnum.BLUE, value);
            return enumMapperBuilder.build();
        }
    }
}