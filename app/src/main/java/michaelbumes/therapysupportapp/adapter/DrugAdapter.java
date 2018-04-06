package michaelbumes.therapysupportapp.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.alarms.AlarmMain;
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
        updateAlarmTime(holder,position);

        //        holder.drugManufacturer.setText((CharSequence) AppDatabase.getAppDatabase(context).manufacturerDao().findById(drugs.get(position).getManufacturer()));


    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView drugName;
        public TextView drugManufacturer;
        public TextView alarmTime;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            drugName = itemView.findViewById(R.id.drug_name);
            drugManufacturer = itemView.findViewById(R.id.drug_manufacturer);
            alarmTime = itemView.findViewById(R.id.drug_plan_alarm_time);
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

    }
    public static DrugEvent convertDrugEventDbToDrugEvent(DrugEvent drugEvent, DrugEventDb drugEventDb, Drug drug){

        if (drugEventDb.isRegularly()){
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
            drugEvent.setAlarmTime(alarmTime);
            drugEvent.setDosage(dosage);
        }




        boolean[] weekdays = new boolean[7];
        weekdays[0] = drugEventDb.isMondaySelected();
        weekdays[1] = drugEventDb.isTuesdaySelected();
        weekdays[2] = drugEventDb.isWednesdaySelected();
        weekdays[3] = drugEventDb.isThursdaySelected();
        weekdays[4] = drugEventDb.isFridaySelected();
        weekdays[5] = drugEventDb.isSaturdaySelected();
        weekdays[6] = drugEventDb.isSundaySelected();

        boolean[] weekdaysDiscrete = new boolean[7];

        weekdaysDiscrete[0] = drugEventDb.isMondaySelectedDiscrete();
        weekdaysDiscrete[1] = drugEventDb.isTuesdaySelectedDiscrete();
        weekdaysDiscrete[2] = drugEventDb.isWednesdaySelectedDiscrete();
        weekdaysDiscrete[3] = drugEventDb.isThursdaySelectedDiscrete();
        weekdaysDiscrete[4] = drugEventDb.isFridaySelectedDiscrete();
        weekdaysDiscrete[5] = drugEventDb.isSaturdaySelectedDiscrete();
        weekdaysDiscrete[6] = drugEventDb.isSundaySelectedDiscrete();



        drugEvent.setDrug(drug);

        drugEvent.setAlarmType(drugEventDb.getAlarmType());
        drugEvent.setRecurringReminder(drugEventDb.isRecurringReminder());
        drugEvent.setTakingPatternWeekdays(weekdays);
        drugEvent.setAlarmDiscretePatternWeekdays(weekdaysDiscrete);

        drugEvent.setRunningTime(drugEventDb.getRunningTime());

        drugEvent.setRegularly(drugEventDb.isRegularly());

        drugEvent.setDiscreteTitle(drugEventDb.getDiscreteTitle());
        drugEvent.setDiscreteBody(drugEventDb.getDiscreteBody());

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



    public void deleteItem(int position){
        DrugEventDb drugEventDb = AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drugs.get(position).getDrugEventDbId());
        String replaceAlarmTime1 = drugEventDb.getAlarmTime().replace("[", "");
        String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
        String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

        List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(context.getApplicationContext(), AlarmMain.class);
        for (int i = 0; i < alarmTime.size() ; i++) {
            int idGenerated = Integer.parseInt(drugEventDb.getId() + "" +String.valueOf(i));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), idGenerated, myIntent, 0);
            alarmManager.cancel(pendingIntent);
        }



        Toast.makeText(context, "Medikament: " + drugs.get(position).getDrugName() + " gelöscht", Toast.LENGTH_SHORT).show();
        AppDatabase.getAppDatabase(context).drugEventDbDao().deleteById(drugs.get(position).getDrugEventDbId());
        AppDatabase.getAppDatabase(context).drugDao().delete(drugs.get(position));
        drugs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,drugs.size());
    }

    public void updateAlarmTime(DrugAdapter.ViewHolder holder, int position){
        String alarmTimeString = "Error";
        String alarmTime1 = AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drugs.get(position).getDrugEventDbId()).getAlarmTime();
        String replaceAlarmTime1 = alarmTime1.replace("[", "");
        String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
        String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

        List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));



        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        int currentTimeInMinutes = hourOfDay*60 + minOfDay;
        int mostRecentAlarmInt = 99999999;



        for (int i = 0; i < alarmTime.size(); i++) {
            int hr = Integer.parseInt(alarmTime.get(i).substring(0, 2));
            int min = Integer.parseInt(alarmTime.get(i).substring(3, 5));

            String hrString = String.valueOf(hr);
            String minString = String.valueOf(min);
            int alarmTimeInMinutes = hr*60 + min;
            if (currentTimeInMinutes < alarmTimeInMinutes){
                if (alarmTimeInMinutes < mostRecentAlarmInt) {
                    mostRecentAlarmInt = alarmTimeInMinutes;
                    if (hr < 10) {
                        hrString = "0" + String.valueOf(hr);

                    }
                    if (min < 10) {
                        minString = "0" + String.valueOf(min);
                    }
                    alarmTimeString = hrString + ":" + minString;
                }
            }
        }
        if (alarmTimeString.equals("Error")){
            alarmTimeString = getFirstAlarmString(alarmTime1);
        }

        holder.alarmTime.setText(alarmTimeString);


    }

    public String getFirstAlarmString(String alarmTimeString) {
        String replaceAlarmTime1 = alarmTimeString.replace("[", "");
        String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
        String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

        List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));

        String mostRecentAlarmString = alarmTime.get(0);


        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        int currentTimeInMinutes = hourOfDay * 60 + minOfDay;
        int mostRecentAlarmInt = 9999;

        for (int i = 0; i < alarmTime.size(); i++) {
            int hr = Integer.parseInt(alarmTime.get(i).substring(0, 2));
            int min = Integer.parseInt(alarmTime.get(i).substring(3, 5));

            String hrString = String.valueOf(hr);
            String minString = String.valueOf(min);
            int alarmTimeInMinutes = hr * 60 + min;
            if (currentTimeInMinutes > alarmTimeInMinutes) {
                if (alarmTimeInMinutes < mostRecentAlarmInt) {
                    mostRecentAlarmInt = alarmTimeInMinutes;
                    if (hr < 10) {
                        hrString = "0" + String.valueOf(hr);

                    }
                    if (min < 10) {
                        minString = "0" + String.valueOf(min);
                    }
                    mostRecentAlarmString = hrString + ":" + minString;
                }
            }
        }
        return mostRecentAlarmString;
    }





}
