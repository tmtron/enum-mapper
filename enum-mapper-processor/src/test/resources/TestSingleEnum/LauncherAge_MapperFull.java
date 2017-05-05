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

import com.tmtron.enums.EnumMapper;

import javax.annotation.Generated;

@Generated(
        value = "com.tmtron.enums.processor.EnumsAnnotationProcessor",
        date = "1976-12-14T15:16:17.234+02:00",
        comments = "origin=com.test.LauncherAge_Source"
)
public final class LauncherAge_MapperFull {
    public static <V> IsetOLD<V> setONLINE(V value) {
        StagedBuilder<V> result = new StagedBuilder<>();
        result.enumMapperBuilder.put(LauncherAge_Source.LauncherAge.ONLINE, value);
        return result;
    }

    public interface IsetOFFLINE<V> {
        EnumMapper<LauncherAge_Source.LauncherAge, V> setOFFLINE(V value);
    }

    public interface IsetOLD<V> {
        IsetOFFLINE<V> setOLD(V value);
    }

    private static class StagedBuilder<V> implements IsetOLD<V>, IsetOFFLINE<V> {
        private final EnumMapper.Builder<LauncherAge_Source.LauncherAge, V> enumMapperBuilder = EnumMapper.builder
                (LauncherAge_Source.LauncherAge.class);

        @Override
        public IsetOFFLINE<V> setOLD(V value) {
            enumMapperBuilder.put(LauncherAge_Source.LauncherAge.OLD, value);
            return this;
        }

        @Override
        public EnumMapper<LauncherAge_Source.LauncherAge, V> setOFFLINE(V value) {
            enumMapperBuilder.put(LauncherAge_Source.LauncherAge.OFFLINE, value);
            return enumMapperBuilder.build();
        }
    }
}
