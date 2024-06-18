package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.recentactivities;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetRecentActivityParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.recentactivity.RecentActivityModel;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMRecentActivities extends ViewModel {

    private final PatrolRepository patrolRepository;

    private final MutableLiveData<Boolean> loadingActivities = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<List<RecentActivityModel>> activities = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public VMRecentActivities(PatrolRepository patrolRepository) {
        this.patrolRepository = patrolRepository;
    }

    public LiveData<Boolean> loadingActivities() {
        return loadingActivities;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<RecentActivityModel>> getActivities() {
        return activities;
    }

    @SuppressLint("CheckResult")
    public void getRecentActivity(String personnelID) {
        loadingActivities.setValue(true);
        GetRecentActivityParams params = new GetRecentActivityParams();
        params.setSUserIDxx(personnelID);
        patrolRepository.getRecentActivity(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loadingActivities.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }
                            List<RecentActivityModel> list = response.getData();
                            activities.setValue(list);
                        },
                        error -> {
                            loadingActivities.setValue(false);
                            errorMessage.setValue(error.getMessage());
                        }
                );
    }
}