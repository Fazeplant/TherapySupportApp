package michaelbumes.therapysupportapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugList;

import static android.app.Activity.RESULT_CANCELED;

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
    String pzn = "-1";
    boolean isQR = false;




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
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isQR = false;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isQR = false;
            }
        });

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(AddMedicineFragment.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanne Barcode");
                integrator.setBeepEnabled(false);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();

            }
        });
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String drugName = nameEdit.getText().toString();
                if (drugName.isEmpty()) {
                    nameEdit.setError("Geben Sie einen Namen ein");
                    return;
                }else if(isQR ==false) {
                    drugList = AppDatabase.getAppDatabase(getContext()).drugListDao().findByName(drugName);
                    if (drugList == null){
                        Toast.makeText(getContext(), "Medizin nicht Gefunden", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        pzn = drugList.getPzn();
                    }
                }if (pzn == "-1"){
                    Toast.makeText(getContext(), "Medizin nicht Gefunden", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    drug = new Drug();
                    drug.setDrugName(drugList.getName());
                    drug.setDosageFormId(drugList.getDosageFormId());
                    drug.setManufacturer(drugList.getManufacturer());
                    drug.setPzn(drugList.getPzn());
                    drug.setSideEffects(drugList.getSideEffects());
                    drug.setTakingNote(drugList.getTakingNote());

                    DrugEvent event = new DrugEvent();
                    event.setDrug(drug);
                    EventBus.getDefault().removeAllStickyEvents();
                    EventBus.getDefault().postSticky(event);

                    fragmentNavigation.pushFragment(DrugFragment.newInstance(instanceInt + 1));
                }
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
        if (resultCode == RESULT_CANCELED){
            isQR = false;
        }
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Abgebrochen", Toast.LENGTH_LONG).show();
                return;

            }else {
                try {
                    String string = result.getContents();
                    pzn = string.replace("\n", "");
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Barcode nicht unterstützt", Toast.LENGTH_SHORT).show();
                }
                try {
                    drugList = AppDatabase.getAppDatabase(getContext()).drugListDao().findByPzn(pzn);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "PZN nicht in Datenbank gefunden", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (drugList != null) {
                    nameEdit.setText(drugList.getName());
                    isQR = true;
                } else {
                    Toast.makeText(getContext(), "PZN nicht in Datenbank gefunden", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(DrugEvent event){
    }



}