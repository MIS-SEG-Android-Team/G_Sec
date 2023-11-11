package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "App_User_Master")
public class EAppUserMaster {

    @PrimaryKey
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx;

    public EAppUserMaster() {
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }
}
