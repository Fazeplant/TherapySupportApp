package michaelbumes.therapysupportapp.alarms;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;


/**
 * Created by Michi on 30.03.2018.
 */

public class NotificationHelper extends ContextWrapper {
    private static final String NOTIFICATION_ID_SOUND = "notification_channel_sound_id";
    private String notificationChannel = "notification_channel_id";
    private static final String NOTIFICATION_ID_SILENT = "notification_channel_silent_id";
    private static final String NOTIFICATION_NAME_SILENT = "Medizineinnahme ohne Ton";
    private static final String NOTIFICATION_NAME_SOUND = "Medizineinnahme Ton";
    private static final String OK_ACTION ="michaelbumes.therapysupportapp.OK_ACTION" ;
    private static final String CANCLE_ACTION ="michaelbumes.therapysupportapp.CANCLE_ACTION" ;
    private Uri notiUri;
    public static Ringtone ringtone;
    int notificationMode = -1;

    private NotificationManager manager;
    public NotificationHelper(Context context){
        super(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannels();
            createSilentChannels();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        //IMPORTANCE_DEFAULT überall mit Ton
        //IMPORTANCE_HIGH überall mit Ton und Bild
        //IMPORTANCE_LOW überall
        //IMPORTANCE_MIN nur Anzeige
        //IMPORTANCE_NONE keine Anzeige
        NotificationChannel channel = null;
        channel = new NotificationChannel(NOTIFICATION_ID_SOUND, NOTIFICATION_NAME_SOUND, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(false);
        channel.setLightColor(R.color.colorAccent);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManger().createNotificationChannel(channel);







    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createSilentChannels() {
        //IMPORTANCE_DEFAULT überall mit Ton
        //IMPORTANCE_HIGH überall mit Ton und Bild
        //IMPORTANCE_LOW überall
        //IMPORTANCE_MIN nur Anzeige
        //IMPORTANCE_NONE keine Anzeige
        NotificationChannel channel = null;
        channel = new NotificationChannel(NOTIFICATION_ID_SILENT, NOTIFICATION_NAME_SILENT, NotificationManager.IMPORTANCE_LOW);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorAccent);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManger().createNotificationChannel(channel);







    }

    public NotificationManager getManger() {
        if (manager ==null){
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
    //Channels werden nur von Android.O aufwärts untertützt deswegen muss man den Ton und die Vibration manuell setzen
    //notificationChannel gibt an in welchen Channel die Notifikation kommen soll (mit/ohne Ton)
    //notiUri zeigt auf die Standart Notifikation
    //notiUri wird auf null gesetzt um die Nachricht ohne Ton abzuspielen

    public NotificationCompat.Builder getChannelNotification(String title, String body, int alarmtype, int id){
        Intent okIntent = getNotificationIntent();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        okIntent.putExtra("notiBundle",bundle);
        int resId = getResources().getIdentifier("ic_medical_pills_couple", "drawable", getPackageName());
        okIntent.setAction(OK_ACTION);
        switch(alarmtype){
            case 1:
                notiUri = null;
                Uri alarmUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }
                ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
                ringtone.setStreamType(AudioManager.STREAM_ALARM);
                ringtone.play();

                notificationChannel = NOTIFICATION_ID_SOUND;
                break;
            case 2:
                notiUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.setStreamType(AudioManager.STREAM_ALARM);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notificationChannel = NOTIFICATION_ID_SOUND;
                break;
            case 3:
                break;
            case 4:
                notificationChannel = NOTIFICATION_ID_SILENT;
                notiUri = null;
                notificationMode = Notification.DEFAULT_LIGHTS;
                break;
            case 5:
                notificationChannel = NOTIFICATION_ID_SILENT;
                notiUri = null;
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                notificationMode = Notification.DEFAULT_VIBRATE;
                break;
            case 6:
                notificationChannel = NOTIFICATION_ID_SILENT;
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                notiUri = null;
                notificationMode = Notification.DEFAULT_ALL;
                //TODO Diskrete Notifikation
                title = "Custom Message";
                body = "Custom Message";
                resId = getResources().getIdentifier("ic_check_black_24dp", "drawable", getPackageName());
                break;

        }

        Intent cancelIntent = getNotificationIntent();
        cancelIntent.putExtra("notiBundle",bundle);
        cancelIntent.setAction(CANCLE_ACTION);
        PendingIntent cancelPendingIntent = PendingIntent.getActivity(this, id, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);




        return new NotificationCompat.Builder(getApplicationContext(), notificationChannel)

                .setDefaults(notificationMode)
                .setAutoCancel(true)
                .setSound(notiUri)
                .setContentText(body)
                .setContentTitle(title)
                .setWhen(System.currentTimeMillis())
                .addAction(R.drawable.ic_check_black_24dp , "Bestätigen" , PendingIntent.getActivity(this, id, okIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_cancel_black_24dp, "Überspringen", PendingIntent.getActivity(this, id, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setDeleteIntent(cancelPendingIntent)
                .setSmallIcon(resId);
    }


    private Intent getNotificationIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }



}
