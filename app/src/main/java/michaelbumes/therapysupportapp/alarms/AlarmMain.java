package michaelbumes.therapysupportapp.alarms;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import michaelbumes.therapysupportapp.fragments.DrugEvent;

import java.util.Date;
import java.util.List;
import java.util.Locale;


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
    private DrugEvent mDrugEvent;
    private Bundle mExtras;
    private int startDay, startMonth, startYear;
    private Date startingDay;



    public AlarmMain() {

    }

    public AlarmMain(Context context, Bundle extras, DrugEvent drugEvent) {

        mDrugEvent = drugEvent;
        mContext = context;
        calendar = Calendar.getInstance();
        alarmTime = drugEvent.getAlarmTime();
        mExtras = extras;

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



        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm", Locale.GERMANY);


        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, AlarmMain.class);
        intent.putExtra(REMINDER_BUNDLE, extras);

        switch (drugEvent.getTakingPattern()){
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
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, startDay);
        calendar.set(Calendar.MONTH, startMonth - 1);
        calendar.set(Calendar.YEAR, startYear);
        PendingIntent pendingIntentStart = PendingIntent.getBroadcast(mContext, mDrugEvent.getDrug().getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntentStart);


        for (int i = 0; i < mDrugEvent.getTakingPatternHourNumber(); i++) {
            calendar.set(Calendar.HOUR_OF_DAY, hr + mDrugEvent.getTakingPatternHourInterval());
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, startDay);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.YEAR, startYear);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, mDrugEvent.getDrug().getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
            
        }
    }

    private void createAlarmDaily() {
        for (int i = 0; i < alarmTime.size(); i++) {
            String s = alarmTime.get(i);
            int hr = Integer.parseInt(s.substring(0, 2));
            int min = Integer.parseInt(s.substring(3, 5));
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, startDay);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.YEAR, startYear);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, mDrugEvent.getDrug().getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void createAlarmEveryOtherDay() {
        long interval = mDrugEvent.getTakingPatternEveryOtherDay() * AlarmManager.INTERVAL_DAY;
        for (int i = 0; i < alarmTime.size(); i++) {
            String s = alarmTime.get(i);
            int hr = Integer.parseInt(s.substring(0, 2));
            int min = Integer.parseInt(s.substring(3, 5));
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, startDay);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.YEAR, startYear);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, mDrugEvent.getDrug().getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // here you can get the extras you passed in when creating the alarm
        Bundle bundle = intent.getBundleExtra(REMINDER_BUNDLE);
        String drugName = bundle.getString("drugName");
        int alarmType = bundle.getInt("alarmType");
        String dosageForm = bundle.getString("dosageForm");
        int takingPattern = bundle.getInt("takingPattern");

        if(takingPattern == 4) {
            boolean[] takingPatternWeekdays = bundle.getBooleanArray("daysToAlarm");
            Calendar c = Calendar.getInstance();
            int currentDay = c.get(Calendar.DAY_OF_WEEK);
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
        builder = helper.getChannelNotification("Medizin einehmen!", drugName + " 1 " + dosageForm, alarmType );
        helper.getManger().notify(1, builder.build());
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }

    public static void cancelAlarm(Context context, int id){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(context, AlarmMain.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);    }

}