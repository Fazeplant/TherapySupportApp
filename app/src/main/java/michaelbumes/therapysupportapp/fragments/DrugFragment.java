package michaelbumes.therapysupportapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.adapter.CustomListViewDrugTime;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugList;


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



    boolean timeButtonClicked = false;


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

    private   RunningTimeFragment runningTimeFragment = null;
    private  DrugDetailFragment drugDetailFragment = null;
    private  TakingPatternFragment takingPatternFragment = null;
    private   AlarmFragment alarmFragment = null;



    private String parentPzn;
    private    CardView cardView1, cardView2, cardView3, cardViewDrugTime;
    private static int mCurCheckPosition;
    private boolean isSaved = false;

    public static DrugFragment newInstance(int instance, String pzn) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString("pzn", pzn);
        DrugFragment fragment = new DrugFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle(R.string.drug);
        parentPzn = getArguments().getString("pzn", "-1");

        mFragmentNavigation = ((MainActivity) getActivity()).getmNavController();

        DrugEvent mDrugEvent = EventBus.getDefault().getStickyEvent(DrugEvent.class);
        drug = mDrugEvent.getDrug();



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

        final Bundle bundle = new Bundle();
        bundle.putSerializable("drug", drug);



        addTimeButton = view.findViewById(R.id.add_time_button);


        cardView2 = view.findViewById(R.id.card_view_drug_2);
        cardView3 = view.findViewById(R.id.card_view_drug_3);
        cardViewDrugTime = view.findViewById(R.id.card_view_drug_time);


        lst = view.findViewById(R.id.list_view_drug);
        lst2 = view.findViewById(R.id.list_view_drug_2);
        lst3 = view.findViewById(R.id.list_view_drug_3);
        lst4 = view.findViewById(R.id.list_view_drug_4);

        lstDrugTime = view.findViewById(R.id.list_view_drug_time);


        stringList1 = new String[]{drug.getDrugName(), "Art der Einnahme"};
        stringList2 =  new String[]{AppDatabase.getAppDatabase(getContext()).manufacturerDao().findById(drug.getManufacturerId()).getManufacturerName(), "Regelmäßig"};
        stringList3 =  new String[]{"Laufzeit", "Einnahmemuster"};
        stringList4 = new String[]{"Unbegrenzte Laufzeit", "Täglich"};
        stringList5 =  new String[]{"Alarm"};
        stringList6 = new String[]{"Standard"};
        stringList7 = new String[]{"Kauferinnerung"};
        stringList8 = new String[]{""};

        stringTime = new ArrayList<String>();
        stringTime.add("08:00");
        stringDosage = new ArrayList<String>();
        stringDosage.add("1");
        stringDosageForm = new ArrayList<String>();
        stringDosageForm.add("Tablette(n)");




        final CustomListView customListView1 = new CustomListView(getActivity(), stringList1, stringList2);
        final CustomListView customListView2 = new CustomListView(getActivity(), stringList3, stringList4);
        final CustomListView customListView3 = new CustomListView(getActivity(), stringList5, stringList6);
        final CustomListView customListView4 = new CustomListView(getActivity(), stringList7, stringList8);

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
                if (timeButtonClicked ==false) {
                    cardViewDrugTime.setVisibility(View.VISIBLE);
                    timeButtonClicked = true;
                    customListViewDrugTime.notifyDataSetChanged();
                    justifyListViewHeightBasedOnChildren(lstDrugTime);

                }else {
                    stringTime.add("09:00");
                    stringDosage.add(String.valueOf(postitionsCheck++));
                    stringDosageForm.add("Tablette(n)");
                    justifyListViewHeightBasedOnChildren(lstDrugTime);
                    customListViewDrugTime.notifyDataSetChanged();
                }
            }
        });
        customListViewDrugTime.setImageButtonOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postition = lstDrugTime.getPositionForView(view);
                stringTime.remove(postition);
                stringDosageForm.remove(postition);
                stringDosage.remove(postition);
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
                            drugDetailFragment.setArguments(bundle);
                        }
                        mFragmentNavigation.pushFragment(drugDetailFragment);
                        customListView1.notifyDataSetChanged();
                        customListView2.notifyDataSetChanged();
                        break;
                    case 1:
                        if (kindOfTakingFlag == REGULARLY) {
                            kindOfTakingFlag = ONLY_WHEN_REQUIRED;
                            cardViewDrugTime.setVisibility(View.GONE);
                            stringList2[1] = "Nur bei Bedarf";
                            cardView2.setVisibility(View.GONE);
                            cardView3.setVisibility(View.GONE);
                            customListView1.notifyDataSetChanged();
                            customListView2.notifyDataSetChanged();
                            break;
                        } else {
                            if (timeButtonClicked ==true) {
                                cardViewDrugTime.setVisibility(View.VISIBLE);

                            }
                            kindOfTakingFlag = REGULARLY;
                            stringList2[1] = "Regelmäßig";
                            cardView2.setVisibility(View.VISIBLE);
                            cardView3.setVisibility(View.VISIBLE);
                            customListView1.notifyDataSetChanged();
                            customListView2.notifyDataSetChanged();
                        }
                }
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


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view1 ==null) {
            view1 = inflater.inflate(R.layout.fragment_drug, container, false);
        }
        setRetainInstance(true);
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

            AppDatabase.getAppDatabase(getContext()).drugDao().insertAll(drug);
            Toast.makeText(getContext(), "Medizin gespeichert", Toast.LENGTH_SHORT).show();
            mFragmentNavigation.clearStack();
            return true;
        }
        return false;
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
                stringDosage.set(position, String.valueOf(numberPicker.getValue()));
                customListViewDrugTime.notifyDataSetChanged();
                lstDrugTime.setAdapter(customListViewDrugTime);

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

    private void pickTime(int position){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        final int mPosition = position;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                stringTime.set( mPosition , curTime);
                customListViewDrugTime.notifyDataSetChanged();
                lstDrugTime.setAdapter(customListViewDrugTime);
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
        Log.d("event", "Event Angekommen Drug");
    }






}

