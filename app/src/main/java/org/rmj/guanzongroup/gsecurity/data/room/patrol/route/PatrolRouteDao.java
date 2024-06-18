package org.rmj.guanzongroup.gsecurity.data.room.patrol.route;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface PatrolRouteDao {

    @Upsert
    void save(List<PatrolRouteEntity> value);

    @Query("SELECT * FROM Patrol_Route WHERE cRecdStat == 1 ORDER BY nPatrolNo ASC")
    List<PatrolRouteEntity> getPatrolCheckPoints();
}
