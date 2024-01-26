package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;
import org.rmj.guanzongroup.gsecurity.data.remote.param.UpdatePatrolPersonnel;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateUpdateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleRepository {

    private final ApiService apiService;
    private final PatrolSchedulerCache patrolSchedulerCache;
    private final PatrolScheduleDao patrolScheduleDao;

    @Inject
    public ScheduleRepository(
            ApiService apiService,
            PatrolSchedulerCache patrolSchedulerCache,
            PatrolScheduleDao patrolScheduleDao
    ) {
        this.apiService = apiService;
        this.patrolSchedulerCache = patrolSchedulerCache;
        this.patrolScheduleDao = patrolScheduleDao;
    }

    public Observable<BaseResponse<Void>> addNewSchedule(CreateUpdateScheduleParams params) {
        return apiService.addNewSchedule(params);
    }

    public Observable<BaseResponse<Void>> updateSchedule(CreateUpdateScheduleParams params) {
        return apiService.updateSchedule(params);
    }

    public Observable<BaseResponse<Void>> updatePatrolRoute(CreateUpdateScheduleParams params) {
        return apiService.updatePatrolRoute(params);
    }

    public Observable<BaseResponse<Void>> updatePatrolPersonnel(UpdatePatrolPersonnel params) {
        return apiService.updatePatrolPersonnel(params);
    }

    public void createNewPatrolScheduleToCache(CreateUpdateScheduleParams value) {
        patrolSchedulerCache.setPatrolSchedule(value);
    }

    public CreateUpdateScheduleParams getPatrolScheduleFromCache() {
        return patrolSchedulerCache.getPatrolSchedule();
    }

    public void updatePatrolScheduleToCache(CreateUpdateScheduleParams value) {
        patrolSchedulerCache.setPatrolSchedule(value);
    }

    public void clearCache() {
        patrolSchedulerCache.clearCache();
    }

    public void savePatrolSchedule(List<PatrolScheduleEntity> value) {
        patrolScheduleDao.save(value);
    }

    public LiveData<List<PatrolScheduleEntity>> getPatrolSchedules() {
        return patrolScheduleDao.getPatrolSchedules();
    }
}
