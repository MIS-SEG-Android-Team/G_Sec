package org.rmj.guanzongroup.gsecurity.ui.screens.settings.warehouse;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetBranchParams;
import org.rmj.guanzongroup.gsecurity.data.repository.BranchRepository;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchDao;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMAddWarehouse extends ViewModel {

    private final BranchRepository branchRepository;

    private final MutableLiveData<Boolean> hasCompleteInfo = new MutableLiveData<>(false);
    private final MutableLiveData<String> branchName = new MutableLiveData<>("");
    private final MutableLiveData<String> branchCode = new MutableLiveData<>("");
    private final MutableLiveData<String> warehouseName = new MutableLiveData<>("");

    @Inject
    public VMAddWarehouse(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
        initializeBranches();
    }

    public LiveData<Boolean> hasCompleteInfo() {
        return hasCompleteInfo;
    }
    public LiveData<String> getbranchName() {
        return branchName;
    }

    public void setBranchName(String value) {
        branchName.setValue(value);
    }
    public void setBranchCode(String value) {
        branchCode.setValue(value);
    }
    public void setWarehouseName(String value) {
        warehouseName.setValue(value);
    }

    public LiveData<List<BranchDao.BranchNameCode>> getBranchList() {
        return branchRepository.getBranchListForSelection();
    }

    @SuppressLint("CheckResult")
    public void initializeBranches() {
        GetBranchParams params = new GetBranchParams();
        params.setDescript("All");
        params.setBsearch(true);
        String timeStamp = branchRepository.getLatestTimeStamp();
        if(timeStamp != null) {
            params.setTimestamp(timeStamp);
        }
        branchRepository.getBranches(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {
                            List<BranchEntity> branchList = baseResponse.getDetail();
                            branchRepository.saveBranchList(branchList);
                        },
                        throwable -> {

                        }
                );
    }

    public void saveWarehouse() {
        AddWarehouseParams params = new AddWarehouseParams();
        params.setSBranchCd(branchCode.getValue());
        params.setCRecdStat("1");
        params.setSWHouseNm(warehouseName.getValue());

    }
}