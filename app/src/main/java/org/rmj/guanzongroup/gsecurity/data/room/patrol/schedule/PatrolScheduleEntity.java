package org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Patrol_Schedule", primaryKeys = {"dTimexxxx"})
public class PatrolScheduleEntity {

    @NonNull
    @ColumnInfo(name = "dTimexxxx")
    protected String dTimexxxx;
    @ColumnInfo(name = "nSchedule")
    protected String nSchedule;
    @ColumnInfo(name = "cRequestxx")
    protected String cRequestxx;
    @ColumnInfo(name = "cAlarmxxx")
    protected boolean cAlarmxxx = false;
    @ColumnInfo(name = "nfcIDxx")
    protected String nfcIDxx;
    @ColumnInfo(name = "schedIDxx")
    protected String schedIDxx;

    public PatrolScheduleEntity() {
    }

    @NonNull
    public String getDTimexxxx() { return dTimexxxx; }
    public void setDTimexxxx(@NonNull String dTimexxxx) { this.dTimexxxx = dTimexxxx; }

    public String getNSchedule() { return nSchedule; }
    public void setNSchedule(String nSchedule) { this.nSchedule = nSchedule; }

    public String getCRequestd() { return cRequestxx; }
    public void setCRequestd(String value) { this.cRequestxx = value; }

    public boolean getCAlarmxxx() {
        return cAlarmxxx;
    }
    public void setCAlarmxxx(boolean cAlarmxxx) {
        this.cAlarmxxx = cAlarmxxx;
    }

    public String getNfcIDxx(){return nfcIDxx;}
    public void setNfcIDxx(String nfcIDxx){this.nfcIDxx = nfcIDxx;}

    public String getSchedIDxx() {return schedIDxx;}
    public void setSchedIDxx(String schedIDxx) {this.schedIDxx = schedIDxx;}
}
