package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.warehouse;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.repository.WarehouseRepository;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMWarehouseSelection extends ViewModel {

    private final WarehouseRepository warehouseRepository;
    private final PatrolSchedulerCache patrolScheduleCache;

    private final MutableLiveData<Boolean> importingWarehouse = new MutableLiveData<>(false);

    @Inject
    public VMWarehouseSelection(
            WarehouseRepository warehouseRepository,
            PatrolSchedulerCache schedulerCache
    ) {
        this.warehouseRepository = warehouseRepository;
        this.patrolScheduleCache = schedulerCache;
        importWarehouse();
    }

    /**
     * This temporarily saves the information of warehouse to cache/preferences in order to handle
     * saveState.
     * @param value id/unique key of selected warehouse for creating patrol schedule...
     */
    public void setWarehouse(String value) {
        patrolScheduleCache.setWarehouseID(value);
    }

    public LiveData<Boolean> importingWarehouse() {
        return importingWarehouse;
    }

    public LiveData<List<WarehouseEntity>> getWarehouseList() {
        return warehouseRepository.getWarehouseList();
    }

    @SuppressLint("CheckResult")
    private void importWarehouse() {
        importingWarehouse.setValue(true);

        GetWarehouseParams params = new GetWarehouseParams();
        String timeStamp = warehouseRepository.getLatestTimeStamp();

        if(timeStamp != null) {
            params.setDTimeStmp(timeStamp);
        }

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