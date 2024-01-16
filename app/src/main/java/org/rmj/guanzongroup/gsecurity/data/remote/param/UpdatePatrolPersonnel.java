package org.rmj.guanzongroup.gsecurity.data.remote.param;

public class UpdatePatrolPersonnel {

    private String sSchedIDx;
    private String sPersonnl;
    private String sAdminIDx;

    public UpdatePatrolPersonnel() {

    }

    public String getsSchedIDx() {
        return sSchedIDx;
    }

    public void setsSchedIDx(String sSchedIDx) {
        this.sSchedIDx = sSchedIDx;
    }

    public String getsPersonnl() {
        return sPersonnl;
    }

    public void setsPersonnl(String sPersonnl) {
        this.sPersonnl = sPersonnl;
    }

    public String getsAdminIDx() {
        return sAdminIDx;
    }

    public void setsAdminIDx(String sAdminIDx) {
        this.sAdminIDx = sAdminIDx;
    }
}
