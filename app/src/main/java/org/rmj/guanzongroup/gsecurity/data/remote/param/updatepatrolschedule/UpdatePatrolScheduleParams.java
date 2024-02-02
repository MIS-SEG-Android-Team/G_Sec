package org.rmj.guanzongroup.gsecurity.data.remote.param.updatepatrolschedule;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolSchedule;

import java.util.List;

public class UpdatePatrolScheduleParams {
    private List<PersonnelPatrolSchedule> sSchedule;
    private String sAdminIDx;
    private String sSchedIDx;

    public List<PersonnelPatrolSchedule> getSSchedule() { return sSchedule; }
    public void setSSchedule(List<PersonnelPatrolSchedule> value) { this.sSchedule = value; }

    public String getSAdminIDx() { return sAdminIDx; }
    public void setSAdminIDx(String value) { this.sAdminIDx = value; }

    public String getSSchedIDx() { return sSchedIDx; }
    public void setSSchedIDx(String value) { this.sSchedIDx = value; }
}

