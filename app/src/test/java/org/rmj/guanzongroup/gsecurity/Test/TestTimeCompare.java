package org.rmj.guanzongroup.gsecurity.Test;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;

import android.annotation.SuppressLint;

import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;

public class TestTimeCompare {

    @Test
    public void TestDuration(){

        @SuppressLint("NewApi")
        DateTimeFormatter dateTimeFormatter =
                new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(DEFAULT_TIME_FORMAT)
                        .toFormatter(Locale.ENGLISH);

        ArrayList<String> schedules = new ArrayList<>();
        schedules.add("11:35 am");
        schedules.add("11:40 am");
        schedules.add("11:45 am");

        for (int i = 0; i < schedules.size(); i++){

            LocalTime current = LocalTime.now();
            LocalTime schedule = LocalTime.parse(schedules.get(i), dateTimeFormatter);

            System.out.println(schedule.getHour() - current.getHour());
            System.out.println(schedule.getMinute() - current.getMinute());

            if (current.isAfter(schedule)){
                System.out.println(schedule + " is finished");
                break;
            }
        }

    }
}
