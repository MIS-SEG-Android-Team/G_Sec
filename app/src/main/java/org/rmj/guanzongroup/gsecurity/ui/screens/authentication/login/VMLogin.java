package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PINParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.authentication.LoginBaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor.AuthorizedInterceptor;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMLogin extends ViewModel {

    private final AuthenticationRepository repository;
    private final DataStore dataStore;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> adminHasLogin = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();


    // Observables for handling UI credentials for admin...
    private final MutableLiveData<String> email = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> hasCredentials = new MutableLiveData<>(false);

    // Observables for handling UI PIN for Officer...
    private final MutableLiveData<String> mpin = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> hasOfficerLogin = new MutableLiveData<>(false);

    @Inject
    public VMLogin(
            AuthenticationRepository repository,
            DataStore dataStore
    ) {
        this.repository = repository;
        this.dataStore = dataStore;

    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> adminHasLoggedIn() {
        return adminHasLogin;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setEmail(String value) {
        hasCredentials.setValue(!value.trim().isEmpty() && !Objects.requireNonNull(password.getValue()).trim().isEmpty());
        this.email.setValue(value);
    }

    public void setPassword(String value) {
        hasCredentials.setValue(!value.trim().isEmpty() && !Objects.requireNonNull(email.getValue()).trim().isEmpty());
        this.password.setValue(value);
    }

    public void setPIN(String value) {
        mpin.setValue(value);
    }
    public LiveData<Boolean> hasCredentials() {
        return hasCredentials;
    }

    public LiveData<String> getPIN() {
        return mpin;
    }

    public LiveData<Boolean> hasOfficerLogin() {
        return hasOfficerLogin;
    }

    @SuppressLint("CheckResult")
    public void loginPersonnel() {

        // Display loading dialog on UI...
        isLoading.setValue(true);
        PINParams param = new PINParams();
        param.setMpin(mpin.getValue());
        repository.loginPersonnel(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {

                            // API Response validation
                            // Any API response with HTTP Error Code 200 is handled in this area...
                            if(baseResponse.getResult().equalsIgnoreCase("error")) {

                                isLoading.setValue(false);
                                errorMessage.setValue(baseResponse.getError().getMessage());
                                return;
                            }

                            // saving of user information to DataStore/SharePreferences...
                            initDataStore(baseResponse);

                            isLoading.setValue(false);
                            hasOfficerLogin.setValue(true);
                        },

                        // Exception handling...
                        throwable -> {
                            errorMessage.setValue(throwable.getMessage());
                            isLoading.setValue(false);
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void loginAdmin(
            String user,
            String password
    ) {

        // Display loading dialog on UI...
        isLoading.setValue(true);

        LoginParams params = new LoginParams();
        params.setUsername(user);
        params.setPassword(password);
        repository.loginAdmin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {

                            // API Response validation
                            // Any API response with HTTP Error Code 200 is handled in this area...
                            if(baseResponse.getResult().equalsIgnoreCase("error")) {

                                isLoading.setValue(false);
                                errorMessage.setValue(baseResponse.getError().getMessage());
                                return;
                            }

                            // saving of user information to DataStore/SharePreferences...
                            initDataStore(baseResponse);

                            isLoading.setValue(false);
                            adminHasLogin.setValue(true);
                        },

                        // Exception handling...
                        throwable -> {
                            errorMessage.setValue(throwable.getMessage());
                            isLoading.setValue(false);
                        }
                );
    }

    private void initDataStore(LoginBaseResponse baseResponse) {
        dataStore.setClientId(baseResponse.getsClientID());
        dataStore.setBranchCode(baseResponse.getsBranchCD());
        dataStore.setBranchName(baseResponse.getsBranchNm());
        dataStore.setLogNumber(baseResponse.getsLogNoxxx());
        dataStore.setUserId(baseResponse.getsUserIDxx());
        dataStore.setEmailAdd(baseResponse.getsEmailAdd());
        dataStore.setUserName(baseResponse.getsUserName());
        dataStore.setUserLevel(baseResponse.getnUserLevl());
        dataStore.setDepartmentId(baseResponse.getsDeptIDxx());
        dataStore.setPositionId(baseResponse.getsPositnID());
        dataStore.setEmployeeLevel(baseResponse.getsEmpLevID());
        dataStore.setEmployeeId(baseResponse.getsEmployID());
        dataStore.setMainOffice(baseResponse.getcMainOffc());
        dataStore.setSelfieLogAllowed(baseResponse.getcSlfieLog());
        dataStore.setAllowedUpdate(baseResponse.getcAllowUpd());
    }
}