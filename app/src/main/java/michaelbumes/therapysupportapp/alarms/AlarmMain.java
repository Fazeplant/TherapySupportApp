package michaelbumes.therapysupportapp.alarms;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;
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


    public AlarmMain() {

    }

    public AlarmMain(Context context, Bundle extras, int timeoutInSeconds) {

        AlarmManager alarmMgr =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmMain.class);
        intent.putExtra(REMINDER_BUNDLE, extras);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(System.currentTimeMillis());
        time.add(Calendar.SECOND, timeoutInSeconds);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(),
                pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // here you can get the extras you passed in when creating the alarm
        //intent.getBundleExtra(REMINDER_BUNDLE));
        helper = new NotificationHelper(context);
        android.support.v4.app.NotificationCompat.Builder builder = helper.getChannelNotification("Title", "Body");
        helper.getManger().notify(1, builder.build());
        Toast.makeText(context, "Alarm went off", Toast.LENGTH_SHORT).show();
    }

}