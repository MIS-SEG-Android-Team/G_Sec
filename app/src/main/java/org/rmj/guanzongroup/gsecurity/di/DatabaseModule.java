package org.rmj.guanzongroup.gsecurity.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.guanzongroup.gsecurity.data.room.GSecureDB;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchDao;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryDao;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionDao;
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
}
