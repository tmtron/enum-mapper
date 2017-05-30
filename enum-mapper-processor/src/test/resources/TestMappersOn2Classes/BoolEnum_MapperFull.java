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
// GENERATED
package com.test;

import com.tmtron.enums.EnumMapperFull;

import javax.annotation.Generated;

@Generated(
        value = "com.tmtron.enums.processor.EnumsAnnotationProcessor",
        date = "1976-12-14T15:16:17.234+02:00",
        comments = "origin=com.test.TwoClasses_Source.AnnotatedClassA"
)
public final class BoolEnum_MapperFull {
    public static <V> IsetON<V> setOFF(V value) {
        StagedBuilder<V> result = new StagedBuilder<>();
        result.enumMapperBuilder.put(TwoClasses_Source.BoolEnum.OFF, value);
        return result;
    }

    public interface IsetON<V> {
        EnumMapperFull<TwoClasses_Source.BoolEnum, V> setON(V value);
    }

    private static class StagedBuilder<V> implements IsetON<V> {
        private final EnumMapperFull.Builder<TwoClasses_Source.BoolEnum, V> enumMapperBuilder = EnumMapperFull.builder
                (TwoClasses_Source.BoolEnum.class);

        @Override
        public EnumMapperFull<TwoClasses_Source.BoolEnum, V> setON(V value) {
            enumMapperBuilder.put(TwoClasses_Source.BoolEnum.ON, value);
            return enumMapperBuilder.build();
        }
    }
}