package org.rmj.guanzongroup.gsecurity.data.repository.authentication;

import io.reactivex.rxjava3.core.Observable;

import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import javax.inject.Inject;

public class LoginRepository {

    private ApiService apiService;

    @Inject
    public LoginRepository(
            ApiService apiService
    ) {
        this.apiService = apiService;
    }

    // Sign in user through mpin
    public Observable<BaseResponse> login(String mpin) {
        return apiService.signIn(mpin);
    }

    // Sign in user through mpin
    public Observable<BaseResponse> loginAdmin(LoginParams loginParams) {
        return apiService.loginAdmin(loginParams);
    }
}
