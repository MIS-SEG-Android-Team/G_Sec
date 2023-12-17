package org.rmj.guanzongroup.gsecurity.data.room.branch;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity (tableName = "Branch")
public class BranchEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sBranchCd")
    protected String sBranchCd;
    @ColumnInfo(name = "sBranchNm")
    protected String sBranchNm;
    @ColumnInfo(name = "sAddressx")
    protected String sAddressx;
    @ColumnInfo(name = "sTownIDxx")
    protected String sTownIDxx;
    @ColumnInfo(name = "dTimeStmp")
    protected String dTimeStmp;
    @ColumnInfo(name = "sDescript")
    protected String sDescript;
    @ColumnInfo(name = "sAreaCode")
    protected String sAreaCode;
    @ColumnInfo(name = "cPromoDiv")
    protected String cPromoDiv;
    @ColumnInfo(name = "cDivision")
    protected String cDivision;
    @ColumnInfo(name = "cRecdStat")
    protected String cRecdStat;

    public BranchEntity() {
    }

    @NonNull
    public String getsBranchCd() {
        return sBranchCd;
    }

    public void setsBranchCd(@NonNull String sBranchCd) {
        this.sBranchCd = sBranchCd;
    }

    public String getSBranchNm() { return sBranchNm; }
    public void setSBranchNm(String value) { this.sBranchNm = value; }

    public String getSAddressx() { return sAddressx; }
    public void setSAddressx(String value) { this.sAddressx = value; }

    public String getSTownIDxx() { return sTownIDxx; }
    public void setSTownIDxx(String value) { this.sTownIDxx = value; }

    public String getDTimeStmp() { return dTimeStmp; }
    public void setDTimeStmp(String value) { this.dTimeStmp = value; }

    public String getSDescript() { return sDescript; }
    public void setSDescript(String value) { this.sDescript = value; }

    public String getSAreaCode() { return sAreaCode; }
    public void setSAreaCode(String value) { this.sAreaCode = value; }

    public String getCPromoDiv() { return cPromoDiv; }
    public void setCPromoDiv(String value) { this.cPromoDiv = value; }

    public String getCDivision() { return cDivision; }
    public void setCDivision(String value) { this.cDivision = value; }

    public String getCRecdStat() { return cRecdStat; }
    public void setCRecdStat(String value) { this.cRecdStat = value; }
}
