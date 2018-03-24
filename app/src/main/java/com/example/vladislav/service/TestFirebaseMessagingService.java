package com.example.vladislav.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.vladislav.menu.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by user on 24.03.2018.
 */

public class TestFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "TestFbseMsgngSvc";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String title = remoteMessage.getData().get("title");
            String text = remoteMessage.getData().get("text");
            int color = (1<<16)|(1<<8)|(0);
            ShowNotification(title, text, color);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    @Override
    public void onDeletedMessages() {}


    void ShowNotification(String title, String text, int color) {
        NotificationCompat.Builder mNotify = new NotificationCompat.Builder(getApplicationContext(), "");
        mNotify.setLights(color, 100, 200);
        mNotify.setSmallIcon(R.mipmap.ic_launcher);
        mNotify.setContentTitle(title);
        mNotify.setContentText(text);
        mNotify.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int mId = 1001;
        try {
            Notification n = mNotify.build();
            mNotificationManager.notify(mId, n);
        }
        catch (Exception e) {
            //e.printStackTrace();
            Log.d(TAG, "Error");
        }
    }
}
