package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.personnellist;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.checkerframework.checker.units.qual.A;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.ActivePersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.repository.PersonnelRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMPersonnelList extends ViewModel {

    private final PersonnelRepository personnelRepository;

    private final MutableLiveData<Boolean> loadingPersonnelList = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<List<ActivePersonnelModel>> activePersonnelList = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public VMPersonnelList(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;

        getPersonnelList();
    }

    public LiveData<List<ActivePersonnelModel>> getActivePersonnel() {
        return activePersonnelList;
    }

    public LiveData<Boolean> isLoadingPersonnelList() {
        return loadingPersonnelList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @SuppressLint("CheckResult")
    public void getPersonnelList() {
        loadingPersonnelList.setValue(true);
        errorMessage.setValue("");
        DateTimeStampParams params = new DateTimeStampParams();
        params.setDTimeStmp("");
        personnelRepository.getActivePersonnels(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loadingPersonnelList.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            List<ActivePersonnelModel> list = response.getData();
                            activePersonnelList.setValue(list);
                        },
                        error -> {
                            errorMessage.setValue(error.getMessage());
                            loadingPersonnelList.setValue(false);
                        }
                );
    }
}