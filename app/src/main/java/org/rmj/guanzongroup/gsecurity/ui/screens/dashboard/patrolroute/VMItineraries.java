package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;
import org.rmj.guanzongroup.gsecurity.mockdata.ListPatrolRoute;
import org.rmj.guanzongroup.gsecurity.pojo.itinerary.PatrolRoute;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMItineraries extends ViewModel {

    private final AuthenticationRepository authenticationRepository;

    private final MutableLiveData<Boolean> hasLogout = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loggingOut = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMItineraries(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public LiveData<List<PatrolRoute>> getItineraryList() {
        return ListPatrolRoute.getPatrolRoute();
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
        authenticationRepository.logoutUser()
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