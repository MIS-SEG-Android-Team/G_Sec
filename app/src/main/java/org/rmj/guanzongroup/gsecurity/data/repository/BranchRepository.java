package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetBranchParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.branch.BranchResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchDao;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class BranchRepository {

    private final ApiService apiService;
    private final BranchDao branchDao;

    @Inject
    public BranchRepository(
            ApiService apiService,
            BranchDao branchDao
    ) {
        this.apiService = apiService;
        this.branchDao = branchDao;
    }

    public Observable<BranchResponse<List<BranchEntity>>> getBranches(GetBranchParams params) {
        return apiService.getBranches(params);
    }

    public void saveBranchList(List<BranchEntity> value) {
        branchDao.saveBranchList(value);
    }

    public void updateBranch(BranchEntity value) {
        branchDao.updateBranch(value);
    }

    public String getLatestTimeStamp() {
        return branchDao.getLatestTimeStamp();
    }

    public LiveData<List<BranchDao.BranchNameCode>>  getBranchListForSelection() {
        return branchDao.getBranchListForSelection();
    }
}
