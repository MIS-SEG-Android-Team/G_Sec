package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_DATE_TIME_FORMAT;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;
import static org.rmj.guanzongroup.gsecurity.etc.DateTime.formatDateTimeResult;
import static org.rmj.guanzongroup.gsecurity.etc.DateTime.getCurrentLocalDateTime;
import static org.rmj.guanzongroup.gsecurity.utils.BugReport.reportException;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolCache;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddNfcTagParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PostPatrolParams;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.RequestVisitRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.UserProfileRepository;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitEntity;
import org.rmj.guanzongroup.gsecurity.service.TimeCheckService;
import org.rmj.guanzongroup.gsecurity.ui.activity.AlarmActivity;
import org.rmj.guanzongroup.gsecurity.utils.TimeComparator;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class VMPatrolRoute extends ViewModel {
    private static final String TAG = TimeCheckService.class.getSimpleName();

    private final DataStore dataStore;
    private final PatrolCache patrolCache;
    private final AuthenticationRepository authenticationRepository;
    private final PatrolRepository patrolRepository;
    private final RequestVisitRepository requestVisitRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserProfileRepository userProfileRepository;


    private final MutableLiveData<Boolean> notificationPermissionEnabled = new MutableLiveData<>(false);
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
    public VMPatrolRoute(
            DataStore dataStore,
            PatrolCache patrolCache,
            AuthenticationRepository authenticationRepository,
            PatrolRepository patrolRepository,
            RequestVisitRepository requestVisitRepository,
            ScheduleRepository scheduleRepository,
            UserProfileRepository userProfileRepository
    ) {
        this.dataStore = dataStore;
        this.patrolCache = patrolCache;
        this.authenticationRepository = authenticationRepository;
        this.patrolRepository = patrolRepository;
        this.requestVisitRepository = requestVisitRepository;
        this.scheduleRepository = scheduleRepository;
        this.userProfileRepository = userProfileRepository;

        getPatrolRouteSchedules();
    }

    public void setNotificationPermissionEnabled(boolean value) {
        notificationPermissionEnabled.setValue(value);
    }

    public LiveData<Boolean> isNotificationPermissionEnabled() {
        return notificationPermissionEnabled;
    }

    public void initPatrolCheckpoints() {
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
        isLoadingPatrolRoutes.setValue(true);
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

                            if (patrolSchedules.isEmpty()) {
                                reportException("", "Imported patrol schedules is empty.");
                            }
                            patrolRepository.savePatrolRoute(patrolRoutes);
                            scheduleRepository.savePatrolSchedule(patrolSchedules);
                            isLoadingPatrolRoutes.setValue(false);
                        },
                        throwable -> {
                            isLoadingPatrolRoutes.setValue(false);
                        }
                );
    }

    @SuppressLint("NewApi")
    public void tagVisitedCheckpoint(String value) {
        try {
            // Triggers the loading dialog on Main Thread...
            isLoadingPosting.setValue(true);
            Gson gson = new Gson();
            Type type = new TypeToken<AddNfcTagParams>() {
            }.getType();
            AddNfcTagParams nfcTag = gson.fromJson(value.replace("\u0002en", ""), type);

            PatrolCheckpoint patrol = taggingCheckpoint.getValue();

            if (patrol == null) {
                errorMessage.setValue("Something went wrong. Please try again.");
                return;
            }

//            if (!nfcTag.getSDescript().equalsIgnoreCase(patrol.getsDescript())) {
//                errorMessage.setValue("You are tagging the wrong NFC checkpoint.");
//                return;
//            }

            String remarks = "";

            if (taggingRemarks.getValue() != null) {
                remarks = taggingRemarks.getValue();
            }

            if (patrolCache.getPatrolSchedule().isEmpty()) {
                reportException("", "Patrol schedule is empty");
                errorMessage.setValue("Unable to tag this checkpoint as visited. Wait for the next patrol schedule.");
                return;
            }



            DateTimeFormatter defaultTimeFormat = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);
            DateTimeFormatter defaultDateTimeFormat = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);

            String currentDateTime = defaultDateTimeFormat.format(LocalDateTime.now());
            String currentTime = defaultTimeFormat.format(LocalTime.now());

            LocalTime schedule = LocalTime.parse(patrolCache.getPatrolSchedule(), defaultTimeFormat);
            LocalDateTime scheduleDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), schedule);
            String patrolSchedule = scheduleDateTime.format(defaultDateTimeFormat);

            if (patrolRepository.checkIfCheckpointIsVisited(patrol.getsNFCIDxxx(), patrolSchedule) != null) {
                errorMessage.setValue("You already tagged this checkpoint as visited.");
                return;
            }

            PatrolLogEntity patrolLogEntity = new PatrolLogEntity();
            patrolLogEntity.setDVisitedx(currentDateTime);
            patrolLogEntity.setDTimeVist(currentTime);
            patrolLogEntity.setSNFCIDxxx(patrol.getsNFCIDxxx());
            patrolLogEntity.setSRemarksx(remarks);
            patrolLogEntity.setSUserIDxx(dataStore.getUserId());
            patrolLogEntity.setCSendStat("0");
            patrolLogEntity.setDSchedule(patrolSchedule);
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
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            errorMessage.setValue("Invalid payload has been scan. Please try again...");
        }
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
        userProfileRepository.logoutUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loggingOut.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            userProfileRepository.clearCache();
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
        try {
            // Triggers the loading dialog on Main Thread...
            isLoadingPosting.setValue(true);
            Gson gson = new Gson();
            Type type = new TypeToken<AddNfcTagParams>() {
            }.getType();
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
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setValue("Invalid payload has been scan. Please try again...");
        }
    }

    @SuppressLint("NewApi")
    private void checkPatrolSchedule() {
        List<PatrolScheduleEntity> patrolSchedule = scheduleRepository.getPatrolScheduleList();
        if (patrolSchedule == null) {
            return;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);

        // Parse the current time
        LocalTime currentTime = LocalTime.now();
        Timber.tag(TAG).d("Current Time: %s", currentTime.format(dateTimeFormatter));

        patrolSchedule.sort(new TimeComparator());
        if (patrolSchedule.isEmpty()) {
            reportException("", "Imported patrol schedules is empty.");
        }
        for (int x = 0; x < patrolSchedule.size(); x++) {
            reportException("", "Imported patrol schedules is empty.");
            PatrolScheduleEntity schedule = patrolSchedule.get(x);

            // Parse the time from the list
            LocalTime patrolTime = LocalTime.parse(schedule.getDTimexxxx(), dateTimeFormatter);

            int comparison = currentTime.compareTo(patrolTime);

            if (comparison < 0) {
                break;
            }

            if (comparison > 0) {
                Duration duration = Duration.between(currentTime, patrolTime);

                long minutes = duration.toMinutes() % 60;

                boolean patrolStarted = patrolCache.getPatrolStarted();

                if (!patrolStarted) {
                    if (minutes <= 1 && minutes > -25) {
                        patrolCache.setPatrolSchedule(patrolTime.format(dateTimeFormatter));
                        break;
                    }
                }

                Timber.tag(TAG).d("Validating time...");
                int nextPatrol = x + 1;
                if (nextPatrol < patrolSchedule.size()) {
                    Timber.tag(TAG).d("Validating next patrol time...");
                    LocalTime nextPatrolSchedule = LocalTime.parse(patrolSchedule.get(nextPatrol).getDTimexxxx(), dateTimeFormatter);

                    comparison = currentTime.compareTo(nextPatrolSchedule);
                    if (comparison > 0) {
                        continue;
                    }

                    if (comparison < 0) {
                        patrolCache.setPatrolSchedule(patrolTime.format(dateTimeFormatter));
                        reportException("", "Patrol schedule is set!, Patrol schedule " + patrolTime.format(dateTimeFormatter));
                        if (patrolCache.getPatrolSchedule().isEmpty()) {
                            reportException("", "Patrol schedule is empty");
                        }
                        Timber.tag(TAG).d("Patrol Schedule: %s", patrolTime.format(dateTimeFormatter));
                        break;
                    }
                }
            }
        }
    }
}