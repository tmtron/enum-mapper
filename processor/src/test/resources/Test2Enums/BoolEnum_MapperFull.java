// GENERATED
package com.test;

import com.tmtron.enums.EnumMapper;

import javax.annotation.Generated;

@Generated(
        value = "com.tmtron.enums.processor.EnumsAnnotationProcessor",
        date = "1976-12-14T15:16:17.234+02:00",
        comments = "origin=com.test.TwoEnums_Source"
)
public final class BoolEnum_MapperFull {
    public static <V> IsetON<V> setOFF(V value) {
        StagedBuilder<V> result = new StagedBuilder<>();
        result.enumMapperBuilder.put(TwoEnums_Source.BoolEnum.OFF, value);
        return result;
    }

    public interface IsetON<V> {
        EnumMapper<TwoEnums_Source.BoolEnum, V> setON(V value);
    }

    private static class StagedBuilder<V> implements IsetON<V> {
        private final EnumMapper.Builder<TwoEnums_Source.BoolEnum, V> enumMapperBuilder = EnumMapper.builder
                (TwoEnums_Source.BoolEnum.class);

        @Override
        public EnumMapper<TwoEnums_Source.BoolEnum, V> setON(V value) {
            enumMapperBuilder.put(TwoEnums_Source.BoolEnum.ON, value);
            return enumMapperBuilder.build();
        }
    }
}