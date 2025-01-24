package org.rmj.guanzongroup.gsecurity.Test;

import org.junit.Test;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_DATE_TIME_FORMAT;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestTimeFormat {

    @Test
    public void TestDateTime() {

        try {

            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            Date timeDate = timeFormat.parse("9:00 AM");

            Date currentDate = new Date();
            SimpleDateFormat combinedFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            System.out.println(combinedFormat.format(currentDate) + " " + new SimpleDateFormat("HH:mm:ss", Locale.US).format(timeDate));


        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}