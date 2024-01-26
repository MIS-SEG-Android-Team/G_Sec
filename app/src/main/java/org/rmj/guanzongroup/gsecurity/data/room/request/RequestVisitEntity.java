package org.rmj.guanzongroup.gsecurity.data.room.request;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Request_Visit", primaryKeys = {"sNFCIDxxx", "dTimexxxx"})
public class RequestVisitEntity {

    @NonNull
    @ColumnInfo(name = "sNFCIDxxx")
    protected String sNFCIDxxx;
    @NonNull
    @ColumnInfo(name = "dTimexxxx")
    protected String dTimexxxx;
    @ColumnInfo(name = "sDescript")
    protected String sDescript;
    @ColumnInfo(name = "dVisitedx")
    protected String dVisitedx;
    @ColumnInfo(name = "cRequestd")
    protected String cRequestd;
    @ColumnInfo(name = "sRemarks1")
    protected String sRemarks1;
    @ColumnInfo(name = "sRemarks2")
    protected String sRemarks2;
    @ColumnInfo(name = "cSendStat")
    protected String cSendStat;

    public String getSNFCIDxxx() { return sNFCIDxxx; }
    public void setSNFCIDxxx(@NonNull String value) { this.sNFCIDxxx = value; }

    public String getDTimexxxx() { return dTimexxxx; }
    public void setDTimexxxx(@NonNull String value) { this.dTimexxxx = value; }

    public String getSDescript() { return sDescript; }
    public void setSDescript(String sDescript) { this.sDescript = sDescript; }

    public String getDVisitedx() { return dVisitedx; }
    public void setDVisitedx(String value) { this.dVisitedx = value; }

    public String getCRequestd() { return cRequestd; }
    public void setCRequestd(String value) { this.cRequestd = value; }

    public String getSRemark1() { return sRemarks1; }
    public void setSRemark1(String value) { this.sRemarks1 = value; }

    public String getSRemark2() { return sRemarks2; }
    public void setSRemark2(String value) { this.sRemarks2 = value; }

    public String getCSendStat() { return cSendStat; }
    public void setCSendStat(String value) { this.cSendStat = value; }
}
