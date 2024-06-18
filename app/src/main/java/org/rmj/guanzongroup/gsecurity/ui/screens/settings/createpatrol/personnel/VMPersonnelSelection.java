package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.personnel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepersonnel.UpdatePatrolPersonnelParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.personnelpatrol.PersonnelPatrolModel;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.PersonnelRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMPersonnelSelection extends ViewModel {

    private final DataStore dataStore;
    private final PersonnelRepository personnelRepository;
    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<Boolean> forUpdate = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLoadingPersonnels = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLoadingUpdate = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<String> message = new MutableLiveData<>("");
    private final MutableLiveData<PersonnelPatrolModel> patrolRoute = new MutableLiveData<>();
    private final MutableLiveData<String> selectedPersonnel = new MutableLiveData<>("");
    private final MutableLiveData<List<PersonnelModel>> personnels = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public VMPersonnelSelection(DataStore dataStore,
                                PersonnelRepository personnelRepository,
                                PatrolRepository patrolRepository,
                                ScheduleRepository scheduleRepository) {
        this.dataStore = dataStore;
        this.personnelRepository = personnelRepository;
        this.scheduleRepository = scheduleRepository;

        forUpdate.setValue(scheduleRepository.getPatrolScheduleFromCache() == null);
        initUpdatePersonnel();
        getPersonnels();
    }

    public LiveData<Boolean> forUpdate() { return forUpdate; }

    private void initUpdatePersonnel() {
        if (scheduleRepository.getPatrolRouteForUpdate() != null) {
            PersonnelPatrolModel patrolRouteModel = scheduleRepository.getPatrolRouteForUpdate();
            patrolRoute.setValue(patrolRouteModel);
        }
    }
    public LiveData<Boolean> isLoadingPersonnel() { return isLoadingPersonnels; }
    public LiveData<List<PersonnelModel>> getPersonnelList() { return personnels; }
    public LiveData<List<PersonnelModel>> getPersonnelListForUpdate() { return personnels; }
    public LiveData<Boolean> isLoadingUpdate() { return isLoadingUpdate; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<String> getMessage() { return message; }

    @SuppressLint("CheckResult")
    private void getPersonnels() {
        isLoadingPersonnels.setValue(true);
        DateTimeStampParams params = new DateTimeStampParams();
        params.setDTimeStmp("");
        personnelRepository.getPersonnels(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {
                            isLoadingPersonnels.setValue(false);

                            if (baseResponse.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<PersonnelModel> personnelModels = baseResponse.getData();
                            personnels.setValue(personnelModels);
                        },
                        throwable -> {
                            isLoadingPersonnels.setValue(false);
                        }
                );
    }

    public void setPersonnel(String name, String id) {
        CreateScheduleParams params = scheduleRepository.getPatrolScheduleFromCache();
        params.setSUserIDxx(id);
        params.setSUserName(name);
        scheduleRepository.updatePatrolScheduleToCache(params);
    }

    @SuppressLint("CheckResult")
    public void updatePersonnel(String id) {
        isLoadingUpdate.setValue(true);
        String schedID = Objects.requireNonNull(patrolRoute.getValue()).getSSchedIDx();
        UpdatePatrolPersonnelParams params = new UpdatePatrolPersonnelParams();
        params.setSAdminIDx(dataStore.getUserId());
        params.setSPersonnl(id);
        params.setSSchedIDx(schedID);
        scheduleRepository.updatePatrolPersonnel(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            isLoadingUpdate.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")){
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }
                            message.setValue(response.getMessage());
                        },
                        error -> {
                            errorMessage.setValue(error.getMessage());
                            isLoadingUpdate.setValue(false);
                        }
                );
    }

    public void clearCache() { scheduleRepository.clearCache(); }
}