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
