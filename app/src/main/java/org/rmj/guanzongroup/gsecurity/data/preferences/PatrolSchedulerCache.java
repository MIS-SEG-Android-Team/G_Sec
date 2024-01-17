package org.rmj.guanzongroup.gsecurity.data.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.rmj.guanzongroup.gsecurity.data.room.schedule.ScheduleRoute;
import org.rmj.guanzongroup.gsecurity.data.room.schedule.ScheduleTime;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PatrolSchedulerCache {

    private final SharedPreferences preferences;

    private final SharedPreferences.Editor editor;

    private static final String PREFERENCES_NAME = "PatrolSchedulerCache";

    private static final String WAREHOUSE = "warehouse_id";
    private static final String SITES = "nfc_sites";
    private static final String SCHEDULES = "patrol_schedules";

    @Inject
    public PatrolSchedulerCache(Application application) {
        this.preferences = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setWarehouseID(String value) {
        editor.putString(WAREHOUSE, value);
        editor.commit();
    }

    public void setPatrolRoute(List<ScheduleRoute> value) {
        String routes = new Gson().toJson(value);
        editor.putString(SITES, routes);
        editor.commit();
    }

    public void setSchedules(List<ScheduleTime> value) {
        String schedules = new Gson().toJson(value);
        editor.putString(SCHEDULES, schedules);
        editor.commit();
    }

    public String getWarehouse() {
        return preferences.getString(WAREHOUSE, "");
    }

    public List<ScheduleRoute> getPatrolRoute() {
        String routes = preferences.getString(SITES, "");
        if (routes.isEmpty()) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<ScheduleRoute>>(){}.getType();
        return gson.fromJson(routes, type);
    }

    public String getPatrolSchedules() {
        String routes = preferences.getString(SCHEDULES, "");
        if (routes.isEmpty()) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<ScheduleRoute>>(){}.getType();
        return gson.fromJson(routes, type);
    }

    public void clearPatrolSchedulerCache() {
        editor.clear();
        editor.commit();
    }
}
