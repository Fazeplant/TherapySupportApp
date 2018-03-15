package michaelbumes.therapysupportapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.entity.Drug;

/**
 * Created by Michi on 06.03.2018.
 */

public class AlarmFragment extends BaseFragment {
    private static final String TAG = AlarmFragment.class.getName();
    RadioGroup radioGroup;
    Switch aSwitch;
    private DrugEvent mDrugEvent;
    private Drug drug;
    private View view1;


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
        aSwitch = view.findViewById(R.id.switch_alarm_recurring_reminder);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mDrugEvent.setRecurringReminder(true);
                }else {
                    mDrugEvent.setRecurringReminder(false);
                }
                EventBus.getDefault().postSticky(mDrugEvent);
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_button_alarm_standard) {
                    mDrugEvent.setAlarmType(1);

                } else if (i == R.id.radio_button_alarm_long) {
                    mDrugEvent.setAlarmType(2);

                } else if (i == R.id.radio_button_alarm_no_notification) {
                    mDrugEvent.setAlarmType(3);

                } else if (i == R.id.radio_button_alarm_silent_notification) {
                    mDrugEvent.setAlarmType(4);

                } else if (i == R.id.radio_button_alarm_only_vibrate) {
                    mDrugEvent.setAlarmType(5);

                } else if (i == R.id.radio_button_alarm_discrete_notification) {
                    mDrugEvent.setAlarmType(6);

                }
                EventBus.getDefault().postSticky(mDrugEvent);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
}