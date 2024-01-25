package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.patrol.PatrolRouteModel;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PatrolRepository {

    private final ApiService apiService;
    private final PatrolRouteDao patrolRouteDao;
    private final PatrolScheduleDao patrolScheduleDao;

    @Inject
    public PatrolRepository(ApiService apiService,
                            PatrolRouteDao patrolRouteDao,
                            PatrolScheduleDao patrolScheduleDao
    ) {
        this.apiService = apiService;
        this.patrolRouteDao = patrolRouteDao;
        this.patrolScheduleDao = patrolScheduleDao;
    }

    public Observable<BaseResponse<List<PatrolRouteModel>>> getPatrolRouteSchedule(GetPatrolRouteParams params) {
        return apiService.getPatrolRoute(params);
    }

    public void savePatrolRoute(List<PatrolRouteEntity> value) {
        patrolRouteDao.save(value);
    }

    public void savePatrolSchedule(List<PatrolScheduleEntity> value) {
        patrolScheduleDao.save(value);
    }

    public LiveData<List<PatrolRouteEntity>> getPatrolCheckpoints() {
        return patrolRouteDao.getPatrolCheckPoints();
    }

    public LiveData<List<PatrolScheduleEntity>> getPatrolSchedules() {
        return patrolScheduleDao.getPatrolSchedules();
    }


}
