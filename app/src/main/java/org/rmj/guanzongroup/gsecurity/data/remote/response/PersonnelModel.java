package org.rmj.guanzongroup.gsecurity.data.remote.response;

import java.time.OffsetDateTime;

public class PersonnelModel {
    private String nUserLvel;
    private String dTimeStmp;
    private String sPositnID;
    private String sUserIDxx;
    private String sUserName;
    private String nPINCodex;

    public String getNUserLvel() { return nUserLvel; }
    public void setNUserLvel(String value) { this.nUserLvel = value; }

    public String getDTimeStmp() { return dTimeStmp; }
    public void setDTimeStmp(String value) { this.dTimeStmp = value; }

    public String getSPositnID() { return sPositnID; }
    public void setSPositnID(String value) { this.sPositnID = value; }

    public String getSUserIDxx() { return sUserIDxx; }
    public void setSUserIDxx(String value) { this.sUserIDxx = value; }

    public String getSUserName() { return sUserName; }
    public void setSUserName(String value) { this.sUserName = value; }

    public String getNPINCodex() { return nPINCodex; }
    public void setNPINCodex(String value) { this.nPINCodex = value; }
}
