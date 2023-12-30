package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddNfcTagParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceDao;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class CheckpointRepository {

    private final ApiService apiService;
    private final NFCDeviceDao nfcDeviceDao;

    @Inject
    public CheckpointRepository(
            ApiService apiService,
            NFCDeviceDao nfcDeviceDao
    ) {
        this.apiService = apiService;
        this.nfcDeviceDao = nfcDeviceDao;
    }

    public Observable<BaseResponse<Void>> addNFCTag(AddNfcTagParams params){
        return apiService.addNFCTag(params);
    }


}
