package michaelbumes.therapysupportapp.alarms;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import michaelbumes.therapysupportapp.fragments.DrugEvent;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;

/**
 * Created by Michi on 06.03.2018.
 */

public class AlarmMain extends BroadcastReceiver {
    private final String REMINDER_BUNDLE = "MyReminderBundle";
    private final String NOTIFICATION_CHANNEL = "MyNotificationChannel";
    private NotificationManager notifManager;
    NotificationHelper helper;
    android.support.v4.app.NotificationCompat.Builder builder;
    Calendar calendar;
    AlarmManager alarmMgr;



    public AlarmMain() {

    }

    public AlarmMain(Context context, Bundle extras, int timeoutInSeconds, DrugEvent drugEvent) {


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.GERMANY);

        calendar = Calendar.getInstance();
        List<String> alarmTime = drugEvent.getAlarmTime();
        int alarmSize = alarmTime.size();






        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmMain.class);
        intent.putExtra(REMINDER_BUNDLE, extras);
        for (int i = 0; i < alarmSize; i++) {
            String s = alarmTime.get(i);
            int hr = Integer.parseInt(s.substring(0, 2));
            int min = Integer.parseInt(s.substring(3, 5));
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        }


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // here you can get the extras you passed in when creating the alarm
        Bundle bundle = intent.getBundleExtra(REMINDER_BUNDLE);
        String drugName = bundle.getString("drugName");
        int alarmType = bundle.getInt("alarmType");
        String dosageForm = bundle.getString("dosageForm");


        helper = new NotificationHelper(context);
        builder = helper.getChannelNotification("Medizin einehmen!", drugName + " 1 " + dosageForm, alarmType );
        helper.getManger().notify(1, builder.build());
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }
    public void cancleAlarm(PendingIntent pendingIntent){
        alarmMgr.cancel(pendingIntent);
    }

}