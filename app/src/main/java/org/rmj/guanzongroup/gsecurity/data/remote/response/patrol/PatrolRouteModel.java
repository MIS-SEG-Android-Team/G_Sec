package org.rmj.guanzongroup.gsecurity.data.remote.response.patrol;

import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;

import java.util.List;

public class PatrolRouteModel {
    private String sNotexxxx;
    private List<PatrolScheduleEntity> sSchedule;
    private List<PatrolRouteEntity> sRoutexxx;
    private String cRecdStat;

    public String getSNotexxxx() { return sNotexxxx; }
    public void setSNotexxxx(String value) { this.sNotexxxx = value; }

    public List<PatrolScheduleEntity> getSSchedule() { return sSchedule; }
    public void setSSchedule(List<PatrolScheduleEntity> value) { this.sSchedule = value; }

    public List<PatrolRouteEntity> getSRoutexxx() { return sRoutexxx; }
    public void setSRoutexxx(List<PatrolRouteEntity> value) { this.sRoutexxx = value; }

    public String getCRecdStat() { return cRecdStat; }
    public void setCRecdStat(String value) { this.cRecdStat = value; }
}
