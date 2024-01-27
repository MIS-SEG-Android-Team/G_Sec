package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddPersonnelParam;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.ActivePersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.AddPersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PersonnelRepository {

    private final ApiService apiService;

    @Inject
    public PersonnelRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseResponse<List<PersonnelModel>>> getPersonnels(DateTimeStampParams params) {
        return apiService.getPersonnels(params);
    }

    public Observable<BaseResponse<AddPersonnelModel>> addPersonnel(AddPersonnelParam params) {
        return apiService.addPersonnel(params);
    }

    public Observable<BaseResponse<List<ActivePersonnelModel>>> getActivePersonnels(DateTimeStampParams value){
        return apiService.getActivePersonnels(value);
    }

}
