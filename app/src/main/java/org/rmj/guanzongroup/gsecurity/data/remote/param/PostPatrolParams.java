package org.rmj.guanzongroup.gsecurity.data.remote.param;

import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogEntity;

import java.util.List;

public class PostPatrolParams {
    private List<PatrolLogEntity> data;

    public List<PatrolLogEntity> getData() { return data; }
    public void setData(List<PatrolLogEntity> value) { this.data = value; }
}
