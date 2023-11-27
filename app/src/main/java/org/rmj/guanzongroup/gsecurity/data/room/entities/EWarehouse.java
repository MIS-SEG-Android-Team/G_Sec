package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Warehouse")
public class EWarehouse {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sWHouseID")
    private int WHouseID;
    @ColumnInfo(name = "sWHouseNm", defaultValue = "")
    private String WHouseNm;
    @ColumnInfo(name = "sBranchCd", defaultValue = "")
    private String BranchCd;
    @ColumnInfo(name = "cRecdStat", defaultValue = "")
    private String RecdStat;
    @ColumnInfo(name = "dModified", defaultValue = "CURRENT_TIMESTAMP")
    private Date Modified;
    @ColumnInfo(name = "sModified", defaultValue = "")
    private String Modifier; // User ID of the admin who modified this record...
    @ColumnInfo(name = "dTimeStmp", defaultValue = "CURRENT_TIMESTAMP")
    private Date TimeStmp;

    public EWarehouse() {
    }

    public int getWHouseID() {
        return WHouseID;
    }

    public void setWHouseID(int WHouseID) {
        this.WHouseID = WHouseID;
    }

    public String getWHouseNm() {
        return WHouseNm;
    }

    public void setWHouseNm(String WHouseNm) {
        this.WHouseNm = WHouseNm;
    }

    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(String branchCd) {
        BranchCd = branchCd;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
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
