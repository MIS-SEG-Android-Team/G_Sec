package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.repository.authentication.LoginRepository;
import org.rmj.guanzongroup.gsecurity.pojo.login.LoginCredentials;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMLogin extends ViewModel {

    private LoginRepository repository;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasLogin = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMLogin(LoginRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getHasLogin() {
        return hasLogin;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void login(LoginCredentials loginCredentials, LoginCallback callback){

        // TODO: This area of code has been temporarily disabled and might be deleted later on...
//        callback.onLogin(PLEASE_WAIT);
//
//        LoginCredentials.Validator validator = new LoginCredentials.Validator();
//
//        if(!validator.isDataValid(loginCredentials)){
//            callback.onFailed(validator.getMessage());
//            return;
//        }
//
//        if(loginCredentials.getEmail().equalsIgnoreCase("admin") &&
//            loginCredentials.getPassword().equalsIgnoreCase("admin")) {
//
//            callback.onAdminSuccessLogin("Login success");
//        } else if(loginCredentials.getEmail().equalsIgnoreCase("user") &&
//                loginCredentials.getPassword().equalsIgnoreCase("user")) {
//
//            callback.onPersonnelSuccessLogin("Login success");
//        } else {
//
//            callback.onFailed("Invalid email or password.");
//        }

    }

    public void loginPersonnel() {
        isLoading.setValue(true);

        LoginParams params = new LoginParams();
        params.setUsername("mikegarcia8748@gmail.com");
        params.setPassword("123456");
        repository.loginAdmin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {
                            isLoading.setValue(false);
                        },
                        throwable -> {
                            errorMessage.setValue(throwable.getMessage());
                            isLoading.setValue(false);
                        }
                );
    }
}