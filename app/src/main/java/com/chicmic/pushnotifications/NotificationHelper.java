package com.chicmic.pushnotifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static com.chicmic.pushnotifications.MainActivity.CHANNEL_ID;

public class NotificationHelper {

    public static void displayNotifications(Context context,String title,String body)
    {

        Intent intent=new Intent(context,ProfileActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT);;


        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,mBuilder.build());
    }
}
