package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "App_User_Master")
public class EAppUserMaster {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sUserIDxx", defaultValue = "")
    private String UserIDxx;
    @ColumnInfo(name = "sUserName", defaultValue = "")
    private String UserName;
    @ColumnInfo(name = "sEmailAdd", defaultValue = "")
    private String EmailAdd;
    @ColumnInfo(name = "sPINCodex", defaultValue = "")
    private String PINCodex;
    @ColumnInfo(name = "sPosition", defaultValue = "")
    private String Position;
    @ColumnInfo(name = "nUserLevl", defaultValue = "")
    private String UserLevl;
    @ColumnInfo(name = "dCreatedx", defaultValue = "")
    private Date Createdx;
    @ColumnInfo(name = "dModified", defaultValue = "CURRENT_TIMESTAMP")
    private Date Modified;
    @ColumnInfo(name = "sModified", defaultValue = "")
    private String Modifier;
    @ColumnInfo(name = "dTimeStmp", defaultValue = "CURRENT_TIMESTAMP")
    private Date TimeStmp;

    public EAppUserMaster() {

    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmailAdd() {
        return EmailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getPINCodex() {
        return PINCodex;
    }

    public void setPINCodex(String PINCodex) {
        this.PINCodex = PINCodex;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getUserLevl() {
        return UserLevl;
    }

    public void setUserLevl(String userLevl) {
        UserLevl = userLevl;
    }

    public Date getCreatedx() {
        return Createdx;
    }

    public void setCreatedx(Date createdx) {
        Createdx = createdx;
    }

    public Date getModified() {
        return Modified;
    }

    public void setModified(Date modified) {
        Modified = modified;
    }

    public String getModifier() {
        return Modifier;
    }

    public void setModifier(String modifier) {
        Modifier = modifier;
    }

    public Date getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(Date timeStmp) {
        TimeStmp = timeStmp;
    }
}
