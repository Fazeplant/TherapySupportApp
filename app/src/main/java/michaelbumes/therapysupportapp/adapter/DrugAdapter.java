package michaelbumes.therapysupportapp.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private final List<Drug> drugs;
    private Context context;
    private final int instanceInt = 0;



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
        //Falls ein Alarm gesetzt wurde
        if (AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drugs.get(position).getDrugEventDbId()).isRegularly()){
            holder.imageViewAlarm.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_alarm_black_24dp));
            //Falls kein Alarm gesetzt wurde
        }else {
            holder.imageViewAlarm.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_alarm_off_black_24dp));
            holder.alarmTime.setText("    -    ");
        }
        //Ändert Icon je nach DosageForm
        switch (drugs.get(position).getDosageFormId()){
            case 1:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
            case 2:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 3:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 4:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_weight));
                break;
            case 5:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_inhalator));
                break;
            case 6:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_syringe));
                break;
            case 7:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 8:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_weight));
                break;
            case 9:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 10:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 11:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 12:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 13:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_suppositories));
                break;
            case 14:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
            case 15:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 16:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 17:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_weight));
                break;
            case 18:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_inhalator));
                break;
            case 19:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_syringe));
                break;
            case 20:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 21:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_weight));
                break;
            case 22:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 23:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 24:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 25:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 26:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_suppositories));
                break;
            case 27:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
            case 28:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 29:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 30:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 31:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 32:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 33:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 34:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 35:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 36:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 37:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_suppositories));
                break;
            case 38:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 39:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 40:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 41:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 42:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 43:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
            case 44:
                holder.imageViewDrug.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
        }
        if (AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drugs.get(position).getDrugEventDbId()).isRegularly()){
            updateAlarmTime(holder,position);
        }


    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView drugName;
        public final TextView drugManufacturer;
        public final TextView alarmTime;
        public final ImageView imageViewDrug;
        public final ImageView imageViewAlarm;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            drugName = itemView.findViewById(R.id.drug_name);
            drugManufacturer = itemView.findViewById(R.id.drug_manufacturer);
            alarmTime = itemView.findViewById(R.id.drug_plan_alarm_time);
            imageViewDrug = itemView.findViewById(R.id.image_drug);
            imageViewAlarm = itemView.findViewById(R.id.image_alarm);
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
    //Wird benötigt um komplexe Daten in der Datenbank zu speichern
    public static DrugEvent convertDrugEventDbToDrugEvent(DrugEvent drugEvent, DrugEventDb drugEventDb, Drug drug){

        if (drugEventDb.isRegularly()){
            String replaceAlarmTime1 = drugEventDb.getAlarmTime().replace("[", "");
            String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
            String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

            List<String> alarmTime = new ArrayList<>(Arrays.asList(replaceAlarmTime3.split(",")));

            String replaceDosage1 = drugEventDb.getDosage().replace("[", "");
            String replaceDosage2 = replaceDosage1.replace("]", "");
            String replaceDosage3 = replaceDosage2.replace(" ", "");
            List<String> arrayList = new ArrayList<>(Arrays.asList(replaceDosage3.split(",")));
            List<Integer> dosage = new ArrayList<>();
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
        drugEvent.setTakingPatternDaysWithIntakeChange(drugEventDb.getTakingPatternDaysWithIntakeChange());
        drugEvent.setTakingPatternDaysWithoutIntakeChange(drugEventDb.getTakingPatternDaysWithOutIntakeChange());

        return drugEvent;

    }



    public void deleteItem(int position){
        DrugEventDb drugEventDb = AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drugs.get(position).getDrugEventDbId());
        if (drugEventDb.isRegularly()){
            String replaceAlarmTime1 = drugEventDb.getAlarmTime().replace("[", "");
            String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
            String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

            List<String> alarmTime = new ArrayList<>(Arrays.asList(replaceAlarmTime3.split(",")));

            //Löscht die Einträge im Alarmmanager damit der Alarm nicht mehr ausgelöst wird
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(context.getApplicationContext(), AlarmMain.class);
            for (int i = 0; i < alarmTime.size() ; i++) {
                int idGenerated = Integer.parseInt(drugEventDb.getId() + "" +String.valueOf(i));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), idGenerated, myIntent, 0);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            }


        }


        Toast.makeText(context, "Medikament: " + drugs.get(position).getDrugName() + " gelöscht", Toast.LENGTH_SHORT).show();
        AppDatabase.getAppDatabase(context).drugEventDbDao().deleteById(drugs.get(position).getDrugEventDbId());
        AppDatabase.getAppDatabase(context).drugDao().delete(drugs.get(position));
        drugs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,drugs.size());
    }
    //Methode um die nächste Alarmzeit anzuzeigen
    private void updateAlarmTime(DrugAdapter.ViewHolder holder, int position){
        String alarmTimeString = "Error";
        String alarmTime1 = AppDatabase.getAppDatabase(context).drugEventDbDao().findById(drugs.get(position).getDrugEventDbId()).getAlarmTime();
        String replaceAlarmTime1 = alarmTime1.replace("[", "");
        String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
        String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

        List<String> alarmTime = new ArrayList<>(Arrays.asList(replaceAlarmTime3.split(",")));



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

    private String getFirstAlarmString(String alarmTimeString) {
        String replaceAlarmTime1 = alarmTimeString.replace("[", "");
        String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
        String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

        List<String> alarmTime = new ArrayList<>(Arrays.asList(replaceAlarmTime3.split(",")));

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
