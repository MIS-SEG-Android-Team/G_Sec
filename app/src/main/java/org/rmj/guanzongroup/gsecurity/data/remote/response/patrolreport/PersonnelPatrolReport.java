package org.rmj.guanzongroup.gsecurity.data.remote.response.patrolreport;
import java.util.List;

public class PersonnelPatrolReport {
    private List<Patrol> patrol;
    private String dSchedule;

    public List<Patrol> getPatrol() { return patrol; }
    public void setPatrol(List<Patrol> value) { this.patrol = value; }

    public String getDSchedule() { return dSchedule; }
    public void setDSchedule(String value) { this.dSchedule = value; }
}

