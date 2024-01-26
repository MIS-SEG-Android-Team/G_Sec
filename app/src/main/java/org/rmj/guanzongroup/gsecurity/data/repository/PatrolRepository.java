package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PostPatrolParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.patrol.PatrolRouteModel;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PatrolRepository {

    private final ApiService apiService;
    private final PatrolRouteDao patrolRouteDao;
    private final PatrolLogDao patrolLogDao;

    @Inject
    public PatrolRepository(ApiService apiService,
                            PatrolRouteDao patrolRouteDao,
                            PatrolLogDao patrolLogDao
    ) {
        this.apiService = apiService;
        this.patrolRouteDao = patrolRouteDao;
        this.patrolLogDao = patrolLogDao;
    }

    public Observable<BaseResponse<List<PatrolRouteModel>>> getPatrolRouteSchedule(GetPatrolRouteParams params) {
        return apiService.getPatrolRoute(params);
    }

    public void savePatrolRoute(List<PatrolRouteEntity> value) {
        patrolRouteDao.save(value);
    }

    public List<PatrolRouteEntity> getPatrolCheckpoints() {
        return patrolRouteDao.getPatrolCheckPoints();
    }

    public PatrolLogEntity getPatrolLog(String nSchedule, String sNFCIDxxx, String date) {
        return patrolLogDao.getPatrolLog(nSchedule, sNFCIDxxx, date);
    }

    public void savePatrolLog(PatrolLogEntity value) {
        patrolLogDao.save(value);
    }

    public void updatePatrolLog(List<PatrolLogEntity> value) {
        patrolLogDao.update(value);
    }

    public List<PatrolLogEntity> getPatrolLogsForPosting(){
        return patrolLogDao.getPatrolLogsForPosting();
    }

    public Observable<BaseResponse<Void>> postPlaceVisited(PostPatrolParams params){
        return apiService.postPlaceVisited(params);
    }
}
