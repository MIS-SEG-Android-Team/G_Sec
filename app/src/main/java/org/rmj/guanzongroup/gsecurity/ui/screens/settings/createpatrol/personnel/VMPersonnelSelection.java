package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.personnel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPersonnelParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateUpdateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
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

    private final PersonnelRepository personnelRepository;
    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<Boolean> isLoadingPersonnels = new MutableLiveData<>(false);
    private final MutableLiveData<List<PersonnelModel>> personnels = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public VMPersonnelSelection(PersonnelRepository personnelRepository,
                                ScheduleRepository scheduleRepository) {
        this.personnelRepository = personnelRepository;
        this.scheduleRepository = scheduleRepository;

        getPersonnels();
    }

    public LiveData<Boolean> isLoadingPersonnel() {
        return isLoadingPersonnels;
    }

    public LiveData<List<PersonnelModel>> getPersonnelList() {
        return personnels;
    }

    @SuppressLint("CheckResult")
    private void getPersonnels() {
        isLoadingPersonnels.setValue(true);
        GetPersonnelParams params = new GetPersonnelParams();
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
        CreateUpdateScheduleParams params = scheduleRepository.getPatrolScheduleFromCache();
        params.setSUserIDxx(id);
        params.setSUserName(name);
        scheduleRepository.updatePatrolScheduleToCache(params);
    }
}