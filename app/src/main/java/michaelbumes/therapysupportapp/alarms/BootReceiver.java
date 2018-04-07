package michaelbumes.therapysupportapp.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import michaelbumes.therapysupportapp.adapter.DrugAdapter;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.fragments.DrugEvent;

/**
 * Created by Michi on 06.04.2018.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Toast.makeText(context, "Reboot successful", Toast.LENGTH_SHORT).show();
            List<Drug> drugList = AppDatabase.getAppDatabase(context).drugDao().getAll();
            for (int i = 0; i < drugList.size(); i++) {
                DrugEventDb drugEventDb = AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drugList.get(i).getDrugEventDbId());
                DrugEvent drugEvent = new DrugEvent();


                Drug drug = drugList.get(i);
                drugEvent = DrugAdapter.convertDrugEventDbToDrugEvent(drugEvent ,drugEventDb, drug);

                Bundle bundle = new Bundle();
                bundle.putString("drugName", drug.getDrugName());
                bundle.putInt("alarmType", drugEvent.getAlarmType());
                bundle.putInt("takingPattern", drugEvent.getTakingPattern());
                bundle.putString("dosageForm", AppDatabase.getAppDatabase(context).dosageFormDao().getNamebyId(drug.getDosageFormId()));
                bundle.putString("endDay", drugEvent.getEndDate());
                bundle.putString("startDay", drugEvent.getStartingDate());
                bundle.putString("discreteTitle", drugEvent.getDiscreteTitle());
                bundle.putString("discreteBody", drugEvent.getDiscreteBody());
                bundle.putBooleanArray("discretePattern", drugEvent.getAlarmDiscretePatternWeekdays());
                bundle.putInt("id", drugEventDb.getId());

                if (drugEvent.getAlarmType() != 3 && drugEvent.isRegularly()) {
                    AlarmMain alarm = new AlarmMain(context, bundle, drugEvent);
                }

            }

        }

    }
}
