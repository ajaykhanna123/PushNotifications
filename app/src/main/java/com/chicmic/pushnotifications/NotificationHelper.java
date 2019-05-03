package com.chicmic.pushnotifications;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static com.chicmic.pushnotifications.MainActivity.CHANNEL_ID;

public class NotificationHelper {

    public static void displayNotifications(Context context,String title,String body)
    {
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,mBuilder.build());
    }
}
