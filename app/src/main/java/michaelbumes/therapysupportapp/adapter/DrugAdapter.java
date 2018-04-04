package michaelbumes.therapysupportapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.fragments.DrugEvent;
import michaelbumes.therapysupportapp.fragments.DrugFragment;

/**
 * Created by Michi on 31.01.2018.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder>{
    List<Drug> drugs;
    private Context context;
    int instanceInt = 0;



    public DrugAdapter(List<Drug> drugs) {
        this.drugs = drugs;
    }

    @Override
    public DrugAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drugs_row, parent,false );
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrugAdapter.ViewHolder holder, int position) {
        holder.drugName.setText(drugs.get(position).getDrugName());
        holder.drugManufacturer.setText(drugs.get(position).getManufacturer());
        //        holder.drugManufacturer.setText((CharSequence) AppDatabase.getAppDatabase(context).manufacturerDao().findById(drugs.get(position).getManufacturer()));


    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView drugName;
        public TextView drugManufacturer;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            drugName = itemView.findViewById(R.id.drug_name);
            drugManufacturer = itemView.findViewById(R.id.drug_manufacturer);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Drug drug = drugs.get(position);
            FragNavController mFragmentNavigation = ((MainActivity) context).getmNavController();

            DrugEvent drugEvent = new DrugEvent();
            DrugEventDb drugEventDb = AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drug.getDrugEventDbId());

            drugEvent = convertDrugEventDbToDrugEvent(drugEvent, drugEventDb,drug);



            EventBus.getDefault().removeAllStickyEvents();
            EventBus.getDefault().postSticky(drugEvent);
            mFragmentNavigation.pushFragment(DrugFragment.newInstance(instanceInt + 1));



        }
        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            Drug drug = drugs.get(position);
            deleteDrug(context, drug,position);

            return true;
        }

    }
    public DrugEvent convertDrugEventDbToDrugEvent(DrugEvent drugEvent, DrugEventDb drugEventDb, Drug drug){
        String replaceAlarmTime1 = drugEventDb.getAlarmTime().replace("[", "");
        String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
        String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

        List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));

        String replaceDosage1 = drugEventDb.getDosage().replace("[", "");
        String replaceDosage2 = replaceDosage1.replace("]", "");
        String replaceDosage3 = replaceDosage2.replace(" ", "");
        List<String> arrayList = new ArrayList<String>(Arrays.asList(replaceDosage3.split(",")));
        List<Integer> dosage = new ArrayList<Integer>();
        for(String i:arrayList){
            dosage.add(Integer.parseInt(i.trim()));
        }




        boolean[] weekdays = new boolean[7];
        weekdays[0] = drugEventDb.isMondaySelected();
        weekdays[1] = drugEventDb.isTuesdaySelected();
        weekdays[2] = drugEventDb.isWednesdaySelected();
        weekdays[3] = drugEventDb.isTuesdaySelected();
        weekdays[4] = drugEventDb.isFridaySelected();
        weekdays[5] = drugEventDb.isSaturdaySelected();
        weekdays[6] = drugEventDb.isSundaySelected();

        drugEvent.setDrug(drug);

        drugEvent.setAlarmType(drugEventDb.getAlarmType());
        drugEvent.setRecurringReminder(drugEventDb.isRecurringReminder());
        drugEvent.setAlarmTime(alarmTime);
        drugEvent.setTakingPatternWeekdays(weekdays);

        drugEvent.setDosage(dosage);
        drugEvent.setRunningTime(drugEventDb.getRunningTime());

        drugEvent.setRegularly(drugEventDb.isRegularly());

        drugEvent.setEndDate(drugEventDb.getEndDate());
        drugEvent.setStartingDate(drugEventDb.getStartingDate());

        drugEvent.setTakingPattern(drugEventDb.getTakingPattern());
        drugEvent.setTakingPatternDaysWithIntake(drugEventDb.getTakingPatternDaysWithIntake());
        drugEvent.setTakingPatternDaysWithoutIntake(drugEventDb.getTakingPatternDaysWithOutIntake());
        drugEvent.setTakingPatternHourInterval(drugEventDb.getTakingPatternHourInterval());
        drugEvent.setTakingPatternEveryOtherDay(drugEventDb.getTakingPatternEveryOtherDay());
        drugEvent.setTakingPatternHourNumber(drugEventDb.getTakingPatternHourNumber());
        drugEvent.setTakingPatternHourStart(drugEventDb.getTakingPatternHourStart());
        return drugEvent;

    }

    public void deleteDrug(Context context, Drug drug, int position){
        drugs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,drugs.size());
        AppDatabase.getAppDatabase(context).drugEventDbDao().deleteById(drug.getDrugEventDbId());
        AppDatabase.getAppDatabase(context).drugDao().delete(drug);
    }


}
