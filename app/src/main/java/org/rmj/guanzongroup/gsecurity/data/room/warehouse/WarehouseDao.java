package org.rmj.guanzongroup.gsecurity.data.room.warehouse;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WarehouseDao {

    @Insert
    void insert(WarehouseEntity warehouse);

    @Update
    void update(WarehouseEntity warehouse);

    @Query("SELECT * FROM Warehouse")
    LiveData<List<WarehouseEntity>> getWarehouses();
}
