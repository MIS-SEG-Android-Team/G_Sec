package org.rmj.guanzongroup.gsecurity.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.guanzongroup.gsecurity.data.room.entities.ECategory;

import java.util.List;

@Dao
public interface DCategory {

    @Insert
    void save(ECategory category);

    @Update
    void update(ECategory category);

    @Query("SELECT * FROM NFC_Category")
    LiveData<List<ECategory>> getCategories();
}
