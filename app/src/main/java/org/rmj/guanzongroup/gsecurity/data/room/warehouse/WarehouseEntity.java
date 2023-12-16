package org.rmj.guanzongroup.gsecurity.data.room.warehouse;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Warehouse")
public class WarehouseEntity {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "sWHouseID")
    protected String sWHouseID;
    @ColumnInfo(name = "sWHouseNm")
    protected String sWHouseNm;
    @ColumnInfo(name = "sBranchNm")
    protected String sBranchNm;
    @ColumnInfo(name = "sBranchCd")
    protected String sBranchCd;
    @ColumnInfo(name = "cRecdStat")
    protected String cRecdStat;
    @ColumnInfo(name = "dTimeStmp")
    protected String dTimeStmp;

    public WarehouseEntity() {
    }

    @NonNull
    public String getsBranchNm() {
        return sBranchNm;
    }

    public void setsBranchNm(@NonNull String sBranchNm) {
        this.sBranchNm = sBranchNm;
    }

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public String getSBranchCd() { return sBranchCd; }
    public void setSBranchCd(String value) { this.sBranchCd = value; }

    public String getDTimeStmp() { return dTimeStmp; }
    public void setDTimeStmp(String value) { this.dTimeStmp = value; }

    public String getSWHouseNm() { return sWHouseNm; }
    public void setSWHouseNm(String value) { this.sWHouseNm = value; }

    public String getCRecdStat() { return cRecdStat; }
    public void setCRecdStat(String value) { this.cRecdStat = value; }
}
