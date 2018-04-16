package michaelbumes.therapysupportapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.huma.room_for_asset.RoomAsset;

import michaelbumes.therapysupportapp.TimestampConverter;
import michaelbumes.therapysupportapp.dao.DosageFormDao;
import michaelbumes.therapysupportapp.dao.DrugListDao;
import michaelbumes.therapysupportapp.entity.DosageForm;
import michaelbumes.therapysupportapp.entity.DrugList;

/**
 * Created by Michi on 11.04.2018.
 */
@Database(entities = {DrugList.class, DosageForm.class}, version = 2)
@TypeConverters(TimestampConverter.class)
public abstract class DatabaseDrugList extends RoomDatabase {

    private static DatabaseDrugList INSTANCE;

    public abstract DrugListDao drugListDao();
    public abstract DosageFormDao dosageFormDao();


    public static DatabaseDrugList getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    RoomAsset
                            .databaseBuilder(context.getApplicationContext(), DatabaseDrugList.class, "drug_list.db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }




}
