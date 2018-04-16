package michaelbumes.therapysupportapp.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.util.Random;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;

import static michaelbumes.therapysupportapp.activities.MainActivity.databaseDrugList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment {
    private Button nukeButton;
    private Button createButton;
    private Button testButton;

    public static SettingsFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_settings);
        nukeButton = view.findViewById(R.id.nuke_button);
        createButton=view.findViewById(R.id.create_drug_list);
        testButton = view.findViewById(R.id.test_button_settings);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDatabse(getContext());
            }
        });
        nukeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.getAppDatabase(getContext()).drugEventDbDao().nukeTable();
                AppDatabase.getAppDatabase(getContext()).drugDao().nukeTable();
                databaseDrugList.drugListDao().nukeTable();
                AppDatabase.getAppDatabase(getContext()).moodDiaryDao().nukeTable();
                AppDatabase.destroyInstance();

            }
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressWarnings("resource")
    private static void exportDatabse(Context context) {
        File backupDB = null;
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + context.getPackageName()
                        + "//databases//" + "database" + "";
                File currentDB = new File(data, currentDBPath);
                backupDB = new File(sd, "database");

                if (currentDB.exists()) {

                    FileChannel src = new FileInputStream(currentDB)
                            .getChannel();
                    FileChannel dst = new FileOutputStream(backupDB)
                            .getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("*/*");
/*        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{email});*/

        Random r = new Random();

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Datenbank " + r.nextInt());
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(backupDB));
        context.startActivity(Intent.createChooser(emailIntent, "Datenbank exportieren"));
    }

}
