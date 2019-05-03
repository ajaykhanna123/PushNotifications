package com.chicmic.pushnotifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingSerivice extends FirebaseMessagingService
{
    private static String refreshedToken;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification() !=null)
        {
            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();

            NotificationHelper.displayNotifications(getApplicationContext(),title,body);
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        refreshedToken = token;
    }

    public static String getRefreshedToken() {
        return refreshedToken;
    }


}
