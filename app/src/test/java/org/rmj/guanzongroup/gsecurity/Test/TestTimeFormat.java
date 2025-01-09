package org.rmj.guanzongroup.gsecurity.Test;

import org.junit.Test;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_DATE_TIME_FORMAT;
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

        System.out.println(
                LocalTime.parse("10:49:00",
                        DateTimeFormatter.ofPattern("HH:mm:ss"))
        );

    }
}