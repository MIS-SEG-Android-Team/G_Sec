package org.rmj.guanzongroup.gsecurity.data.repository.personel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.PersonnelParam;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
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

    public Observable<BaseResponse<List<PersonnelModel>>> getPersonnels() {
        return apiService.getPersonnels();
    }

    public Observable<BaseResponse<Void>> addPersonnel(PersonnelParam params) {
        return apiService.addPersonnel(params);
    }


}
