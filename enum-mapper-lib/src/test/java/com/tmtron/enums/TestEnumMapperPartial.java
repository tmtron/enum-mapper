/*
 * Copyright © 2018 Martin Trummer (martin.trummer@tmtron.com)
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestEnumMapperPartial {

    enum Nine {I1, I2, I3, I4, I5, I6, I7, I8, I9}

    final EnumMapperFull<Nine, Integer> fullMapper = EnumMapperFull.of(I1, 1, Nine.I2, 2, I3, 3
            , I4, 4, I5, 5, I6, 6, I7, 7, I8, 8, I9, 9);

    private void assertMapping(EnumMapperPartial<Nine, Integer> mapper, int value, boolean expectedToBeOkay) {
        Nine enumResult = mapper.getEnumOrNull(value);
        if (expectedToBeOkay) {
            assertNotNull(enumResult);
            assertEquals(Integer.valueOf(value), mapper.getValueOrNull(enumResult));
            assertTrue(mapper.isEnumMapped(enumResult));
            assertTrue(mapper.isValueMapped(value));

        } else {
            assertNull(enumResult);
            assertEquals(I9, mapper.getEnumOrDefault(value, I9));
            assertFalse(mapper.isValueMapped(value));
            Nine nonMappedEnum = fullMapper.getEnumOrRaise(value);
            assertFalse(mapper.isEnumMapped(nonMappedEnum));
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
        assertMapper(2, EnumMapperPartial.of(I1, 1, I2, 2));
        assertMapper(3, EnumMapperPartial.of(I1, 1, I2, 2, I3, 3));
        assertMapper(4, EnumMapperPartial.of(I1, 1, I2, 2, I3, 3, I4, 4));
        assertMapper(5, EnumMapperPartial.of(I1, 1, I2, 2, I3, 3, I4, 4
                , I5, 5));
        assertMapper(6, EnumMapperPartial.of(I1, 1, I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6));
        assertMapper(7, EnumMapperPartial.of(I1, 1, I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6, I7, 7));
        assertMapper(8, EnumMapperPartial.of(I1, 1, I2, 2, I3, 3, I4, 4
                , I5, 5, I6, 6, I7, 7, I8, 8));
        assertMapper(9, EnumMapperPartial.of(I1, 1, I2, 2, I3, 3, I4, 4
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

    @Test(expected = IllegalArgumentException.class)
    public void testGetEnumOrRaise_throws_for_unknown_value() throws Exception {
        EnumMapperPartial<Nine, String> mapper = EnumMapperPartial.builder(I1, "1")
                .put(I2, "2")
                .build();
        mapper.getEnumOrRaise("7");
    }

    @Test
    public void testGetValueOrRaise_returns_value() throws Exception {
        EnumMapperPartial<Nine, String> mapper = EnumMapperPartial.builder(I1, "1")
                .put(I2, "2")
                .build();
        assertEquals("1", mapper.getValueOrRaise(I1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueOrRaise_throws_for_unknown_value() throws Exception {
        EnumMapperPartial<Nine, String> mapper = EnumMapperPartial.builder(I1, "1")
                .put(I2, "2")
                .build();
        mapper.getValueOrRaise(I9);
    }

}