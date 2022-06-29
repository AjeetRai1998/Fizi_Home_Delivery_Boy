package digi.coders.capsicodeliverypartner.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.activity.DashboardActivity;

/* loaded from: classes5.dex */
public class NotificationService extends Service {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private static final String default_notification_channel_id = "default";
    NotificationManager mNotificationManager;
    String title;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent.hasExtra("title")) {
            this.title = intent.getExtras().getString("title");
            createNotification();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    public void createNotification() {
        try {
            NotificationManager notificationManager = this.mNotificationManager;
            if (notificationManager != null) {
                notificationManager.cancelAll();
            }
            Intent notificationIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            notificationIntent.putExtra("fromNotification", true);
            notificationIntent.setFlags(603979776);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            this.mNotificationManager = (NotificationManager) getSystemService("notification");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), default_notification_channel_id);
            mBuilder.setContentTitle("Capsico real time ordering");
            mBuilder.setContentIntent(pendingIntent);
            if (this.title.equalsIgnoreCase("online")) {
                mBuilder.setContentText("Status : Accepting Orders");
            } else {
                mBuilder.setContentText("Status : Offline");
            }
            mBuilder.setSmallIcon(R.drawable.splash_icon);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.noti_icon));
            mBuilder.setAutoCancel(false);
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", 4);
                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                NotificationManager notificationManager2 = this.mNotificationManager;
                if (notificationManager2 != null) {
                    notificationManager2.createNotificationChannel(notificationChannel);
                } else {
                    throw new AssertionError();
                }
            }
            NotificationManager notificationManager3 = this.mNotificationManager;
            if (notificationManager3 != null) {
                notificationManager3.notify((int) System.currentTimeMillis(), mBuilder.build());
                return;
            }
            throw new AssertionError();
        } catch (Exception e) {
        }
    }
}
