package digi.coders.capsicodeliverypartner.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.activity.DashboardActivity;

/* loaded from: classes5.dex */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    String imageUrl;
    private NotificationUtils notificationUtils;
    MediaPlayer player;
    SharedPreferences sPref;
    String timestamp;
    Vibrator v;
    String channelId = "channel-01";
    long[] pattern = {0, 100, 1000};

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String str = TAG;
        Log.e(str, "From: " + remoteMessage.getFrom());
        this.v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (remoteMessage != null && remoteMessage.getData().size() > 0) {
            Log.e(str, "Data Payload: " + remoteMessage.getData().toString());
            try {
                handleDataMessage(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"), remoteMessage.getData().get(NotificationCompat.CATEGORY_STATUS));
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }

    private void handleDataMessage(String title, String desc, String status) {
        try {
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent fullScreenIntent = new Intent(this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                fullScreenIntent.putExtra("order_id", "order_id");
                if (desc.equalsIgnoreCase("New Order") || status.equalsIgnoreCase("Prepared")) {
                    Intent intent = new Intent("GPSLocationUpdates");
                    intent.putExtra("order_id", "order_id");
                    intent.putExtra("amount", "amount");
                    intent.putExtra(NotificationCompat.CATEGORY_STATUS, status);
                    if (!status.equalsIgnoreCase("simple")) {
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                    }
                }
                if (TextUtils.isEmpty(this.imageUrl)) {
                    showNotificationMessage(getApplicationContext(), desc, title, "timestamp", fullScreenIntent, status);
                } else {
                    showNotificationMessageWithBigImage(getApplicationContext(), desc, title, "timestamp", fullScreenIntent, this.imageUrl, status);
                }
                return;
            }
            Intent fullScreenIntent2 = new Intent(this, DashboardActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            fullScreenIntent2.putExtra("order_id", "order_id");
            if (desc.equalsIgnoreCase("New Order") || status.equalsIgnoreCase("Prepared")) {
                Intent intent2 = new Intent("GPSLocationUpdates");
                intent2.putExtra("order_id", "order_id");
                intent2.putExtra("amount", "amount");
                intent2.putExtra("id", "id");
                intent2.putExtra(NotificationCompat.CATEGORY_STATUS, status);
                if (!status.equalsIgnoreCase("simple")) {
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent2);
                }
            }
            if (TextUtils.isEmpty(this.imageUrl)) {
                showNotificationMessage(getApplicationContext(), desc, title, "timestamp", fullScreenIntent2, status);
            } else {
                showNotificationMessageWithBigImage(getApplicationContext(), desc, title, "timestamp", fullScreenIntent2, this.imageUrl, status);
            }
        } catch (Exception e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent, String status) {
        this.notificationUtils = new NotificationUtils(context, this.channelId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.notificationUtils.showNotificationMessage(title, message, timeStamp, intent, status);
    }

    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl, String status) {
        this.notificationUtils = new NotificationUtils(context, this.channelId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl, status);
    }

    public void startRingtone() {
        MediaPlayer create = MediaPlayer.create(this, (int) R.raw.delivery_boy_tone);
        this.player = create;
        create.setLooping(true);
        this.player.start();
    }

    @Override // com.google.firebase.messaging.EnhancedIntentService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }
}
