package org.rmj.guanzongroup.gsecurity.data.remote.response.base.login;

import org.rmj.guanzongroup.gsecurity.data.remote.response.base.ErrorResponse;

public class LoginBaseResponse {
//    {"result":"error","error":{"code":40012,"message":"Invalid AUTH KEY detected"}}
    private String result;

    private String sClientID;
    private String sBranchCD;
    private String sBranchNm;
    private String sLogNoxxx;
    private String sUserIDxx;
    private String sEmailAdd;
    private String sUserName;
    private String nUserLevl;
    private String sDeptIDxx;
    private String sPositnID;
    private String sEmpLevID;
    private String sEmployID;
    private String cMainOffc;
    private String cSlfieLog;
    private String cAllowUpd;

    private ErrorResponse error;

    public LoginBaseResponse() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ErrorResponse getError() {
        return error;
    }

    public String getsClientID() {
        return sClientID;
    }

    public void setsClientID(String sClientID) {
        this.sClientID = sClientID;
    }

    public String getsBranchCD() {
        return sBranchCD;
    }

    public void setsBranchCD(String sBranchCD) {
        this.sBranchCD = sBranchCD;
    }

    public String getsBranchNm() {
        return sBranchNm;
    }

    public void setsBranchNm(String sBranchNm) {
        this.sBranchNm = sBranchNm;
    }

    public String getsLogNoxxx() {
        return sLogNoxxx;
    }

    public void setsLogNoxxx(String sLogNoxxx) {
        this.sLogNoxxx = sLogNoxxx;
    }

    public String getsUserIDxx() {
        return sUserIDxx;
    }

    public void setsUserIDxx(String sUserIDxx) {
        this.sUserIDxx = sUserIDxx;
    }

    public String getsEmailAdd() {
        return sEmailAdd;
    }

    public void setsEmailAdd(String sEmailAdd) {
        this.sEmailAdd = sEmailAdd;
    }

    public String getsUserName() {
        return sUserName;
    }

    public void setsUserName(String sUserName) {
        this.sUserName = sUserName;
    }

    public String getnUserLevl() {
        return nUserLevl;
    }

    public void setnUserLevl(String nUserLevl) {
        this.nUserLevl = nUserLevl;
    }

    public String getsDeptIDxx() {
        return sDeptIDxx;
    }

    public void setsDeptIDxx(String sDeptIDxx) {
        this.sDeptIDxx = sDeptIDxx;
    }

    public String getsPositnID() {
        return sPositnID;
    }

    public void setsPositnID(String sPositnID) {
        this.sPositnID = sPositnID;
    }

    public String getsEmpLevID() {
        return sEmpLevID;
    }

    public void setsEmpLevID(String sEmpLevID) {
        this.sEmpLevID = sEmpLevID;
    }

    public String getsEmployID() {
        return sEmployID;
    }

    public void setsEmployID(String sEmployID) {
        this.sEmployID = sEmployID;
    }

    public String getcMainOffc() {
        return cMainOffc;
    }

    public void setcMainOffc(String cMainOffc) {
        this.cMainOffc = cMainOffc;
    }

    public String getcSlfieLog() {
        return cSlfieLog;
    }

    public void setcSlfieLog(String cSlfieLog) {
        this.cSlfieLog = cSlfieLog;
    }

    public String getcAllowUpd() {
        return cAllowUpd;
    }

    public void setcAllowUpd(String cAllowUpd) {
        this.cAllowUpd = cAllowUpd;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

}
