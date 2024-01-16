package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.schedule.CreatedScheduleDao;
import org.rmj.guanzongroup.gsecurity.data.room.schedule.ScheduleEntity;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleRepository {

    private final ApiService apiService;
    private final CreatedScheduleDao createdScheduleDao;

    @Inject
    public ScheduleRepository(
            ApiService apiService,
            CreatedScheduleDao createdScheduleDao) {
        this.apiService = apiService;
        this.createdScheduleDao = createdScheduleDao;
    }

    public void createNew(ScheduleEntity value) {
        createdScheduleDao.createNew(value);
    }

    public LiveData<ScheduleEntity> getCreatedSchedule() {
        return createdScheduleDao.getCreatedSchedule();
    }

    public void updateSchedule(ScheduleEntity value) {
        createdScheduleDao.updateSchedule(value);
    }

    public void clearCache() {
        createdScheduleDao.clearCache();
    }

    public Observable<BaseResponse<Void>> addNewSchedule(ScheduleEntity value) {
        return apiService.addNewSchedule(value);
    }
}
