package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetNFCTagsParams;
import org.rmj.guanzongroup.gsecurity.data.repository.CheckpointRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceEntity;
import org.rmj.guanzongroup.gsecurity.data.room.schedule.ScheduleRoute;

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
    private final PatrolSchedulerCache patrolSchedulerCache;
    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<Boolean> isLoadingCheckpoints = new MutableLiveData<>(false);

    private final MutableLiveData<List<Checkpoint>> checkpoints = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<ScheduleRoute>> patrolRoute = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public VMRouteSelection(
            CheckpointRepository checkpointRepository,
            PatrolSchedulerCache patrolSchedulerCache,
            ScheduleRepository scheduleRepository
    ) {
        this.checkpointRepository = checkpointRepository;
        this.patrolSchedulerCache = patrolSchedulerCache;
        this.scheduleRepository = scheduleRepository;

        getNfcTags();
        patrolRoute.setValue(patrolSchedulerCache.getPatrolRoute());
    }

    public LiveData<Boolean> isLoadingCheckpoints() {
        return isLoadingCheckpoints;
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

    public LiveData<ScheduleEntity> getCreatedSchedule() {
        return scheduleRepository.getCreatedSchedule();
    }

    public void initializeCreatedSchedule() {
        List<ScheduleRoute> patrolRoutes = patrolSchedulerCache.getPatrolRoute();
        if (patrolRoutes != null) {
            return;
        }

        patrolRoute.setValue(patrolRoutes);
    }

    public LiveData<List<NFCDeviceEntity>> getNfcDeviceEntities() {
        return checkpointRepository.getNfcTags(patrolSchedulerCache.getWarehouse());
    }

    public void initializeSelectedCheckpoints(List<Checkpoint> value) {
        checkpoints.setValue(value);
    }

    public LiveData<List<Checkpoint>> getCheckpoints() {
        return checkpoints;
    }

    public void setSelectedCheckpoint(int position, boolean hasSelected) {
        Objects.requireNonNull(checkpoints.getValue()).get(position).hasSelected(hasSelected);
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

        List<ScheduleRoute> routes = new ArrayList<>();

        for (int x = 0; x < selectedCheckpoints.size(); x++) {
            Checkpoint checkpoint = selectedCheckpoints.get(x);
            ScheduleRoute route = new ScheduleRoute();
            route.setsNFCIDxxx(checkpoint.getsNFCIDxxx());
            route.setnPatrolNo(String.valueOf(x));
            routes.add(route);
        }

        Objects.requireNonNull(patrolRoute.getValue()).setSRoutexxx(routes);

        scheduleRepository.updateSchedule(patrolRoute.getValue());
    }
}