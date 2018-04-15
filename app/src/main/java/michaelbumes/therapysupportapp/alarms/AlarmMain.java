package michaelbumes.therapysupportapp.alarms;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.fragments.DrugEvent;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 * Created by Michi on 06.03.2018.
 */

public class AlarmMain extends BroadcastReceiver {
    private final String REMINDER_BUNDLE = "MyReminderBundle";
    private final String NOTIFICATION_CHANNEL = "MyNotificationChannel";
    private NotificationManager notifManager;
    private NotificationHelper helper;
    private android.support.v4.app.NotificationCompat.Builder builder;
    private Calendar calendar;
    private AlarmManager alarmMgr;
    private Intent intent;
    private Context mContext;
    private List<String> alarmTime;
    private List<Integer> dosage;
    private DrugEvent mDrugEvent;
    private Bundle mExtras;
    private int startDay, startMonth, startYear;
    private Date startingDay, endDay;
    private int id;


    public AlarmMain() {

    }

    public AlarmMain(Context context, Bundle extras, DrugEvent drugEvent) {

        mDrugEvent = drugEvent;
        mContext = context;
        calendar = Calendar.getInstance();
        alarmTime = drugEvent.getAlarmTime();
        dosage = drugEvent.getDosage();
        mExtras = extras;
        id = extras.getInt("id");


        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            startingDay = sdfDate.parse(mDrugEvent.getStartingDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        startDay = Integer.parseInt(df.format("dd", startingDay).toString());
        startMonth = Integer.parseInt(df.format("MM", startingDay).toString());
        startYear = Integer.parseInt(df.format("yyyy", startingDay).toString());


        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, AlarmMain.class);
        intent.putExtra(REMINDER_BUNDLE, mExtras);

        switch (drugEvent.getTakingPattern()) {
            case 1:
                createAlarmDaily();
                break;
            case 2:
                createAlarmEveryHour();
                break;
            case 3:
                createAlarmEveryOtherDay();
                break;
            case 4:
                createAlarmWeekdays();
                break;
            case 5:
                createAlarmCycle();
                break;

        }


        ;


    }

