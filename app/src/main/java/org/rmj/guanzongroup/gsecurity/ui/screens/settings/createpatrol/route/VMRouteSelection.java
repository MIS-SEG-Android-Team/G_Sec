package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetNFCTagsParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolRoute;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepatrolroute.UpdatePatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.personnelpatrol.PersonnelPatrolModel;
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

    private final DataStore dataStore;
    private final CheckpointRepository checkpointRepository;
    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<Boolean> isLoadingCheckpoints = new MutableLiveData<>(false);

    private final MutableLiveData<List<Checkpoint>> checkpoints = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<CreateScheduleParams> patrolRoute = new MutableLiveData<>(new CreateScheduleParams());
    private final MutableLiveData<Boolean> savedPatrolCheckpoints = new MutableLiveData<>(false);
    private final MutableLiveData<String> listErrorMessage = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> forUpdate = new MutableLiveData<>(false);
    private final MutableLiveData<PersonnelPatrolModel> patrolRouteForUpdate = new MutableLiveData<>(new PersonnelPatrolModel());
    private final MutableLiveData<Boolean> isLoadingUpdatedRoute = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<String> message = new MutableLiveData<>("");

    @Inject
    public VMRouteSelection(
            DataStore dataStore,
            CheckpointRepository checkpointRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.dataStore = dataStore;
        this.checkpointRepository = checkpointRepository;
        this.scheduleRepository = scheduleRepository;
        initPatrolRoutes();
    }

    public LiveData<Boolean> isLoadingCheckpoints() { return isLoadingCheckpoints; }
    public LiveData<String> getListErrorMessage() { return listErrorMessage; }
    public LiveData<List<Checkpoint>> getCheckpoints() { return checkpoints; }
    public LiveData<Boolean> savedPatrolCheckpoints() { return savedPatrolCheckpoints; }
    public LiveData<Boolean> forUpdate() { return forUpdate; }
    public LiveData<Boolean> isLoadingUpdatedRoute() { return isLoadingUpdatedRoute; }
    public LiveData<String> getMessage() { return message; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    private void initPatrolRoutes() {
        // This patrol schedule from cache is empty means that the current instance for this
        // fragment is use for updating route selection...
        forUpdate.setValue(scheduleRepository.getPatrolScheduleFromCache() == null);
    }

    public void initForCreatingSchedule() {
        patrolRoute.setValue(scheduleRepository.getPatrolScheduleFromCache());
    }

    public void initForUpdate() {
        patrolRouteForUpdate.setValue(scheduleRepository.getPatrolRouteForUpdate());
    }

    @SuppressLint("CheckResult")
    public void getNfcTags() {
        listErrorMessage.setValue("");
        isLoadingCheckpoints.setValue(true);
        GetNFCTagsParams params = new GetNFCTagsParams();
        if (Boolean.TRUE.equals(forUpdate.getValue())) {
            params.setSWhouseID(Objects.requireNonNull(patrolRouteForUpdate.getValue()).getSWHouseID());
        } else {
            params.setSWhouseID(Objects.requireNonNull(patrolRoute.getValue()).getSWHouseID());
        }
        String timeStamp = checkpointRepository.getLatestTimeStamp();
        if (timeStamp != null) {
            params.setDTimeStmp(timeStamp);
        }

        checkpointRepository.getNFCTags(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            isLoadingCheckpoints.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                listErrorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            List<NFCDeviceEntity> nfcDeviceEntities = response.getData();
                            checkpointRepository.saveNfcTags(nfcDeviceEntities);
                        },
                        error -> {
                            listErrorMessage.setValue(error.getMessage());
                            isLoadingCheckpoints.setValue(false);
                        }
                );
    }

    public LiveData<List<NFCDeviceEntity>> getNfcDeviceEntities() {
        if (Boolean.TRUE.equals(forUpdate.getValue())) {
            return checkpointRepository.getNfcTags(Objects.requireNonNull(patrolRouteForUpdate.getValue()).getSWHouseID());
        } else {
            return checkpointRepository.getNfcTags(Objects.requireNonNull(patrolRoute.getValue()).getSWHouseID());
        }
    }

    public void initializeSelectedCheckpoints(List<Checkpoint> value) {
        if (Boolean.TRUE.equals(forUpdate.getValue())) {
            if (patrolRouteForUpdate.getValue() != null) {
                PersonnelPatrolModel params = patrolRouteForUpdate.getValue();
                List<PersonnelPatrolRoute> routes = params.getSRoutexxx();
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
        } else {
            if (patrolRoute.getValue() != null) {
                CreateScheduleParams params = patrolRoute.getValue();
                List<PersonnelPatrolRoute> routes = params.getSRoutexxx();
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
        }
        checkpoints.setValue(value);
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

        List<PersonnelPatrolRoute> routes = new ArrayList<>();

        for (int x = 0; x < selectedCheckpoints.size(); x++) {
            Checkpoint checkpoint = selectedCheckpoints.get(x);
            PersonnelPatrolRoute route = new PersonnelPatrolRoute();
            route.setSDescript(checkpoint.getsDescript());
            route.setSNFCIDxxx(checkpoint.getsNFCIDxxx());
            route.setNPatrolNo(String.valueOf(x));
            routes.add(route);
        }

        Objects.requireNonNull(patrolRoute.getValue()).setSRoutexxx(routes);
        scheduleRepository.updatePatrolScheduleToCache(patrolRoute.getValue());
        savedPatrolCheckpoints.setValue(true);
    }

    @SuppressLint("CheckResult")
    public void updatePatrolRoute() {
        isLoadingUpdatedRoute.setValue(true);
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

        List<PersonnelPatrolRoute> routes = new ArrayList<>();

        for (int x = 0; x < selectedCheckpoints.size(); x++) {
            Checkpoint checkpoint = selectedCheckpoints.get(x);
            PersonnelPatrolRoute route = new PersonnelPatrolRoute();
            route.setSDescript(checkpoint.getsDescript());
            route.setSNFCIDxxx(checkpoint.getsNFCIDxxx());
            route.setNPatrolNo(String.valueOf(x));
            routes.add(route);
        }

        PersonnelPatrolModel personnelPatrolModel = patrolRouteForUpdate.getValue();
        if (personnelPatrolModel == null) {
            errorMessage.setValue("Something went wrong...");
            return;
        }
        personnelPatrolModel.setSRoutexxx(routes);
        scheduleRepository.updatePatrolRouteForUpdate(personnelPatrolModel);
        UpdatePatrolRouteParams params = new UpdatePatrolRouteParams();
        params.setSAdminIDx(dataStore.getUserId());
        params.setSSchedIDx(personnelPatrolModel.getSSchedIDx());
        params.setSRoutexxx(routes);
        scheduleRepository.updatePatrolRoute(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    isLoadingUpdatedRoute.setValue(false);
                                    if (response.getResult().equalsIgnoreCase("error")) {
                                        errorMessage.setValue(response.getError().getMessage());
                                        return;
                                    }
                                    message.setValue(response.getMessage());
                                },
                                error -> {
                                    isLoadingUpdatedRoute.setValue(false);
                                    errorMessage.setValue(error.getMessage());
                                }
                        );
    }
}