package org.rmj.guanzongroup.gsecurity.etc;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_DATE_TIME_FORMAT;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTime {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime getCurrentLocalDateTime() {
        // Get the current time in milliseconds since the epoch
        long currentTimeMillis = System.currentTimeMillis();

        // Convert the milliseconds to LocalDateTime
        Instant instant = Instant.ofEpochMilli(currentTimeMillis);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatDateTimeResult(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
        return localDateTime.format(formatter);
    }
}
