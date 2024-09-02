package org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;
import java.util.List;

@Dao
public interface PatrolScheduleDao {

    @Upsert
    void save(List<PatrolScheduleEntity> value);

    @Query("UPDATE Patrol_Schedule SET cRequestxx = '2' WHERE dTimexxxx <= :schedule AND cRequestxx = '1' " +
            "AND schedIDxx = :schedIDxx")
    void updateCRequest(String schedule, String schedIDxx);

    @Query("SELECT * FROM Patrol_Schedule WHERE cRequestxx IN ('0', '1') ORDER BY dTimexxxx ASC")
    List<PatrolScheduleEntity> getPatrolScheduleList();

    @Query("SELECT dTimexxxx FROM Patrol_Schedule WHERE dTimexxxx < :schedule ORDER BY dTimexxxx DESC LIMIT 1")
    String getRecentSchedule(String schedule);

    @Query("SELECT cRequestxx FROM Patrol_Schedule WHERE dTimexxxx = :schedule")
    String getCRequest(String schedule);

}
