package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolRepository;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMItineraries extends ViewModel {

    private final DataStore dataStore;
    private final AuthenticationRepository authenticationRepository;
    private final PatrolRepository patrolRepository;

    private final MutableLiveData<Boolean> isLoadingPatrolRoutes = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> hasLogout = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loggingOut = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMItineraries(DataStore dataStore,
                         AuthenticationRepository authenticationRepository,
                         PatrolRepository patrolRepository) {
        this.dataStore = dataStore;
        this.authenticationRepository = authenticationRepository;
        this.patrolRepository = patrolRepository;

        getPatrolRouteSchedules();
    }

    public LiveData<Boolean> isLoadingPatrolRoute() {
        return isLoadingPatrolRoutes;
    }

    @SuppressLint("CheckResult")
    public void getPatrolRouteSchedules() {
        GetPatrolRouteParams params = new GetPatrolRouteParams();
        params.setSUserIDxx(dataStore.getUserId());
        patrolRepository.getPatrolRouteSchedule(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<PatrolRouteEntity> patrolRoutes = response.getData().get(0).getSRoutexxx();
                            List<PatrolScheduleEntity> patrolSchedules = response.getData().get(0).getSSchedule();

                            patrolRepository.savePatrolRoute(patrolRoutes);
                            patrolRepository.savePatrolSchedule(patrolSchedules);
                        },
                        throwable -> {

                        }
                );
    }

    public LiveData<List<PatrolRouteEntity>> getPatrolCheckpoints() {
        return patrolRepository.getPatrolCheckpoints();
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
                        response -> {

                            if (response.getResult().equalsIgnoreCase("error")) {

                                loggingOut.setValue(false);
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            loggingOut.setValue(false);

                        }
                );
    }
}