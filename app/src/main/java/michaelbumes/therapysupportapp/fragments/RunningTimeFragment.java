package michaelbumes.therapysupportapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;

/**
 * Created by Michi on 06.03.2018.
 */

public class RunningTimeFragment extends BaseFragment {
    private static final String TAG = RunningTimeFragment.class.getName();

    private View view1;
    ListView lst1, lst2;
    RadioGroup radioGroup;
    String[] stringFirstNotification, stringStartDate, stringMain2, stringSecond2;
    CardView cardView1, cardView2;




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

        radioGroup = view.findViewById(R.id.radio_group_running_time);

        lst1 = view.findViewById(R.id.list_view_running_time_1);
        lst2 = view.findViewById(R.id.list_view_running_time_2);

        cardView1 = view.findViewById(R.id.card_view_running_time_1);
        cardView2 = view.findViewById(R.id.card_view_running_time_2);

        stringFirstNotification = new String[]{"Erste Erinnerung"};
        stringStartDate = new String[]{"Heute"};
        stringMain2 = new String[]{"Letzte Erinnerung"};
        stringSecond2 = new String[]{"16.03.18"};


        final CustomListView customListView1 = new CustomListView(getActivity(), stringFirstNotification, stringStartDate);
        final CustomListView customListView2 = new CustomListView(getActivity(), stringMain2, stringSecond2);

        lst1.setAdapter(customListView1);
        lst2.setAdapter(customListView2);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_button_unlimited_term) {
                    cardView2.setVisibility(View.GONE);

                } else if (i == R.id.radio_button_defined_end_date) {
                    cardView2.setVisibility(View.VISIBLE);
                    stringMain2[0] = "Letzte Erinnerung";
                    stringSecond2[0] = "16.03.18";
                    customListView2.notifyDataSetChanged();


                } else {
                    cardView2.setVisibility(View.VISIBLE);
                    stringMain2[0] = "Laufzeit";
                    stringSecond2[0] = "10";
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

    private String[] createList1() {
        return new String[]{"Erste Erinnerung", "Heute"};
    }

    private String[] createList2() {
        return new String[]{"", ""};
    }

}
