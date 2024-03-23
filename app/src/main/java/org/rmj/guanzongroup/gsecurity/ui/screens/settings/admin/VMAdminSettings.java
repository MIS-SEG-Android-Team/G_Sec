package org.rmj.guanzongroup.gsecurity.ui.screens.settings.admin;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.repository.UserProfileRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMAdminSettings extends ViewModel {

    private final UserProfileRepository userProfileRepository;

    private final MutableLiveData<Boolean> hasLogout = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loggingOut = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMAdminSettings(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
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
        loggingOut.setValue(true);
        userProfileRepository.logoutAdmin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {
                            loggingOut.setValue(false);
                            if (baseResponse.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(baseResponse.getError().getMessage());
                                return;
                            }
                            userProfileRepository.clearCache();
                            hasLogout.setValue(true);
                        },
                        error -> {
                            loggingOut.setValue(false);
                            errorMessage.setValue(error.getMessage());
                        }
                );
    }
}