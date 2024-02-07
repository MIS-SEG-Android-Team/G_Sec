package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.personnellist;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetActivePersonnelParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.ActivePersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.repository.PersonnelRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMActivePersonnelList extends ViewModel {

    private final PersonnelRepository personnelRepository;

    private final MutableLiveData<Boolean> loadingPersonnelList = new MutableLiveData<>(false);
    private final MutableLiveData<String> listErrorMessage = new MutableLiveData<>("");
    private final MutableLiveData<List<ActivePersonnelModel>> activePersonnelList = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public VMActivePersonnelList(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;

        getPersonnelList();
    }

    public LiveData<List<ActivePersonnelModel>> getActivePersonnel() { return activePersonnelList; }
    public LiveData<Boolean> isLoadingPersonnelList() { return loadingPersonnelList; }
    public LiveData<String> getListErrorMessage() { return listErrorMessage; }

    @SuppressLint("CheckResult")
    public void getPersonnelList() {
        loadingPersonnelList.setValue(true);
        listErrorMessage.setValue("");
        GetActivePersonnelParams params = new GetActivePersonnelParams();
        params.setSWHouseID("");
        personnelRepository.getActivePersonnels(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loadingPersonnelList.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                listErrorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            List<ActivePersonnelModel> list = response.getData();
                            activePersonnelList.setValue(list);
                        },
                        error -> {
                            listErrorMessage.setValue(error.getMessage());
                            loadingPersonnelList.setValue(false);
                        }
                );
    }
}