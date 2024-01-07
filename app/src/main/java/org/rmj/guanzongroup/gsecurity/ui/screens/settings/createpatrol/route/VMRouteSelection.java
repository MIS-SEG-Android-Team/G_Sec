package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetNFCTagsParams;
import org.rmj.guanzongroup.gsecurity.data.repository.CheckpointRepository;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMRouteSelection extends ViewModel {

    private final CheckpointRepository checkpointRepository;
    private final PatrolSchedulerCache patrolSchedulerCache;

    private final MutableLiveData<Boolean> isLoadingCheckpoints = new MutableLiveData<>(false);

    private final MutableLiveData<List<NFCDeviceEntity>> checkpoints = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public VMRouteSelection(
            CheckpointRepository checkpointRepository,
            PatrolSchedulerCache patrolSchedulerCache
    ) {
        this.checkpointRepository = checkpointRepository;
        this.patrolSchedulerCache = patrolSchedulerCache;

        getNfcTags();
    }

    public LiveData<Boolean> isLoadingCheckpoints() {
        return isLoadingCheckpoints;
    }

    public LiveData<List<NFCDeviceEntity>> getNfcDeviceEntities() {
        return checkpointRepository.getNfcTags(patrolSchedulerCache.getWarehouse());
    }

    public void initializeSelectedCheckpoints(List<NFCDeviceEntity> value) {
        checkpoints.setValue(value);
    }

    public LiveData<List<NFCDeviceEntity>> getCheckpoints() {
        return checkpoints;
    }

    @SuppressLint("CheckResult")
    public void getNfcTags() {

        GetNFCTagsParams params = new GetNFCTagsParams();
        params.setSWhouseID(patrolSchedulerCache.getWarehouse());
        String timeStamp = checkpointRepository.getLatestTimeStamp();
        if (timeStamp != null) {
            params.setDTimeStmp(timeStamp);
        }

        checkpointRepository.getNFCTags(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        baseResponse -> {

                            if (baseResponse.getResult().equalsIgnoreCase("error")) {
                               return;
                            }

                            List<NFCDeviceEntity> nfcDeviceEntities = baseResponse.getData();
                            checkpointRepository.saveNfcTags(nfcDeviceEntities);
                        },

                        throwable -> {

                        }
                );
    }

    private void initSelectedCheckpoints() {

    }

    public void addSelectedCheckpoint() {

    }

    public void removeCheckpoint(String id) {

    }

    public void selectAllCheckPoint() {

    }
}