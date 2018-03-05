package michaelbumes.therapysupportapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.dao.DrugListDao;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugList;


public class DrugFragment extends BaseFragment {
    private static final String TAG = DrugFragment.class.getName();

    ListView lst;

    DrugList drugList;
    String parentPzn;

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
        getActivity().setTitle(R.string.drug);
        parentPzn = getArguments().getString("pzn", "-1");
        try {
            drugList = AppDatabase.getAppDatabase(getContext()).drugListDao().findByPzn(parentPzn);
        }catch (Exception e){
        }

        lst = view.findViewById(R.id.list_view_drug);
        CustomListView customListView = new CustomListView(getActivity(),createList1(), createList2());
        lst.setAdapter(customListView);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_drug, container, false);
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
        if (resID == R.id.save_drug){
            Drug drug = new Drug();
            drug.setDrugName(drugList.getName());
            drug.setDosageFormId(drugList.getDosageFormId());
            drug.setManufacturerId(drugList.getManufacturerId());
            drug.setPzn(drugList.getPzn());
            drug.setSideEffects(drugList.getSideEffects());
            drug.setTakingNote(drugList.getTakingNote());
            AppDatabase.getAppDatabase(getContext()).drugDao().insertAll(drug);
            Toast.makeText(getContext(), "Medizin gespeichert", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }

    private String[] createList1(){
         return new String[]{drugList.getName(), "Art der Einnahme", "Laufzeit", "Einnahmemuster", "Alarm", "Kauferrinerung"};
    }
    private String[] createList2(){
        return new String[]{AppDatabase.getAppDatabase(getContext()).manufacturerDao().findById(drugList.getManufacturerId()).getManufacturerName(), AppDatabase.getAppDatabase(getContext()).dosageFormDao().findById(drugList.getDosageFormId()).getDosageFormName(), "Unbegretzte Laufzeit", "Täglich", "Standart" ,""};
    }
}
