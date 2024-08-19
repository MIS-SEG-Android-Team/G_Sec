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

        LocalTime currentTime = LocalTime.parse("01:32 pm", dateTimeFormatter);
        LocalTime patrolTime = LocalTime.parse("01:31 pm", dateTimeFormatter);

        System.out.println(Duration.between(currentTime, patrolTime).toMinutes());

    }
}
