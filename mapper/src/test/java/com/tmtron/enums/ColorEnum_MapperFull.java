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

import javax.annotation.Generated;

@Generated(
        value = "com.tmtron.enums.processor.EnumsAnnotationProcessor",
        date = "1976-12-14T15:16:17.234+02:00",
        comments = "origin=com.test.TwoEnums_Source"
)
public final class ColorEnum_MapperFull {
    public static <V> IsetBLUE<V> setRED(V value) {
        StagedBuilder<V> result = new StagedBuilder<>();
        result.enumMapperBuilder.put(TwoEnums_Source.ColorEnum.RED, value);
        return result;
    }

    public interface IsetBLUE<V> {
        EnumMapper<TwoEnums_Source.ColorEnum, V> setBLUE(V value);
    }

    private static class StagedBuilder<V> implements IsetBLUE<V> {
        private final EnumMapper.Builder<TwoEnums_Source.ColorEnum, V> enumMapperBuilder = EnumMapper.builder
                (TwoEnums_Source.ColorEnum.class);

        @Override
        public EnumMapper<TwoEnums_Source.ColorEnum, V> setBLUE(V value) {
            enumMapperBuilder.put(TwoEnums_Source.ColorEnum.BLUE, value);
            return enumMapperBuilder.build();
        }
    }
}