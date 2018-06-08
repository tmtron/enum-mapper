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

import static com.tmtron.enums.TestEnumMapperFull.Nine.I1;
import static com.tmtron.enums.TestEnumMapperFull.Nine.I2;
import static com.tmtron.enums.TestEnumMapperFull.Nine.I3;
import static com.tmtron.enums.TestEnumMapperFull.Nine.I4;
import static com.tmtron.enums.TestEnumMapperFull.Nine.I5;
import static com.tmtron.enums.TestEnumMapperFull.Nine.I6;
import static com.tmtron.enums.TestEnumMapperFull.Nine.I7;
import static com.tmtron.enums.TestEnumMapperFull.Nine.I8;
import static com.tmtron.enums.TestEnumMapperFull.Nine.I9;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestEnumMapperFull {

    enum Nine {I1, I2, I3, I4, I5, I6, I7, I8, I9}

    private EnumMapperFull<Nine, Integer> fullMapper = EnumMapperFull.of(I1, 1, Nine.I2, 2, I3, 3
            , I4, 4, I5, 5, I6, 6, I7, 7, I8, 8, I9, 9);

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues2() {
        EnumMapperFull<Nine, Integer> xx = EnumMapperFull.of(I1, 1, Nine.I2, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues3() {
        EnumMapperFull.of(I1, 1, Nine.I2, 2, I3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues4() {
        EnumMapperFull.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues5() {
        EnumMapperFull.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4, I5, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues6() {
        EnumMapperFull.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4, I5, 5, I6, 6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues7() {
        EnumMapperFull.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4, I5, 5, I6, 6, I7, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapperOfThrowsOnMissingValues8() {
        EnumMapperFull.of(I1, 1, Nine.I2, 2, I3, 3, I4, 4, I5, 5, I6, 6, I7, 7
                , I8, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderThrowsOnMissingValues() throws Exception {
        EnumMapperFull<Nine, String> mapper = EnumMapperFull.builder(I1, "1")
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

    @Test
    public void testIsEnumMapped() {
        assertTrue(fullMapper.isEnumMapped(I1));
        assertTrue(fullMapper.isEnumMapped(I2));
        assertTrue(fullMapper.isEnumMapped(I3));
        assertTrue(fullMapper.isEnumMapped(I4));
        assertTrue(fullMapper.isEnumMapped(I5));
        assertTrue(fullMapper.isEnumMapped(I6));
        assertTrue(fullMapper.isEnumMapped(I7));
        assertTrue(fullMapper.isEnumMapped(I8));
        assertTrue(fullMapper.isEnumMapped(I9));
    }

    @Test
    public void testIsValueMapped() {
        assertTrue(fullMapper.isValueMapped(1));
        assertTrue(fullMapper.isValueMapped(2));
        assertTrue(fullMapper.isValueMapped(3));
        assertTrue(fullMapper.isValueMapped(4));
        assertTrue(fullMapper.isValueMapped(5));
        assertTrue(fullMapper.isValueMapped(6));
        assertTrue(fullMapper.isValueMapped(7));
        assertTrue(fullMapper.isValueMapped(8));
        assertTrue(fullMapper.isValueMapped(9));
    }

    @Test
    public void testIsValueMapped_returns_false() {
        assertFalse(fullMapper.isValueMapped(-1));
        assertFalse(fullMapper.isValueMapped(0));
        assertFalse(fullMapper.isValueMapped(10));
    }

}