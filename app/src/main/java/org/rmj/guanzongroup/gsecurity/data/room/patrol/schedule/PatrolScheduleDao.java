package org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;
import java.util.List;

@Dao
public interface PatrolScheduleDao {

    @Upsert
    void save(List<PatrolScheduleEntity> value);

    @Query("SELECT * FROM Patrol_Schedule ORDER BY nSchedule ASC")
    List<PatrolScheduleEntity> getPatrolScheduleList();

}
