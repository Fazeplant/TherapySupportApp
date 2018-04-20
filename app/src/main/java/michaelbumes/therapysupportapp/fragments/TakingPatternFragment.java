package michaelbumes.therapysupportapp.fragments;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.entity.Drug;

import static michaelbumes.therapysupportapp.activities.MainActivity.databaseDrugList;


/**
 * Created by Michi on 06.03.2018.
 */

public class TakingPatternFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = TakingPatternFragment.class.getName();

    private static final int DAY = 0;
    private static final int CYCLE = 1;
    private static final int NOTHING = -1;
    private static final int DOSAGE = 1;
    private static final int INTERVAL = 2;
    private static final int NUMBER = 3;



    private int dayOrCycleFlag = NOTHING;
    private Drug drug;
    private DrugEvent mDrugEvent;
    private int mTakingPattern = 1;
    private CustomListView customListView1;
    private CustomListView customListView2;
    private CustomListView customListViewHours;
    private View view1;
    private boolean[] allFalse;
    private RadioButton radioButtonDaily;


    private ListView lst1;
    private ListView lst2;
    private ListView lstHour;
    private RadioGroup radioGroup;
    private CheckBox checkBoxMonday, checkBoxTuesday, checkBoxWednesday, checkBoxThursday, checkBoxFriday, checkBoxSaturday, checkBoxSunday;
    private String[] stringAll;
    private String[] stringDuration;
    private String[] stringListCycle1;
    private String[] stringListCycle2;
    private String[] stringListHour1;
    private String[] stringListHour2;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardViewHour;
    private boolean[] weekDays;


    public static TakingPatternFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        TakingPatternFragment fragment = new TakingPatternFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.taking_pattern);

        mDrugEvent = EventBus.getDefault().getStickyEvent(DrugEvent.class);
        drug = mDrugEvent.getDrug();



        weekDays = new boolean[7];
        weekDays[0] = true;
        weekDays[1] = true;
        weekDays[2] = true;
        weekDays[3] = true;
        weekDays[4] = true;
        weekDays[5] = false;
        weekDays[6] = false;

        allFalse = new boolean[7];


        if (Arrays.equals(mDrugEvent.getTakingPatternWeekdays(), allFalse)){
            mDrugEvent.setTakingPatternWeekdays(weekDays);
            EventBus.getDefault().postSticky(mDrugEvent);
        }
        radioButtonDaily = view.findViewById(R.id.radio_button_daily_day);
        RadioButton radioButtonDailyHour = view.findViewById(R.id.radio_button_daily_hour);
        RadioButton radioButtonAllDay = view.findViewById(R.id.radio_all_day);
        RadioButton radioButtonWeekdays = view.findViewById(R.id.radio_button_weekdays);
        RadioButton radioButtonCycle = view.findViewById(R.id.radio_button_cycle);


        checkBoxMonday = view.findViewById(R.id.check_box_taking_pattern_monday);
        checkBoxMonday.setOnClickListener(this);
        checkBoxTuesday = view.findViewById(R.id.check_box_taking_pattern_tuesday);
        checkBoxTuesday.setOnClickListener(this);
        checkBoxWednesday = view.findViewById(R.id.check_box_taking_pattern_wednesday);
        checkBoxWednesday.setOnClickListener(this);
        checkBoxThursday = view.findViewById(R.id.check_box_taking_pattern_thursday);
        checkBoxThursday.setOnClickListener(this);
        checkBoxFriday = view.findViewById(R.id.check_box_taking_pattern_friday);
        checkBoxFriday.setOnClickListener(this);
        checkBoxSaturday = view.findViewById(R.id.check_box_taking_pattern_saturday);
        checkBoxSaturday.setOnClickListener(this);
        checkBoxSunday = view.findViewById(R.id.check_box_taking_pattern_sunday);
        checkBoxSunday.setOnClickListener(this);





        radioGroup = view.findViewById(R.id.radio_group_taking_pattern);

        lst1 = view.findViewById(R.id.list_view_taking_pattern_daily);
        lst2 = view.findViewById(R.id.list_view_taking_pattern_cycle);
        lstHour = view.findViewById(R.id.list_view_taking_pattern_daily_hour);



        cardView1 = view.findViewById(R.id.card_view_taking_pattern_list_daily);
        cardView2 = view.findViewById(R.id.card_view_taking_pattern_list_cycle);
        cardView3 = view.findViewById(R.id.card_view_taking_pattern_check_days);
        cardViewHour = view.findViewById(R.id.card_view_taking_pattern_daily_hour);


        //Setzte alle Strings nach DrugEvent oder falls neues DrugEvent mit Standard Werten
        stringAll = new String[]{"Alle"};
        if (mDrugEvent.getTakingPatternEveryOtherDay() == -1){
            stringDuration = new String[]{"2 Tage"};
        }else {
            stringDuration = new String[]{mDrugEvent.getTakingPatternEveryOtherDay()+" Tage"};

        }
        stringListCycle1 = new String[]{"Tage mit Einnahme", "Tage ohne Einnahme" ,""};
        if (mDrugEvent.getTakingPatternDaysWithIntake() == -1){
            stringListCycle2 = new String[]{"14", "7", ""};

        }else {
            stringListCycle2 = new String[]{String.valueOf(mDrugEvent.getTakingPatternDaysWithIntake()), String.valueOf(mDrugEvent.getTakingPatternDaysWithoutIntake()), ""};

        }
        stringListHour1 = new String[]{"Intervall", "Start", "Anzahl Intervalle", "Dosierung"};
        if (mDrugEvent.getTakingPatternHourNumber() == -1){
            stringListHour2 = new String[]{"4 Stunden", "08:00", "2",  "1 " + databaseDrugList.dosageFormDao().getNameById(drug.getDosageFormId())};
        }else {
            stringListHour2 = new String[]{mDrugEvent.getTakingPatternHourInterval() + " Stunden", mDrugEvent.getTakingPatternHourStart(), String.valueOf(mDrugEvent.getTakingPatternHourNumber()),  "1 " + databaseDrugList.dosageFormDao().getNameById(drug.getDosageFormId())};

        }

        customListView1 = new CustomListView(getActivity(), stringAll, stringDuration);
        customListView2 = new CustomListView(getActivity(), stringListCycle1, stringListCycle2);
        customListViewHours = new CustomListView(getActivity(), stringListHour1, stringListHour2);


        lst1.setAdapter(customListView1);
        lst2.setAdapter(customListView2);
        lstHour.setAdapter(customListViewHours);

        //Anzahl Tage zwischen den Alarmen
        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pickDays("Alle", 3, i);
            }
        });
        //Zyklus
        lst2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        pickDays("Tage mit Einnahme", 5, i);
                        break;
                    case 1:
                        pickDays("Tage ohne Einnahme",5, i);
                        break;
                    case 2:
                        pickDays("Start des Zyklus an Tag",5, i);
                        break;
                }
            }
        });
        //Alarm Intervall
        lstHour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        pickDosage(INTERVAL);
                        break;
                    case 1:
                        pickTime();
                        break;
                    case 2:
                        pickDosage(NUMBER);

                        break;
                    case 3:
                        pickDosage(DOSAGE);
                        break;

                }
            }
        });
        //Wechselt Einahmemuster und löscht die nicht benötigten Werte und setzt standard Werte
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_button_daily_day) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.GONE);
                    cardViewHour.setVisibility(View.GONE);

                    mDrugEvent.setTakingPatternDaysWithIntake(-1);
                    mDrugEvent.setTakingPatternDaysWithoutIntake(-1);
                    mDrugEvent.setTakingPatternEveryOtherDay(-1);
                    mDrugEvent.setTakingPatternHourNumber(-1);
                    mDrugEvent.setTakingPatternHourStart("-1");
                    mDrugEvent.setTakingPatternHourInterval(-1);

                    mTakingPattern = 1;
                } else if (i == R.id.radio_button_daily_hour) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.GONE);
                    cardViewHour.setVisibility(View.VISIBLE);

                    if (mDrugEvent.getTakingPatternHourNumber() != -1){
                        stringListHour2[0] = String.valueOf(mDrugEvent.getTakingPatternHourInterval()) + " Stunden";
                        stringListHour2[1] = String.valueOf(mDrugEvent.getTakingPatternHourStart());
                        stringListHour2[2] = String.valueOf(mDrugEvent.getTakingPatternHourNumber());

                    }else {
                        mDrugEvent.setTakingPatternHourInterval(4);
                        mDrugEvent.setTakingPatternHourStart("08:00");
                        mDrugEvent.setTakingPatternHourNumber(2);

                        stringListHour2[0] = "4 Stunden";
                        stringListHour2[1] = "08:00";
                        stringListHour2[2] = "2";
                    }


                    mDrugEvent.setTakingPatternDaysWithIntake(-1);
                    mDrugEvent.setTakingPatternDaysWithoutIntake(-1);
                    mDrugEvent.setTakingPatternEveryOtherDay(-1);


                    mTakingPattern = 2;


                } else if (i == R.id.radio_all_day) {
                    cardView2.setVisibility(View.GONE);
                    cardView1.setVisibility(View.VISIBLE);
                    cardView3.setVisibility(View.GONE);
                    cardViewHour.setVisibility(View.GONE);

                    if (mDrugEvent.getTakingPatternEveryOtherDay() != -1){
                        stringDuration[0] = String.valueOf(mDrugEvent.getTakingPatternEveryOtherDay()) + " Tage";
                    }else {
                        mDrugEvent.setTakingPatternEveryOtherDay(2);
                        stringDuration[0] = "2";
                    }

                    mDrugEvent.setTakingPatternDaysWithIntake(-1);
                    mDrugEvent.setTakingPatternDaysWithoutIntake(-1);
                    mDrugEvent.setTakingPatternHourNumber(-1);
                    mDrugEvent.setTakingPatternHourStart("-1");
                    mDrugEvent.setTakingPatternHourInterval(-1);


                    mTakingPattern = 3;


                } else if (i == R.id.radio_button_weekdays) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.VISIBLE);
                    cardViewHour.setVisibility(View.GONE);

                    boolean[] eventDays = mDrugEvent.getTakingPatternWeekdays();
                    checkBoxMonday.setChecked(eventDays[0]);
                    checkBoxTuesday.setChecked(eventDays[1]);
                    checkBoxWednesday.setChecked(eventDays[2]);
                    checkBoxThursday.setChecked(eventDays[3]);
                    checkBoxFriday.setChecked(eventDays[4]);
                    checkBoxSaturday.setChecked(eventDays[5]);
                    checkBoxSunday.setChecked(eventDays[6]);




                    mDrugEvent.setTakingPatternDaysWithIntake(-1);
                    mDrugEvent.setTakingPatternDaysWithoutIntake(-1);
                    mDrugEvent.setTakingPatternEveryOtherDay(-1);
                    mDrugEvent.setTakingPatternHourNumber(-1);
                    mDrugEvent.setTakingPatternHourStart("-1");
                    mDrugEvent.setTakingPatternHourInterval(-1);




                    mTakingPattern = 4;


                } else {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.VISIBLE);
                    cardView3.setVisibility(View.GONE);
                    cardViewHour.setVisibility(View.GONE);

                    if (mDrugEvent.getTakingPatternDaysWithIntake() != -1 && mDrugEvent.getTakingPatternDaysWithoutIntake() != -1){
                        stringListCycle2[0] = String.valueOf(mDrugEvent.getTakingPatternDaysWithIntake());
                        stringListCycle2[1] = String.valueOf(mDrugEvent.getTakingPatternDaysWithoutIntake());
                    }else {
                        stringListCycle2[0] = "14";
                        stringListCycle2[1] = "7";
                        mDrugEvent.setTakingPatternDaysWithIntake(14);
                        mDrugEvent.setTakingPatternDaysWithIntakeChange(14);
                        mDrugEvent.setTakingPatternDaysWithoutIntake(7);
                        mDrugEvent.setTakingPatternDaysWithoutIntakeChange(7);
                    }



                    mDrugEvent.setTakingPatternEveryOtherDay(-1);
                    mDrugEvent.setTakingPatternHourNumber(-1);
                    mDrugEvent.setTakingPatternHourStart("-1");
                    mDrugEvent.setTakingPatternHourInterval(-1);


                    mTakingPattern = 5;


                }
                customListView1.notifyDataSetChanged();
                customListView2.notifyDataSetChanged();
                customListViewHours.notifyDataSetChanged();
                lst1.setAdapter(customListView1);
                lst2.setAdapter(customListView2);
                lstHour.setAdapter(customListViewHours);
                mDrugEvent.setTakingPattern(mTakingPattern);
                EventBus.getDefault().postSticky(mDrugEvent);

            }
        });
            //Wechselt auf den richtigen Zustand
            switch (mDrugEvent.getTakingPattern()){
                case 1:
                    radioButtonDaily.performClick();
                    break;
                case 2:
                    radioButtonDailyHour.performClick();
                    break;
                case 3:
                    radioButtonAllDay.performClick();
                    break;
                case 4:
                    radioButtonWeekdays.performClick();
                    break;
                case 5:
                    radioButtonCycle.performClick();
                    break;

        }



    }
    @Override
    public void onClick(View view){
        boolean[] mWeekdays = weekDays;

        try {
            mWeekdays = mDrugEvent.getTakingPatternWeekdays();
        }catch (Exception ignored){

        }
        switch (view.getId()){
            case R.id.check_box_taking_pattern_monday:
                mWeekdays[0] = checkBoxMonday.isChecked();
                break;
            case R.id.check_box_taking_pattern_tuesday:
                mWeekdays[1] = checkBoxTuesday.isChecked();
                break;
            case R.id.check_box_taking_pattern_wednesday:
                mWeekdays[2] = checkBoxWednesday.isChecked();
            case R.id.check_box_taking_pattern_thursday:
                mWeekdays[3] = checkBoxThursday.isChecked();
                break;
            case R.id.check_box_taking_pattern_friday:
                mWeekdays[4] = checkBoxFriday.isChecked();
            case R.id.check_box_taking_pattern_saturday:
                mWeekdays[5] = checkBoxSaturday.isChecked();
                break;
            case R.id.check_box_taking_pattern_sunday:
                mWeekdays[6] = checkBoxSunday.isChecked();
                break;
        }
        if (Arrays.equals(mWeekdays, allFalse)){
            Toast.makeText(getContext(), "Bitte wählen Sie mindestens einen Tag aus", Toast.LENGTH_SHORT).show();
            checkBoxMonday.performClick();
            radioButtonDaily.performClick();
            return;
            
        }
        mDrugEvent.setTakingPatternWeekdays(mWeekdays);
        EventBus.getDefault().postSticky(mDrugEvent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view1 == null) {
            view1 = inflater.inflate(R.layout.fragment_taking_pattern, container, false);
        }
        return view1;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(DrugEvent event){
        drug = event.getDrug();
        mDrugEvent = event;

    }
    private void pickDays(String title, final int takingPattern, final int listItemPosition) {
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
        d.setTitle(title);
        d.setMessage("");
        d.setView(dialogView);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(365);
        if (takingPattern == 3) {
            numberPicker.setMinValue(2);
        }else {
            numberPicker.setMinValue(1);

        }
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            }
        });
        d.setPositiveButton("Fertig", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DrugEvent drugEvent = new DrugEvent();
                drugEvent.setDrug(drug);
                if (takingPattern == 3) {
                    stringDuration[0] = String.valueOf(numberPicker.getValue()) + " Tage";
                    mDrugEvent.setTakingPatternEveryOtherDay(numberPicker.getValue());
                    customListView1.notifyDataSetChanged();
                }
                else if (takingPattern == 5) {
                    switch (listItemPosition) {
                        case 0:
                            stringListCycle2[0] = String.valueOf(numberPicker.getValue());
                            mDrugEvent.setTakingPatternDaysWithIntake(numberPicker.getValue());
                            mDrugEvent.setTakingPatternDaysWithIntakeChange(numberPicker.getValue());
                            break;
                        case 1:
                            stringListCycle2[1] = String.valueOf(numberPicker.getValue());
                            mDrugEvent.setTakingPatternDaysWithoutIntake(numberPicker.getValue());
                            mDrugEvent.setTakingPatternDaysWithoutIntakeChange(numberPicker.getValue());
                            break;

                    }
                    customListView2.notifyDataSetChanged();
                }
                EventBus.getDefault().postSticky(mDrugEvent);
            }
        });
        d.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();

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
                mDrugEvent.setTakingPatternHourStart(curTime);
                List<String> alarmTime = new ArrayList<>();
                alarmTime.add(curTime);
                mDrugEvent.setAlarmTime(alarmTime);
                stringListHour2[1] = curTime;
                customListViewHours.notifyDataSetChanged();
                lstHour.setAdapter(customListViewHours);
                EventBus.getDefault().postSticky(mDrugEvent);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Zeit wählen");
        mTimePicker.show();

    }

    private void pickDosage(final int mode) {
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
        d.setTitle("Wähle Anzahl");
        d.setMessage("");
        d.setView(dialogView);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(50);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            }

        });
        d.setPositiveButton("Fertig", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mode == DOSAGE){
                    List<Integer> mDosage = new ArrayList<>();
                    mDosage.add(numberPicker.getValue());
                    mDrugEvent.setDosage(mDosage);
                    stringListHour2[3] = String.valueOf(numberPicker.getValue()) + " " + databaseDrugList.dosageFormDao().getNameById(drug.getDosageFormId());
                }else if(mode == INTERVAL) {
                    mDrugEvent.setTakingPatternHourInterval(numberPicker.getValue());
                    stringListHour2[0] = String.valueOf(numberPicker.getValue()) + " Stunden";

                }else if (mode == NUMBER){
                    mDrugEvent.setTakingPatternHourNumber(numberPicker.getValue());
                    stringListHour2[2] = String.valueOf(numberPicker.getValue());
                }

                customListViewHours.notifyDataSetChanged();
                lstHour.setAdapter(customListViewHours);
                EventBus.getDefault().postSticky(mDrugEvent);

            }
        });
        d.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();

    }
}
