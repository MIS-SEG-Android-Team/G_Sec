package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.warehouse;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.WarehouseRepository;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMWarehouseSelection extends ViewModel {

    private final ScheduleRepository scheduleRepository;
    private final WarehouseRepository warehouseRepository;

    private final MutableLiveData<Boolean> importingWarehouse = new MutableLiveData<>(false);

    private final MutableLiveData<CreateScheduleParams> schedule = new MutableLiveData<>(new CreateScheduleParams());

    @Inject
    public VMWarehouseSelection(
            ScheduleRepository scheduleRepository,
            WarehouseRepository warehouseRepository) {
        this.scheduleRepository = scheduleRepository;
        this.warehouseRepository = warehouseRepository;

        // Clear any remaining cache first before starting to create new schedule...
        scheduleRepository.clearCache();
        importWarehouse();
    }

    /**
     * This temporarily saves the information of warehouse to cache/preferences in order to handle
     * saveState.
     * @param warehouseID id/unique key of selected warehouse for creating patrol schedule...
     */
    public void setWarehouse(String warehouseID, String warehouseName) {
        Objects.requireNonNull(schedule.getValue()).setSWHouseID(warehouseID);
        Objects.requireNonNull(schedule.getValue()).setSWHouseNm(warehouseName);
        scheduleRepository.createNewPatrolScheduleToCache(schedule.getValue());
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

        DateTimeStampParams params = new DateTimeStampParams();
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