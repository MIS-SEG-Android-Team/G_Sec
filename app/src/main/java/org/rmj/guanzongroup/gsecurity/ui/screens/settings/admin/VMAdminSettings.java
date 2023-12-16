package org.rmj.guanzongroup.gsecurity.ui.screens.settings.admin;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMAdminSettings extends ViewModel {

    private final AuthenticationRepository repository;

    private final MutableLiveData<Boolean> hasLogout = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loggingOut = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMAdminSettings(AuthenticationRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> hasLogout() {
        return hasLogout;
    }

    public LiveData<Boolean> isLoggingOut() {
        return loggingOut;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @SuppressLint("CheckResult")
    public void logoutUser() {
        repository.logoutUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {

                            if (baseResponse.getResult().equalsIgnoreCase("error")) {

                                loggingOut.setValue(false);
                                errorMessage.setValue(baseResponse.getError().getMessage());
                                return;
                            }

                            loggingOut.setValue(false);

                        }
                );
    }
}