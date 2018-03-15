package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.DosageForm;
import michaelbumes.therapysupportapp.entity.DrugList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment {
    Button nukeButton;
    Button createButton;

    public static SettingsFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_settings);
        nukeButton = view.findViewById(R.id.nuke_button);
        createButton=view.findViewById(R.id.create_drug_list);
        nukeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.destroyInstance();
                AppDatabase.getAppDatabase(getContext()).drugDao().nukeTable();
                AppDatabase.getAppDatabase(getContext()).drugListDao().nukeTable();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DosageForm dosageForm1 = new DosageForm();
                DosageForm dosageForm2 = new DosageForm();
                DosageForm dosageForm3 = new DosageForm();
                DosageForm dosageForm4 = new DosageForm();
                DosageForm dosageForm5 = new DosageForm();
                DosageForm dosageForm6 = new DosageForm();
                DosageForm dosageForm7 = new DosageForm();
                DosageForm dosageForm8 = new DosageForm();
                DosageForm dosageForm9 = new DosageForm();
                DosageForm dosageForm10 = new DosageForm();
                DosageForm dosageForm11 = new DosageForm();
                DosageForm dosageForm12 = new DosageForm();
                DosageForm dosageForm13 = new DosageForm();

                dosageForm1.setDosageFormName("Ampulle(n)");
                dosageForm2.setDosageFormName("Anwendung(en)");
                dosageForm3.setDosageFormName("Einheit(en)");
                dosageForm4.setDosageFormName("Gramm");
                dosageForm5.setDosageFormName("Hub/Hübe");
                dosageForm6.setDosageFormName("Injektion(en)");
                dosageForm7.setDosageFormName("Kapsel(n)");
                dosageForm8.setDosageFormName("Milligramm");
                dosageForm9.setDosageFormName("Milliliter");
                dosageForm10.setDosageFormName("Stück");
                dosageForm11.setDosageFormName("Tablette(n)");
                dosageForm12.setDosageFormName("Tropfen");
                dosageForm13.setDosageFormName("Zäpfchen");


                DrugList drug1 = new DrugList();
                DrugList drug2 = new DrugList();
                DrugList drug3 = new DrugList();
                DrugList drug4 = new DrugList();

                drug1.setName("Antidepressiva Test 1 P");
                drug1.setManufacturer("STADA Diagnostik");
                drug1.setPzn("10110497");
                drug1.setDosageFormId(11);
                drug1.setSideEffects("Appetitlosigkeit, Erhöhung des Blutzuckers");
                drug1.setTakingNote("Täglich über drei Tage");

                drug2.setName("LITHIUM APOGEPHA");
                drug2.setManufacturer("Apogepha Arzneimittel GmbH");
                drug2.setPzn("4775459");
                drug2.setDosageFormId(11);
                drug2.setSideEffects("Vermehrung der weißen Blutkörperchen");
                drug2.setTakingNote("Täglich über drei Tage");

                drug3.setName("Hypnorex ret.");
                drug3.setManufacturer("Sanofi-Aventis Deutschland GmbH");
                drug3.setPzn("3873987");
                drug3.setDosageFormId(11);
                drug3.setSideEffects("Haluzinationen");
                drug3.setTakingNote("Täglich über drei Tage");

                drug4.setName("Isla Cassis");
                drug4.setManufacturer("Engelhard Arzneimittel GmbH & Co.KG");
                drug4.setPzn("-03397699");
                drug4.setDosageFormId(11);
                drug4.setSideEffects("Haluzinationen");
                drug4.setTakingNote("Täglich über drei Tage");


                AppDatabase.getAppDatabase(getContext()).drugListDao().insertAll(drug1,drug2,drug3,drug4);
                AppDatabase.getAppDatabase(getContext()).dosageFormDao().insertAll(dosageForm1,dosageForm2, dosageForm3, dosageForm4, dosageForm5, dosageForm6, dosageForm7, dosageForm8, dosageForm9, dosageForm10, dosageForm11, dosageForm12, dosageForm13);
            }
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
