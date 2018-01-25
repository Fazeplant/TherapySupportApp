package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavPopController;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.utils.DatabaseInitializer;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicineFragment extends BaseFragment {
    private static final String TAG = AddMedicineFragment.class.getName();

    private EditText manufacturerEdit;
    private Button storeButton;
    private Drug drug;
    AutoCompleteTextView nameEdit;
    String[] drugListNames;


    public static AddMedicineFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        AddMedicineFragment fragment = new AddMedicineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.add_medicine);
        nameEdit = view.findViewById(R.id.edit_drug_name);
        manufacturerEdit = view.findViewById(R.id.edit_drug_manufacturer);
        drugListNames = AppDatabase.getAppDatabase(getContext()).drugListDao().getAllNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,drugListNames);
        nameEdit.setAdapter(adapter);
        storeButton = view.findViewById(R.id.store_button);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createDrug(AppDatabase.getAppDatabase(getContext()));
                Drug mDrug = new Drug();
                mDrug.setDrugName(nameEdit.getText().toString());
                mDrug.setManufacturer(manufacturerEdit.getText().toString());
                AppDatabase.getAppDatabase(getContext()).drugDao().insertAll(mDrug);
                getActivity().onBackPressed();
                Toast.makeText(getContext(), "Medizin gespeichert!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_add_medicine, container, false);
        return view1;
    }
}