package com.tmtron.enums;

import org.junit.Test;

import static com.tmtron.enums.TestEnumMapperPartial.Nine.I1;
import static com.tmtron.enums.TestEnumMapperPartial.Nine.I2;
import static com.tmtron.enums.TestEnumMapperPartial.Nine.I3;
import static com.tmtron.enums.TestEnumMapperPartial.Nine.I4;
import static com.tmtron.enums.TestEnumMapperPartial.Nine.I5;
import static com.tmtron.enums.TestEnumMapperPartial.Nine.I6;
import static com.tmtron.enums.TestEnumMapperPartial.Nine.I7;
import static com.tmtron.enums.TestEnumMapperPartial.Nine.I8;
import static com.tmtron.enums.TestEnumMapperPartial.Nine.I9;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestEnumMapperPartial {

    enum Nine {I1, I2, I3, I4, I5, I6, I7, I8, I9}

    private void assertMapping(EnumMapperPartial<Nine, Integer> mapper, int value, boolean expectedToBeOkay) {
        Nine enumResult = mapper.getEnumOrNull(value);
        if (expectedToBeOkay) {
            assertNotNull(enumResult);
            assertEquals(Integer.valueOf(value), mapper.getValueOrNull(enumResult));
        } else {
            assertNull(enumResult);
            assertEquals(I9, mapper.getEnumOrDefault(value, I9));
        }
    }

    private void assertMapper(int upperLimitIncluded, EnumMapperPartial<Nine, Integer> mapper) {
        assertEquals(upperLimitIncluded, mapper.size());
        for (int i = 1; i <= 9; i++) {
            assertMapping(mapper, i, i <= upperLimitIncluded);
        }
    }

    @Test
    public void testMapperof() {
        assertMapper(2, EnumMapperPartial.of(I1, 1, Nine.I2, 2));
        assertMapper(3, EnumMapperPartial.of(I1, 1, Nine.I2, 2, I3, 3));
        assertMapper(4, EnumMapperPartial.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4));
        assertMapper(5, EnumMapperPartial.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5));
        assertMapper(6, EnumMapperPartial.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6));
        assertMapper(7, EnumMapperPartial.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6, I7, 7));
        assertMapper(8, EnumMapperPartial.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6, I7, 7, I8, 8));
        assertMapper(9, EnumMapperPartial.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6, I7, 7, I8, 8, I9, 9));
    }

    @Test
    public void testBuildPartial() throws Exception {
        EnumMapperPartial<Nine, String> mapper = EnumMapperPartial.builder(I1, "1")
                .put(I2, "2")
                .build();
        assertEquals("1", mapper.getValueOrNull(I1));
        assertEquals("2", mapper.getValueOrNull(I2));
        assertEquals(I1, mapper.getEnumOrNull("1"));
        assertEquals(I2, mapper.getEnumOrNull("2"));
        assertNull(mapper.getEnumOrNull("3"));
    }

    @Test
    public void testGetDefault() throws Exception {
        EnumMapperPartial<Nine, String> mapper = EnumMapperPartial.builder(I1, "1")
                .put(I2, "2")
                .build();
        assertEquals(I9, mapper.getEnumOrDefault("xx", I9));
    }

    @Test
    public void testGetValueOrDefault_returns_value() throws Exception {
        EnumMapperPartial<Nine, String> mapper = EnumMapperPartial.builder(I1, "1")
                .put(I2, "2")
                .build();
        assertEquals("2", mapper.getValueOrDefault(I2, "xx"));
    }

    @Test
    public void testGetValueOrDefault_returns_default() throws Exception {
        EnumMapperPartial<Nine, String> mapper = EnumMapperPartial.builder(I1, "1")
                .put(I2, "2")
                .build();
        assertEquals("xx", mapper.getValueOrDefault(I9, "xx"));
    }

}