package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseDao;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class WarehouseRepository {

    private final ApiService apiService;
    private final WarehouseDao warehouseDao;

    @Inject
    public WarehouseRepository(
            ApiService apiService,
           WarehouseDao warehouseDao
    ) {
        this.apiService = apiService;
        this.warehouseDao = warehouseDao;
    }

    public Observable<BaseResponse<Void>> addWarehouse(AddWarehouseParams params) {
        return apiService.addWarehouse(params);
    }
}
