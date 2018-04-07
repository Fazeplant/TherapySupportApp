package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
   private RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Drug> drugs;


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

        recyclerView = view.findViewById(R.id.recyler_view);

        drugs = AppDatabase.getAppDatabase(getContext()).drugDao().getAll();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DrugAdapter(drugs);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);





        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentNavigation.pushFragment(AddMedicineFragment.newInstance(instanceInt+1));
            }
        });




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_plan, container, false);

        return view;
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((DrugAdapter)recyclerView.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };




}
