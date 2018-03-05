package michaelbumes.therapysupportapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import michaelbumes.therapysupportapp.dao.DosageFormDao;
import michaelbumes.therapysupportapp.dao.DrugListDao;
import michaelbumes.therapysupportapp.dao.ManufacturerDao;
import michaelbumes.therapysupportapp.dao.MoodDiaryDao;
import michaelbumes.therapysupportapp.entity.DosageForm;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.dao.DrugDao;
import michaelbumes.therapysupportapp.entity.DrugList;
import michaelbumes.therapysupportapp.entity.Manufacturer;
import michaelbumes.therapysupportapp.entity.MoodDiary;

/**
 * Created by Michi on 16.01.2018.
 */
@Database(entities = {Drug.class, MoodDiary.class, DrugList.class, DosageForm.class, Manufacturer.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public abstract DrugDao drugDao();
    public abstract MoodDiaryDao moodDiaryDao();
    public abstract DrugListDao drugListDao();
    public abstract DosageFormDao dosageFormDao();
    public abstract ManufacturerDao manufacturerDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
