package com.tmtron.enums;

import org.junit.Test;

import static com.tmtron.enums.TestEnumMapper.Nine.I1;
import static com.tmtron.enums.TestEnumMapper.Nine.I2;
import static com.tmtron.enums.TestEnumMapper.Nine.I3;
import static com.tmtron.enums.TestEnumMapper.Nine.I4;
import static com.tmtron.enums.TestEnumMapper.Nine.I5;
import static com.tmtron.enums.TestEnumMapper.Nine.I6;
import static com.tmtron.enums.TestEnumMapper.Nine.I7;
import static com.tmtron.enums.TestEnumMapper.Nine.I8;
import static com.tmtron.enums.TestEnumMapper.Nine.I9;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestEnumMapper {

    enum Nine {I1, I2, I3, I4, I5, I6, I7, I8, I9}

    private EnumMapper<Nine, Integer> fullMapper = EnumMapper.of(I1, 1, Nine.I2, 2, I3, 3
            , I4, 4, I5, 5, I6, 6, I7, 7, I8, 8, I9, 9);

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

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderThrowsOnMissingValues() throws Exception {
        EnumMapper<Nine, String> mapper = EnumMapper.builder(I1, "1")
                .put(I2, "2")
                .build();
    }

    @Test
    public void testGetOrDefault() throws Exception {
        assertEquals(I7, fullMapper.getEnumOrDefault(77, I7));
    }

    @Test
    public void testGetOrNull() throws Exception {
        assertNull(fullMapper.getEnumOrNull(77));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOrRaise() throws Exception {
        fullMapper.getEnumOrRaise(77);
    }

}