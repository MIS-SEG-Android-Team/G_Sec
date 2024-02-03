package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class UserProfileRepository {

    private final ApiService apiService;
    private final DataStore dataStore;

    @Inject
    public UserProfileRepository(ApiService apiService, DataStore dataStore) {
        this.apiService = apiService;
        this.dataStore = dataStore;
    }

    public String getUserID() {
        return dataStore.getUserId();
    }

    public Observable<BaseResponse<Void>> logoutUser() {
        return apiService.logout();
    }

    public Boolean hasUserSession() {
        return !dataStore.getLogNumber().isEmpty();
    }

    public Boolean isAdmin() {
        return !dataStore.getClientId().isEmpty();
    }

    public String getSessionDateTime() {
        return dataStore.getSessionDateTime();
    }

    public void clearCache() {
        dataStore.clearDataStore();
    }
}
