package com.example.bigfamilyv20.SendNotificationPack;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.bigfamilyv20.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String title,message;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title=remoteMessage.getData().get("Title");
        message=remoteMessage.getData().get("Message");

        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.iconliv)
                .setContentTitle(title)
                .setContentText(message);
        NotificationManager manager=
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2,builder.build());

    }
}
