package com.solvina.esf.utils;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 12:43 PM
 */
public class Utils {
    public static final DateTimeFormatter dmf = DateTimeFormatter.ISO_DATE_TIME;
    public static final Charset charset = Charset.forName("UTF-8");


    public static LocalDateTime toLocalDateTime(Long stamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(stamp), ZoneId.systemDefault());
    }

    public static long toStamp(LocalDateTime dateTime){
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
