package kr.gg.lol.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static kr.gg.lol.common.util.DateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilsTest {
    @Test
    void testDateTimeConvert(){
        long now = 1680831764185L;
        LocalDateTime dateTime = dateTimeOf(now);
        assertEquals(now, unixTimestampOf(dateTime));
    }
}