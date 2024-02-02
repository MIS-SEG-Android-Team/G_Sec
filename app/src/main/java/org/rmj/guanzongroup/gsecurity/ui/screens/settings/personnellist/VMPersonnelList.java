package org.rmj.guanzongroup.gsecurity.ui.screens.settings.personnellist;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
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

    private final MutableLiveData<List<PersonnelModel>> personnelList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> loadingPersonnel = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMPersonnelList(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;

        importPersonnelList();
    }

    public LiveData<Boolean> loadingPersonnel() {
        return loadingPersonnel;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<PersonnelModel>> getPersonnelList() {
        return personnelList;
    }

    @SuppressLint("CheckResult")
    public void importPersonnelList() {
        loadingPersonnel.setValue(true);
        DateTimeStampParams params = new DateTimeStampParams();
        params.setDTimeStmp("");
        personnelRepository.getPersonnels(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loadingPersonnel.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            List<PersonnelModel> list = response.getData();
                            personnelList.setValue(list);
                        },
                        error -> {
                            errorMessage.setValue(error.getMessage());
                            loadingPersonnel.setValue(false);
                        }
                );
    }
}