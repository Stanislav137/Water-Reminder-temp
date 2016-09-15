package app.reminder.stas.waterreminder;

import java.util.concurrent.TimeUnit;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyService extends Service {
    NotificationManager nm;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendNotif();
        return super.onStartCommand(intent, flags, startId);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void sendNotif() {

        // 3-я часть
        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, WaterActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setSmallIcon(R.mipmap.icon56x56)
                .setContentTitle(getString(R.string.waterbody) + "'" + getString(R.string.waterbody2))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setTicker(getString(R.string.watertime))
                .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
                .setContentText(getString(R.string.waterdrink));
        Notification notification = builder.build();
            notification.defaults = Notification.DEFAULT_SOUND;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        // notification.sound = Uri.parse("file:///sdcard/water.mp3");
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

}
