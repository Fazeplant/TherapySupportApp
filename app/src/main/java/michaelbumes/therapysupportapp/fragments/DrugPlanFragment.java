package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.DrugAdapter;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugPlanFragment extends BaseFragment {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;


    public static DrugPlanFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        DrugPlanFragment fragment = new DrugPlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_drug_plan);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_plan, container, false);

        recyclerView = view.findViewById(R.id.recyler_view);

        List<Drug> drugs = AppDatabase.getAppDatabase(getContext()).drugDao().getAll();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DrugAdapter(drugs);
        recyclerView.setAdapter(adapter);




        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentNavigation.pushFragment(AddMedicineFragment.newInstance(instanceInt+1));
            }
        });

        return view;
    }

}
