package org.rmj.guanzongroup.gsecurity.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.guanzongroup.gsecurity.data.room.GSecureDB;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchDao;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryDao;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs.PatrolLogDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteDao;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleDao;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionDao;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitDao;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static GSecureDB provideGSecureDB(Application application) {
        return Room.databaseBuilder(application, GSecureDB.class, "GSec_DBF.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                })
                .build();
    }

    @Provides
    @Singleton
    public static BranchDao provideBranchDao(GSecureDB gSecureDB) {
        return gSecureDB.branchDao();
    }

    @Provides
    @Singleton
    public static WarehouseDao provideWarehouseDao(GSecureDB gSecureDB) {
        return gSecureDB.warehouseDao();
    }

    @Provides
    @Singleton
    public static PositionDao providePositionDao(GSecureDB gSecureDB) {
        return gSecureDB.positionDao();
    }

    @Provides
    @Singleton
    public static CategoryDao provideCategoryDao(GSecureDB gSecureDB) {
        return gSecureDB.categoryDao();
    }

    @Provides
    @Singleton
    public static NFCDeviceDao provideNFCDeviceDao(GSecureDB gSecureDB) {
        return gSecureDB.nfcDeviceDao();
    }

    @Provides
    @Singleton
    public static PatrolRouteDao providePatrolRouteDao(GSecureDB gSecureDB) {
        return gSecureDB.patrolRouteDao();
    }

    @Provides
    @Singleton
    public static PatrolScheduleDao providePatrolScheduleDao(GSecureDB gSecureDB) {
        return gSecureDB.patrolScheduleDao();
    }

    @Provides
    @Singleton
    public static PatrolLogDao providePatrolLogDao(GSecureDB gSecureDB) {
        return gSecureDB.patrolLogDao();
    }

    @Provides
    @Singleton
    public static RequestVisitDao provideRequestVisitDao(GSecureDB gSecureDB) {
        return gSecureDB.requestVisitDao();
    }
}
