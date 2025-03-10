package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.user_log.EUserLog;
import org.rmj.guanzongroup.gsecurity.data.room.user_log.UserLogDao;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class UserProfileRepository {

    private final ApiService apiService;
    private final DataStore dataStore;
    private final UserLogDao userLogDao;

    @Inject
    public UserProfileRepository(ApiService apiService, DataStore dataStore, UserLogDao userLogDao) {
        this.apiService = apiService;
        this.dataStore = dataStore;
        this.userLogDao = userLogDao;
    }

    public String getUserID() {
        return dataStore.getUserId();
    }

    public void saveLog(EUserLog userLog){
        userLogDao.saveUserLog(userLog);
    }

    public void clearUserLog(){
        userLogDao.clearUserLog();
    }

    public LiveData<String> getLastLog(){
        return userLogDao.getLastLog(dataStore.getUserId());
    }

    public Observable<BaseResponse<Void>> logoutUser() {
        return apiService.logout();
    }

    public Observable<BaseResponse<Void>> logoutAdmin() {
        return apiService.logoutAdmin();
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
