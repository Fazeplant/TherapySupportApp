package michaelbumes.therapysupportapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;

/**
 * Created by Michi on 06.03.2018.
 */

public class TakingPatternFragment extends BaseFragment {
    private static final String TAG = TakingPatternFragment.class.getName();

    private static final int DAY = 0;
    private static final int CYCLE = 1;
    private static final int NOTHING = -1;
    private int dayOrCycleFlag = NOTHING;

    ListView lst1, lst2;
    RadioGroup radioGroup;
    CheckBox checkBoxMonday, checkBoxTuesday, checkBoxWednesday, checkBoxThursday, checkBoxFriday, checkBoxSaturday, checkBoxSunday;
    String[] stringAll, stringDuration, stringListCycle1, stringListCycle2;
    CardView cardView1, cardView2;


    public static TakingPatternFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        TakingPatternFragment fragment = new TakingPatternFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.taking_pattern);

        radioGroup = view.findViewById(R.id.radio_group_taking_pattern);

        lst1 = view.findViewById(R.id.list_view_taking_pattern_daily);
        lst2 = view.findViewById(R.id.list_view_taking_pattern_cycle);

        cardView1 = view.findViewById(R.id.card_view_taking_pattern_list_daily);
        cardView2 = view.findViewById(R.id.card_view_taking_pattern_list_cycle);

        stringAll = new String[]{"Alle"};
        stringDuration = new String[]{"2 Tage"};
        stringListCycle1 = new String[]{"Tage mit Einnahme", "Tage ohne Einnahme", "Start des Zyklus an Tag"};
        stringListCycle2 = new String[]{"21", "7", "1"};

        final CustomListView customListView1 = new CustomListView(getActivity(), stringAll, stringDuration);
        final CustomListView customListView2 = new CustomListView(getActivity(), stringListCycle1, stringListCycle2);


        lst1.setAdapter(customListView1);
        lst2.setAdapter(customListView2);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_button_daily_day) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                } else if (i == R.id.radio_button_daily_hour) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);

                } else if (i == R.id.radio_all_day) {
                    cardView2.setVisibility(View.GONE);
                    cardView1.setVisibility(View.VISIBLE);

                } else if (i == R.id.radio_button_weekdays) {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);

                } else {
                    cardView1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.VISIBLE);

                }

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_taking_pattern, container, false);
        return view1;
    }
}
