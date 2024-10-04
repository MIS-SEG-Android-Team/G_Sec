package org.rmj.guanzongroup.gsecurity.data.room.patrol.route;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Patrol_Route", primaryKeys = {"sNFCIDxxx", "schedIDxx"})
public class PatrolRouteEntity {

    @NonNull
    @ColumnInfo(name = "sNFCIDxxx")
    protected String sNFCIDxxx;
    @ColumnInfo(name = "nPatrolNo")
    protected String nPatrolNo;
    @ColumnInfo(name = "sDescript")
    protected String sDescript;
    @ColumnInfo(name = "cRecdStat")
    protected String cRecdStat;
    @NonNull
    @ColumnInfo(name = "schedIDxx")
    protected String schedIDxx;

    @NonNull
    public String getSNFCIDxxx() { return sNFCIDxxx; }
    public void setSNFCIDxxx(@NonNull String sNFCIDxxx) { this.sNFCIDxxx = sNFCIDxxx; }

    public String getNPatrolNo() { return nPatrolNo; }
    public void setNPatrolNo(String nPatrolNo) { this.nPatrolNo = nPatrolNo; }

    public String getSDescript() { return sDescript; }
    public void setSDescript(String sDescript) { this.sDescript = sDescript; }

    public String getCRecdStat() { return cRecdStat; }
    public void setCRecdStat(String cRecdStat) { this.cRecdStat = cRecdStat; }

    @NonNull
    public String getSchedIDxx() {return schedIDxx;}
    public void setSchedIDxx(@NonNull String schedIDxx) {this.schedIDxx = schedIDxx;}
}
