package org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Upsert;
import androidx.sqlite.db.SimpleSQLiteQuery;

import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogEntity;

import java.util.List;

@Dao
public interface PatrolScheduleDao {

    @Upsert
    void save(List<PatrolScheduleEntity> value);

    @Query("SELECT * FROM Patrol_Schedule ORDER BY dTimexxxx DESC")
    LiveData<List<PatrolScheduleEntity>> getPatrolSchedules();

    @Query("SELECT * FROM Patrol_Schedule ORDER BY nSchedule ASC")
    List<PatrolScheduleEntity> getPatrolScheduleList();

}
