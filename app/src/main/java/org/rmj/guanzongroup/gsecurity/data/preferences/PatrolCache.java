package org.rmj.guanzongroup.gsecurity.data.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PatrolCache {

    private final SharedPreferences preferences;

    private final SharedPreferences.Editor editor;

    private static final String PREFERENCES_NAME = "PatrolCache";

    private static final String PATROL_WAREHOUSE = "patrol_warehouse_id";
    private static final String PATROL_NOTES = "patrol_notes";

    @Inject
    public PatrolCache(Application application) {
        this.preferences = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public String getWarehouse() {
        return preferences.getString(PATROL_WAREHOUSE, "");
    }

    public void setWarehouse(String value) {
        editor.putString(PATROL_WAREHOUSE, value);
        editor.commit();
    }

    public String getNotes() {
        return preferences.getString(PATROL_WAREHOUSE, "");
    }

    public void setNotes(String value) {
        editor.putString(PATROL_WAREHOUSE, value);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
