package org.rmj.guanzongroup.gsecurity.etc;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_DATE_TIME_FORMAT;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static String formatDateTimeToUIPreview(String dateTime) {
        try {
            if (dateTime != null) {
                Date parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
                return new SimpleDateFormat("MMMM dd, yyyy hh:mm aa").format(parseDate);
            } else {
                return "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
