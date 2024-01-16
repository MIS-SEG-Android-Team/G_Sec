package org.rmj.guanzongroup.gsecurity.data.room.schedule;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.List;

@Entity(tableName = "Created_Schedule")
public class ScheduleEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    protected int id;
    @ColumnInfo(name = "sWHouseID")
    protected String sWHouseID;
    @ColumnInfo(name = "sSchedule")
    protected List<ScheduleTime> sSchedule;
    @ColumnInfo(name = "sUserIDxx")
    protected String sUserIDxx;
    @ColumnInfo(name = "sRoutexxx")
    protected List<ScheduleRoute> sRoutexxx;
    @ColumnInfo(name = "sNotesxxx")
    protected String sNotesxxx;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public List<ScheduleTime> getSSchedule() { return sSchedule; }
    public void setSSchedule(List<ScheduleTime> value) { this.sSchedule = value; }

    public String getSUserIDxx() { return sUserIDxx; }
    public void setSUserIDxx(String value) { this.sUserIDxx = value; }

    public List<ScheduleRoute> getSRoutexxx() { return sRoutexxx; }
    public void setSRoutexxx(List<ScheduleRoute> value) { this.sRoutexxx = value; }

    public String getSNotesxxx() { return sNotesxxx; }
    public void setSNotesxxx(String value) { this.sNotesxxx = value; }
}

