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

        LocalTime currentTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalTime schedule = LocalTime.parse("14:15:03");

        System.out.println(Duration.between(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))),
                LocalTime.parse("15:26:03")).toMinutes());

    }
}
