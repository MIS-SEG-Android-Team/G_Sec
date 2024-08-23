package org.rmj.guanzongroup.gsecurity.data.repository;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;
import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolUpdateCache;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepatrolroute.UpdatePatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepatrolschedule.UpdatePatrolScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepersonnel.UpdatePatrolPersonnelParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.personnelpatrol.PersonnelPatrolModel;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

public class ScheduleRepository {

    private final ApiService apiService;
    private final PatrolScheduleDao patrolScheduleDao;
    private final PatrolSchedulerCache patrolSchedulerCache;
    private final PatrolUpdateCache patrolUpdateCache;

    @SuppressLint("NewApi")
    private final DateTimeFormatter dateTimeFormatter =
            new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern(DEFAULT_TIME_FORMAT)
            .toFormatter(Locale.ENGLISH);

    @Inject
    public ScheduleRepository(
            ApiService apiService,
            PatrolScheduleDao patrolScheduleDao,
            PatrolSchedulerCache patrolSchedulerCache,
            PatrolUpdateCache patrolUpdateCache
    ) {
        this.apiService = apiService;
        this.patrolSchedulerCache = patrolSchedulerCache;
        this.patrolScheduleDao = patrolScheduleDao;
        this.patrolUpdateCache = patrolUpdateCache;
    }

    public Observable<BaseResponse<Void>> addNewSchedule(CreateScheduleParams params) {
        return apiService.addNewSchedule(params);
    }

    public Observable<BaseResponse<List<PersonnelPatrolModel>>> getPatrolRouteForUpdate(GetPatrolRouteParams params) {
        return apiService.getPatrolRouteForUpdate(params);
    }

    public Observable<BaseResponse<Void>> updateSchedule(UpdatePatrolScheduleParams params) {
        return apiService.updateSchedule(params);
    }

    public Observable<BaseResponse<Void>> updatePatrolRoute(UpdatePatrolRouteParams params) {
        return apiService.updatePatrolRoute(params);
    }

    public Observable<BaseResponse<Void>> updatePatrolPersonnel(UpdatePatrolPersonnelParams params) {
        return apiService.updatePatrolPersonnel(params);
    }

    public void createNewPatrolScheduleToCache(CreateScheduleParams value) {
        patrolSchedulerCache.setPatrolSchedule(value);
    }

    public CreateScheduleParams getPatrolScheduleFromCache() {
        return patrolSchedulerCache.getPatrolSchedule();
    }

    public void updatePatrolScheduleToCache(CreateScheduleParams value) {
        patrolSchedulerCache.setPatrolSchedule(value);
    }

    public void savePatrolSchedule(List<PatrolScheduleEntity> value) {
        patrolScheduleDao.save(value);
    }

    public void updateRequestSchedule(String schedule){
        patrolScheduleDao.updateCRequest(schedule);
    }

    public String getCRequestTime(String schedule){
        return patrolScheduleDao.getCRequest(schedule);
    }

    public String getRecentSchedule(String schedule){
        return patrolScheduleDao.getRecentSchedule(schedule);
    }

    public List<PatrolScheduleEntity> getPatrolScheduleList() {
        return patrolScheduleDao.getPatrolScheduleList();
    }

    public void setPatrolUpdateCache(PersonnelPatrolModel value) {
        patrolUpdateCache.setPatrolUpdateSchedule(value);
    }

    public PersonnelPatrolModel getPatrolRouteForUpdate() {
        return patrolUpdateCache.getPatrolScheduleForUpdate();
    }

    public void updatePatrolRouteForUpdate(PersonnelPatrolModel value) {
        patrolUpdateCache.clearCache();
        patrolUpdateCache.setPatrolUpdateSchedule(value);
    }

    public void clearCache(){
        patrolUpdateCache.clearCache();
        patrolSchedulerCache.clearCache();
    }
}
