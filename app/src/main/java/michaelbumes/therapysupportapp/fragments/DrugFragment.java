package michaelbumes.therapysupportapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.adapter.CustomListViewDrugTime;
import michaelbumes.therapysupportapp.alarms.AlarmMain;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;


public class DrugFragment extends BaseFragment implements NumberPicker.OnValueChangeListener {
    private static final String TAG = DrugFragment.class.getName();
    private static final int REGULARLY = 0;
    private static final int ONLY_WHEN_REQUIRED = 1;
    private int kindOfTakingFlag = REGULARLY;
    private View view1;

    private Drug drug;


    FragNavController mFragmentNavigation;

    static Dialog d ;




    //exeriemt
    private int postitionsCheck = 1;

    private CustomListViewDrugTime customListViewDrugTime;



    private ListView lst ,lst2, lst3, lst4, lstDrugTime;
    private  Button addTimeButton;

    private  String[] stringList1;
    private   String[] stringList2;
    private    String[] stringList3;
    private   String[] stringList4;
    private   String[] stringList5;
    private  String[] stringList6;
    private   String[] stringList7;
    private  String[] stringList8;
    private  ArrayList<String> stringTime;
    private  ArrayList<String> stringDosage;
    private  ArrayList<String> stringDosageForm;
    private String[] tempString;

    List<String> mAlarmTime;
    List<Integer> mDosage;
    List<String> mTimeList;

    private   RunningTimeFragment runningTimeFragment = null;
    private  DrugDetailFragment drugDetailFragment = null;
    private  TakingPatternFragment takingPatternFragment = null;
    private   AlarmFragment alarmFragment = null;
    private DrugEvent mDrugEvent;

    private CustomListView customListView1,customListView2, customListView3, customListView4;



    private    CardView cardView1, cardView2, cardView3, cardViewDrugTime;
    private static int mCurCheckPosition;

    public static DrugFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        DrugFragment fragment = new DrugFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle(R.string.drug);

        mFragmentNavigation = ((MainActivity) getActivity()).getmNavController();

        mDrugEvent = EventBus.getDefault().getStickyEvent(DrugEvent.class);
        drug = mDrugEvent.getDrug();

        if (mDrugEvent.getDosage().isEmpty()){
            mDosage = new ArrayList<Integer>();
            mDosage.add(1);
            mDrugEvent.setDosage(mDosage);

            mAlarmTime = new ArrayList<String>();
            mAlarmTime.add("08:00");
            mDrugEvent.setAlarmTime(mAlarmTime);

            EventBus.getDefault().postSticky(mDrugEvent);
        }


        setStrings();



/*
        numberDosage = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};

        //Number Picker
        spinnerDosage = view.findViewById(R.id.spinner_dosage);
        spinnerDosage = new Spinner(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numberDosage);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDosage.setAdapter(adapter);
*/

        //tempDrug



        addTimeButton = view.findViewById(R.id.add_time_button);


        cardView2 = view.findViewById(R.id.card_view_drug_2);
        cardView3 = view.findViewById(R.id.card_view_drug_3);
        cardViewDrugTime = view.findViewById(R.id.card_view_drug_time);


        lst = view.findViewById(R.id.list_view_drug);
        lst2 = view.findViewById(R.id.list_view_drug_2);
        lst3 = view.findViewById(R.id.list_view_drug_3);
        lst4 = view.findViewById(R.id.list_view_drug_4);

        lstDrugTime = view.findViewById(R.id.list_view_drug_time);





        customListView1 = new CustomListView(getActivity(), stringList1, stringList2);
        customListView2 = new CustomListView(getActivity(), stringList3, stringList4);
        customListView3 = new CustomListView(getActivity(), stringList5, stringList6);
        customListView4 = new CustomListView(getActivity(), stringList7, stringList8);

        customListViewDrugTime = new CustomListViewDrugTime(getActivity(), stringTime, stringDosage, stringDosageForm);






        lst.setAdapter(customListView1);
        lst2.setAdapter(customListView2);
        lst3.setAdapter(customListView3);
        lst4.setAdapter(customListView4);
        lstDrugTime.setAdapter(customListViewDrugTime);









        justifyListViewHeightBasedOnChildren(lst);
        justifyListViewHeightBasedOnChildren(lst2);
        justifyListViewHeightBasedOnChildren(lst3);
        justifyListViewHeightBasedOnChildren(lst4);
        justifyListViewHeightBasedOnChildren(lstDrugTime);







        addTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //TODO zwei mal selbe Zeit machte keinen sinn
                    mDosage = new ArrayList<Integer>(mDrugEvent.getDosage());
                    mDosage.add(1);
                    mDrugEvent.setDosage(mDosage);
                    stringTime.add("08:00");


                    mAlarmTime = new ArrayList<String>(mDrugEvent.getAlarmTime());

                    mAlarmTime.add("08:00");

                    mDrugEvent.setAlarmTime(mAlarmTime);

                    EventBus.getDefault().postSticky(mDrugEvent);


                    stringDosage.add("1");
                    stringDosageForm.add(AppDatabase.getAppDatabase(getContext()).dosageFormDao().getNamebyId(drug.getDosageFormId()));
                    justifyListViewHeightBasedOnChildren(lstDrugTime);
                    customListViewDrugTime.notifyDataSetChanged();
                }

        });

        customListViewDrugTime.setImageButtonOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = lstDrugTime.getPositionForView(view);
                stringTime.remove(position);
                stringDosageForm.remove(position);
                stringDosage.remove(position);
                List<Integer> tempInt = new ArrayList<Integer>(mDrugEvent.getDosage());
                List<String> tempString = new ArrayList<String>(mDrugEvent.getAlarmTime());
                tempInt.remove(position);
                tempString.remove(position);
                mDrugEvent.setAlarmTime(tempString);
                mDrugEvent.setDosage(tempInt);
                EventBus.getDefault().postSticky(mDrugEvent);

                customListViewDrugTime.notifyDataSetChanged();
                lstDrugTime.setAdapter(customListViewDrugTime);
                justifyListViewHeightBasedOnChildren(lstDrugTime);

            }
        });
        customListViewDrugTime.setDurgTimeOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = lstDrugTime.getPositionForView(view);
                pickTime(position);
            }
        });
        customListViewDrugTime.setDosageFormeOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = lstDrugTime.getPositionForView(view);
                pickDosage(position);

            }
        });
        customListViewDrugTime.setDosageOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = lstDrugTime.getPositionForView(view);
                pickDosage(position);

            }
        });


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        if (drugDetailFragment == null){
                            drugDetailFragment = DrugDetailFragment.newInstance(instanceInt+1);
                        }
                        mFragmentNavigation.pushFragment(drugDetailFragment);
                        customListView1.notifyDataSetChanged();
                        customListView2.notifyDataSetChanged();
                        break;
                    case 1:
                        if (mDrugEvent.isRegularly()) {

                            kindOfTakingFlag = ONLY_WHEN_REQUIRED;
                            cardViewDrugTime.setVisibility(View.GONE);
                            addTimeButton.setVisibility(View.GONE);
                            stringList2[1] = "Nur bei Bedarf";
                            List<String> alarmTimeList = new ArrayList<String>(mDrugEvent.getAlarmTime());
                            List<Integer> dosageList = new ArrayList<Integer>(mDrugEvent.getDosage());
                            alarmTimeList.clear();
                            dosageList.clear();
                            mDrugEvent.setAlarmTime(alarmTimeList);
                            mDrugEvent.setDosage(dosageList);
                            stringTime.clear();
                            stringDosageForm.clear();
                            stringDosage.clear();

                            mDrugEvent.setRegularly(false);
                            cardView2.setVisibility(View.GONE);
                            cardView3.setVisibility(View.GONE);

                            break;
                        } else {
                            if (mDrugEvent.getTakingPattern() == 2){
                                cardViewDrugTime.setVisibility(View.GONE);
                                addTimeButton.setVisibility(View.GONE);
                            }else {
                                cardViewDrugTime.setVisibility(View.VISIBLE);
                                addTimeButton.setVisibility(View.VISIBLE);
                                addTimeButton.performClick();
                            }

                            kindOfTakingFlag = REGULARLY;
                            stringList2[1] = "Regelmäßig";
                            mDrugEvent.setRegularly(true);
                            cardView2.setVisibility(View.VISIBLE);
                            cardView3.setVisibility(View.VISIBLE);

                        }
                }
                customListView1.notifyDataSetChanged();
                customListView2.notifyDataSetChanged();
                customListViewDrugTime.notifyDataSetChanged();
                lstDrugTime.setAdapter(customListViewDrugTime);
                EventBus.getDefault().postSticky(mDrugEvent);
            }
        });

        lst2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (runningTimeFragment == null) {
                            runningTimeFragment = RunningTimeFragment.newInstance(instanceInt + 1);
                        }
                        mFragmentNavigation.pushFragment(runningTimeFragment);
                        break;
                    case 1:
                        if (takingPatternFragment == null){
                            takingPatternFragment = TakingPatternFragment.newInstance(instanceInt+1);
                        }
                        mFragmentNavigation.pushFragment(takingPatternFragment);
                        break;
                }

            }
        });
        lst3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (alarmFragment == null){
                            alarmFragment = AlarmFragment.newInstance(instanceInt+1);
                        }
                        mFragmentNavigation.pushFragment(alarmFragment);
                        break;
                    case 1:
                        break;
                }

            }
        });
        if (!mDrugEvent.isRegularly()){
            kindOfTakingFlag = ONLY_WHEN_REQUIRED;
            cardViewDrugTime.setVisibility(View.GONE);
            addTimeButton.setVisibility(View.GONE);
            stringList2[1] = "Nur bei Bedarf";
            cardView2.setVisibility(View.GONE);
            cardView3.setVisibility(View.GONE);
            customListView1.notifyDataSetChanged();
            customListView2.notifyDataSetChanged();
        }else {
            cardViewDrugTime.setVisibility(View.VISIBLE);
            addTimeButton.setVisibility(View.VISIBLE);
            kindOfTakingFlag = REGULARLY;
            stringList2[1] = "Regelmäßig";
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            customListView1.notifyDataSetChanged();
            customListView2.notifyDataSetChanged();
        }


        if (mDrugEvent.getTakingPattern() == 2){
            cardViewDrugTime.setVisibility(View.GONE);
            addTimeButton.setVisibility(View.GONE);
        }else {
            addTimeButton.setVisibility(View.VISIBLE);
            cardViewDrugTime.setVisibility(View.VISIBLE);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view1 ==null) {
            view1 = inflater.inflate(R.layout.fragment_drug, container, false);
        }
        setHasOptionsMenu(true);
        return view1;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_drug_fragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int resID = item.getItemId();
        if (resID == R.id.save_drug) {
            if (mDrugEvent.getAlarmTime().isEmpty()&& mDrugEvent.isRegularly()) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Bitte fügen sie mindestens eine Zeit hinzu.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;

            } else {
                Bundle bundle = new Bundle();
                bundle.putString("drugName", drug.getDrugName());
                bundle.putInt("alarmType", mDrugEvent.getAlarmType());
                bundle.putInt("takingPattern", mDrugEvent.getTakingPattern());
                bundle.putString("dosageForm", AppDatabase.getAppDatabase(getContext()).dosageFormDao().getNamebyId(drug.getDosageFormId()));
                bundle.putString("endDay", mDrugEvent.getEndDate());
                bundle.putString("startDay", mDrugEvent.getStartingDate());
                bundle.putString("discreteTitle", mDrugEvent.getDiscreteTitle());
                bundle.putString("discreteBody", mDrugEvent.getDiscreteBody());
                bundle.putBooleanArray("discretePattern", mDrugEvent.getAlarmDiscretePatternWeekdays());


                long drugEventDbId = saveDrugEventDb();
                bundle.putInt("id", (int) drugEventDbId);

                drug.setDrugEventDbId(drugEventDbId);


                int result = AppDatabase.getAppDatabase(getContext()).drugDao().update(drug);
                if (result <= 0){
                    AppDatabase.getAppDatabase(getContext()).drugDao().insert(drug);
                }


                if (mDrugEvent.getAlarmType() != 3 && mDrugEvent.isRegularly()) {
                    AlarmMain alarm = new AlarmMain(getContext(), bundle, mDrugEvent);
                }

                Toast.makeText(getContext(), "Medizin gespeichert", Toast.LENGTH_SHORT).show();
                mFragmentNavigation.clearStack();
                return true;
            }
        }
        return false;
    }

    private long saveDrugEventDb() {

        boolean[] weekdays = mDrugEvent.getTakingPatternWeekdays();
        boolean[] weekdaysAlarmDiscrete = mDrugEvent.getAlarmDiscretePatternWeekdays();

        DrugEventDb drugEventDb = new DrugEventDb();
        if (mDrugEvent.isRegularly()){
            drugEventDb.setDosage(mDrugEvent.getDosage().toString());
            drugEventDb.setAlarmTime(mDrugEvent.getAlarmTime().toString());

        }

        drugEventDb.setRecurringReminder(mDrugEvent.isRecurringReminder());
        drugEventDb.setAlarmType(mDrugEvent.getAlarmType());
        drugEventDb.setRecurringReminder(mDrugEvent.isRecurringReminder());
        drugEventDb.setMondaySelected(weekdays[0]);
        drugEventDb.setTuesdaySelected(weekdays[1]);
        drugEventDb.setWednesdaySelected(weekdays[2]);
        drugEventDb.setThursdaySelected(weekdays[3]);
        drugEventDb.setFridaySelected(weekdays[4]);
        drugEventDb.setSaturdaySelected(weekdays[5]);
        drugEventDb.setSundaySelected(weekdays[6]);


        drugEventDb.setMondaySelectedDiscrete(weekdaysAlarmDiscrete[0]);
        drugEventDb.setTuesdaySelectedDiscrete(weekdaysAlarmDiscrete[1]);
        drugEventDb.setWednesdaySelectedDiscrete(weekdaysAlarmDiscrete[2]);
        drugEventDb.setThursdaySelectedDiscrete(weekdaysAlarmDiscrete[3]);
        drugEventDb.setFridaySelectedDiscrete(weekdaysAlarmDiscrete[4]);
        drugEventDb.setSaturdaySelectedDiscrete(weekdaysAlarmDiscrete[5]);
        drugEventDb.setSundaySelectedDiscrete(weekdaysAlarmDiscrete[6]);

        drugEventDb.setDiscreteTitle(mDrugEvent.getDiscreteTitle());
        drugEventDb.setDiscreteBody(mDrugEvent.getDiscreteBody());

        drugEventDb.setRunningTime(mDrugEvent.getRunningTime());

        drugEventDb.setRegularly(mDrugEvent.isRegularly());

        drugEventDb.setEndDate(mDrugEvent.getEndDate());
        drugEventDb.setStartingDate(mDrugEvent.getStartingDate());

        drugEventDb.setTakingPattern(mDrugEvent.getTakingPattern());
        drugEventDb.setTakingPatternDaysWithIntake(mDrugEvent.getTakingPatternDaysWithIntake());
        drugEventDb.setTakingPatternDaysWithOutIntake(mDrugEvent.getTakingPatternDaysWithoutIntake());
        drugEventDb.setTakingPatternHourInterval(mDrugEvent.getTakingPatternHourInterval());
        drugEventDb.setTakingPatternEveryOtherDay(mDrugEvent.getTakingPatternEveryOtherDay());
        drugEventDb.setTakingPatternHourNumber(mDrugEvent.getTakingPatternHourNumber());
        drugEventDb.setTakingPatternHourStart(mDrugEvent.getTakingPatternHourStart());

        return AppDatabase.getAppDatabase(getContext()).drugEventDbDao().insert(drugEventDb);


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

    public void pickDosage(final int position) {
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
        d.setTitle("Wähle Anzahl");
        d.setMessage("");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
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
                mDosage = mDrugEvent.getDosage();
                if (mDosage.size() == position){
                    mDosage.add(position, numberPicker.getValue());
                }else {
                    mDosage.set(position, numberPicker.getValue());
                }
                mDrugEvent.setDosage(mDosage);
                stringDosage.set(position, String.valueOf(numberPicker.getValue()));
                customListViewDrugTime.notifyDataSetChanged();
                lstDrugTime.setAdapter(customListViewDrugTime);
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
    private void setStrings(){


        stringList1 = new String[]{drug.getDrugName(), "Art der Einnahme"};
        String kindOfTaking;

        if (mDrugEvent.isRegularly()){
            kindOfTaking = "Regelmäßig";
        }else {
            kindOfTaking = "Nur bei Bedarf";

        }
        stringList2 =  new String[]{drug.getManufacturer(), kindOfTaking};
        stringList3 =  new String[]{"Laufzeit", "Einnahmemuster"};
        stringList4 = new String[]{"Unbegrenzte Laufzeit", "Täglich"};
        stringList5 =  new String[]{"Alarm"};
        stringList6 = new String[]{"Benachrichtigungston"};
        stringList7 = new String[]{"Kauferinnerung"};
        stringList8 = new String[]{""};

        stringTime = new ArrayList<String>();
        stringDosage = new ArrayList<String>();
        stringDosageForm = new ArrayList<String>();


        switch(mDrugEvent.getAlarmType()){
            case 1:
                stringList6[0] = getString(R.string.alarm);
                break;
            case 2:
                stringList6[0] = getString(R.string.notification);
                break;
            case 3:
                stringList6[0] = getString(R.string.no_notification);
                break;
            case 4:
                stringList6[0] = getString(R.string.silent_notification);
                break;
            case 5:
                stringList6[0] = getString(R.string.only_vibration);
                break;
            case 6:
                stringList6[0] = getString(R.string.discrete_notification);
                break;

        }

            mTimeList = mDrugEvent.getAlarmTime();
            List<String> mDosageList = new ArrayList<String>(mDrugEvent.getDosage().size());
            for (Integer myInt : mDrugEvent.getDosage()) {
                mDosageList.add(String.valueOf(myInt));
            }
            stringTime = new ArrayList<String>((ArrayList<String>) mTimeList);
            stringDosage = new ArrayList<String>((ArrayList<String>) mDosageList);
            for (int i = 0; i <  mTimeList.size(); i++) {
                stringDosageForm.add(AppDatabase.getAppDatabase(getContext()).dosageFormDao().getNamebyId(drug.getDosageFormId()));
            }




        if (!mDrugEvent.getEndDate().equals("-1")){
            stringList4[0] = "bis " + mDrugEvent.getEndDate();

        }else {
            stringList4[0] = "Unbegrentze Laufzeit";
        }
        switch (mDrugEvent.getTakingPattern()){
            case 1:
                stringList4[1] = "Täglich";
                break;
            case 2:
                stringList4[1] = "Täglich, alle " + mDrugEvent.getTakingPatternHourInterval()+ " Stunden";
                break;

            case 3:
                stringList4[1] = "Alle " + mDrugEvent.getTakingPatternEveryOtherDay() + " Tage";
                break;

            case 4:
                boolean[] weekdays = new boolean[7];
                weekdays[0] = true;
                weekdays[1] = true;
                weekdays[2] = true;
                weekdays[3] = true;
                weekdays[4] = true;
                weekdays[5] = false;
                weekdays[6] = false;

                boolean[] weekend = new boolean[7];
                weekend[0] = false;
                weekend[1] = false;
                weekend[2] = false;
                weekend[3] = false;
                weekend[4] = false;
                weekend[5] = true;
                weekend[6] = true;

                if (Arrays.equals(mDrugEvent.getTakingPatternWeekdays(),weekdays)){
                    stringList4[1] = "Wochentage";
                    break;
                }else if (Arrays.equals(mDrugEvent.getTakingPatternWeekdays(),weekend)){
                    stringList4[1] = "Wochenende";
                    break;
                }else {
                    tempString = new String[7];
                    boolean[] takingPattern = mDrugEvent.getTakingPatternWeekdays();
                    for (int i = 0; i < 7 ; i++) {
                        if (takingPattern[i] == true){
                            switch (i){
                                case 0:
                                    tempString[i] = "Mo";
                                    break;
                                case 1:
                                    tempString[i] = "Di";
                                    break;
                                case 2:
                                    tempString[i] = "Mi";
                                    break;
                                case 3:
                                    tempString[i] = "Do";
                                    break;
                                case 4:
                                    tempString[i] = "Fr";
                                    break;
                                case 5:
                                    tempString[i] = "Sa";
                                    break;
                                case 6:
                                    tempString[i] = "So";
                                    break;


                            }
                        }

                    }
                    //Null entfernen
                    List<String> list = new ArrayList<String>(Arrays.asList(tempString));
                    list.removeAll(Collections.singleton(null));
                    tempString = list.toArray(new String[list.size()]);

                    StringBuilder buffer = new StringBuilder();
                    for (String each : tempString)
                        buffer.append(". ").append(each);
                    String joined = buffer.deleteCharAt(0).toString();

                    stringList4[1] = joined;
                    break;
                }

            case 5:
                stringList4[1] = String.valueOf(mDrugEvent.getTakingPatternDaysWithIntake()) + " mit Einnahme, " + String.valueOf(mDrugEvent.getTakingPatternDaysWithoutIntake()) + " ohne Einnahme";
                break;
        }



    }

    private void pickTime(final int position){
        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                mAlarmTime = mDrugEvent.getAlarmTime();
                if (mAlarmTime.size() == position){
                    mAlarmTime.add(position, curTime);
                }else {
                    mAlarmTime.set(position, curTime);
                }
                stringTime.set( position , curTime);
                mDrugEvent.setAlarmTime(mAlarmTime);
                customListViewDrugTime.notifyDataSetChanged();
                lstDrugTime.setAdapter(customListViewDrugTime);
                EventBus.getDefault().postSticky(mDrugEvent);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Zeit wählen");
        mTimePicker.show();

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Toast.makeText(getContext(), newVal, Toast.LENGTH_SHORT).show();

    }

    public class Alarm implements Serializable {
        int id;
        String name;

        // GETTERS AND SETTERS
    }

    //EventBus
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(DrugEvent event){
        drug = event.getDrug();
        mDrugEvent = event;
    }






}

