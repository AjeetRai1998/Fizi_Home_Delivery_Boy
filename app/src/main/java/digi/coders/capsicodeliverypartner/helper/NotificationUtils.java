package digi.coders.capsicodeliverypartner.helper;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import androidx.core.app.NotificationCompat;
import digi.coders.capsicodeliverypartner.R;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/* loaded from: classes5.dex */
public class NotificationUtils {
    public static final int NOTIFICATION_ID = 100;
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    private static String TAG = NotificationUtils.class.getSimpleName();
    public static final String TOPIC_GLOBAL = "global";
    public static MediaPlayer mMediaPlayer;
    String channelId;
    private Context mContext;

    public NotificationUtils(Context mContext, String ChannedID) {
        this.mContext = mContext;
        this.channelId = ChannedID;
    }

    public void showNotificationMessage(String title, String message, String timeStamp, Intent intent, String status) {
        showNotificationMessage(title, message, timeStamp, intent, null, status);
    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, String imageUrl, String status) {
        if (!TextUtils.isEmpty(message)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this.mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_MUTABLE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.mContext, this.channelId);
            Uri alarmSound = Uri.parse("android.resource://" + this.mContext.getPackageName() + "/raw/noti.mp3");
            if (TextUtils.isEmpty(imageUrl)) {
                showSmallNotification(mBuilder, R.drawable.splash_icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                if (!status.equalsIgnoreCase("simple")) {
                    playNotificationSound();
                    vibrateDevice();
                }
            } else if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Bitmap bitmap = getBitmapFromURL(imageUrl);
                if (bitmap != null) {
                    showBigNotification(bitmap, mBuilder, R.drawable.splash_icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                    if (!status.equalsIgnoreCase("simple")) {
                        playNotificationSound();
                        vibrateDevice();
                        return;
                    }
                    return;
                }
                showSmallNotification(mBuilder, R.drawable.splash_icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                if (!status.equalsIgnoreCase("simple")) {
                    playNotificationSound();
                    vibrateDevice();
                }
            }
        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(this.channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setSmallIcon(R.drawable.splash_icon).setAutoCancel(true).setContentTitle(title).setContentIntent(resultPendingIntent).setSound(alarmSound).setStyle(inboxStyle).setWhen(getTimeMilliSec(timeStamp)).setSmallIcon(R.drawable.splash_icon).setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.noti_icon)).setContentText(message).setChannelId(this.channelId).setPriority(5);
        } else {
            mBuilder.setSmallIcon(R.drawable.splash_icon).setAutoCancel(true).setContentTitle(title).setContentIntent(resultPendingIntent).setSound(alarmSound).setStyle(inboxStyle).setWhen(getTimeMilliSec(timeStamp)).setSmallIcon(R.drawable.splash_icon).setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.noti_icon)).setContentText(message).setChannelId(this.channelId).setPriority(5);
        }
        notificationManager.notify(100, mBuilder.build());
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("channel-01", "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setSmallIcon(R.drawable.splash_icon).setAutoCancel(true).setContentTitle(title).setContentIntent(resultPendingIntent).setSound(alarmSound).setStyle(bigPictureStyle).setWhen(getTimeMilliSec(timeStamp)).setSmallIcon(R.drawable.splash_icon).setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.noti_icon)).setContentText(message).setChannelId("channel-01").setPriority(4);
        } else {
            mBuilder.setSmallIcon(R.drawable.splash_icon).setAutoCancel(true).setContentTitle(title).setContentIntent(resultPendingIntent).setSound(alarmSound).setStyle(bigPictureStyle).setWhen(getTimeMilliSec(timeStamp)).setSmallIcon(R.drawable.splash_icon).setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.noti_icon)).setContentText(message).setChannelId("channel-01").setPriority(4);
        }
        notificationManager.notify(100, mBuilder.build());
    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void playNotificationSound() {
        try {
            mMediaPlayer = new MediaPlayer();
            MediaPlayer create = MediaPlayer.create(this.mContext, (int) R.raw.ring);
            mMediaPlayer = create;
            create.setAudioStreamType(3);
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vibrator vibrator;
    public void vibrateDevice(){
        vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(240000);
        } else {
            //deprecated in API 26
            vibrator.vibrate(240000);
        }
    }


    public static boolean isAppIsInBackground(Context context) {
        String[] strArr;
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 20) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == 100) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
            return isInBackground;
        }
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        if (componentInfo.getPackageName().equals(context.getPackageName())) {
            return false;
        }
        return true;
    }

    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
