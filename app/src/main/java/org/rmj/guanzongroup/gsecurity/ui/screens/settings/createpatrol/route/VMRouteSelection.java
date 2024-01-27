package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetNFCTagsParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateUpdateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.SRoutexxx;
import org.rmj.guanzongroup.gsecurity.data.repository.CheckpointRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMRouteSelection extends ViewModel {

    private final CheckpointRepository checkpointRepository;
    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<Boolean> isLoadingCheckpoints = new MutableLiveData<>(false);

    private final MutableLiveData<List<Checkpoint>> checkpoints = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<CreateUpdateScheduleParams> patrolRoute = new MutableLiveData<>(new CreateUpdateScheduleParams());
    private final MutableLiveData<Boolean> savedPatrolCheckpoints = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMRouteSelection(
            CheckpointRepository checkpointRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.checkpointRepository = checkpointRepository;
        this.scheduleRepository = scheduleRepository;

        patrolRoute.setValue(scheduleRepository.getPatrolScheduleFromCache());
        getNfcTags();
    }

    public LiveData<Boolean> isLoadingCheckpoints() {
        return isLoadingCheckpoints;
    }

    public LiveData<Boolean> savedPatrolCheckpoints() {
        return savedPatrolCheckpoints;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @SuppressLint("CheckResult")
    public void getNfcTags() {

        GetNFCTagsParams params = new GetNFCTagsParams();
        params.setSWhouseID(Objects.requireNonNull(patrolRoute.getValue()).getSWHouseID());
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

    public LiveData<List<NFCDeviceEntity>> getNfcDeviceEntities() {
        return checkpointRepository.getNfcTags(Objects.requireNonNull(patrolRoute.getValue()).getSWHouseID());
    }

    public void initializeSelectedCheckpoints(List<Checkpoint> value) {
        if (patrolRoute.getValue() != null) {
            CreateUpdateScheduleParams params = patrolRoute.getValue();
            List<SRoutexxx> routes = params.getSRoutexxx();
            if (routes != null) {
                if (value != null) {
                    for (int x = 0; x < routes.size(); x++) {
                        for (int i = 0; i < value.size(); i++) {
                            if (routes.get(x).getSNFCIDxxx().equalsIgnoreCase(value.get(i).getsNFCIDxxx())) {
                                value.get(i).hasSelected(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
        checkpoints.setValue(value);
    }

    public LiveData<List<Checkpoint>> getCheckpoints() {
        return checkpoints;
    }

    public void setSelectedCheckpoint(int position, boolean hasSelected) {
        List<Checkpoint> list = checkpoints.getValue();
        list.get(position).hasSelected(hasSelected);
        checkpoints.setValue(list);
    }

    public void saveRouteSelection() {
        // Add all selected checkpoints to this list...
        List<Checkpoint> selectedCheckpoints = new ArrayList<>();

        for (int x = 0; x < Objects.requireNonNull(checkpoints.getValue()).size(); x ++) {
            Checkpoint checkpoint = checkpoints.getValue().get(x);
            if (checkpoint.isSelected()) {
                selectedCheckpoints.add(checkpoint);
            }
        }

        if (selectedCheckpoints.isEmpty()) {
            errorMessage.setValue("Unable to proceed without any checkpoints selected.");
            return;
        }

        List<SRoutexxx> routes = new ArrayList<>();

        for (int x = 0; x < selectedCheckpoints.size(); x++) {
            Checkpoint checkpoint = selectedCheckpoints.get(x);
            SRoutexxx route = new SRoutexxx();
            route.setSCheckpnt(checkpoint.getsDescript());
            route.setSNFCIDxxx(checkpoint.getsNFCIDxxx());
            route.setNPatrolNo(String.valueOf(x));
            routes.add(route);
        }

        Objects.requireNonNull(patrolRoute.getValue()).setSRoutexxx(routes);

        scheduleRepository.updatePatrolScheduleToCache(patrolRoute.getValue());
        savedPatrolCheckpoints.setValue(true);
    }
}