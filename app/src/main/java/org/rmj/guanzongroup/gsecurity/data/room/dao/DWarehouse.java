package org.rmj.guanzongroup.gsecurity.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.guanzongroup.gsecurity.data.room.entities.EWarehouse;

import java.util.List;

@Dao
public interface DWarehouse {

    @Insert
    void insert(EWarehouse warehouse);

    @Update
    void update(EWarehouse warehouse);

    @Query("SELECT * FROM Warehouse")
    LiveData<List<EWarehouse>> getWarehouses();
}
