package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.review;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateUpdateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMScheduleReview extends ViewModel {

    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<CreateUpdateScheduleParams> createdSchedule = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoadingSaveSchedule = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> scheduleSave = new MutableLiveData<>(false);

    @Inject
    public VMScheduleReview(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
        createdSchedule.setValue(scheduleRepository.getPatrolScheduleFromCache());
    }

    public LiveData<Boolean> isLoadingSaveSchedule() {
        return isLoadingSaveSchedule;
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

    public LiveData<CreateUpdateScheduleParams> getCreatedSchedule() {
        return createdSchedule;
    }
    @SuppressLint("CheckResult")
    public void saveSchedule() {
        CreateUpdateScheduleParams params = createdSchedule.getValue();
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
                            },
                            throwable -> {
                                isLoadingSaveSchedule.setValue(false);
                                errorMessage.setValue(throwable.getMessage());
                            }
                    );
        }
    }
}