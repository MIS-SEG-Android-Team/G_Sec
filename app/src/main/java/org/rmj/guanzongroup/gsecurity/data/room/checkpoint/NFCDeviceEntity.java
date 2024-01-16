package org.rmj.guanzongroup.gsecurity.data.room.checkpoint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NFC_Device")
public class NFCDeviceEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sNFCIDxxx")
    protected String sNFCIDxxx;
    @ColumnInfo(name = "sWHouseID")
    protected String sWHouseID;
    @ColumnInfo(name = "dTimeStmp")
    protected String dTimeStmp;
    @ColumnInfo(name = "sDescript")
    protected String sDescript;
    @ColumnInfo(name = "sCatIDxxx")
    protected String sCatIDxxx;
    @ColumnInfo(name = "cRecdStat")
    protected String cRecdStat;

    @NonNull
    public String getSNFCIDxxx() { return sNFCIDxxx; }
    public void setSNFCIDxxx(@NonNull String value) { this.sNFCIDxxx = value; }

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public String getDTimeStmp() { return dTimeStmp; }
    public void setDTimeStmp(String value) { this.dTimeStmp = value; }

    public String getSDescript() { return sDescript; }
    public void setSDescript(String value) { this.sDescript = value; }

    public String getSCatIDxxx() { return sCatIDxxx; }
    public void setSCatIDxxx(String value) { this.sCatIDxxx = value; }

    public String getCRecdStat() { return cRecdStat; }
    public void setCRecdStat(String value) { this.cRecdStat = value; }
}
