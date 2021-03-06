package michaelbumes.therapysupportapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.CustomListView;
import michaelbumes.therapysupportapp.entity.Drug;

import static michaelbumes.therapysupportapp.activities.MainActivity.databaseDrugList;


/**
 * Created by Michi on 08.03.2018.
 */

public class DrugDetailFragment extends BaseFragment {
    private static final String TAG = DrugDetailFragment.class.getName();
    private final int CHANGE_NAME = 1;
    private final int CHANGE_MANUFACTURER = 2;

    private View view1;
    private String[] string2;
    private String[] dosageFormList;
    private AlertDialog alertDialogDosageForm;
    private CustomListView customListView;
    private Drug drug;
    private DrugEvent mDrugEvent;





    public static DrugDetailFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        DrugDetailFragment fragment = new DrugDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.drug_detail);

        mDrugEvent = EventBus.getDefault().getStickyEvent(DrugEvent.class);
        drug = mDrugEvent.getDrug();


        ListView lst1 = view.findViewById(R.id.list_view_drug_detail);


        dosageFormList = databaseDrugList.dosageFormDao().getAllNames();

        String name = drug.getDrugName();
        String manufacturer = drug.getManufacturer();
        String dosageForm = dosageFormList[drug.getDosageFormId() - 1];

        String[] string1 = new String[]{"Name", "Hersteller", "Darreichungsform"};
        string2 = new String[]{name, manufacturer, dosageForm};

        customListView = new CustomListView(getActivity(), string1, string2);

        lst1.setAdapter(customListView);

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        createAlertDialogText("Name ändern", CHANGE_NAME);
                        break;
                    case 1:
                        createAlertDialogText("Hersteller ändern", CHANGE_MANUFACTURER);
                        break;
                    case 2:
                        createAlertDialogDosageFrom();
                        break;
                }

            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view1 == null) {
            view1 = inflater.inflate(R.layout.fragment_drug_detail, container, false);
        }
        return view1;
    }

    private void createAlertDialogDosageFrom(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Darreichungsform");

        builder.setSingleChoiceItems(dosageFormList, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                drug.setDosageFormId(item + 1);
                string2[2] = dosageFormList[item];
                customListView.notifyDataSetChanged();

                mDrugEvent.setDrug(drug);
                EventBus.getDefault().post(mDrugEvent);

                alertDialogDosageForm.dismiss();
            }
        });
        alertDialogDosageForm = builder.create();
        alertDialogDosageForm.show();

    }


    private void createAlertDialogText(String title, final int mode){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        final EditText input = new EditText(getContext());

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Fertig", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mode == CHANGE_NAME) {
                    drug.setDrugName(input.getText().toString());
                    string2[0] = drug.getDrugName();
                    customListView.notifyDataSetChanged();
                }else{
                    drug.setManufacturer(input.getText().toString());
                    string2[1] = drug.getManufacturer();
                    customListView.notifyDataSetChanged();
                }
                mDrugEvent.setDrug(drug);
                EventBus.getDefault().post(mDrugEvent);
            }
        });
        builder.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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

    @Subscribe
    public void onEvent(DrugEvent event){
        drug = event.getDrug();
        mDrugEvent = event;

    }







}
