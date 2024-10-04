package org.rmj.guanzongroup.gsecurity.data.room.request;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Request_Visit", primaryKeys = {"sRquestID"})
public class RequestVisitEntity {

    @NonNull
    @ColumnInfo(name = "sRquestID")
    protected String sRquestID;
    @NonNull
    @ColumnInfo(name = "sUserIDxx")
    protected String sUserIDxx;
    @ColumnInfo(name = "sWHouseID")
    protected String sWHouseID;
    @ColumnInfo(name = "sNFCIDxxx")
    protected String sNFCIDxxx;
    @ColumnInfo(name = "dSchedule")
    protected String dSchedule;
    @ColumnInfo(name = "sRemarksx")
    protected String sRemarksx;

    @NonNull
    public String getsRquestID() {
        return sRquestID;
    }

    public void setsRquestID(@NonNull String sRquestID) {
        this.sRquestID = sRquestID;
    }

    @NonNull
    public String getsUserIDxx() {
        return sUserIDxx;
    }

    public void setsUserIDxx(@NonNull String sUserIDxx) {
        this.sUserIDxx = sUserIDxx;
    }

    public String getsWHouseID() {
        return sWHouseID;
    }

    public void setsWHouseID(String sWHouseID) {
        this.sWHouseID = sWHouseID;
    }

    public String getsNFCIDxxx() {
        return sNFCIDxxx;
    }

    public void setsNFCIDxxx(String sNFCIDxxx) {
        this.sNFCIDxxx = sNFCIDxxx;
    }

    public String getdSchedule() {
        return dSchedule;
    }

    public void setdSchedule(String dSchedule) {
        this.dSchedule = dSchedule;
    }

    public String getsRemarksx() {
        return sRemarksx;
    }

    public void setsRemarksx(String sRemarksx) {
        this.sRemarksx = sRemarksx;
    }
}
