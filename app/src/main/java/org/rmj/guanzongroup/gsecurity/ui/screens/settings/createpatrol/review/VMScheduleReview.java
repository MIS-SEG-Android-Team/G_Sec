package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.review;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.patrol.PatrolRouteModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.personnelpatrol.PersonnelPatrolModel;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMScheduleReview extends ViewModel {

    private final ScheduleRepository scheduleRepository;
    private final PatrolRepository patrolRepository;

    private final MutableLiveData<CreateScheduleParams> createdSchedule = new MutableLiveData<>();
    private final MutableLiveData<PersonnelPatrolModel> patrolRouteModel = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoadingSchedule = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> scheduleSave = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> isLoadingSaveSchedule = new MutableLiveData<>(false);

    @Inject
    public VMScheduleReview(ScheduleRepository scheduleRepository,
                            PatrolRepository patrolRepository) {
        this.scheduleRepository = scheduleRepository;
        this.patrolRepository = patrolRepository;
        initPatrolSchedule();
    }

    private void initPatrolSchedule() {
        if (scheduleRepository.getPatrolScheduleFromCache() != null) {
            // Validate if the cache has value this means UI is being use to create schedule,
            // else UI is being use to preview schedule for specific officer.
            createdSchedule.setValue(scheduleRepository.getPatrolScheduleFromCache());
            return;
        }
    }

    public LiveData<Boolean> isLoadingSaveSchedule() {
        return isLoadingSaveSchedule;
    }
    public LiveData<Boolean> isLoadingSchedule() {
        return isLoadingSchedule;
    }
    public LiveData<String> errorMessage() {
        return errorMessage;
    }
    public LiveData<Boolean> scheduleSaved() {
        return scheduleSave;
    }
    public void dismissErrorDialog() {
        errorMessage.setValue("");
    }

    public LiveData<CreateScheduleParams> getCreatedSchedule() {
        return createdSchedule;
    }
    public LiveData<PersonnelPatrolModel> getPatrolRouteForUpdate() {
        return patrolRouteModel;
    }
    @SuppressLint("CheckResult")
    public void saveSchedule() {
        CreateScheduleParams params = createdSchedule.getValue();
        isLoadingSaveSchedule.setValue(true);
        if (params != null) {
            scheduleRepository.addNewSchedule(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            baseResponse -> {
                                isLoadingSaveSchedule.setValue(false);
                                if (baseResponse.getResult().equalsIgnoreCase("error")) {
                                    errorMessage.setValue(baseResponse.getError().getMessage());
                                    return;
                                }

                                scheduleSave.setValue(true);
                                scheduleRepository.clearCache();
                            },
                            throwable -> {
                                errorMessage.setValue(throwable.getMessage());
                                isLoadingSaveSchedule.setValue(false);
                            }
                    );
        }
    }

    @SuppressLint("CheckResult")
    public void getPatrolSchedulerForUser(String userID) {
        isLoadingSchedule.setValue(true);
        GetPatrolRouteParams params = new GetPatrolRouteParams();
        params.setSUserIDxx(userID);
        scheduleRepository.getPatrolRouteForUpdate(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            isLoadingSchedule.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            scheduleRepository.setPatrolUpdateCache(response.getData().get(0));
                            patrolRouteModel.setValue(response.getData().get(0));
                        },
                        error -> {
                            isLoadingSchedule.setValue(false);
                            errorMessage.setValue(error.getMessage());
                        }
                );
    }
}