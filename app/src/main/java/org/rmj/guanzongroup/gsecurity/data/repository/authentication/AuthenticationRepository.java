package org.rmj.guanzongroup.gsecurity.data.repository.authentication;

import io.reactivex.rxjava3.core.Observable;

import org.rmj.guanzongroup.gsecurity.config.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PINParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.authentication.LoginBaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import javax.inject.Inject;

public class AuthenticationRepository {

    private final ApiService apiService;
    private final DataStore dataStore;

    @Inject
    public AuthenticationRepository(
            ApiService apiService,
            DataStore dataStore
    ) {
        this.apiService = apiService;
        this.dataStore = dataStore;
    }

    /**
     * Sign in an admin account.
     * The endpoint of this API is still the old API use for Guanzon Circle and Guanzon Connect.
     * Response has old JSON structure and different from most APIs use in this application...
     * @param loginParams => use the LoginParams object to pass the user and password.
     * @return LoginBaseResponse => is a non generic response use for the app.
     */
    public Observable<LoginBaseResponse> loginAdmin(LoginParams loginParams) {
        return apiService.loginAdmin(loginParams);
    }

    public Observable<LoginBaseResponse> loginPersonnel(PINParams mpin) {
        return apiService.loginPersonnel(mpin);
    }

    public Observable<BaseResponse<Void>> logoutUser() {
        return apiService.logout();
    }

    public Boolean hasUserSession() {
        return !dataStore.getLogNumber().isEmpty();
    }
}
