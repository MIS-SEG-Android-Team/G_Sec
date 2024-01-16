package org.rmj.guanzongroup.gsecurity.data.room;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.rmj.guanzongroup.gsecurity.data.room.schedule.ScheduleRoute;

import java.lang.reflect.Type;
import java.util.List;

@ProvidedTypeConverter
public class Converters {

    @TypeConverter
    public String fromScheduleRouteListToString(List<ScheduleRoute> scheduleRoutesList) {
        if (scheduleRoutesList == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<ScheduleRoute>>(){}.getType();
        return gson.toJson(scheduleRoutesList, type);
    }

    @TypeConverter
    public List<ScheduleRoute> fromScheduleRouteStringToList(String scheduleRouteString) {
        if (scheduleRouteString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<ScheduleRoute>>(){}.getType();
        return gson.fromJson(scheduleRouteString, type);
    }
}
