package org.rmj.guanzongroup.gsecurity.data.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.RequestSiteVisitParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitDao;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class RequestVisitRepository {

    private final ApiService apiService;
    private final RequestVisitDao requestVisitDao;

    @Inject
    public RequestVisitRepository(
            ApiService apiService,
            RequestVisitDao requestVisitDao
    ) {
        this.apiService = apiService;
        this.requestVisitDao = requestVisitDao;
    }

    public void save(RequestVisitEntity value) {
        requestVisitDao.save(value);
    }

    public void update(RequestVisitEntity value) {
        requestVisitDao.update(value);
    }

    @SuppressLint("NewApi")
    public LiveData<RequestVisitDao.RequestSchedule> getRequestedVisit() {
        return requestVisitDao.getRequestedVisit(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public Observable<BaseResponse<Void>> sendVisitedNotification(RequestVisitEntity params) {
        return apiService.sendVisitedNotification(params);
    }

    public Observable<BaseResponse<Void>> sendVisitationRequest(RequestSiteVisitParams params) {
        return apiService.sendVisitationRequest(params);
    }

    public Observable<BaseResponse<RequestVisitEntity>> downloadVisitRequests(GetPatrolRouteParams params) {
        return apiService.downloadVisitRequests(params);
    }
}
