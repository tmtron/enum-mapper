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

import static org.junit.Assert.assertEquals;

public class TestLauncherAge {

    enum LauncherAge {
        ONLINE, OLD, OFFLINE
    }

    EnumMapperFull<LauncherAge, String> testMapper = LauncherAge_MapperFull
            .setONLINE("online")
            .setOLD("old")
            .setOFFLINE("offline");

    @Test
    public void testLauncherAgeGetEnum() {
        assertEquals(LauncherAge.ONLINE, testMapper.getEnumOrRaise("online"));
        assertEquals(LauncherAge.OLD, testMapper.getEnumOrRaise("old"));
        assertEquals(LauncherAge.OFFLINE, testMapper.getEnumOrRaise("offline"));
    }

    @Test
    public void testLauncherAgeGetValues() {
        assertEquals("online", testMapper.getValue(LauncherAge.ONLINE));
        assertEquals("old", testMapper.getValue(LauncherAge.OLD));
        assertEquals("offline", testMapper.getValue(LauncherAge.OFFLINE));
    }

}