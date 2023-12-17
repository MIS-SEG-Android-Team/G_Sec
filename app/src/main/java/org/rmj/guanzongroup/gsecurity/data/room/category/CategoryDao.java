package org.rmj.guanzongroup.gsecurity.data.room.category;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface CategoryDao {

    @Upsert
    void saveCategories(List<CategoryEntity> value);

    @Query("SELECT dTimeStmp FROM Category ORDER BY dTimeStmp DESC LIMIT 1")
    String getLatestTimeStamp();
}
