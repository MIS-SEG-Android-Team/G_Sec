package org.rmj.guanzongroup.gsecurity.Test;

import org.junit.Test;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;

import android.annotation.SuppressLint;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestTimeFormat {

    @Test
    public void TestDateTime() {

        @SuppressLint("NewApi")
        DateTimeFormatter dateTimeFormatter =
                new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(DEFAULT_TIME_FORMAT)
                        .toFormatter(Locale.ENGLISH);

        LocalTime firstFormat = LocalTime.parse(
                LocalTime.parse("21:20:00")
                        .format(dateTimeFormatter), dateTimeFormatter);

        LocalTime secFormat = LocalTime.parse("09:20 pm", dateTimeFormatter);

        System.out.println(LocalDateTime.of(LocalDateTime.now().toLocalDate(), firstFormat).format(dateTimeFormatter));
        /*System.out.println(secFormat);
        System.out.println(firstFormat.equals(secFormat));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));*/

    }
}