package kr.gg.lol.common.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {
    public static String ZONE_ID = "Asia/Seoul";
    public static LocalDateTime dateTimeOf(long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                ZoneId.of(ZONE_ID));
    }
    public static long unixTimestampOf(LocalDateTime dateTime){
        return dateTime.atZone(ZoneId.of(ZONE_ID)).toInstant()
                .toEpochMilli();
    }
    public static Timestamp timestampOf(LocalDateTime dateTime){
        return dateTime == null ? null : Timestamp.valueOf(dateTime);
    }
    public static LocalDateTime dateTimeOf(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
