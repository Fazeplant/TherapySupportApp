package michaelbumes.therapysupportapp.utils;

/**
 * Created by Michi on 16.01.2018.
 */

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;


public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, String drugName, String drugManufacturer) {
        PopulateDbAsync task = new PopulateDbAsync(db, drugName, drugManufacturer);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db, String drugName, int drugManufacturer) {
        populateWithTestData(db, drugName,drugManufacturer);
    }

    private static Drug addDrug(final AppDatabase db, Drug drug) {
        db.drugDao().insertAll(drug);
        return drug;
    }

    private static void populateWithTestData(AppDatabase db, String drugName, int drugManufacturer) {
        Drug drug = new Drug();
        drug.setDrugName(drugName);
        drug.setManufacturerId(drugManufacturer);
        addDrug(db, drug);

        List<Drug> drugList = db.drugDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + drugList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private String mDrugName;
        private int mDrugManufacturer;

        PopulateDbAsync(AppDatabase db, String drugName, String drugManufacturer) {
            mDb = db;
            String mDrugName = drugName;
            String mDrugManufacturer = drugManufacturer;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb, mDrugName, mDrugManufacturer);
            return null;
        }

    }
}
