package com.example.vladislav.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by user on 24.03.2018.
 */

public class TestFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public static final String TAG = "TestFbseInstIdSvc";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //~sendRegistrationToServer(refreshedToken);
    }
}
