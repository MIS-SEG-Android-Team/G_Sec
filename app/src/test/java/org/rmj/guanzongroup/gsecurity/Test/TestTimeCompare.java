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
        schedules.add("10:35 am");
        schedules.add("10:40 am");
        schedules.add("10:45 am");

        for (int i = 0; i < schedules.size(); i++){

            LocalTime current = LocalTime.parse(LocalTime.now().format(dateTimeFormatter), dateTimeFormatter);
            LocalTime schedule = LocalTime.parse(schedules.get(i), dateTimeFormatter);

            System.out.println(current.compareTo(schedule));

            if (current.isAfter(schedule)){
                System.out.println(schedule + " is finished");
                break;
            }
        }

    }
}
