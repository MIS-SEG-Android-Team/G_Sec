package org.rmj.guanzongroup.gsecurity.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchDao;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchEntity;
import org.rmj.guanzongroup.gsecurity.data.room.dao.DAppUserMaster;
import org.rmj.guanzongroup.gsecurity.data.room.entities.EAppUserMaster;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseDao;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;

@Database(
        entities = {
                EAppUserMaster.class,
//                EBuildingVisitRequest.class,
//                EBuildingVisitSchedule.class,
//                ECategory.class,
//                EDailyActivePersonnel.class,
//                ENFCDevice.class,
//                EPatrolRoute.class,
//                EPersonnelPosition.class,
                WarehouseEntity.class,
                BranchEntity.class
        },
        version = 1,
        exportSchema = false)
public abstract class GSecureDB extends RoomDatabase {

    public abstract BranchDao branchDao();
    public abstract WarehouseDao warehouseDao();

}
