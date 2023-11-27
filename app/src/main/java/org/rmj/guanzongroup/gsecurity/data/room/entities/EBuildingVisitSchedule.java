package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Building_Visit_Schedule")
public class EBuildingVisitSchedule {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sVisitIDx", defaultValue = "")
    private String VisitIDx;
    @ColumnInfo(name = "dTransact", defaultValue = "")
    private String Transact;
    @ColumnInfo(name = "sUserIDxx", defaultValue = "")
    private String UserIDxx;
    @ColumnInfo(name = "sNFCIDxxx", defaultValue = "")
    private String NFCIDxxx;
    @ColumnInfo(name = "sRemarksx", defaultValue = "")
    private String Remarksx;
    @ColumnInfo(name = "dModified", defaultValue = "CURRENT_TIMESTAMP")
    private Date Modified;
    @ColumnInfo(name = "dTimeStmp", defaultValue = "CURRENT_TIMESTAMP")
    private Date TimeStmp;

    public EBuildingVisitSchedule() {
    }

    public String getVisitIDx() {
        return VisitIDx;
    }

    public void setVisitIDx(String visitIDx) {
        VisitIDx = visitIDx;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getNFCIDxxx() {
        return NFCIDxxx;
    }

    public void setNFCIDxxx(String NFCIDxxx) {
        this.NFCIDxxx = NFCIDxxx;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public Date getModified() {
        return Modified;
    }

    public void setModified(Date modified) {
        Modified = modified;
    }

    public Date getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(Date timeStmp) {
        TimeStmp = timeStmp;
    }
}
