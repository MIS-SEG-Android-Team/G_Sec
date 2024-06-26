package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddPositionParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionDao;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PositionRepository {

    private final ApiService apiService;
    private final PositionDao positionDao;

    @Inject
    public PositionRepository(
            ApiService apiService,
            PositionDao positionDao
    ) {
        this.apiService = apiService;
        this.positionDao = positionDao;
    }

    public Observable<BaseResponse<Void>> addPosition(AddPositionParams params) {
        return apiService.addPosition(params);
    }

    public Observable<BaseResponse<List<PositionEntity>>> getPositions(DateTimeStampParams params) {
        return apiService.getPositions(params);
    }

    public void savePositions(List<PositionEntity> value)  {
        positionDao.savePositions(value);
    }

    public LiveData<List<PositionEntity>> getPositionFromCache() {
        return positionDao.getPositions();
    }

    public String getLatestTimeStamp() {
        return positionDao.getPositionLatestTimeStamp();
    }
}
