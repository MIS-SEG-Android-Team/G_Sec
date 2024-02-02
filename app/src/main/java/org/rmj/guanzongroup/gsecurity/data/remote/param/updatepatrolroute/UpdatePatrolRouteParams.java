package org.rmj.guanzongroup.gsecurity.data.remote.param.updatepatrolroute;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolRoute;

import java.util.List;

public class UpdatePatrolRouteParams {
    private String sAdminIDx;
    private String sSchedIDx;
    private List<PersonnelPatrolRoute> sRoutexxx;

    public String getSAdminIDx() { return sAdminIDx; }
    public void setSAdminIDx(String value) { this.sAdminIDx = value; }

    public String getSSchedIDx() { return sSchedIDx; }
    public void setSSchedIDx(String value) { this.sSchedIDx = value; }

    public List<PersonnelPatrolRoute> getSRoutexxx() { return sRoutexxx; }
    public void setSRoutexxx(List<PersonnelPatrolRoute> value) { this.sRoutexxx = value; }
}

