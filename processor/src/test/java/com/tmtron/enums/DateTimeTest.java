package com.tmtron.enums;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTest {

    @Test
    public void testDateTimeFormat() throws Exception {
        // we must use ZonedDateTime
        // see http://stackoverflow.com/a/25561565/6287240
        String dateString = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now());
        Assert.assertTrue(dateString.contains("+"));
    }
}
