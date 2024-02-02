package org.rmj.guanzongroup.gsecurity.data.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.rmj.guanzongroup.gsecurity.data.remote.response.personnelpatrol.PersonnelPatrolModel;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PatrolUpdateCache {

    private final SharedPreferences preferences;

    private final SharedPreferences.Editor editor;

    private static final String PREFERENCES_NAME = "PatrolUpdateCache";

    private static final String PATROL_UPDATE_SCHEDULE = "patrol_update_schedule";

    @Inject
    public PatrolUpdateCache(Application application) {
        this.preferences = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setPatrolUpdateSchedule(PersonnelPatrolModel value) {
        String routes = new Gson().toJson(value);
        editor.putString(PATROL_UPDATE_SCHEDULE, routes);
        editor.commit();
    }

    public PersonnelPatrolModel getPatrolScheduleForUpdate() {
        String routes = preferences.getString(PATROL_UPDATE_SCHEDULE, "");
        if (routes.isEmpty()) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<PersonnelPatrolModel>(){}.getType();
        return gson.fromJson(routes, type);
    }

    public void clearCache() {
        editor.clear();
        editor.commit();
    }
}
