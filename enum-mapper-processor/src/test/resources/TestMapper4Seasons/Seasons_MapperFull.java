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
package com.test;

import com.tmtron.enums.EnumMapperFull;

import javax.annotation.Generated;

@Generated(
        value = "com.tmtron.enums.processor.EnumsAnnotationProcessor",
        date = "1976-12-14T15:16:17.234+02:00",
        comments = "origin=com.test.Seasons"
)
public final class Seasons_MapperFull {
    public static <V> IsetSUMMER<V> setSPRING(V value) {
        StagedBuilder<V> result = new StagedBuilder<>();
        result.enumMapperBuilder.put(Seasons.SPRING, value);
        return result;
    }

    public interface IsetWINTER<V> {
        EnumMapperFull<Seasons, V> setWINTER(V value);
    }

    public interface IsetFALL<V> {
        IsetWINTER<V> setFALL(V value);
    }

    public interface IsetSUMMER<V> {
        IsetFALL<V> setSUMMER(V value);
    }

    private static class StagedBuilder<V> implements IsetSUMMER<V>, IsetFALL<V>, IsetWINTER<V> {
        private final EnumMapperFull.Builder<Seasons, V> enumMapperBuilder = EnumMapperFull.builder(Seasons.class);

        @Override
        public IsetFALL<V> setSUMMER(V value) {
            enumMapperBuilder.put(Seasons.SUMMER, value);
            return this;
        }

        @Override
        public IsetWINTER<V> setFALL(V value) {
            enumMapperBuilder.put(Seasons.FALL, value);
            return this;
        }

        @Override
        public EnumMapperFull<Seasons, V> setWINTER(V value) {
            enumMapperBuilder.put(Seasons.WINTER, value);
            return enumMapperBuilder.build();
        }
    }
}