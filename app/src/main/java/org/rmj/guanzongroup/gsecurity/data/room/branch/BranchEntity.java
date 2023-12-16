package org.rmj.guanzongroup.gsecurity.data.room.branch;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Branch")
public class BranchEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sBranchCd")
    protected String sBranchCd;
    @ColumnInfo(name = "sBranchNm")
    protected String sBranchNm;
    @ColumnInfo(name = "sDescript")
    protected String sDescript;
    @ColumnInfo(name = "sAddressx")
    protected String sAddressx;
    @ColumnInfo(name = "sTownIDxx")
    protected String sTownIDxx;
    @ColumnInfo(name = "sAreaCode")
    protected String sAreaCode;
    @ColumnInfo(name = "cDivision")
    protected String cDivision;
    @ColumnInfo(name = "cPromoDiv")
    protected String cPromoDiv;
    @ColumnInfo(name = "cRecdStat")
    protected String cRecdStat;
    @ColumnInfo(name = "dTimeStmp")
    protected String dTimeStmp;
    @ColumnInfo(name = "dLstUpdte")
    protected String dLstUpdte;

    public BranchEntity() {

    }

    @NonNull
    public String getsBranchCd() {
        return sBranchCd;
    }

    public void setsBranchCd(@NonNull String sBranchCd) {
        this.sBranchCd = sBranchCd;
    }

    public String getsBranchNm() {
        return sBranchNm;
    }

    public void setsBranchNm(String sBranchNm) {
        this.sBranchNm = sBranchNm;
    }

    public String getsDescript() {
        return sDescript;
    }

    public void setsDescript(String sDescript) {
        this.sDescript = sDescript;
    }

    public String getsAddressx() {
        return sAddressx;
    }

    public void setsAddressx(String sAddressx) {
        this.sAddressx = sAddressx;
    }

    public String getsTownIDxx() {
        return sTownIDxx;
    }

    public void setsTownIDxx(String sTownIDxx) {
        this.sTownIDxx = sTownIDxx;
    }

    public String getsAreaCode() {
        return sAreaCode;
    }

    public void setsAreaCode(String sAreaCode) {
        this.sAreaCode = sAreaCode;
    }

    public String getcDivision() {
        return cDivision;
    }

    public void setcDivision(String cDivision) {
        this.cDivision = cDivision;
    }

    public String getcPromoDiv() {
        return cPromoDiv;
    }

    public void setcPromoDiv(String cPromoDiv) {
        this.cPromoDiv = cPromoDiv;
    }

    public String getcRecdStat() {
        return cRecdStat;
    }

    public void setcRecdStat(String cRecdStat) {
        this.cRecdStat = cRecdStat;
    }

    public String getdTimeStmp() {
        return dTimeStmp;
    }

    public void setdTimeStmp(String dTimeStmp) {
        this.dTimeStmp = dTimeStmp;
    }

    public String getdLstUpdte() {
        return dLstUpdte;
    }

    public void setdLstUpdte(String dLstUpdte) {
        this.dLstUpdte = dLstUpdte;
    }
}
