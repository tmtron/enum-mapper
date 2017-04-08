package com.tmtron.enums;

import org.junit.Test;

import static com.tmtron.enums.TestBuilder.Nine.I1;
import static com.tmtron.enums.TestBuilder.Nine.I2;
import static com.tmtron.enums.TestBuilder.Nine.I3;
import static com.tmtron.enums.TestBuilder.Nine.I4;
import static com.tmtron.enums.TestBuilder.Nine.I5;
import static com.tmtron.enums.TestBuilder.Nine.I6;
import static com.tmtron.enums.TestBuilder.Nine.I7;
import static com.tmtron.enums.TestBuilder.Nine.I8;
import static com.tmtron.enums.TestBuilder.Nine.I9;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestBuilder {

    enum Nine {I1, I2, I3, I4, I5, I6, I7, I8, I9}

    private void assertMapping(EnumMapper<Nine, Integer> mapper, int value, boolean expectedToBeOkay) {
        Nine enumResult = mapper.getEnum(value);
        if (expectedToBeOkay) {
            assertNotNull(enumResult);
            assertEquals(Integer.valueOf(value), mapper.getValue(enumResult));
        } else {
            assertNull(enumResult);
            assertEquals(I9, mapper.getEnumOrDefault(value, I9));
        }
    }

    private void assertMapper(int upperLimitIncluded, EnumMapper<Nine, Integer> mapper) {
        assertEquals(upperLimitIncluded, mapper.size());
        for (int i = 1; i <= 9; i++) {
            assertMapping(mapper, i, i <= upperLimitIncluded);
        }
    }

    @Test
    public void testMapperOfPartial() {
        assertMapper(2, EnumMapper.ofPartial(I1, 1, Nine.I2, 2));
        assertMapper(3, EnumMapper.ofPartial(I1, 1, Nine.I2, 2, I3, 3));
        assertMapper(4, EnumMapper.ofPartial(I1, 1, Nine.I2, 2, I3, 3, I4, 4));
        assertMapper(5, EnumMapper.ofPartial(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5));
        assertMapper(6, EnumMapper.ofPartial(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6));
        assertMapper(7, EnumMapper.ofPartial(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6, I7, 7));
        assertMapper(8, EnumMapper.ofPartial(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6, I7, 7, I8, 8));
        assertMapper(9, EnumMapper.ofPartial(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6, I7, 7, I8, 8, I9, 9));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues2() {
        EnumMapper.of(I1, 1, Nine.I2, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues3() {
        EnumMapper.of(I1, 1, Nine.I2, 2, I3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues4() {
        EnumMapper.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues5() {
        EnumMapper.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4, I5, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues6() {
        EnumMapper.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4, I5, 5, I6, 6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues7() {
        EnumMapper.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4, I5, 5, I6, 6, I7, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues8() {
        EnumMapper.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4, I5, 5, I6, 6, I7, 7
                , I8, 8);
    }

    @Test
    public void testBuildPartial() throws Exception {
        EnumMapper<Nine, String> mapper = EnumMapper.builder(I1, "1")
                .put(I2, "2")
                .buildPartial();
        assertEquals("1", mapper.getValue(I1));
        assertEquals("2", mapper.getValue(I2));
        assertEquals(I1, mapper.getEnum("1"));
        assertEquals(I2, mapper.getEnum("2"));
        assertNull(mapper.getEnum("3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderThrowsOnMissingValues() throws Exception {
        EnumMapper<Nine, String> mapper = EnumMapper.builder(I1, "1")
                .put(I2, "2")
                .build();
    }

    @Test
    public void testGetDefault() throws Exception {
        EnumMapper<Nine, String> mapper = EnumMapper.builder(I1, "1")
                .put(I2, "2")
                .buildPartial();
        assertEquals(I9, mapper.getEnumOrDefault("xx", I9));
    }
}