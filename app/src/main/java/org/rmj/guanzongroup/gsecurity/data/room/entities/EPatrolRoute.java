package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Patrol_Route")
public class EPatrolRoute {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sPatrolID")
    private String PatrolID;
    @ColumnInfo(name = "sNFCIDxxx", defaultValue = "")
    private String NFCIDxxx;
    @ColumnInfo(name = "cRecdStat", defaultValue = "0")
    private int RecdStat;
    @ColumnInfo(name = "dModified", defaultValue = "CURRENT_TIMESTAMP")
    private Date Modified;
    @ColumnInfo(name = "sModified", defaultValue = "")
    private String Modifier;
    @ColumnInfo(name = "dTimeStmp", defaultValue = "CURRENT_TIMESTAMP")
    private Date TimeStmp;

    public EPatrolRoute() {

    }

    public String getPatrolID() {
        return PatrolID;
    }

    public void setPatrolID(String patrolID) {
        PatrolID = patrolID;
    }

    public String getNFCIDxxx() {
        return NFCIDxxx;
    }

    public void setNFCIDxxx(String NFCIDxxx) {
        this.NFCIDxxx = NFCIDxxx;
    }

    public int getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(int recdStat) {
        RecdStat = recdStat;
    }

    public Date getModified() {
        return Modified;
    }

    public void setModified(Date modified) {
        Modified = modified;
    }

    public String getModifier() {
        return Modifier;
    }

    public void setModifier(String modifier) {
        Modifier = modifier;
    }

    public Date getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(Date timeStmp) {
        TimeStmp = timeStmp;
    }
}
