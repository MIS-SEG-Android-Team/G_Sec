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
    private static final String PATROL_SCHEDULE = "patrol_schedule";
    private static final String PATROL_RE_SCHEDULE = "patrol_re_schedule";
    private static final String PATROL_STARTED = "patrol_started";
    private static final String PATROL_CHECKPOINT = "patrol_nfcidxx";
    private static final String PATROL_SCHEDULE_ID = "patrol_schedid";
    private static final String PATROL_CHECKPOINT_DURATION = "patrol_checkpoint_duration";
    private static final String PATROL_LOG_POSTING = "patrol_log_posting";

    @Inject
    public PatrolCache(Application application) {
        this.preferences = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setPatrolSchedule(String value) {
        editor.putString(PATROL_SCHEDULE, value);
        editor.commit();
    }

    public String getPatrolSchedule(){
        return preferences.getString(PATROL_SCHEDULE, "");
    }

    public void setPatrolScheduleID(String value) {
        editor.putString(PATROL_SCHEDULE_ID, value);
        editor.commit();
    }

    public String getPatrolScheduleID(){
        return preferences.getString(PATROL_SCHEDULE_ID, "");
    }

    public void setPatrolReSchedule(String value) {
        editor.putString(PATROL_RE_SCHEDULE, value);
        editor.commit();
    }

    public String getPatrolReSchedule() {
        return preferences.getString(PATROL_RE_SCHEDULE, "");
    }

    public void setPatrolStarted(boolean hasStarted) {
        editor.putBoolean(PATROL_STARTED, hasStarted);
        editor.commit();
    }

    public boolean getPatrolStarted() {
        return preferences.getBoolean(PATROL_STARTED, false);
    }

    public String getWarehouse() {
        return preferences.getString(PATROL_WAREHOUSE, "");
    }

    public void setWarehouse(String value) {
        editor.putString(PATROL_WAREHOUSE, value);
        editor.commit();
    }

    public String getCheckpoint() {
        return preferences.getString(PATROL_CHECKPOINT, "");
    }

    public void setPatrolCheckpoint(String value){
        editor.putString(PATROL_CHECKPOINT, value);
        editor.commit();
    }

    public Integer getCheckpointDuration() {return preferences.getInt(PATROL_CHECKPOINT_DURATION, 0);}

    public void setCheckpointDuration(Integer value){
        editor.putInt(PATROL_CHECKPOINT_DURATION, value);
        editor.commit();
    }

    public void setPatrolLogPosting(boolean value) {
        editor.putBoolean(PATROL_LOG_POSTING, value);
        editor.commit();
    }

    public boolean getPatrolLogPosting() {
        return preferences.getBoolean(PATROL_LOG_POSTING, false);
    }

    public String getNotes() {
        return preferences.getString(PATROL_NOTES, "");
    }

    public void setNotes(String value) {
        editor.putString(PATROL_NOTES, value);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
