package com.example.alshimaa.smartguide;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

//import com.raaleat.R;


import com.example.alshimaa.smartguide.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyNotification {

    public static final int ID_SMALL_NOTIFICATION = 235;
    private Context mCtx;
    public static Notification notification;
    public MyNotification(Context mCtx) {
        this.mCtx = mCtx;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showSmallNotification(String title, String message, String address, String time, String day, Intent intent) {


        PendingIntent resultPendingIntent =
                PendingIntent.getService(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );


        Notification.Builder mBuilder = new Notification.Builder(mCtx);
        mBuilder.setStyle(new Notification.InboxStyle()
                .addLine(message)
                .addLine(address)
                .addLine("Time    "+time+"     "+"Day     "+day));
        notification = mBuilder.setTicker(title)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setOngoing(true)
                .build();
        NotificationManager notificationManager=(NotificationManager)mCtx.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,mBuilder.build());

//        try {
//
//            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    + "://" + mCtx.getPackageName() + "/raw/songemergency");
//
//            AudioManager manager = (AudioManager)mCtx.getSystemService(Context.AUDIO_SERVICE);
//            manager.setStreamVolume(
//                    AudioManager.STREAM_MUSIC,
//                    manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
//                    0);
//
////            Ringtone r = RingtoneManager.getRingtone(mCtx, alarmSound);
//            MediaPlayer player = MediaPlayer.create(mCtx, alarmSound);
//            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            player.setLooping(true);
//            player.start();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showSmallNotificati(String title, String message, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("CHANNEL_1", "Example channel", NotificationManager.IMPORTANCE_DEFAULT);
            Notification.Builder mBuilder = new Notification.Builder(mCtx,"CHANNEL_1");
            Notification notification;
            notification = mBuilder.setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentIntent(resultPendingIntent)
                    .setContentTitle(title)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                    .setContentText(message)
                    .setStyle(new Notification.BigTextStyle().bigText(message))
                    .build();

            try {
                Uri notificatio = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(mCtx, notificatio);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(ID_SMALL_NOTIFICATION, notification);

        }else {
            Notification.Builder mBuilder = new Notification.Builder(mCtx);
            Notification notification;
            notification = mBuilder.setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentIntent(resultPendingIntent)
                    .setContentTitle(title)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                    .setContentText(message)
                    .setStyle(new Notification.BigTextStyle().bigText(message))
                    .build();

            try {
                Uri notificatio = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(mCtx, notificatio);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(ID_SMALL_NOTIFICATION, notification);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeNotificationChannel(String id, String name, int importance)
    {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setShowBadge(true); // set false to disable badges, Oreo exclusive

        NotificationManager notificationManager =
                (NotificationManager)mCtx.getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }
}

