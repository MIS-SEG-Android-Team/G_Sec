package org.rmj.guanzongroup.gsecurity.data.remote.response.personnelpatrol;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolRoute;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolSchedule;

import java.util.List;

public class PersonnelPatrolModel {
    private String sNotexxxx;
    private String sWHouseID;
    private List<PersonnelPatrolSchedule> sSchedule;
    private String sSchedIDx;
    private List<PersonnelPatrolRoute> sRoutexxx;
    private String cRecdStat;

    public String getSNotexxxx() { return sNotexxxx; }
    public void setSNotexxxx(String value) { this.sNotexxxx = value; }

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public List<PersonnelPatrolSchedule> getSSchedule() { return sSchedule; }
    public void setSSchedule(List<PersonnelPatrolSchedule> value) { this.sSchedule = value; }

    public String getSSchedIDx() { return sSchedIDx; }
    public void setSSchedIDx(String value) { this.sSchedIDx = value; }

    public List<PersonnelPatrolRoute> getSRoutexxx() { return sRoutexxx; }
    public void setSRoutexxx(List<PersonnelPatrolRoute> value) { this.sRoutexxx = value; }

    public String getCRecdStat() { return cRecdStat; }
    public void setCRecdStat(String value) { this.cRecdStat = value; }
}
