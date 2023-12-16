package org.rmj.guanzongroup.gsecurity.data.room.position;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface PositionDao {

    @Upsert
    void savePositions(List<PositionEntity> value);

    @Query("SELECT * FROM Position")
    LiveData<List<PositionEntity>> getPositions();
}
