package michaelbumes.therapysupportapp.fragments;

import android.app.AlertDialog;
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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.entity.Drug;

/**
 * Created by Michi on 06.03.2018.
 */

public class TakingPatternFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = TakingPatternFragment.class.getName();

    private static final int DAY = 0;
    private static final int CYCLE = 1;
    private static final int NOTHING = -1;
    private int dayOrCycleFlag = NOTHING;
    private Drug drug;
    private DrugEvent mDrugEvent;
    private int mTakingPattern = 1;
    CustomListView customListView1, customListView2;
    private View view1;
    private boolean[] allFalse;
    private RadioButton radioButtonDaily;




    ListView lst1, lst2;
    RadioGroup radioGroup;
    private CheckBox checkBoxMonday, checkBoxTuesday, checkBoxWednesday, checkBoxThursday, checkBoxFriday, checkBoxSaturday, checkBoxSunday;
    String[] stringAll, stringDuration, stringListCycle1, stringListCycle2;
    CardView cardView1, cardView2, cardView3;
    boolean[] weekDays;


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
        //onCheckboxClicked(view);

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

        cardView1 = view.findViewById(R.id.card_view_taking_pattern_list_daily);
        cardView2 = view.findViewById(R.id.card_view_taking_pattern_list_cycle);
        cardView3 = view.findViewById(R.id.card_view_taking_pattern_check_days);

        stringAll = new String[]{"Alle"};
        stringDuration = new String[]{"2 Tage"};
        stringListCycle1 = new String[]{"Tage mit Einnahme", "Tage ohne Einnahme", "Start des Zyklus an Tag"};
        stringListCycle2 = new String[]{"14", "7", "1"};

        customListView1 = new CustomListView(getActivity(), stringAll, stringDuration);
        customListView2 = new CustomListView(getActivity(), stringListCycle1, stringListCycle2);


        lst1.setAdapter(customListView1);
        lst2.setAdapter(customListView2);

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pickDays("Alle", 3, i);
            }
        });

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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_button_daily_day) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.GONE);

                    mDrugEvent.setTakingPatternDaysWithIntake(-1);
                    mDrugEvent.setTakingPatternDaysWithOutIntake(-1);
                    mDrugEvent.setTakingPatternEveryOtherDay(-1);
                    mDrugEvent.setTakingPatternStart(1);

                    mTakingPattern = 1;
                } else if (i == R.id.radio_button_daily_hour) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.GONE);


                    mDrugEvent.setTakingPatternDaysWithIntake(-1);
                    mDrugEvent.setTakingPatternDaysWithOutIntake(-1);
                    mDrugEvent.setTakingPatternEveryOtherDay(-1);
                    mDrugEvent.setTakingPatternStart(1);

                    mTakingPattern = 2;


                } else if (i == R.id.radio_all_day) {
                    cardView2.setVisibility(View.GONE);
                    cardView1.setVisibility(View.VISIBLE);
                    cardView3.setVisibility(View.GONE);


                    mDrugEvent.setTakingPatternDaysWithIntake(-1);
                    mDrugEvent.setTakingPatternDaysWithOutIntake(-1);
                    mDrugEvent.setTakingPatternEveryOtherDay(2);
                    mDrugEvent.setTakingPatternStart(1);

                    mTakingPattern = 3;


                } else if (i == R.id.radio_button_weekdays) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.VISIBLE);


                    mDrugEvent.setTakingPatternDaysWithIntake(-1);
                    mDrugEvent.setTakingPatternDaysWithOutIntake(-1);
                    mDrugEvent.setTakingPatternEveryOtherDay(-1);
                    mDrugEvent.setTakingPatternStart(1);



                    mTakingPattern = 4;


                } else {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.VISIBLE);
                    cardView3.setVisibility(View.GONE);


                    mDrugEvent.setTakingPatternDaysWithIntake(14);
                    mDrugEvent.setTakingPatternDaysWithOutIntake(7);
                    mDrugEvent.setTakingPatternEveryOtherDay(-1);
                    mDrugEvent.setTakingPatternStart(1);

                    mTakingPattern = 5;


                }

                mDrugEvent.setTakingPattern(mTakingPattern);
                EventBus.getDefault().postSticky(mDrugEvent);

            }
        });



    }
    @Override
    public void onClick(View view){
        boolean[] mWeekdays = weekDays;

        try {
            mWeekdays = mDrugEvent.getTakingPatternWeekdays();
        }catch (Exception e){

        }
        switch (view.getId()){
            case R.id.check_box_taking_pattern_monday:
                if (checkBoxMonday.isChecked()){
                    mWeekdays[0] = true;
                }else {
                    mWeekdays[0] = false;

                }
                break;
            case R.id.check_box_taking_pattern_tuesday:
                if (checkBoxTuesday.isChecked()){
                    mWeekdays[1] = true;
                }else {
                    mWeekdays[1] = false;

                }
                break;
            case R.id.check_box_taking_pattern_wednesday:
                if (checkBoxWednesday.isChecked()){
                    mWeekdays[2] = true;
                }else {
                    mWeekdays[2] = false;

                }
            case R.id.check_box_taking_pattern_thursday:
                if (checkBoxThursday.isChecked()){
                    mWeekdays[3] = true;
                }else {
                    mWeekdays[3] = false;

                }
                break;
            case R.id.check_box_taking_pattern_friday:
                if (checkBoxFriday.isChecked()){
                    mWeekdays[4] = true;
                }else {
                    mWeekdays[4] = false;

                }
            case R.id.check_box_taking_pattern_saturday:
                if (checkBoxSaturday.isChecked()){
                    mWeekdays[5] = true;
                }else {
                    mWeekdays[5] = false;

                }
                break;
            case R.id.check_box_taking_pattern_sunday:
                if (checkBoxSunday.isChecked()){
                    mWeekdays[6] = true;
                }else {
                    mWeekdays[6] = false;

                }
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
    public void pickDays(String title, final int takingPattern, final int listItemPosition) {
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
        d.setTitle(title);
        d.setMessage("");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
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
                        case 1:
                            stringListCycle2[1] = String.valueOf(numberPicker.getValue());
                            mDrugEvent.setTakingPatternDaysWithOutIntake(numberPicker.getValue());
                        case 2:
                            stringListCycle2[2] = String.valueOf(numberPicker.getValue());
                            mDrugEvent.setTakingPatternStart(numberPicker.getValue());


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
}
