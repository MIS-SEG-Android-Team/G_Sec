package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class EPersonnelPosition {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sPositnID")
    private String sPositnID;
    @ColumnInfo(name = "sPositnNm", defaultValue = "")
    private String sPositnNm;
    @ColumnInfo(name = "dCreatedx", defaultValue = "CURRENT_TIMESTAMP")
    private Date dCreatedx;
    @ColumnInfo(name = "dModified", defaultValue = "CURRENT_TIMESTAMP")
    private Date dModified;
}
