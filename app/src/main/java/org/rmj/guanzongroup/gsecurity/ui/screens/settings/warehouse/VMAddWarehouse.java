package org.rmj.guanzongroup.gsecurity.ui.screens.settings.warehouse;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetBranchParams;
import org.rmj.guanzongroup.gsecurity.data.repository.BranchRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.WarehouseRepository;
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
    private final WarehouseRepository warehouseRepository;

    private final MutableLiveData<Boolean> hasCompleteInfo = new MutableLiveData<>(false);
    private final MutableLiveData<String> branchName = new MutableLiveData<>("");
    private final MutableLiveData<String> branchCode = new MutableLiveData<>("");
    private final MutableLiveData<String> warehouseName = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> savingWarehouse = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> successfullySave = new MutableLiveData<>(false);

    @Inject
    public VMAddWarehouse(
            BranchRepository branchRepository,
            WarehouseRepository warehouseRepository
    ) {
        this.branchRepository = branchRepository;
        this.warehouseRepository = warehouseRepository;
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
        hasCompleteInfo.setValue(!value.trim().isEmpty() && !warehouseName.getValue().trim().isEmpty());
    }
    public void setWarehouseName(String value) {
        warehouseName.setValue(value);
        hasCompleteInfo.setValue(!value.trim().isEmpty() && !branchCode.getValue().trim().isEmpty());
    }

    public LiveData<List<BranchDao.BranchNameCode>> getBranchList() {
        return branchRepository.getBranchListForSelection();
    }

    public LiveData<Boolean> savingWarehouse() {
        return savingWarehouse;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<Boolean> successfullySave() {
        return successfullySave;
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
                            if(baseResponse.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<BranchEntity> branchList = baseResponse.getDetail();
                            branchRepository.saveBranchList(branchList);
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void saveWarehouse() {
        savingWarehouse.setValue(true);
        AddWarehouseParams params = new AddWarehouseParams();
        params.setSBranchCd(branchCode.getValue());
        params.setCRecdStat("1");
        params.setSWHouseNm(warehouseName.getValue());

        warehouseRepository.addWarehouse(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {
                            savingWarehouse.setValue(false);
                            if(baseResponse.getResult().equalsIgnoreCase("error")) {

                                errorMessage.setValue(baseResponse.getError().getMessage());
                                return;
                            }

                            successfullySave.setValue(true);
                        },
                        throwable -> {
                            savingWarehouse.setValue(false);
                            errorMessage.setValue(throwable.getMessage());
                        }
                );
    }
}