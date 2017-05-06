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
package com.tmtron.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEnumMapperFullExamples {

    @Test
    public void testBool() throws Exception {
        EnumMapperFull<TwoEnums_Source.BoolEnum, String> boolMapper = BoolEnum_MapperFull.setOFF("off")
                .setON("on");
        assertEquals(TwoEnums_Source.BoolEnum.OFF, boolMapper.getEnumOrRaise("off"));
        assertEquals(TwoEnums_Source.BoolEnum.ON, boolMapper.getEnumOrRaise("on"));

        assertEquals("off", boolMapper.getValue(TwoEnums_Source.BoolEnum.OFF));
        assertEquals("on", boolMapper.getValue(TwoEnums_Source.BoolEnum.ON));
    }

    @Test
    public void testColor() throws Exception {
        EnumMapperFull<TwoEnums_Source.ColorEnum, String> boolMapper = ColorEnum_MapperFull.setRED("red")
                .setBLUE("blue");
        assertEquals(TwoEnums_Source.ColorEnum.RED, boolMapper.getEnumOrRaise("red"));
        assertEquals(TwoEnums_Source.ColorEnum.BLUE, boolMapper.getEnumOrRaise("blue"));

        assertEquals("red", boolMapper.getValue(TwoEnums_Source.ColorEnum.RED));
        assertEquals("blue", boolMapper.getValue(TwoEnums_Source.ColorEnum.BLUE));
    }
}