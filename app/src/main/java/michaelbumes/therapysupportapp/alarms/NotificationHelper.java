package michaelbumes.therapysupportapp.alarms;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.fragments.DrugPlanFragment;

/**
 * Created by Michi on 30.03.2018.
 */

public class NotificationHelper extends ContextWrapper {
    private static final String NOTIFICATION_ID = "notification_channel_id";
    private static final String NOTIFICATION_NAME = "notification_Channel";
    private static final String OK_ACTION ="michaelbumes.therapysupportapp.OK_ACTION" ;
    private static final String CANCLE_ACTION ="michaelbumes.therapysupportapp.CANCLE_ACTION" ;

    private NotificationManager manager;
    public NotificationHelper(Context context){
        super(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannels();
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
        channel = new NotificationChannel(NOTIFICATION_ID, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH);
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
    public NotificationCompat.Builder getChannelNotification(String title, String body){
        Intent okIntent = getNotificationIntent();
        okIntent.setAction(OK_ACTION);

        Intent cancelIntent = getNotificationIntent();
        cancelIntent.setAction(CANCLE_ACTION);


            return new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_ID)

                    .setContentText(body)
                    .setContentTitle(title)
                    .setWhen(System.currentTimeMillis())
                    .addAction(R.drawable.ic_check_black_24dp , "Bestätigen" , PendingIntent.getActivity(this, 0, okIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .addAction(R.drawable.ic_cancel_black_24dp, "Überspringen", PendingIntent.getActivity(this, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setSmallIcon(R.drawable.ic_medical_pills_couple)
                    .setAutoCancel(true);
    }

    private Intent getNotificationIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }



}