    private void createAlarmCycle() {
        mExtras.putInt("daysWithIntake", mDrugEvent.getTakingPatternDaysWithIntake());
        mExtras.putInt("daysWithoutIntake", mDrugEvent.getTakingPatternDaysWithoutIntake());
        mExtras.putInt("daysWithIntakeStatic", mDrugEvent.getTakingPatternDaysWithIntake());
        mExtras.putInt("daysWithoutIntakeStatic", mDrugEvent.getTakingPatternDaysWithoutIntake());

        ArrayList<String> alarmTimeArray = new ArrayList<>(alarmTime);
        mExtras.putStringArrayList("alarmTime", alarmTimeArray);

        for (int i = 0; i < alarmTime.size(); i++) {
            String s = alarmTime.get(i);
            int hr = Integer.parseInt(s.substring(0, 2));
            int min = Integer.parseInt(s.substring(3, 5));
            int idGenerated = Integer.parseInt(id + "" +String.valueOf(i));
            mExtras.putInt("dosage", dosage.get(i));
            mExtras.putString("alarmTimeI" , alarmTime.get(i));
            mExtras.putInt("idGenerated" ,idGenerated);
            intent.putExtra(REMINDER_BUNDLE,mExtras);
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, startDay);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.YEAR, startYear);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, idGenerated, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


    }

    private void createAlarmWeekdays() {
        mExtras.putBooleanArray("daysToAlarm", mDrugEvent.getTakingPatternWeekdays());
        intent.putExtra(REMINDER_BUNDLE, mExtras);
        createAlarmDaily();
    }

    private void createAlarmEveryHour() {

        String s = mDrugEvent.getTakingPatternHourStart();
        int hr = Integer.parseInt(s.substring(0, 2));
        int min = Integer.parseInt(s.substring(3, 5));
        mExtras.putInt("dosage", dosage.get(0));
        mExtras.putString("alarmTimeI" , s);
        mExtras.putInt("idGenerated" ,Integer.parseInt(id + "" +String.valueOf(0)));
        intent.putExtra(REMINDER_BUNDLE,mExtras);
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, startDay);
        calendar.set(Calendar.MONTH, startMonth - 1);
        calendar.set(Calendar.YEAR, startYear);
        PendingIntent pendingIntentStart = PendingIntent.getBroadcast(mContext,Integer.parseInt(id + "" +String.valueOf(0)), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentStart);


        for (int i = 0; i < mDrugEvent.getTakingPatternHourNumber(); i++) {
            int idGenerated = Integer.parseInt(id + "" +String.valueOf(i+1));
            mExtras.putInt("dosage", dosage.get(0));
            mExtras.putInt("idGenerated" ,idGenerated);
            int alarmTimeHr = hr + mDrugEvent.getTakingPatternHourInterval();
            String alarmTimeHrString;
            String alarmTimeMinString;
            if (min<10){
                alarmTimeMinString = "0" + String.valueOf(min);
            }else {
                alarmTimeMinString = String.valueOf(min);
            }
            if (alarmTimeHr> 24){
                alarmTimeHr = alarmTimeHr -24;
            }
            if (alarmTimeHr <10){

                alarmTimeHrString = "0" + String.valueOf(alarmTimeHr) + ":" +alarmTimeMinString;
            }else {
                alarmTimeHrString = String.valueOf(alarmTimeHr) + ":" +alarmTimeMinString;
            }
            mExtras.putString("alarmTimeI" , alarmTimeHrString);
            intent.putExtra(REMINDER_BUNDLE,mExtras);
            calendar.set(Calendar.HOUR_OF_DAY, hr + mDrugEvent.getTakingPatternHourInterval());
            hr = hr + mDrugEvent.getTakingPatternHourInterval();
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, startDay);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.YEAR, startYear);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, idGenerated, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }
    }

    private void createAlarmDaily() {
        for (int i = 0; i < alarmTime.size(); i++) {
            String s = alarmTime.get(i);
            mExtras.putInt("dosage", dosage.get(i));
            int idGenerated = Integer.parseInt(id + "" +String.valueOf(i));

            mExtras.putString("alarmTimeI" , alarmTime.get(i));
            mExtras.putInt("idGenerated" ,idGenerated);

            intent.putExtra(REMINDER_BUNDLE,mExtras);
            int hr = Integer.parseInt(s.substring(0, 2));
            int min = Integer.parseInt(s.substring(3, 5));
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, startDay);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.YEAR, startYear);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, idGenerated, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void createAlarmEveryOtherDay() {
        long interval = mDrugEvent.getTakingPatternEveryOtherDay() * AlarmManager.INTERVAL_DAY;
        for (int i = 0; i < alarmTime.size(); i++) {
            String s = alarmTime.get(i);
            int idGenerated = Integer.parseInt(id + "" +String.valueOf(i));
            mExtras.putInt("dosage", dosage.get(i));
            mExtras.putInt("idGenerated" ,idGenerated);
            mExtras.putString("alarmTimeI" , alarmTime.get(i));
            intent.putExtra(REMINDER_BUNDLE,mExtras);
            int hr = Integer.parseInt(s.substring(0, 2));
            int min = Integer.parseInt(s.substring(3, 5));
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, startDay);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.YEAR, startYear);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, idGenerated, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // here you can get the extras you passed in when creating the alarm
        Bundle bundle = intent.getBundleExtra(REMINDER_BUNDLE);
        String drugName = bundle.getString("drugName");
        int alarmType = bundle.getInt("alarmType");
        String alarmTimeI = bundle.getString("alarmTimeI");
        String dosageForm = bundle.getString("dosageForm");
        int dosage = bundle.getInt("dosage");
        int takingPattern = bundle.getInt("takingPattern");
        int mIdGenerated = bundle.getInt("idGenerated");
        String endDayString = bundle.getString("endDay");
        boolean[] discretePattern = bundle.getBooleanArray("discretePattern");

        String discreteTitle = bundle.getString("discreteTitle");
        String discreteBody = bundle.getString("discreteBody");

        Date startDayDate = null;
        String startDayString = bundle.getString("startDay");
        int id = bundle.getInt("id");
        int daysWithIntake = 0;
        int daysWithoutIntake = 0;


        Calendar c = Calendar.getInstance();

        int currentDay = c.get(Calendar.DAY_OF_WEEK);




        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date startDate1 = cal.getTime();
        cal.setTime(startDate1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //int year  = cal.get(Calendar.HOUR_OF_DAY);
        //int month = cal.get(Calendar.MONTH);
        //int date  = cal.get(Calendar.DATE);
        //cal.clear();
        //cal.set(year, month, date);
        long todayMillis2 = cal.getTimeInMillis();
        long millis = System.currentTimeMillis();

        Date todayDate = null;
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");

        String todayString = sdfDate.format(startDate1);
        try {
             todayDate = sdfDate.parse(todayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Long hrLong = TimeUnit.HOURS.toMillis(Integer.parseInt(alarmTimeI.substring(0, 2)));
        Long minLong = TimeUnit.MINUTES.toMillis(Integer.parseInt(alarmTimeI.substring(3, 5)));




        try {
            startDayDate = sdfDate.parse(startDayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //600000 = 10 min Toleranz
        if (todayMillis2  < startDayDate.getTime() + hrLong +minLong) {
            return;
        }
        if ((todayDate.getTime() == startDayDate.getTime()) && ((todayMillis2 > startDayDate.getTime() + hrLong + minLong))){
            return;
        }

        Date endDayDate = null;

        if (!endDayString.equals("-1")) {
            try {
                endDayDate = sdfDate.parse(endDayString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (todayMillis2 > endDayDate.getTime()) {
                cancelAlarm(context, mIdGenerated);
                return;
            }
        }
        if (takingPattern == 5) {
            DrugEventDb drugEventDb = AppDatabase.getAppDatabase(context).drugEventDbDao().findById(AppDatabase.getAppDatabase(context).drugDao().findById(id).getDrugEventDbId());
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            daysWithIntake = drugEventDb.getTakingPatternDaysWithIntakeChange();
            daysWithoutIntake = drugEventDb.getTakingPatternDaysWithOutIntakeChange();

            String replaceAlarmTime1 = drugEventDb.getAlarmTime().replace("[", "");
            String replaceAlarmTime2 = replaceAlarmTime1.replace("]", "");
            String replaceAlarmTime3 = replaceAlarmTime2.replace(" ", "");

            List<String> alarmTime = new ArrayList<String>(Arrays.asList(replaceAlarmTime3.split(",")));
            //ArrayList<String> alarmTimeArray = new ArrayList<String>(Integer.parseInt(drugEventDb.getAlarmTime()));

            if (daysWithIntake > 0) {
                drugEventDb.setTakingPatternDaysWithIntakeChange(daysWithIntake - 1);
                AppDatabase.getAppDatabase(context).drugEventDbDao().update(drugEventDb);

                for (int i = 0; i < alarmTime.size(); i++) {
                    String s = alarmTime.get(i);
                    int idGenerated = Integer.parseInt(id + "" +String.valueOf(i));
                    bundle.putInt("idGenerated" ,idGenerated);
                    bundle.putString("alarmTimeI" , alarmTime.get(i));
                    intent.putExtra(REMINDER_BUNDLE,bundle);
                    int hr = Integer.parseInt(s.substring(0, 2));
                    int min = Integer.parseInt(s.substring(3, 5));
                    c.set(Calendar.HOUR_OF_DAY, hr);
                    c.set(Calendar.MINUTE, min);
                    c.set(Calendar.SECOND, 0);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idGenerated, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //plus einen Tag = + 86400000L
                    alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+ 86400000L, pendingIntent);
                }


            } else if (daysWithoutIntake < 1) {
                int daysWithIntakeStatic = drugEventDb.getTakingPatternDaysWithIntake();
                int daysWithoutIntakeStatic = drugEventDb.getTakingPatternDaysWithOutIntake();

                drugEventDb.setTakingPatternDaysWithIntakeChange(daysWithIntakeStatic);
                drugEventDb.setTakingPatternDaysWithIntakeChange(daysWithoutIntakeStatic);
                AppDatabase.getAppDatabase(context).drugEventDbDao().update(drugEventDb);

                for (int i = 0; i < alarmTime.size(); i++) {
                    String s = alarmTime.get(i);
                    int hr = Integer.parseInt(s.substring(0, 2));
                    int min = Integer.parseInt(s.substring(3, 5));
                    int idGenerated = Integer.parseInt(id + "" +String.valueOf(i));
                    bundle.putString("alarmTimeI" , alarmTime.get(i));
                    bundle.putInt("idGenerated" ,idGenerated);
                    intent.putExtra(REMINDER_BUNDLE,bundle);
                    //Kein Tag, Monat oder Jahr, weil Alarm auf morgen gesetzt werden soll
                    c.set(Calendar.HOUR_OF_DAY, hr);
                    c.set(Calendar.MINUTE, min);
                    c.set(Calendar.SECOND, 0);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idGenerated, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 86400000L, pendingIntent);
                }
            } else {
                drugEventDb.setTakingPatternDaysWithOutIntakeChange(daysWithoutIntake - 1);
                AppDatabase.getAppDatabase(context).drugEventDbDao().update(drugEventDb);

                for (int i = 0; i < alarmTime.size(); i++) {
                    String s = alarmTime.get(i);
                    int idGenerated = Integer.parseInt(id + "" +String.valueOf(i));
                    bundle.putInt("idGenerated" ,idGenerated);
                    bundle.putString("alarmTimeI" , alarmTime.get(i));
                    intent.putExtra(REMINDER_BUNDLE,bundle);
                    int hr = Integer.parseInt(s.substring(0, 2));
                    int min = Integer.parseInt(s.substring(3, 5));
                    c.set(Calendar.HOUR_OF_DAY, hr);
                    c.set(Calendar.MINUTE, min);
                    c.set(Calendar.SECOND, 0);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idGenerated, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 86400000L, pendingIntent);
                    return;
                }

            }
        }

        if (takingPattern == 4) {
            boolean[] takingPatternWeekdays = bundle.getBooleanArray("daysToAlarm");
            if (currentDay == Calendar.SUNDAY && takingPatternWeekdays[6] == false) {
                return;
            } else if (currentDay == Calendar.MONDAY && takingPatternWeekdays[0] == false) {
                return;

            } else if (currentDay == Calendar.TUESDAY && takingPatternWeekdays[1] == false) {
                return;
            } else if (currentDay == Calendar.WEDNESDAY && takingPatternWeekdays[2] == false) {
                return;
            } else if (currentDay == Calendar.THURSDAY && takingPatternWeekdays[3] == false) {
                return;
            } else if (currentDay == Calendar.FRIDAY && takingPatternWeekdays[4] == false) {
                return;
            } else if (currentDay == Calendar.SATURDAY && takingPatternWeekdays[5] == false) {
                return;
            }
        }


        helper = new NotificationHelper(context);
        builder = helper.getChannelNotification("Medizin einehmen! ", drugName + " ", dosage ," " + dosageForm, alarmType, mIdGenerated,discreteTitle,discreteBody,discretePattern);
        helper.getManger().notify(mIdGenerated, builder.build());
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }

    public static void cancelAlarm(Context context, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(context, AlarmMain.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

}