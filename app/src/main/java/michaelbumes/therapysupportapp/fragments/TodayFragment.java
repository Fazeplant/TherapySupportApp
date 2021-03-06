package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.adapter.DrugAdapter;
import michaelbumes.therapysupportapp.adapter.FoodAdapter;
import michaelbumes.therapysupportapp.adapter.MoodAdapter;
import michaelbumes.therapysupportapp.adapter.NoteAdapter;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.entity.MoodDiary;

import static michaelbumes.therapysupportapp.activities.MainActivity.databaseDrugList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends BaseFragment {
    private Drug drug;
    private DrugEventDb drugEventDb;
    private RecyclerView recyclerViewMood, recyclerViewNote, recyclerViewFood;
    private View mView;
    private TextView textViewMood, textViewNote, textViewFood;


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
        mView = view;
        Bundle mBundle = savedInstanceState;


        TextView textViewDrugName = view.findViewById(R.id.drug_name_alarm);
        TextView textViewDosage = view.findViewById(R.id.drug_dosage_alarm);
        ImageView imageViewDrug = view.findViewById(R.id.image_drug_alarm);
        TextView textViewTime = view.findViewById(R.id.alarm_time_alarm);
        TextView textViewEmpty = view.findViewById(R.id.text_view_alarm_empty);
        
        RelativeLayout relativeLayout = view.findViewById(R.id.drug_event_alarm);




        textViewMood = view.findViewById(R.id.text_view_mood_today);
        textViewNote = view.findViewById(R.id.text_view_note_today);
        textViewFood = view.findViewById(R.id.text_view_food_today);



        //Zeigt den nächsten Alarm an
        if (AppDatabase.getAppDatabase(getContext()).drugDao().countDrugs() != 0) {

            drugEventDb = getLatestDrugEventDb();
            if (!drugEventDb.isRegularly()){
                relativeLayout.setVisibility(View.GONE);
                textViewEmpty.setVisibility(View.VISIBLE);
                return;
            }
            String latestAlarmStringPlusId = getLatestAlarmString(drugEventDb);
            String latestAlarmString = latestAlarmStringPlusId.substring(0, 5);
            int latestId = 0;
            try {
                 latestId = Integer.parseInt(latestAlarmStringPlusId.substring(5));
            }catch (Exception e){

            }
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
            switch (drug.getDosageFormId()){
                case 1:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_ampoules));
                    break;
                case 2:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_hashtag));
                    break;
                case 3:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_hashtag));
                    break;
                case 4:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_weight));
                    break;
                case 5:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_inhalator));
                    break;
                case 6:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_syringe));
                    break;
                case 7:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pills));
                    break;
                case 8:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_weight));
                    break;
                case 9:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 10:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_hashtag));
                    break;
                case 11:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                    break;
                case 12:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 13:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_suppositories));
                    break;
                case 14:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_ampoules));
                    break;
                case 15:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_hashtag));
                    break;
                case 16:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_hashtag));
                    break;
                case 17:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_weight));
                    break;
                case 18:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_inhalator));
                    break;
                case 19:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_syringe));
                    break;
                case 20:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pills));
                    break;
                case 21:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_weight));
                    break;
                case 22:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 23:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_hashtag));
                    break;
                case 24:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                    break;
                case 25:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 26:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_suppositories));
                    break;
                case 27:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_ampoules));
                    break;
                case 28:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 29:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pills));
                    break;
                case 30:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                    break;
                case 31:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 32:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 33:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 34:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                    break;
                case 35:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pills));
                    break;
                case 36:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                    break;
                case 37:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_suppositories));
                    break;
                case 38:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                    break;
                case 39:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_drop));
                    break;
                case 40:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                    break;
                case 41:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                    break;
                case 42:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pills));
                    break;
                case 43:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_ampoules));
                    break;
                case 44:
                    imageViewDrug.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_ampoules));
                    break;
            }
            textViewDosage.setText(dosage.get(latestId) + " " + databaseDrugList.dosageFormDao().findById(drug.getDosageFormId()).getDosageFormName());
            textViewTime.setText(latestAlarmString);

        } else {
            relativeLayout.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
        }

        //Drug bearbeiten
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

    private DrugEventDb getLatestDrugEventDb() {
        List<Drug> drugList = AppDatabase.getAppDatabase(getContext()).drugDao().getAll();
        DrugEventDb latestDrugEventDb = null;
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        int currentTimeInMinutes = hourOfDay * 60 + minOfDay;
        int mostRecentAlarmInt = 99999999;


        for (int i = 0; i < drugList.size(); i++) {
            DrugEventDb drugEventDb = AppDatabase.getAppDatabase(getContext()).drugEventDbDao().findById(drugList.get(i).getDrugEventDbId());
            if (drugEventDb.isRegularly()){
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



        }
        if (latestDrugEventDb == null) {
            return getFirstDrugEventDb();
        } else {
            return latestDrugEventDb;
        }

    }


    private String getLatestAlarmString(DrugEventDb drugEventDb) {
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

    private DrugEventDb getFirstDrugEventDb() {
        List<Drug> drugList = AppDatabase.getAppDatabase(getContext()).drugDao().getAll();
        DrugEventDb latestDrugEventDb = AppDatabase.getAppDatabase(getContext()).drugEventDbDao().findById(drugList.get(0).getDrugEventDbId());
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        int currentTimeInMinutes = hourOfDay * 60 + minOfDay;
        int mostRecentAlarmInt = 99999;


        for (int i = 0; i < drugList.size(); i++) {
            DrugEventDb drugEventDb = AppDatabase.getAppDatabase(getContext()).drugEventDbDao().findById(drugList.get(i).getDrugEventDbId());
            if (drugEventDb.isRegularly()){
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



        }
        return latestDrugEventDb;
    }


    private String getFirstAlarmString(DrugEventDb drugEventDb) {
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


    @Override
    public void onResume() {
        super.onResume();

        Calendar calStartOfDay = Calendar.getInstance(TimeZone.getDefault());
        calStartOfDay.setTime(calStartOfDay.getTime());
        calStartOfDay.set(Calendar.HOUR_OF_DAY, 0);
        calStartOfDay.set(Calendar.MINUTE, 0);
        calStartOfDay.set(Calendar.SECOND, 0);
        calStartOfDay.set(Calendar.MILLISECOND, 0);

        Calendar calEndOfDay = Calendar.getInstance(TimeZone.getDefault());
        calEndOfDay.setTime(calEndOfDay.getTime());
        calEndOfDay.set(Calendar.HOUR_OF_DAY, 23);
        calEndOfDay.set(Calendar.MINUTE, 59);
        calEndOfDay.set(Calendar.SECOND, 59);
        calEndOfDay.set(Calendar.MILLISECOND, 999);

        //Alle MoodDiaries für den heutigen Tag
        List<MoodDiary> moodDiaries = AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getAll();

        //Anzeigen der RecyclerViews
        recyclerViewMood = mView.findViewById(R.id.mood_recyler_view);

        recyclerViewMood.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapterMood = new MoodAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewMood.setAdapter(adapterMood);

        recyclerViewNote = mView.findViewById(R.id.note_recyler_view);

        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapterNote = new NoteAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewNote.setAdapter(adapterNote);

        recyclerViewFood = mView.findViewById(R.id.food_recyler_view);

        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapterFood = new FoodAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewFood.setAdapter(adapterFood);

        if (adapterMood.getItemCount() != 0) {
            textViewMood.setVisibility(View.VISIBLE);
        } else {
            textViewMood.setVisibility(View.GONE);

        }
        if (adapterFood.getItemCount() != 0) {
            textViewFood.setVisibility(View.VISIBLE);
        } else {
            textViewFood.setVisibility(View.GONE);

        }
        if (adapterNote.getItemCount() != 0) {
            textViewNote.setVisibility(View.VISIBLE);
        } else {
            textViewNote.setVisibility(View.GONE);

        }

        ItemTouchHelper itemTouchHelperMood = new ItemTouchHelper(simpleItemTouchCallbackMood);
        itemTouchHelperMood.attachToRecyclerView(recyclerViewMood);

        ItemTouchHelper itemTouchHelperNote = new ItemTouchHelper(simpleItemTouchCallbackNote);
        itemTouchHelperNote.attachToRecyclerView(recyclerViewNote);

        ItemTouchHelper itemTouchHelperNoteFood = new ItemTouchHelper(simpleItemTouchCallbackFood);
        itemTouchHelperNoteFood.attachToRecyclerView(recyclerViewFood);

    }
    //Sind für das Wegwischen zum löschen zuständig
    private final ItemTouchHelper.SimpleCallback simpleItemTouchCallbackMood = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((MoodAdapter) recyclerViewMood.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };

    private final ItemTouchHelper.SimpleCallback simpleItemTouchCallbackNote = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((NoteAdapter) recyclerViewNote.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };
    private final ItemTouchHelper.SimpleCallback simpleItemTouchCallbackFood = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((FoodAdapter) recyclerViewFood.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };


}





