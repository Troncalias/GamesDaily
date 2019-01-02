package com.example.tronc.gamesdaily.Notifications;


import android.app.Notification;
import android.app.NotificationChannel;

import android.content.Context;

import android.os.Build;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.example.tronc.gamesdaily.Activity.AdminActivity;
import com.example.tronc.gamesdaily.R;


public class MyNotification {

    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    private static final String CHANNEL_1_ID = "channel1";
    private static Context c;


    private static NotificationChannel createNotificationChannel(Context context) {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_1_ID, TITULO, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(DESCRICAO);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }
        return channel;
    }


    public static void addStoreNotification(AdminActivity adminActivity, String titulo, String morada, String NotTitle, Context context) {
        c = context;
        NotificationCompat.Builder novaNoti = new NotificationCompat.Builder(adminActivity, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle(NotTitle)
                .setContentText(titulo + " - " + morada)
                .setAutoCancel(true);

        NotificationManager managerNot = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            managerNot.createNotificationChannel(createNotificationChannel(context));
        }
        managerNot.notify(1, novaNoti.build());
    }

    public static void addGameNotification(AdminActivity adminActivity, String titulo, String descricao, String NotTitle, Context context) {
        c = context;
        NotificationCompat.Builder novaNoti = new NotificationCompat.Builder(adminActivity, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle(NotTitle)
                .setContentText(titulo + " - " + descricao)
                .setAutoCancel(true);

        NotificationManager managerNot = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            managerNot.createNotificationChannel(createNotificationChannel(context));
        }
        managerNot.notify(1, novaNoti.build());
    }

    public static void addChatNotification(AdminActivity adminActivity, String titulo, String descricao, String NotTitle, Context context) {
        c = context;
        NotificationCompat.Builder novaNoti = new NotificationCompat.Builder(adminActivity, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle(NotTitle)
                .setContentText(titulo + " - " + descricao)
                .setAutoCancel(true);

        NotificationManager managerNot = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            managerNot.createNotificationChannel(createNotificationChannel(context));
        }
        managerNot.notify(1, novaNoti.build());
    }




}
