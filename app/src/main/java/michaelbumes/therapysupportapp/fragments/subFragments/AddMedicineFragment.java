package michaelbumes.therapysupportapp.fragments.subFragments;


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
import android.widget.Button;
import android.widget.EditText;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.fragments.mainFragments.BaseFragment;
import michaelbumes.therapysupportapp.fragments.mainFragments.DrugPlanFragment;
import michaelbumes.therapysupportapp.utils.DatabaseInitializer;

import static michaelbumes.therapysupportapp.fragments.mainFragments.BaseFragment.ARGS_INSTANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicineFragment extends BaseFragment {

    private EditText nameEdit, manufacturerEdit;
    private Button storeButton;
    private Drug drug;

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
        storeButton = view.findViewById(R.id.store_button);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDrug(AppDatabase.getAppDatabase(getContext()));
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medicine, container, false);



        return view;
    }
    private static Drug addDrug(final AppDatabase db, Drug drug) {
        db.drugDao().insertAll(drug);
        return drug;
    }
    private void createDrug(AppDatabase db) {
        drug = new Drug();
        drug.setDrugName(nameEdit.getText().toString());
        drug.setManufacturer(nameEdit.getText().toString());
        addDrug(db, drug);

    }
    
    private void setupClickListeners() {
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //addEventViewModel.setEventName(s.toString());
            }
        });

        manufacturerEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //addEventViewModel.setEventDescription(s.toString());
            }
        });

    }
}