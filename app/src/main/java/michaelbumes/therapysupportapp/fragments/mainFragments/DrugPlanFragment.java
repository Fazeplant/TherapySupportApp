package michaelbumes.therapysupportapp.fragments.mainFragments;


import android.arch.persistence.room.Database;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.dao.DrugDao;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.fragments.subFragments.AddMedicineFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugPlanFragment extends BaseFragment {
    FloatingActionButton fab;
    TextView textView;


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


        textView = (TextView)view.findViewById(R.id.textView);


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
