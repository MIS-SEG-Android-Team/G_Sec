package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import static org.rmj.guanzongroup.gsecurity.etc.DateTime.formatDateTimeResult;
import static org.rmj.guanzongroup.gsecurity.etc.DateTime.getCurrentLocalDateTime;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddNfcTagParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PostPatrolParams;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.RequestVisitRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitEntity;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMItineraries extends ViewModel {

    private final DataStore dataStore;
    private final AuthenticationRepository authenticationRepository;
    private final PatrolRepository patrolRepository;
    private final RequestVisitRepository requestVisitRepository;
    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<Boolean> isLoadingPatrolRoutes = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> hasLogout = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loggingOut = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<RequestVisitEntity> requestedVisit = new MutableLiveData<>(new RequestVisitEntity());
    private final MutableLiveData<PatrolCheckpoint> taggingCheckpoint = new MutableLiveData<>();
    private final MutableLiveData<Integer> checkpointIndex = new MutableLiveData<>(0);
    private final MutableLiveData<List<PatrolCheckpoint>> patrolCheckpoints = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> taggingRemarks = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> isLoadingPosting = new MutableLiveData<>(false);
    private final MutableLiveData<String> successMessage = new MutableLiveData<>("");

    @Inject
    public VMItineraries(
            DataStore dataStore,
            AuthenticationRepository authenticationRepository,
            PatrolRepository patrolRepository,
            RequestVisitRepository requestVisitRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.dataStore = dataStore;
        this.authenticationRepository = authenticationRepository;
        this.patrolRepository = patrolRepository;
        this.requestVisitRepository = requestVisitRepository;
        this.scheduleRepository = scheduleRepository;

        getPatrolRouteSchedules();
        initPatrolCheckpoints();
    }

    private void initPatrolCheckpoints() {
        List<PatrolCheckpoint> checkpoints = new ArrayList<>();
        List<PatrolRouteEntity> patrolRouteEntities = patrolRepository.getPatrolCheckpoints();

        if (patrolRouteEntities == null) {
            return;
        }

        for (int x=0; x < patrolRouteEntities.size(); x++) {
            PatrolRouteEntity checkpoint = patrolRouteEntities.get(x);
            checkpoints.add(new PatrolCheckpoint(
                    checkpoint.getSNFCIDxxx(),
                    checkpoint.getNPatrolNo(),
                    checkpoint.getSDescript())
            );
        }
        patrolCheckpoints.setValue(checkpoints);
    }

    public LiveData<List<PatrolCheckpoint>> getPatrolCheckpoints() {
        return patrolCheckpoints;
    }

    public LiveData<Boolean> isLoadingPatrolRoute() {
        return isLoadingPatrolRoutes;
    }

    public LiveData<Boolean> hasLogout() {
        return hasLogout;
    }

    public LiveData<Boolean> isLoggingOut() {
        return loggingOut;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<RequestVisitEntity> getRequestedVisit() {
        return requestVisitRepository.getRequestedVisit();
    }

    public LiveData<String> successfullyTagged() {
        return successMessage;
    }

    public void setCheckpoint(PatrolCheckpoint patrol, int position) {
        this.taggingCheckpoint.setValue(patrol);
        this.checkpointIndex.setValue(position);
    }

    public void setRequestedVisit(RequestVisitEntity patrol) {
        this.requestedVisit.setValue(patrol);
    }

    public void setRemarks(String value) {
        this.taggingRemarks.setValue(value);
    }

    public void clearMessage() {
        this.successMessage.setValue("");
    }

    @SuppressLint("CheckResult")
    public void getPatrolRouteSchedules() {
        GetPatrolRouteParams params = new GetPatrolRouteParams();
        params.setSUserIDxx(dataStore.getUserId());
        patrolRepository.getPatrolRouteSchedule(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<PatrolRouteEntity> patrolRoutes = response.getData().get(0).getSRoutexxx();
                            List<PatrolScheduleEntity> patrolSchedules = response.getData().get(0).getSSchedule();

                            patrolRepository.savePatrolRoute(patrolRoutes);
                            scheduleRepository.savePatrolSchedule(patrolSchedules);
                        },
                        throwable -> {

                        }
                );
    }

    @SuppressLint("NewApi")
    public void tagVisitedCheckpoint(String value) {

        // Triggers the loading dialog on Main Thread...
        isLoadingPosting.setValue(true);
        Gson gson = new Gson();
        Type type = new TypeToken<AddNfcTagParams>(){}.getType();
        AddNfcTagParams nfcTag = gson.fromJson(value.replace("\u0002en", ""), type);

        PatrolCheckpoint patrol = taggingCheckpoint.getValue();

        if (patrol == null) {
            errorMessage.setValue("Something went wrong. Please try again...");
            return;
        }

        if (!nfcTag.getSDescript().equalsIgnoreCase(patrol.getsDescript())) {
            errorMessage.setValue("You are tagging the wrong NFC checkpoint.");
            return;
        }

        String remarks = "";

        if (taggingRemarks.getValue() != null) {
            remarks = taggingRemarks.getValue();
        }

        String currentDateTime = formatDateTimeResult(getCurrentLocalDateTime());

        PatrolLogEntity patrolLogEntity = new PatrolLogEntity();
        patrolLogEntity.setDVisitedx(currentDateTime);
        patrolLogEntity.setSNFCIDxxx(patrol.getsNFCIDxxx());
        patrolLogEntity.setSRemarksx(remarks);
        patrolLogEntity.setSUserIDxx(dataStore.getUserId());
        patrolLogEntity.setCSendStat("0");
        patrolLogEntity.setSSchedule("0");
        patrolRepository.savePatrolLog(patrolLogEntity);

        isLoadingPosting.setValue(false);
        successMessage.setValue("You visited " + nfcTag.getSDescript());
        if (checkpointIndex.getValue() != null) {
            int checkpointPosition = checkpointIndex.getValue();
            List<PatrolCheckpoint> checkpoints = patrolCheckpoints.getValue();
            checkpoints.get(checkpointPosition).setVisited(true);
            patrolCheckpoints.setValue(checkpoints);
        }
        postTaggedCheckpoints();
    }

    @SuppressLint("CheckResult")
    private void postTaggedCheckpoints() {
        try{
            List<PatrolLogEntity> patrols = patrolRepository.getPatrolLogsForPosting();

            if (patrols == null) {
                return;
            }

            if (patrols.isEmpty()) {
                return;
            }

            PostPatrolParams params = new PostPatrolParams();
            params.setData(patrols);

            patrolRepository.postPlaceVisited(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response -> {
                                if (response.getResult().equalsIgnoreCase("error")) {
                                    return;
                                }

                                for (int x = 0; x < patrols.size(); x++) {
                                    patrols.get(x).setCSendStat("1");
                                }
                                patrolRepository.updatePatrolLog(patrols);
                            },
                            error -> {

                            }
                    );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("CheckResult")
    public void logoutUser() {
        loggingOut.setValue(true);
        authenticationRepository.logoutUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loggingOut.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            hasLogout.setValue(true);
                        },
                        throwable -> {
                            loggingOut.setValue(false);
                            errorMessage.setValue(throwable.getMessage());
                        }
                );
    }

    @SuppressLint({"NewApi", "CheckResult"})
    public void tagRequestedVisit(String value) {

        // Triggers the loading dialog on Main Thread...
        isLoadingPosting.setValue(true);
        Gson gson = new Gson();
        Type type = new TypeToken<AddNfcTagParams>(){}.getType();
        AddNfcTagParams nfcTag = gson.fromJson(value.replace("\u0002en", ""), type);

        RequestVisitEntity requestVisit = requestedVisit.getValue();

        if (requestVisit == null) {
            errorMessage.setValue("Something went wrong. Please try again...");
            return;
        }

        if (!nfcTag.getSDescript().equalsIgnoreCase(requestVisit.getSDescript())) {
            errorMessage.setValue("You are tagging the wrong NFC checkpoint.");
            return;
        }

        String remarks = "";

        if (taggingRemarks.getValue() != null) {
            remarks = taggingRemarks.getValue();
        }

        String currentDateTime = formatDateTimeResult(getCurrentLocalDateTime());

        requestVisit.setDVisitedx(currentDateTime);
        requestVisit.setSRemark2(remarks);
        requestVisit.setCSendStat("0");
        requestVisitRepository.update(requestVisit);

        isLoadingPosting.setValue(false);
        successMessage.setValue("You visited " + nfcTag.getSDescript());

        requestVisitRepository.sendVisitedNotification(requestVisit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            if (response.getResult().equalsIgnoreCase("error")) {
                                return;
                            }
                            requestVisit.setCSendStat("1");
                            requestVisitRepository.update(requestVisit);
                        });
    }
}