package org.rmj.guanzongroup.gsecurity.data.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.guanzongroup.gsecurity.data.room.dao.DAppUserMaster;
import org.rmj.guanzongroup.gsecurity.data.room.entities.EAppUserMaster;
import org.rmj.guanzongroup.gsecurity.data.room.entities.EBuildingVisitRequest;
import org.rmj.guanzongroup.gsecurity.data.room.entities.EBuildingVisitSchedule;
import org.rmj.guanzongroup.gsecurity.data.room.entities.ECategory;
import org.rmj.guanzongroup.gsecurity.data.room.entities.EDailyActivePersonnel;
import org.rmj.guanzongroup.gsecurity.data.room.entities.ENFCDevice;
import org.rmj.guanzongroup.gsecurity.data.room.entities.EPatrolRoute;
import org.rmj.guanzongroup.gsecurity.data.room.entities.EPersonnelPosition;
import org.rmj.guanzongroup.gsecurity.data.room.entities.EWarehouse;

@Database(
        entities = {
                EAppUserMaster.class,
                EBuildingVisitRequest.class,
                EBuildingVisitSchedule.class,
                ECategory.class,
                EDailyActivePersonnel.class,
                ENFCDevice.class,
                EPatrolRoute.class,
                EPersonnelPosition.class,
                EWarehouse.class
        },
        version = 1,
        exportSchema = false
)
public abstract class GSecureDB extends RoomDatabase {
    private static final String TAG = GSecureDB.class.getSimpleName();

    private static GSecureDB instance;

    public abstract DAppUserMaster dAppUserMaster();

        public static synchronized GSecureDB getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
            GSecureDB.class, "GSec_DBF.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "GSec_DBF has been created...");
        }
    };
}
