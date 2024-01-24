package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;
import org.rmj.guanzongroup.gsecurity.data.remote.param.UpdatePatrolPersonnel;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateUpdateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleRepository {

    private final ApiService apiService;
    private final PatrolSchedulerCache patrolSchedulerCache;

    @Inject
    public ScheduleRepository(
            ApiService apiService,
            PatrolSchedulerCache patrolSchedulerCache) {
        this.apiService = apiService;
        this.patrolSchedulerCache = patrolSchedulerCache;
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
}
