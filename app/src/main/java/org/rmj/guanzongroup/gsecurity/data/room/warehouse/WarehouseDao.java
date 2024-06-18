package org.rmj.guanzongroup.gsecurity.data.room.warehouse;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface WarehouseDao {

    @Upsert
    void save(List<WarehouseEntity> warehouse);

    @Query("SELECT * FROM Warehouse")
    LiveData<List<WarehouseEntity>> getWarehouses();

    @Query("SELECT dTimeStmp FROM Warehouse ORDER BY dTimeStmp DESC LIMIT 1")
    String getLatestTimeStamp();
}
