package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.adapter.DrugAdapter;
import michaelbumes.therapysupportapp.adapter.MoodAdapter;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.entity.MoodDiary;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends BaseFragment {
    private TextView textViewDrugName, textViewDosage, textViewTime, textViewEmpty;
    private Drug drug;
    private DrugEventDb drugEventDb;
    private Calendar calStartOfDay, calEndOfDay;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MoodDiary> moodDiaries;


    public static TodayFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_today);
        Button testButton = view.findViewById(R.id.today_test_button);
        textViewDrugName = view.findViewById(R.id.drug_name_alarm);
        textViewDosage = view.findViewById(R.id.drug_dosage_alarm);
        textViewTime = view.findViewById(R.id.alarm_time_alarm);
        textViewEmpty = view.findViewById(R.id.text_view_alarm_empty);
        RelativeLayout relativeLayout = view.findViewById(R.id.drug_event_alarm);

        calStartOfDay = Calendar.getInstance(TimeZone.getDefault());
        calStartOfDay.setTime(calStartOfDay.getTime()); // compute start of the day for the timestamp
        calStartOfDay.set(Calendar.HOUR_OF_DAY, 0);
        calStartOfDay.set(Calendar.MINUTE, 0);
        calStartOfDay.set(Calendar.SECOND, 0);
        calStartOfDay.set(Calendar.MILLISECOND, 0);

        calEndOfDay = Calendar.getInstance(TimeZone.getDefault());
        calEndOfDay.setTime(calEndOfDay.getTime()); // compute start of the day for the timestamp
        calEndOfDay.set(Calendar.HOUR_OF_DAY, 23);
        calEndOfDay.set(Calendar.MINUTE, 59);
        calEndOfDay.set(Calendar.SECOND, 59);
        calEndOfDay.set(Calendar.MILLISECOND, 999);

        moodDiaries = AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getAll();


        recyclerView = view.findViewById(R.id.mood_recyler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MoodAdapter(moodDiaries);
        recyclerView.setAdapter(adapter);


        if (AppDatabase.getAppDatabase(getContext()).drugDao().countDrugs() != 0) {
            textViewEmpty.setVisibility(View.GONE);
            drugEventDb = getLatestDrugEventDb();
            String latestAlarmStringPlusId = getLatestAlarmString(drugEventDb);
            String latestAlarmString = latestAlarmStringPlusId.substring(0, 5);
            int latestId = Integer.parseInt(latestAlarmStringPlusId.substring(5));
            String dosageString = drugEventDb.getDosage();

            String replaceDosage1 = dosageString.replace("[", "");
            String replaceDosage2 = replaceDosage1.replace("]", "");
            String replaceDosage3 = replaceDosage2.replace(" ", "");
            List<String> arrayList = new ArrayList<String>(Arrays.asList(replaceDosage3.split(",")));
            List<Integer> dosage = new ArrayList<Integer>();
            for (String i : arrayList) {
                dosage.add(Integer.parseInt(i.trim()));
            }

            drug = AppDatabase.getAppDatabase(getContext()).drugDao().findByDrugEventDbId(drugEventDb.getId());


            textViewDrugName.setText(drug.getDrugName());
            textViewDosage.setText(dosage.get(latestId) + " " + AppDatabase.getAppDatabase(getContext()).dosageFormDao().findById(drug.getDosageFormId()).getDosageFormName());
            textViewTime.setText(latestAlarmString);

        } else {
            relativeLayout.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
        }
        if (AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getFromTable(calStartOfDay.getTime(), calEndOfDay.getTime()).size() != 0) {
            List<MoodDiary> moodDiaryToday = AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getFromTable(calStartOfDay.getTime(), calEndOfDay.getTime());
            for (int i = 0; i < moodDiaryToday.size(); i++) {
                if (moodDiaryToday.get(i).getArtID() == 1) {


                }
            }


        }


        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    List<MoodDiary> moodDiaryToday = AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getFromTable(calStartOfDay.getTime(), calEndOfDay.getTime());
                    List<MoodDiary> moodDiary2 = AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getAll();

                    Toast.makeText(getContext(), "dfr", Toast.LENGTH_SHORT).show();


                } catch (Exception e) {

                }


            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragNavController mFragmentNavigation = ((MainActivity) getContext()).getmNavController();

                DrugEvent drugEvent = new DrugEvent();

                drugEvent = DrugAdapter.convertDrugEventDbToDrugEvent(drugEvent, drugEventDb, drug);

                EventBus.getDefault().removeAllStickyEvents();
                EventBus.getDefault().postSticky(drugEvent);
                mFragmentNavigation.pushFragment(DrugFragment.newInstance(instanceInt + 1));

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    public DrugEventDb getLatestDrugEventDb() {
        List<Drug> drugList = AppDatabase.getAppDatabase(getContext()).drugDao().getAll();
        DrugEventDb latestDrugEventDb = null;
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        int currentTimeInMinutes = hourOfDay * 60 + minOfDay;
        int mostRecentAlarmInt = 99999999;


        for (int i = 0; i < drugList.size(); i++) {
            DrugEventDb drugEventDb = AppDatabase.getAppDatabase(getContext()).drugEventDbDao().findById(drugList.get(i).getDrugEventDbId());
            String replaceAlarmTime1 = drugEventDb.getAlarmTime().replace("[", "");
            String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
            String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

            List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));
            for (int j = 0; j < alarmTime.size(); j++) {
                int hr = Integer.parseInt(alarmTime.get(j).substring(0, 2));
                int min = Integer.parseInt(alarmTime.get(j).substring(3, 5));
                int alarmTimeInMinutes = hr * 60 + min;
                if (currentTimeInMinutes < alarmTimeInMinutes) {
                    if (alarmTimeInMinutes < mostRecentAlarmInt) {
                        mostRecentAlarmInt = alarmTimeInMinutes;
                        latestDrugEventDb = drugEventDb;

                    }

                }

            }


        }
        if (latestDrugEventDb == null) {
            return getFirstDrugEventDb();
        } else {
            return latestDrugEventDb;
        }

    }


    public String getLatestAlarmString(DrugEventDb drugEventDb) {
        String alarmTimeString = drugEventDb.getAlarmTime();
        String replaceAlarmTime1 = alarmTimeString.replace("[", "");
        String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
        String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

        List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));

        String mostRecentAlarmString = null;


        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        int currentTimeInMinutes = hourOfDay * 60 + minOfDay;
        int mostRecentAlarmInt = 99999999;

        for (int i = 0; i < alarmTime.size(); i++) {
            int hr = Integer.parseInt(alarmTime.get(i).substring(0, 2));
            int min = Integer.parseInt(alarmTime.get(i).substring(3, 5));

            String hrString = String.valueOf(hr);
            String minString = String.valueOf(min);
            int alarmTimeInMinutes = hr * 60 + min;
            if (currentTimeInMinutes < alarmTimeInMinutes) {
                if (alarmTimeInMinutes < mostRecentAlarmInt) {
                    mostRecentAlarmInt = alarmTimeInMinutes;
                    if (hr < 10) {
                        hrString = "0" + String.valueOf(hr);

                    }
                    if (min < 10) {
                        minString = "0" + String.valueOf(min);
                    }
                    mostRecentAlarmString = hrString + ":" + minString + "" + String.valueOf(i);
                }
            }
        }
        if (mostRecentAlarmString == null) {
            return getFirstAlarmString(drugEventDb);
        }
        return mostRecentAlarmString;

    }

    public DrugEventDb getFirstDrugEventDb() {
        List<Drug> drugList = AppDatabase.getAppDatabase(getContext()).drugDao().getAll();
        DrugEventDb latestDrugEventDb = AppDatabase.getAppDatabase(getContext()).drugEventDbDao().findById(drugList.get(0).getDrugEventDbId());
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        int currentTimeInMinutes = hourOfDay * 60 + minOfDay;
        int mostRecentAlarmInt = 99999;


        for (int i = 0; i < drugList.size(); i++) {
            DrugEventDb drugEventDb = AppDatabase.getAppDatabase(getContext()).drugEventDbDao().findById(drugList.get(i).getDrugEventDbId());
            String replaceAlarmTime1 = drugEventDb.getAlarmTime().replace("[", "");
            String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
            String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

            List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));
            for (int j = 0; j < alarmTime.size(); j++) {
                int hr = Integer.parseInt(alarmTime.get(j).substring(0, 2));
                int min = Integer.parseInt(alarmTime.get(j).substring(3, 5));
                int alarmTimeInMinutes = hr * 60 + min;
                if (currentTimeInMinutes > alarmTimeInMinutes) {
                    if (alarmTimeInMinutes < mostRecentAlarmInt) {
                        mostRecentAlarmInt = alarmTimeInMinutes;
                        latestDrugEventDb = drugEventDb;

                    }

                }

            }


        }
        return latestDrugEventDb;
    }


    public String getFirstAlarmString(DrugEventDb drugEventDb) {
        String alarmTimeString = drugEventDb.getAlarmTime();
        String replaceAlarmTime1 = alarmTimeString.replace("[", "");
        String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
        String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

        List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));

        String mostRecentAlarmString = alarmTime.get(0);


        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        int currentTimeInMinutes = hourOfDay * 60 + minOfDay;
        int mostRecentAlarmInt = 9999;

        for (int i = 0; i < alarmTime.size(); i++) {
            int hr = Integer.parseInt(alarmTime.get(i).substring(0, 2));
            int min = Integer.parseInt(alarmTime.get(i).substring(3, 5));

            String hrString = String.valueOf(hr);
            String minString = String.valueOf(min);
            int alarmTimeInMinutes = hr * 60 + min;
            if (currentTimeInMinutes > alarmTimeInMinutes) {
                if (alarmTimeInMinutes < mostRecentAlarmInt) {
                    mostRecentAlarmInt = alarmTimeInMinutes;
                    if (hr < 10) {
                        hrString = "0" + String.valueOf(hr);

                    }
                    if (min < 10) {
                        minString = "0" + String.valueOf(min);
                    }
                    mostRecentAlarmString = hrString + ":" + minString + "" + String.valueOf(i);
                }
            }
        }
        return mostRecentAlarmString;
    }

    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


}





