package michaelbumes.therapysupportapp.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import michaelbumes.therapysupportapp.adapter.DrugAdapter;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.database.DatabaseDrugList;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.fragments.DrugEvent;

import static michaelbumes.therapysupportapp.activities.MainActivity.databaseDrugList;


/**
 * Created by Michi on 06.04.2018.
 */
//Wird nach Neustart ausgeführt und setzt alle Alarme erneut aus der Datenbank
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            List<Drug> drugList = AppDatabase.getAppDatabase(context).drugDao().getAll();
            for (int i = 0; i < drugList.size(); i++) {
                DrugEventDb drugEventDb = AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drugList.get(i).getDrugEventDbId());
                DrugEvent drugEvent = new DrugEvent();

                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

                Drug drug = drugList.get(i);
                drugEvent = DrugAdapter.convertDrugEventDbToDrugEvent(drugEvent ,drugEventDb, drug);
                //Wird überschrieben damit nach neustart nicht alle Alarme sofort ausgelöst werden
                try {if (sdfDate.parse(sdfDate.format(Calendar.getInstance().getTime())).getTime() >= sdfDate.parse(drugEvent.getStartingDate()).getTime()) {
                        drugEvent.setStartingDate(sdfDate.format(Calendar.getInstance().getTime()));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putString("drugName", drug.getDrugName());
                bundle.putInt("alarmType", drugEvent.getAlarmType());
                bundle.putInt("takingPattern", drugEvent.getTakingPattern());
                bundle.putString("dosageForm", DatabaseDrugList.getAppDatabase(context).dosageFormDao().getNameById(drug.getDosageFormId()));
                bundle.putString("endDay", drugEvent.getEndDate());
                bundle.putString("startDay", drugEvent.getStartingDate());
                bundle.putString("discreteTitle", drugEvent.getDiscreteTitle());
                bundle.putString("discreteBody", drugEvent.getDiscreteBody());
                bundle.putBooleanArray("discretePattern", drugEvent.getAlarmDiscretePatternWeekdays());
                bundle.putInt("id", drug.getId());



                if (drugEvent.getAlarmType() != 3 && drugEvent.isRegularly()) {
                    AlarmMain alarm = new AlarmMain(context, bundle, drugEvent);
                }

            }

        }

    }
}
