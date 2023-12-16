package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.String;

@Entity(tableName = "App_User_Master")
public class EAppUserMaster {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "sUserIDxx")
    protected String sUserIDxx;
    @ColumnInfo(name = "sUserName")
    protected String sUserName;
    @ColumnInfo(name = "sEmailAdd")
    protected String sEmailAdd;
    @ColumnInfo(name = "sPINCodex")
    protected String sPINCodex;
    @ColumnInfo(name = "sPosition")
    protected String sPosition;
    @ColumnInfo(name = "nUserLevl")
    protected String nUserLevl;
    @ColumnInfo(name = "dCreatedx")
    protected String dCreatedx;
    @ColumnInfo(name = "dModified")
    protected String dModified;
    @ColumnInfo(name = "sModified")
    protected String sModified;
    @ColumnInfo(name = "dTimeStmp")
    protected String dTimeStmp;

    public EAppUserMaster() {

    }

    @NonNull
    public String getsUserIDxx() {
        return sUserIDxx;
    }

    public void setsUserIDxx(@NonNull String sUserIDxx) {
        this.sUserIDxx = sUserIDxx;
    }

    public String getsUserName() {
        return sUserName;
    }

    public void setsUserName(String sUserName) {
        this.sUserName = sUserName;
    }

    public String getsEmailAdd() {
        return sEmailAdd;
    }

    public void setsEmailAdd(String sEmailAdd) {
        this.sEmailAdd = sEmailAdd;
    }

    public String getsPINCodex() {
        return sPINCodex;
    }

    public void setsPINCodex(String sPINCodex) {
        this.sPINCodex = sPINCodex;
    }

    public String getsPosition() {
        return sPosition;
    }

    public void setsPosition(String sPosition) {
        this.sPosition = sPosition;
    }

    public String getnUserLevl() {
        return nUserLevl;
    }

    public void setnUserLevl(String nUserLevl) {
        this.nUserLevl = nUserLevl;
    }

    public String getdCreatedx() {
        return dCreatedx;
    }

    public void setdCreatedx(String dCreatedx) {
        this.dCreatedx = dCreatedx;
    }

    public String getdModified() {
        return dModified;
    }

    public void setdModified(String dModified) {
        this.dModified = dModified;
    }

    public String getsModified() {
        return sModified;
    }

    public void setsModified(String sModified) {
        this.sModified = sModified;
    }

    public String getdTimeStmp() {
        return dTimeStmp;
    }

    public void setdTimeStmp(String dTimeStmp) {
        this.dTimeStmp = dTimeStmp;
    }
}
