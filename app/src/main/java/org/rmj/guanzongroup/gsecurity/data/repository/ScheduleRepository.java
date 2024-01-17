package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.remote.param.UpdatePatrolPersonnel;
import org.rmj.guanzongroup.gsecurity.data.remote.param.UpdatePatrolRoute;
import org.rmj.guanzongroup.gsecurity.data.remote.param.UpdatePatrolSchedule;
import androidx.lifecycle.LiveData;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;

public class ScheduleRepository {

    private final ApiService apiService;

    @Inject
    public ScheduleRepository(
            ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseResponse<Void>> updateSchedule(@Body UpdatePatrolSchedule params) {
        return apiService.updateSchedule(params);
    }
    public Observable<BaseResponse<Void>> updatePatrolRoute(@Body UpdatePatrolRoute params) {
        return apiService.updatePatrolRoute(params);
    }
    public Observable<BaseResponse<Void>> updatePatrolPersonnel(@Body UpdatePatrolPersonnel params) {
        return apiService.updatePatrolPersonnel(params);
    }

}
