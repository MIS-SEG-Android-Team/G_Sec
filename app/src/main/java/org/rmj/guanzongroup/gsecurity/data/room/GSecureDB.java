package org.rmj.guanzongroup.gsecurity.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchDao;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchEntity;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryDao;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryEntity;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceDao;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionDao;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionEntity;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitDao;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitEntity;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseDao;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;

@Database(
        entities = {
//                EBuildingVisitRequest.class,
//                EBuildingVisitSchedule.class,
//                ECategory.class,
//                EDailyActivePersonnel.class,
//                EPatrolRoute.class,
                RequestVisitEntity.class,
                PatrolLogEntity.class,
                PatrolRouteEntity.class,
                PatrolScheduleEntity.class,
                NFCDeviceEntity.class,
                CategoryEntity.class,
                PositionEntity.class,
                WarehouseEntity.class,
                BranchEntity.class
        },
        version = 2,
        exportSchema = false)
public abstract class GSecureDB extends RoomDatabase {

    public abstract BranchDao branchDao();
    public abstract WarehouseDao warehouseDao();
    public abstract PositionDao positionDao();
    public abstract CategoryDao categoryDao();
    public abstract NFCDeviceDao nfcDeviceDao();
    public abstract PatrolRouteDao patrolRouteDao();
    public abstract PatrolScheduleDao patrolScheduleDao();
    public abstract PatrolLogDao patrolLogDao();
    public abstract RequestVisitDao requestVisitDao();

}
