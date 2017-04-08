package com.tmtron.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEnumMapper {

    @Test
    public void testBool() throws Exception {
        EnumMapper<TwoEnums_Source.BoolEnum, String> boolMapper = BoolEnum_MapperFull.setOFF("off")
                .setON("on");
        assertEquals(TwoEnums_Source.BoolEnum.OFF, boolMapper.getEnum("off"));
        assertEquals(TwoEnums_Source.BoolEnum.ON, boolMapper.getEnum("on"));

        assertEquals("off", boolMapper.getValue(TwoEnums_Source.BoolEnum.OFF));
        assertEquals("on", boolMapper.getValue(TwoEnums_Source.BoolEnum.ON));
    }

    @Test
    public void testColor() throws Exception {
        EnumMapper<TwoEnums_Source.ColorEnum, String> boolMapper = ColorEnum_MapperFull.setRED("red")
                .setBLUE("blue");
        assertEquals(TwoEnums_Source.ColorEnum.RED, boolMapper.getEnum("red"));
        assertEquals(TwoEnums_Source.ColorEnum.BLUE, boolMapper.getEnum("blue"));

        assertEquals("red", boolMapper.getValue(TwoEnums_Source.ColorEnum.RED));
        assertEquals("blue", boolMapper.getValue(TwoEnums_Source.ColorEnum.BLUE));
    }
}