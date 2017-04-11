package com.tmtron.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLauncherAge {

    enum LauncherAge {
        ONLINE, OLD, OFFLINE
    }

    EnumMapper<LauncherAge, String> testMapper = LauncherAge_MapperFull
            .setONLINE("online")
            .setOLD("old")
            .setOFFLINE("offline");

    @Test
    public void testLauncherAgeGetEnum() throws Exception {
        assertEquals(LauncherAge.ONLINE, testMapper.getEnumOrRaise("online"));
        assertEquals(LauncherAge.OLD, testMapper.getEnumOrRaise("old"));
        assertEquals(LauncherAge.OFFLINE, testMapper.getEnumOrRaise("offline"));
    }

    @Test
    public void testLauncherAgeGetValues() throws Exception {
        assertEquals("online", testMapper.getValue(LauncherAge.ONLINE));
        assertEquals("old", testMapper.getValue(LauncherAge.OLD));
        assertEquals("offline", testMapper.getValue(LauncherAge.OFFLINE));
    }

}