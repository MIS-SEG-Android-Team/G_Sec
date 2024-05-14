package org.rmj.guanzongroup.gsecurity.utils;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class TimeComparator implements Comparator<PatrolScheduleEntity> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    @Override
    public int compare(PatrolScheduleEntity o1, PatrolScheduleEntity o2) {
        try {
            Date date1 = dateFormat.parse(o1.getDTimexxxx());
            Date date2 = dateFormat.parse(o2.getDTimexxxx());
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Handle parsing error
        }
    }
}