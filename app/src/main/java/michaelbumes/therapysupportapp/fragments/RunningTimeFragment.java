package michaelbumes.therapysupportapp.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.entity.Drug;

/**
 * Created by Michi on 06.03.2018.
 */

public class RunningTimeFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = RunningTimeFragment.class.getName();
    private static final int DAYS = 1;
    private static final int DEFINED = 2;
    private static final int START = 1;
    private static final int END = 2;



    private int runningTimeFlag = -1;
    private int notificationFlag = -1;

    private View view1;
    private ListView lst1, lst2;
    private RadioGroup radioGroup;
    private String[] stringFirstNotification, stringStartDate, stringMain2, stringSecond2;
    private CardView cardView1, cardView2;
    private Drug drug;
    private  CustomListView customListView1, customListView2;
    private SimpleDateFormat sdf;
    private Date tempDate;
    private DrugEvent myDrugEvent;
    private boolean mIsRunningTimeDefined;




    public static RunningTimeFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        RunningTimeFragment fragment = new RunningTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.running_time);

        myDrugEvent = EventBus.getDefault().getStickyEvent(DrugEvent.class);
        drug = myDrugEvent.getDrug();

        final Calendar c = Calendar.getInstance();
        int startYear = c.get(Calendar.YEAR);
        int startMonth = c.get(Calendar.MONTH);
        int startDay = c.get(Calendar.DAY_OF_MONTH);
        Date currentDate = c.getTime();

        final Calendar cAddTime = c;
        cAddTime.setTime(currentDate);
        cAddTime.add(Calendar.DATE, 10);
        sdf = new SimpleDateFormat("dd/MM/yyyy");


        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), this, startYear, startMonth, startDay);

        radioGroup = view.findViewById(R.id.radio_group_running_time);

        lst1 = view.findViewById(R.id.list_view_running_time_1);
        lst2 = view.findViewById(R.id.list_view_running_time_2);

        cardView1 = view.findViewById(R.id.card_view_running_time_1);
        cardView2 = view.findViewById(R.id.card_view_running_time_2);

        stringFirstNotification = new String[]{"Erste Erinnerung"};
        stringStartDate = new String[]{"Heute"};

        if (mIsRunningTimeDefined){
            stringMain2 = new String[]{"Letzte Erinnerung"};

        }else {
            stringMain2 = new String[]{"Laufzeit"};

        }

        if (myDrugEvent.getEndDate() == "-1"){
            stringSecond2 = new String[]{sdf.format(cAddTime.getTime())};
        }else {
            if (mIsRunningTimeDefined){
                stringSecond2 = new String[]{myDrugEvent.getEndDate()};
            }else {
                try {
                    tempDate = sdf.parse(myDrugEvent.getEndDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long msDiff = tempDate.getTime() - Calendar.getInstance().getTimeInMillis();
                long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff) + 1;
                stringSecond2[0] = String.valueOf(daysDiff);
            }
        }


        customListView1 = new CustomListView(getActivity(), stringFirstNotification, stringStartDate);
        customListView2 = new CustomListView(getActivity(), stringMain2, stringSecond2);

        lst1.setAdapter(customListView1);
        lst2.setAdapter(customListView2);

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        notificationFlag = START;
                        datePickerDialog.show();
                        break;

                }
            }
        });

        lst2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (runningTimeFlag == DEFINED){
                    notificationFlag = END;
                    datePickerDialog.show();
                }else {
                    pickDays();
                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_button_unlimited_term) {
                    cardView2.setVisibility(View.GONE);

                } else if (i == R.id.radio_button_defined_end_date) {
                    runningTimeFlag = DEFINED;
                    mIsRunningTimeDefined = true;
                    cardView2.setVisibility(View.VISIBLE);
                    stringMain2[0] = "Letzte Erinnerung";
                    if (myDrugEvent.getEndDate() == "-1"){
                        DrugEvent drugEvent = new DrugEvent();
                        drugEvent.setDrug(drug);
                        drugEvent.setEndDate(sdf.format(cAddTime.getTime()));
                        EventBus.getDefault().postSticky(drugEvent);
                        stringSecond2[0] = sdf.format(cAddTime.getTime());
                    }else {
                        stringSecond2[0] = myDrugEvent.getEndDate();
                    }
                    customListView2.notifyDataSetChanged();


                } else {
                    runningTimeFlag = DAYS;
                    mIsRunningTimeDefined = false;
                    cardView2.setVisibility(View.VISIBLE);
                    stringMain2[0] = "Laufzeit";

                    if (myDrugEvent.getEndDate() == "-1"){
                        DrugEvent drugEvent = new DrugEvent();
                        drugEvent.setDrug(drug);
                        drugEvent.setEndDate(sdf.format(cAddTime.getTime()));
                        EventBus.getDefault().postSticky(drugEvent);
                        stringSecond2[0] = "10";
                    }else {
                        try {
                            tempDate = sdf.parse(myDrugEvent.getEndDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long msDiff = tempDate.getTime() - Calendar.getInstance().getTimeInMillis();
                        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff) + 1;
                        stringSecond2[0] = String.valueOf(daysDiff);
                    }
                    customListView2.notifyDataSetChanged();

                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view1 == null) {
            view1 = inflater.inflate(R.layout.fragment_running_time, container, false);
        }
        return view1;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        DrugEvent drugEvent = new DrugEvent();
        drugEvent.setDrug(drug);
        String date = String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);

        if (notificationFlag == START) {
            drugEvent.setStartingDate(date);
            stringStartDate[0] = date;
            customListView1.notifyDataSetChanged();
        }else if(notificationFlag == END){
            drugEvent.setRunningTimeDefined(mIsRunningTimeDefined);
            drugEvent.setEndDate(date);
            stringSecond2[0] = date;
            customListView2.notifyDataSetChanged();


        }
        EventBus.getDefault().postSticky(drugEvent);

    }

    public void pickDays() {
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
        d.setTitle("Anzahl Tage");
        d.setMessage("");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(365);
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
                stringSecond2[0] = String.valueOf(numberPicker.getValue());
                DrugEvent drugEvent = new DrugEvent();
                drugEvent.setDrug(drug);
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE, numberPicker.getValue());

                drugEvent.setEndDate(sdf.format(calendar.getTime()));
                drugEvent.setRunningTimeDefined(mIsRunningTimeDefined);
                EventBus.getDefault().postSticky(drugEvent);

                customListView2.notifyDataSetChanged();

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
        myDrugEvent = event;

    }
}
