package org.rmj.guanzongroup.gsecurity.data.remote.param;

import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.ScheduleParams;

import java.util.List;

public class UpdatePatrolSchedule {

    private String sSchedIDx;
    private String sAdminIDx;
    private List<ScheduleParams> sSchedule;

    public UpdatePatrolSchedule() {

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

    public List<ScheduleParams> getsSchedule() {
        return sSchedule;
    }

    public void setsSchedule(List<ScheduleParams> sSchedule) {
        this.sSchedule = sSchedule;
    }
}
