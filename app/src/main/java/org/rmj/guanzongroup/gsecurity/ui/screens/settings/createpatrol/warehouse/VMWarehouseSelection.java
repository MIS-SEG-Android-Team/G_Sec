package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.warehouse;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.repository.PersonnelRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.WarehouseRepository;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;
import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMWarehouseSelection extends ViewModel {

    private final WarehouseRepository warehouseRepository;
    private final PersonnelRepository personnelRepository;
    private final PatrolSchedulerCache schedulerCache;

    private final MutableLiveData<Boolean> importingWarehouse = new MutableLiveData<>(false);

    @Inject
    public VMWarehouseSelection(
            WarehouseRepository warehouseRepository,
            PersonnelRepository personnelRepository,
            PatrolSchedulerCache schedulerCache
    ) {
        this.warehouseRepository = warehouseRepository;
        this.personnelRepository = personnelRepository;
        this.schedulerCache = schedulerCache;
        importWarehouse();
    }

    public void setWarehouse(String value) {
        schedulerCache.setWarehouseID(value);
    }

    public LiveData<Boolean> importingWarehouse() {
        return importingWarehouse;
    }

    public LiveData<List<WarehouseEntity>> getWarehouseList() {
        return warehouseRepository.getWarehouseList();
    }

    private void importWarehouse() {
        String timeStamp = warehouseRepository.getLatestTimeStamp();
        if(timeStamp == null || timeStamp.isEmpty()) {
            getWarehouses("");
        } else {
            getWarehouses(timeStamp);
        }
    }

    @SuppressLint("CheckResult")
    private void getWarehouses() {
//        importingWarehouse.setValue(true);
//        warehouseRepository.getWarehouse()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        baseResponse -> {
//                            importingWarehouse.setValue(false);
//
//                            if(baseResponse.getResult().equalsIgnoreCase("error")) {
//                                return;
//                            }
//
//                            List<WarehouseEntity> warehouseEntityList = baseResponse.getData();
//                            warehouseRepository.saveWarehouses(warehouseEntityList);
//                        },
//                        throwable -> {
//                            importingWarehouse.setValue(false);
//                        }
//                );
    }

    @SuppressLint("CheckResult")
    private void getWarehouses(String timeStamp) {
        importingWarehouse.setValue(true);
        GetWarehouseParams params = new GetWarehouseParams();
        params.setDTimeStmp(timeStamp);
        warehouseRepository.getWarehouse(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {
                            importingWarehouse.setValue(false);

                            if(baseResponse.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<WarehouseEntity> warehouseEntityList = baseResponse.getData();
                            warehouseRepository.saveWarehouses(warehouseEntityList);
                        },
                        throwable -> {
                            importingWarehouse.setValue(false);
                        }
                );
    }
}