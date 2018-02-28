package michaelbumes.therapysupportapp.fragments;


import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavPopController;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugList;
import michaelbumes.therapysupportapp.utils.DatabaseInitializer;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicineFragment extends BaseFragment {
    private static final String TAG = AddMedicineFragment.class.getName();

    private Button storeButton,qrButton;
    private Drug drug;
    AutoCompleteTextView nameEdit;
    String[] drugListNames;
    DrugList drugList;



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
        drugListNames = AppDatabase.getAppDatabase(getContext()).drugListDao().getAllNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,drugListNames);
        nameEdit.setAdapter(adapter);
        storeButton = view.findViewById(R.id.store_button);
        qrButton = view.findViewById(R.id.qr_button);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(AddMedicineFragment.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scanne Barcode");
                integrator.setBeepEnabled(false);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();

            }
        });
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createDrug(AppDatabase.getAppDatabase(getContext()));
                Drug mDrug = new Drug();
                mDrug.setDrugName(nameEdit.getText().toString());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled1", Toast.LENGTH_LONG).show();
            }else{
                int pzn = Integer.parseInt(result.getContents());
                if (AppDatabase.getAppDatabase(getContext()).drugListDao().findByPzn(pzn) != null) {
                    drugList = AppDatabase.getAppDatabase(getContext()).drugListDao().findByPzn(pzn);
                    nameEdit.setText(drugList.getName());
                }else {
                    Toast.makeText(getContext(), "PZN nicht in Datenbank gefunden", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}