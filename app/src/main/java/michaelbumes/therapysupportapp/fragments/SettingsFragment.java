package michaelbumes.therapysupportapp.fragments;


import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Random;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.adapter.CustomListViewDrugTime;
import michaelbumes.therapysupportapp.alarms.AlarmMain;
import michaelbumes.therapysupportapp.database.AppDatabase;

import static michaelbumes.therapysupportapp.activities.MainActivity.databaseDrugList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment {
    public static String dailyNotificationTime;
    CardView cardViewDailyNotifikation, cardViewExport, cardViewImprint;
    TextView textViewTime;

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

        cardViewDailyNotifikation = view.findViewById(R.id.card_view_setting_1);
        cardViewExport = view.findViewById(R.id.card_view_setting_2);
        cardViewImprint = view.findViewById(R.id.card_view_setting_3);
        textViewTime = view.findViewById(R.id.text_view_daily_notification_time);

        cardViewDailyNotifikation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime();
            }
        });

        cardViewExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDatabse(getContext());

            }
        });

        cardViewImprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentNavigation.pushFragment(ImprintFragment.newInstance(instanceInt + 1));
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


    private void pickTime(){
        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String curTime = String.format(getResources().getConfiguration().locale,"%02d:%02d", selectedHour, selectedMinute);
                textViewTime.setText(curTime);
                dailyNotificationTime = curTime;
                AlarmMain alarm = new AlarmMain(getContext());
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Zeit wählen");
        mTimePicker.show();

    }


}
