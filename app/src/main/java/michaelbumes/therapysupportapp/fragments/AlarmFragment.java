package michaelbumes.therapysupportapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import michaelbumes.therapysupportapp.R;

/**
 * Created by Michi on 06.03.2018.
 */

public class AlarmFragment extends BaseFragment {
    private static final String TAG = AlarmFragment.class.getName();
    RadioGroup radioGroup;
    Switch aSwitch;



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

        radioGroup = view.findViewById(R.id.radio_group_alarm);
        aSwitch = view.findViewById(R.id.switch_alarm_recurring_reminder);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_button_alarm_standard){
                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();

                }else if (i == R.id.radio_button_alarm_long){
                    Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();

                }else if (i == R.id.radio_button_alarm_no_notification){
                    Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();

                }else if (i == R.id.radio_button_alarm_silent_notification){
                    Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();

                }else if (i == R.id.radio_button_alarm_only_vibrate){
                    Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();

                }else if (i == R.id.radio_button_alarm_discrete_notification){
                    Toast.makeText(getContext(), "6", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_alarm, container, false);
        return view1;
    }
}
