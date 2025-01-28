package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;
import static org.rmj.guanzongroup.gsecurity.utils.BugReport.reportException;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.RequiresApi;
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
import org.rmj.guanzongroup.gsecurity.data.remote.response.patrol.PatrolRouteModel;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.RequestVisitRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.UserProfileRepository;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitDao;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitEntity;
import org.rmj.guanzongroup.gsecurity.service.TimeCheckService;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@RequiresApi(api = Build.VERSION_CODES.O)
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
    private final MutableLiveData<CacheNFCSchedule> nfcCache = new MutableLiveData<>(new CacheNFCSchedule("", ""));

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

    public void initNFCacheSchedule(){
        nfcCache.setValue(new CacheNFCSchedule(patrolCache.getCheckpoint(), patrolCache.getPatrolSchedule()));
    }

    public LiveData<CacheNFCSchedule> getNFCCache(){
        return nfcCache;
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

    public LiveData<RequestVisitDao.RequestSchedule> getRequestedVisit() {
        return requestVisitRepository.getRequestedVisit();
    }

    public LiveData<String> successfullyTagged() {
        return successMessage;
    }

    public void setCheckpoint(PatrolCheckpoint patrol, int position) {
        this.taggingCheckpoint.setValue(patrol);
        this.checkpointIndex.setValue(position);
    }

    public void setRemarks(String value) {
        this.taggingRemarks.setValue(value);
    }

    public void clearMessage() {
        this.successMessage.setValue("");
    }

    public boolean getPatrolStarted(){
        return patrolCache.getPatrolStarted();
    }

    public int isPatrolVisited(String schedule){
        return patrolRepository.checkIfPatrolFinished(schedule);
    }

    public PatrolScheduleDao.CacheSchedule getNextSchedule(String schedule){
        return scheduleRepository.getNextSchedule(schedule);
    }

    public String getLastNFCSchedule(String nfcIDxx){
        return scheduleRepository.getLastNFCSchedule(nfcIDxx);
    }

    @SuppressLint({"CheckResult", "NewApi"})
    public void getPatrolRouteSchedules() {

        try {

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

                                @SuppressLint({"NewApi", "LocalSuppress"})
                                DateTimeFormatter dateTimeFormatter =
                                        new DateTimeFormatterBuilder()
                                                .parseCaseInsensitive()
                                                .appendPattern(DEFAULT_TIME_FORMAT)
                                                .toFormatter(Locale.ENGLISH);

                                //todo: clear all data, reset scheduling
                                patrolRepository.clearPatrolRoute();
                                scheduleRepository.clearPatrolSchedule();
                                scheduleRepository.clearCache();
                                patrolCache.clear();

                                for(PatrolRouteModel obj: response.getData()) {

                                    List<PatrolRouteEntity> patrolRoutes = obj.getSRoutexxx();
                                    List<PatrolScheduleEntity> patrolSchedules = obj.getSSchedule();

                                    if (patrolSchedules.isEmpty()) {
                                        reportException("", "Imported patrol schedules is empty.");
                                    }else {

                                        for (PatrolScheduleEntity value: patrolSchedules) {
                                            value.setCRequestd(obj.getcRequestx());
                                            value.setSchedIDxx(obj.getSSchedIDx());

                                            Timber.tag("VMPatrolRoute").d(value.getDTimexxxx());

                                            LocalTime schedFormat = LocalTime.parse(value.getDTimexxxx(), dateTimeFormatter);
                                            String formattedTime = schedFormat.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                                            value.setDTimexxxx(formattedTime);
                                        }

                                        for (PatrolRouteEntity routes: patrolRoutes){

                                            routes.setSchedIDxx(obj.getSSchedIDx());

                                        }

                                    }

                                    patrolRepository.savePatrolRoute(patrolRoutes);
                                    scheduleRepository.savePatrolSchedule(patrolSchedules);
                                    isLoadingPatrolRoutes.setValue(false);

                                }

                                //todo: triggers observation of schedule cache upon first login, due to delayed cache upon starting service
                                if (patrolCache.getPatrolSchedule().isEmpty()){

                                    //todo: if empty, set patrol schedule from local data on cache
                                    patrolCache.setPatrolSchedule(
                                            LocalTime.parse(
                                                    scheduleRepository.getCacheSchedule().getdTimexxxx(),
                                                    DateTimeFormatter.ofPattern("HH:mm:ss")
                                            ).format(DateTimeFormatter.ofPattern("HH:mm"))
                                    );

                                }

                                if (patrolCache.getCheckpoint().isEmpty()){
                                    //todo: if empty, set patrol checkpoint from local data on cache
                                    patrolCache.setPatrolCheckpoint(scheduleRepository.getCacheSchedule().getsNFCIDxxx());
                                }

                                //todo: if two cache above is set, set value for live observation of nfc cache
                                if (!patrolCache.getPatrolSchedule().isEmpty() && !patrolCache.getCheckpoint().isEmpty()){
                                    nfcCache.setValue(
                                            new CacheNFCSchedule(
                                                    patrolCache.getCheckpoint(),
                                                    patrolCache.getPatrolSchedule()
                                            ));
                                }

                            },
                            throwable -> {
                                Timber.tag("VMPatrolRoute").d(throwable);
                                isLoadingPatrolRoutes.setValue(false);
                            }
                    );

            Thread.sleep(1000);

            requestVisitRepository.downloadVisitRequests(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(

                            requestVisitEntityBaseResponse -> {

                                if (requestVisitEntityBaseResponse.getResult().equalsIgnoreCase("error")) {

                                    Timber.tag("GSecureMessagingService").d(requestVisitEntityBaseResponse.getResult());

                                }else {

                                    requestVisitRepository.save(requestVisitEntityBaseResponse.getData());

                                }

                            }
                    );

        }catch (Exception e){
            e.printStackTrace();
        }
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

            String remarks = "";

            if (taggingRemarks.getValue() != null) {
                remarks = taggingRemarks.getValue();
            }

            if (patrolCache.getPatrolSchedule().isEmpty()) {
                reportException("", "Patrol schedule is empty");
                errorMessage.setValue("Unable to tag this checkpoint as visited. Wait for the next patrol schedule.");
                return;
            }

            DateTimeFormatter defaultDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            //TODO: FORMAT CURRENT DATE AND TIME
            String currentDateTime = defaultDateFormat.format(LocalDateTime.now());
            String currentTime = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now());

            //TODO: INITIALIZE PATROL SCHEDULE TO CURRENT CACHE
            String patrolSchedule = LocalTime.parse(patrolCache.getPatrolSchedule(),
                    DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            //TODO: CHECK AGAIN, IF CURRENT SCHEDULE VISITED RETURN
            if (patrolRepository.checkIfCheckpointIsVisited(patrol.getsNFCIDxxx(), currentDateTime + " " + patrolSchedule) != null){

                errorMessage.setValue("You already tagged this checkpoint as visited.");
                return;

            }

            PatrolLogEntity patrolLogEntity = new PatrolLogEntity();
            patrolLogEntity.setDVisitedx(currentDateTime +" "+ currentTime);
            patrolLogEntity.setDTimeVist(currentTime);
            patrolLogEntity.setSNFCIDxxx(patrol.getsNFCIDxxx());
            patrolLogEntity.setSRemarksx(remarks);
            patrolLogEntity.setSUserIDxx(dataStore.getUserId());
            patrolLogEntity.setCSendStat("0");
            patrolLogEntity.setDSchedule(
                    defaultDateFormat.format(LocalDateTime.now()) + " " + patrolSchedule);

            String cRequestSchedule = scheduleRepository.getCRequestTime(patrolSchedule);

            if (cRequestSchedule.equals("1")){

                //todo: this should be same value with visit schedule 'cRequested'
                patrolLogEntity.setcRequested("2");

                //todo: update visit request's cRequest to '2'
                scheduleRepository.updateRequestSchedule(patrolSchedule, patrol.getsNFCIDxxx());

            }else {

                //todo: this should be same value with visit schedule 'cRequested'
                patrolLogEntity.setcRequested(cRequestSchedule);
            }

            //todo: save to patrol log
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
                                if ( response.getResult().equalsIgnoreCase("error")) {
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

        //todo: clear all data after logout
        patrolRepository.clearPatrolRoute();
        scheduleRepository.clearPatrolSchedule();
        patrolRepository.clearPatrollog();
        scheduleRepository.clearCache();
        patrolCache.clear();

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

    static class CacheNFCSchedule{
        String nfccheckpoint;
        String schedule;

        public CacheNFCSchedule(String nfccheckpoint, String schedule) {
            this.nfccheckpoint = nfccheckpoint;
            this.schedule = schedule;
        }

        public String getNfccheckpoint() {
            return nfccheckpoint;
        }

        public String getSchedule() {
            return schedule;
        }

    }

    /*@SuppressLint({"NewApi", "CheckResult"})
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
    }*/
}