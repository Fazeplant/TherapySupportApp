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

                DrugList drug1 = new DrugList();
                DrugList drug2 = new DrugList();
                DrugList drug3 = new DrugList();

                drug1.setName("STADA Diagnostik Antidepressiva Test 1 P");
                drug1.setManufacturerId(1);
                drug1.setPzn(10110497);
                drug1.setDosageFormId(1);
                drug1.setSideEffects("Appetitlosigkeit, Erhöhung des Blutzuckers");
                drug1.setTakingNote("Täglich über drei Tage");

                drug2.setName("LITHIUM APOGEPHA");
                drug2.setManufacturerId(2);
                drug2.setPzn(4775459);
                drug2.setDosageFormId(2);
                drug2.setSideEffects("Vermehrung der weißen Blutkörperchen");
                drug2.setTakingNote("Täglich über drei Tage");

                drug3.setName("Hypnorex ret.");
                drug3.setManufacturerId(3);
                drug3.setPzn(3873987);
                drug3.setDosageFormId(3);
                drug3.setSideEffects("Haluzinationen");
                drug3.setTakingNote("Täglich über drei Tage");


                AppDatabase.getAppDatabase(getContext()).drugListDao().insertAll(drug1,drug2,drug3);
            }
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
