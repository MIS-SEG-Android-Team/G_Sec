package org.rmj.guanzongroup.gsecurity.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

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

    public void setNFCSites(String value) {
        editor.putString(SITES, value);
        editor.commit();
    }

    public void setSchedules(String value) {
        editor.putString(SCHEDULES, value);
        editor.commit();
    }

    public String getWarehouse() {
        return preferences.getString(WAREHOUSE, "");
    }

    public String getSites() {
        return preferences.getString(SITES, "");
    }

    public String getSchedules() {
        return preferences.getString(SCHEDULES, "");
    }

    public void clearPatrolSchedulerCache() {
        editor.clear();
        editor.commit();
    }
}
