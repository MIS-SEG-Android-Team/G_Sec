package org.rmj.guanzongroup.gsecurity.data.room.branch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface BranchDao {

    @Upsert
    void saveBranchList(List<BranchEntity> value);

    @Update
    void updateBranch(BranchEntity value);

    @Query("SELECT dTimeStmp FROM Branch ORDER BY dTimeStmp DESC LIMIT 1")
    String getLatestTimeStamp();

    @Query("SELECT sBranchCd, sBranchNm FROM Branch WHERE cRecdStat == '1'")
    LiveData<List<BranchNameCode>> getBranchListForSelection();

    class BranchNameCode{
        public String sBranchCd;
        public String sBranchNm;
    }
}
