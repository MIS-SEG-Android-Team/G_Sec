package org.rmj.guanzongroup.gsecurity.data.room.checkpoint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.String;

@Entity(tableName = "NFC_Device")
public class NFCDeviceEntity {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "sNFCIDxxx")
    protected String sNFCIDxxx;
    @ColumnInfo(name = "sDescript")
    protected String sDescript;
    @ColumnInfo(name = "sCatIDxxx")
    protected String sCatIDxxx;
    @ColumnInfo(name = "sPayloadx")
    protected String sPayloadx;
    @ColumnInfo(name = "sWHouseID")
    protected String sWHouseID;
    @ColumnInfo(name = "cSelected")
    protected String cSelected;
    @ColumnInfo(name = "cRecdStat")
    protected String cRecdStat;
    @ColumnInfo(name = "dModified")
    protected String dModified;
    @ColumnInfo(name = "sModifier")
    protected String sModifier;
    @ColumnInfo(name = "dTimeStmp")
    protected String dTimeStmp;

    public NFCDeviceEntity() {
    }

    @NonNull
    public String getsNFCIDxxx() {
        return sNFCIDxxx;
    }

    public void setsNFCIDxxx(@NonNull String sNFCIDxxx) {
        this.sNFCIDxxx = sNFCIDxxx;
    }

    public String getsDescript() {
        return sDescript;
    }

    public void setsDescript(String sDescript) {
        this.sDescript = sDescript;
    }

    public String getsCatIDxxx() {
        return sCatIDxxx;
    }

    public void setsCatIDxxx(String sCatIDxxx) {
        this.sCatIDxxx = sCatIDxxx;
    }

    public String getsPayloadx() {
        return sPayloadx;
    }

    public void setsPayloadx(String sPayloadx) {
        this.sPayloadx = sPayloadx;
    }

    public String getsWHouseID() {
        return sWHouseID;
    }

    public void setsWHouseID(String sWHouseID) {
        this.sWHouseID = sWHouseID;
    }

    public String getcRecdStat() {
        return cRecdStat;
    }

    public void setcRecdStat(String cRecdStat) {
        this.cRecdStat = cRecdStat;
    }

    public String getdModified() {
        return dModified;
    }

    public void setdModified(String dModified) {
        this.dModified = dModified;
    }

    public String getsModifier() {
        return sModifier;
    }

    public void setsModifier(String sModifier) {
        this.sModifier = sModifier;
    }

    public String getdTimeStmp() {
        return dTimeStmp;
    }

    public void setdTimeStmp(String dTimeStmp) {
        this.dTimeStmp = dTimeStmp;
    }

    public String getcSelected() {
        return cSelected;
    }

    public void setcSelected(String cSelected) {
        this.cSelected = cSelected;
    }
}
