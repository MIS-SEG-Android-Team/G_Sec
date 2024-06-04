package org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Patrol_Schedule")
public class PatrolScheduleEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dTimexxxx")
    protected String dTimexxxx;
    @ColumnInfo(name = "nSchedule")
    protected String nSchedule;
    @ColumnInfo(name = "cAlarmxxx")
    protected boolean cAlarmxxx = false;

    public PatrolScheduleEntity() {
    }

    @NonNull
    public String getDTimexxxx() { return dTimexxxx; }
    public void setDTimexxxx(@NonNull String dTimexxxx) { this.dTimexxxx = dTimexxxx; }

    public String getNSchedule() { return nSchedule; }
    public void setNSchedule(String nSchedule) { this.nSchedule = nSchedule; }

    public boolean getCAlarmxxx() {
        return cAlarmxxx;
    }

    public void setCAlarmxxx(boolean cAlarmxxx) {
        this.cAlarmxxx = cAlarmxxx;
    }
}
