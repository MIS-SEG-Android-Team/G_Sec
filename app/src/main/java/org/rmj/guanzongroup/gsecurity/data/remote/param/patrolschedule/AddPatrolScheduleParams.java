package org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule;

import java.util.List;

public class AddPatrolScheduleParams {

    private String sWHouseID;
    private List<ScheduleParams> sSchedule;
    private String sUserIDxx;
    private List<RouteParams> sRoutexxx;
    private String sNotesxxx;

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public List<ScheduleParams> getSSchedule() { return sSchedule; }
    public void setSSchedule(List<ScheduleParams> value) { this.sSchedule = value; }

    public String getSUserIDxx() { return sUserIDxx; }
    public void setSUserIDxx(String value) { this.sUserIDxx = value; }

    public List<RouteParams> getSRoutexxx() { return sRoutexxx; }
    public void setSRoutexxx(List<RouteParams> value) { this.sRoutexxx = value; }

    public String getSNotesxxx() { return sNotesxxx; }
    public void setSNotesxxx(String value) { this.sNotesxxx = value; }
}