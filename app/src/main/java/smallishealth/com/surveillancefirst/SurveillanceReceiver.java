package smallishealth.com.surveillancefirst;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
//import android.support.v4.app.NotificationCompat;


public class SurveillanceReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 0;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Intent notifyIntent = new Intent(context, MainActivity.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, notifyIntent, getPendingFlags());


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelId");
            builder.setContentTitle("Surveillance reminder")
                    .setContentText("Prière de faire une recherche active de cas")
                    .setSmallIcon(R.drawable.ic_notification)
                    // .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true);

            Notification notification = builder.build();
            notificationManager.notify(NOTIFICATION_ID,notification);




    }

    private int getPendingFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        };
        return PendingIntent.FLAG_UPDATE_CURRENT;
    };
}
