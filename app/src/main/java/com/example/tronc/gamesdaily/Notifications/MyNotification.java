package com.example.tronc.gamesdaily.Notifications;


import android.app.Notification;
import android.app.NotificationChannel;

import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.os.Build;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.example.tronc.gamesdaily.Activity.AdminActivity;
import com.example.tronc.gamesdaily.Activity.ChatActivity;
import com.example.tronc.gamesdaily.Activity.MainActivity;
import com.example.tronc.gamesdaily.Activity.NewsActivity;
import com.example.tronc.gamesdaily.Activity.SplashActivity;
import com.example.tronc.gamesdaily.R;


public class MyNotification {

    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    private static final String CHANNEL_1_ID = "channel1";



    private static NotificationChannel createNotificationChannel(Context context) {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_1_ID, TITULO, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(DESCRICAO);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }
        return channel;
    }


    public static void addStoreNotification(Context c, String titulo, String morada, String NotTitle, Context context) {

        Intent nextIntent = new Intent(c, SplashActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(c, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder novaNoti = new NotificationCompat.Builder(c, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle(NotTitle)
                .setContentIntent(resultPendingIntent)
                .setContentText(titulo + " - " + morada)
                .setAutoCancel(true);

        NotificationManager managerNot = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            managerNot.createNotificationChannel(createNotificationChannel(context));
        }
        managerNot.notify(1, novaNoti.build());
    }

    public static void addGameNotification(Context c, String titulo, String descricao, String NotTitle, Context context) {

        Intent nextIntent = new Intent(c, SplashActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(c, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder novaNoti = new NotificationCompat.Builder(c, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle(NotTitle)
                .setContentIntent(resultPendingIntent)
                .setContentText(titulo + " - " + descricao)
                .setAutoCancel(true);

        NotificationManager managerNot = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            managerNot.createNotificationChannel(createNotificationChannel(context));
        }
        managerNot.notify(1, novaNoti.build());
    }

    public static void addChatNotification(Context c, String titulo, String descricao, String NotTitle, Context context) {

        Intent nextIntent = new Intent(c, SplashActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(c, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder novaNoti = new NotificationCompat.Builder(c, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle(NotTitle)
                .setContentIntent(resultPendingIntent)
                .setContentText(titulo + " - " + descricao)
                .setAutoCancel(true);

        NotificationManager managerNot = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            managerNot.createNotificationChannel(createNotificationChannel(context));
        }
        managerNot.notify(1, novaNoti.build());
    }




}
