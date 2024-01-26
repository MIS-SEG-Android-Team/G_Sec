package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitDao;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitEntity;

import javax.inject.Inject;

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

    public LiveData<RequestVisitEntity> getRequestedVisit() {
        return requestVisitDao.getRequestedVisit();
    }
}