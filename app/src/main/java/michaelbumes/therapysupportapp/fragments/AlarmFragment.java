package michaelbumes.therapysupportapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.entity.Drug;

/**
 * Created by Michi on 06.03.2018.
 */

public class AlarmFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = AlarmFragment.class.getName();
    private static final int TITLE = 1;
    private RadioGroup radioGroup;
   // private Switch aSwitch;
    private DrugEvent mDrugEvent;
    private Drug drug;
    private View view1;
    private CardView cardViewDiscrete;
    private CustomListView customListViewDiscrete;
    private String[] stringDiscrete2;
    private CheckBox checkBoxMonday, checkBoxTuesday, checkBoxWednesday, checkBoxThursday, checkBoxFriday, checkBoxSaturday, checkBoxSunday;



    public static AlarmFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        AlarmFragment fragment = new AlarmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.alarm);

        mDrugEvent = EventBus.getDefault().getStickyEvent(DrugEvent.class);
        drug = mDrugEvent.getDrug();

        radioGroup = view.findViewById(R.id.radio_group_alarm);

        //String für die ListViews
        String[] stringDiscrete1 = new String[]{"Titel", "Inhalt"};
        stringDiscrete2 = new String[]{mDrugEvent.getDiscreteTitle(), mDrugEvent.getDiscreteBody()};

        cardViewDiscrete = view.findViewById(R.id.card_view_alarm_discrete);
        customListViewDiscrete  = new CustomListView(getActivity(), stringDiscrete1, stringDiscrete2);
        ListView listViewDiscrete = view.findViewById(R.id.list_view_alarm_discrete);
        listViewDiscrete.setAdapter(customListViewDiscrete);
        justifyListViewHeightBasedOnChildren(listViewDiscrete);

        checkBoxMonday = view.findViewById(R.id.check_box_alarm_monday);
        checkBoxMonday.setOnClickListener(this);
        checkBoxTuesday = view.findViewById(R.id.check_box_alarm_tuesday);
        checkBoxTuesday.setOnClickListener(this);
        checkBoxWednesday = view.findViewById(R.id.check_box_alarm_wednesday);
        checkBoxWednesday.setOnClickListener(this);
        checkBoxThursday = view.findViewById(R.id.check_box_alarm_thursday);
        checkBoxThursday.setOnClickListener(this);
        checkBoxFriday = view.findViewById(R.id.check_box_alarm_friday);
        checkBoxFriday.setOnClickListener(this);
        checkBoxSaturday = view.findViewById(R.id.check_box_alarm_saturday);
        checkBoxSaturday.setOnClickListener(this);
        checkBoxSunday = view.findViewById(R.id.check_box_alarm_sunday);
        checkBoxSunday.setOnClickListener(this);


        listViewDiscrete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        createAlertDialogText("Titel setzen", TITLE);
                        break;
                    case 1:
                        createAlertDialogText("Nachricht setzen", 2);
                        break;

                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_button_alarm_standard) {
                    mDrugEvent.setAlarmType(1);
                    cardViewDiscrete.setVisibility(View.GONE);


                } else if (i == R.id.radio_button_alarm_sound) {
                    mDrugEvent.setAlarmType(2);
                    cardViewDiscrete.setVisibility(View.GONE);


                } else if (i == R.id.radio_button_alarm_no_notification) {
                    mDrugEvent.setAlarmType(3);
                    cardViewDiscrete.setVisibility(View.GONE);


                } else if (i == R.id.radio_button_alarm_silent_notification) {
                    mDrugEvent.setAlarmType(4);
                    cardViewDiscrete.setVisibility(View.GONE);


                } else if (i == R.id.radio_button_alarm_only_vibrate) {
                    mDrugEvent.setAlarmType(5);
                    cardViewDiscrete.setVisibility(View.GONE);


                } else if (i == R.id.radio_button_alarm_discrete_notification) {
                    boolean[] eventDays = mDrugEvent.getAlarmDiscretePatternWeekdays();
                    checkBoxMonday.setChecked(eventDays[0]);
                    checkBoxTuesday.setChecked(eventDays[1]);
                    checkBoxWednesday.setChecked(eventDays[2]);
                    checkBoxThursday.setChecked(eventDays[3]);
                    checkBoxFriday.setChecked(eventDays[4]);
                    checkBoxSaturday.setChecked(eventDays[5]);
                    checkBoxSunday.setChecked(eventDays[6]);
                    mDrugEvent.setAlarmType(6);
                    cardViewDiscrete.setVisibility(View.VISIBLE);

                }
                EventBus.getDefault().postSticky(mDrugEvent);
            }
        });

        switch (mDrugEvent.getAlarmType()){
            case 1:
                radioGroup.check(R.id.radio_button_alarm_standard);
                break;

            case 2:
                radioGroup.check(R.id.radio_button_alarm_sound);
                break;

            case 3:

                radioGroup.check(R.id.radio_button_alarm_no_notification);
                break;
            case 4:

                radioGroup.check(R.id.radio_button_alarm_silent_notification);
                break;
            case 5:

                radioGroup.check(R.id.radio_button_alarm_only_vibrate);
                break;
            case 6:

                radioGroup.check(R.id.radio_button_alarm_discrete_notification);
                break;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view1 == null) {
            view1 = inflater.inflate(R.layout.fragment_alarm, container, false);
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
    public void onEvent(DrugEvent event) {
        drug = event.getDrug();
        mDrugEvent = event;

    }
    //Berechnet die Größe des Layouts
    private void justifyListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    private void createAlertDialogText(String title, final int mode){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        final EditText input = new EditText(getContext());

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Fertig", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mode == TITLE) {
                    mDrugEvent.setDiscreteTitle(input.getText().toString());
                    stringDiscrete2[0] = input.getText().toString();
                    customListViewDiscrete.notifyDataSetChanged();
                }else{
                    mDrugEvent.setDiscreteBody(input.getText().toString());
                    stringDiscrete2[1] = input.getText().toString();
                    customListViewDiscrete.notifyDataSetChanged();
                }
                EventBus.getDefault().post(mDrugEvent);
            }
        });
        builder.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onClick(View view){
        boolean[] mWeekdays = new boolean[7];

        try {
            mWeekdays = mDrugEvent.getAlarmDiscretePatternWeekdays();
        }catch (Exception ignored){

        }
        switch (view.getId()){
            case R.id.check_box_alarm_monday:
                mWeekdays[0] = checkBoxMonday.isChecked();
                break;
            case R.id.check_box_alarm_tuesday:
                mWeekdays[1] = checkBoxTuesday.isChecked();
                break;
            case R.id.check_box_alarm_wednesday:
                mWeekdays[2] = checkBoxWednesday.isChecked();
            case R.id.check_box_alarm_thursday:
                mWeekdays[3] = checkBoxThursday.isChecked();
                break;
            case R.id.check_box_alarm_friday:
                mWeekdays[4] = checkBoxFriday.isChecked();
            case R.id.check_box_alarm_saturday:
                mWeekdays[5] = checkBoxSaturday.isChecked();
                break;
            case R.id.check_box_alarm_sunday:
                mWeekdays[6] = checkBoxSunday.isChecked();
                break;
        }
        mDrugEvent.setAlarmDiscretePatternWeekdays(mWeekdays);
        EventBus.getDefault().postSticky(mDrugEvent);
    }
}