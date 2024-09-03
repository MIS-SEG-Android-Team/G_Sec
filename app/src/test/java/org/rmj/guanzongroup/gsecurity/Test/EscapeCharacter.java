package org.rmj.guanzongroup.gsecurity.Test;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;
import android.annotation.SuppressLint;
import org.junit.Test;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class EscapeCharacter {

    @Test
    public void TestString(){

        String character  = "UPDATE Patrol_Schedule SET cRequestxx = '2' " +
                "WHERE 02:17 PM %s <= :schedule AND cRequestxx = '1'";

        int frstindex = character.indexOf("02:17 PM");
        int lstindex = frstindex + "02:17 PM".length();

        @SuppressLint("NewApi")
        DateTimeFormatter dateTimeFormatter =
                new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(DEFAULT_TIME_FORMAT)
                        .toFormatter(Locale.ENGLISH);

        LocalTime secFormat = LocalTime.parse(character.substring(frstindex, lstindex), dateTimeFormatter);

        System.out.println(String.format(character, secFormat).replace("02:17 PM", ""));
    }
}
