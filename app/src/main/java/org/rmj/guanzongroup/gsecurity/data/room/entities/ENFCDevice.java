package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "NFC_Device")
public class ENFCDevice {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sNFCIDxxx", defaultValue = "")
    private String sNFCIDxxx;
    @ColumnInfo(name = "sDescript", defaultValue = "")
    private String sDescript;
    @ColumnInfo(name = "sCatIDxxx", defaultValue = "")
    private String sCatIDxxx;
    @ColumnInfo(name = "sPayloadx", defaultValue = "")
    private String sPayloadx;
    @ColumnInfo(name = "sWHouseID", defaultValue = "")
    private String sWHouseID;
    @ColumnInfo(name = "cRecdStat", defaultValue = "")
    private String cRecdStat;
    @ColumnInfo(name = "dModified", defaultValue = "")
    private Date dModified;
    @ColumnInfo(name = "sModifier", defaultValue = "")
    private String sModifier;
    @ColumnInfo(name = "dTimeStmp", defaultValue = "")
    private Date dTimeStmp;
}
