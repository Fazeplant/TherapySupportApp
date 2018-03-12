package michaelbumes.therapysupportapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.DrugList;

/**
 * Created by Michi on 08.03.2018.
 */

public class DrugDetailFragment extends BaseFragment {
    private static final String TAG = DrugDetailFragment.class.getName();

    ListView lst1;
    private String[] string1;
    private String[] string2;
    String parentPzn;
    DrugList drugList;




    public static DrugDetailFragment newInstance(int instance, String pzn) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString("pzn", pzn);
        DrugDetailFragment fragment = new DrugDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.drug_detail);

        parentPzn = getArguments().getString("pzn", "-1");
        try {
            drugList = AppDatabase.getAppDatabase(getContext()).drugListDao().findByPzn(parentPzn);
        } catch (Exception e) {
        }

        lst1 = view.findViewById(R.id.list_view_drug_detail);

        String name = drugList.getName();
        String manufacturer = AppDatabase.getAppDatabase(getContext()).manufacturerDao().findById(drugList.getManufacturerId()).getManufacturerName();
        String dosageForm = AppDatabase.getAppDatabase(getContext()).dosageFormDao().findById(drugList.getDosageFormId()).getDosageFormName();

        string1 = new String[]{"Name", "Hersteller", "Darreichungsform"};
        string2 = new String[]{name , manufacturer, dosageForm};

        final CustomListView customListView1 = new CustomListView(getActivity(), string1, string2);

        lst1.setAdapter(customListView1);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_drug_detail, container, false);
        return view1;
    }

}
