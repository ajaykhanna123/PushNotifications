package com.chicmic.pushnotifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    private static String refreshedToken;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        refreshedToken = token;
    }

    public static String getRefreshedToken() {
        return refreshedToken;
    }

    public static void setRefreshedToken(String refreshedToken) {
        MessagingService.refreshedToken = refreshedToken;
    }
}
