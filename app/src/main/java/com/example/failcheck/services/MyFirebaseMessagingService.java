
package com.example.failcheck.services;
import android.util.Log;
import androidx.core.*;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagingService  extends FirebaseMessagingService {
    private static final String TAG = "MyFMService";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendRegistrationToServer(String token) {
        // Send the token to your server to register it for push notifications
    }
}

