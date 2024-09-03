package org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Upsert;
import androidx.sqlite.db.SimpleSQLiteQuery;
import java.util.List;

@Dao
public interface PatrolScheduleDao {

    @RawQuery
    Object executeRawQueryt(SimpleSQLiteQuery query);

    @Upsert
    void save(List<PatrolScheduleEntity> value);

    @Query("SELECT * FROM Patrol_Schedule WHERE cRequestxx IN ('0', '1') ORDER BY dTimexxxx ASC")
    List<PatrolScheduleEntity> getPatrolScheduleList();

    @Query("SELECT cRequestxx FROM Patrol_Schedule WHERE dTimexxxx = :schedule")
    String getCRequest(String schedule);

}
