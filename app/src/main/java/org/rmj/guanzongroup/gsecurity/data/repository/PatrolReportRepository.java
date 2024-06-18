package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPersonnelPatrolReportParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.patrolreport.PersonnelPatrolReport;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PatrolReportRepository {

    private final ApiService apiService;

    @Inject
    public PatrolReportRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseResponse<List<PersonnelPatrolReport>>> getPersonnelPatrolReport(GetPersonnelPatrolReportParams params) {
        return apiService.getPersonnelPatrolReport(params);
    }
}
