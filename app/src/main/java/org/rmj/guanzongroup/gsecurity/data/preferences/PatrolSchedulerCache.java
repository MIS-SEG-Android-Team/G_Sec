package org.rmj.guanzongroup.gsecurity.data.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateScheduleParams;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PatrolSchedulerCache {

    private final SharedPreferences preferences;

    private final SharedPreferences.Editor editor;

    private static final String PREFERENCES_NAME = "PatrolSchedulerCache";

    private static final String PATROL_SCHEDULE = "patrol_schedule";

    @Inject
    public PatrolSchedulerCache(Application application) {
        this.preferences = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setPatrolSchedule(CreateScheduleParams value) {
        String routes = new Gson().toJson(value);
        editor.putString(PATROL_SCHEDULE, routes);
        editor.commit();
    }

    public CreateScheduleParams getPatrolSchedule() {
        String routes = preferences.getString(PATROL_SCHEDULE, "");
        if (routes.isEmpty()) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<CreateScheduleParams>(){}.getType();
        return gson.fromJson(routes, type);
    }

    public void clearCache() {
        editor.clear();
        editor.commit();
    }
}
