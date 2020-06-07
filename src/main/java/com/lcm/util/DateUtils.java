package com.lcm.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-M-d");
    public static final DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter dateFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //将LocalDateTime转为自定义的时间格式的字符串
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    //将long类型转为LocalDateTime
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    //将LocalDateTime转为long类型
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    //将某字符串时间转为对应时间格式的LocalDateTime;"2018-06-01 00:00:00", "yyyy-MM-dd HH:mm:ss"
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

}
