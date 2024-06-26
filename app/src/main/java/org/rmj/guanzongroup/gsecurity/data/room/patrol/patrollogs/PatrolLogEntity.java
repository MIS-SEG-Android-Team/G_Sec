package org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Patrol_Log", primaryKeys = {"sNFCIDxxx", "dVisitedx"})
public class PatrolLogEntity {

    @NonNull
    @ColumnInfo(name = "sNFCIDxxx")
    protected String sNFCIDxxx;
    @NonNull
    @ColumnInfo(name = "dVisitedx")
    protected String dVisitedx;

    @ColumnInfo(name = "dTimeVist")
    protected String dTimeVist;
    @ColumnInfo(name = "sRemarksx")
    protected String sRemarksx;
    @ColumnInfo(name = "sUserIDxx")
    protected String sUserIDxx;
    @ColumnInfo(name = "cSendStat")
    protected String cSendStat;
    @ColumnInfo(name = "dSchedule")
    protected String dSchedule;

    public PatrolLogEntity() {
    }

    public String getSNFCIDxxx() { return sNFCIDxxx; }
    public void setSNFCIDxxx(@NonNull String value) { this.sNFCIDxxx = value; }

    public String getDVisitedx() { return dVisitedx; }
    public void setDVisitedx(@NonNull String value) { this.dVisitedx = value; }

    public String getSRemarksx() { return sRemarksx; }
    public void setSRemarksx(String value) { this.sRemarksx = value; }

    public String getSUserIDxx() { return sUserIDxx; }
    public void setSUserIDxx(String value) { this.sUserIDxx = value; }

    public String getCSendStat() { return cSendStat; }
    public void setCSendStat(String value) { this.cSendStat = value; }

    public String getDSchedule() { return dSchedule; }
    public void setDSchedule(String value) { this.dSchedule = value; }

    public String getDTimeVist() { return dTimeVist; }
    public void setDTimeVist(String dTimeVist) { this.dTimeVist = dTimeVist; }
}
