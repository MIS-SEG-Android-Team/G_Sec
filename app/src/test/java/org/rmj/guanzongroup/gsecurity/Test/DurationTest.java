package org.rmj.guanzongroup.gsecurity.Test;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class DurationTest {

    @Test
    public void TestDuration(){

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(DEFAULT_TIME_FORMAT)
                .toFormatter(Locale.ENGLISH);

        LocalTime currentTime = LocalTime.parse("11:53 am", dateTimeFormatter);
        LocalTime patrolTime = LocalTime.parse("12:30 am", dateTimeFormatter);

        System.out.println(Duration.between(currentTime, patrolTime).toMinutes());

    }
}
