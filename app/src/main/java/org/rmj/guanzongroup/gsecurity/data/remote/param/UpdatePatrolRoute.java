package org.rmj.guanzongroup.gsecurity.data.remote.param;

import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.RouteParams;

import java.util.List;

public class UpdatePatrolRoute {

    private String sSchedIDx;
    private String sAdminIDx;
    private List<RouteParams> sRoutexxx;

    public UpdatePatrolRoute() {

    }

    public String getsSchedIDx() {
        return sSchedIDx;
    }

    public void setsSchedIDx(String sSchedIDx) {
        this.sSchedIDx = sSchedIDx;
    }

    public String getsAdminIDx() {
        return sAdminIDx;
    }

    public void setsAdminIDx(String sAdminIDx) {
        this.sAdminIDx = sAdminIDx;
    }

    public List<RouteParams> getsRoutexxx() {
        return sRoutexxx;
    }

    public void setsRoutexxx(List<RouteParams> sRoutexxx) {
        this.sRoutexxx = sRoutexxx;
    }
}
