package com.test;

import com.tmtron.enums.EnumMapper;

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