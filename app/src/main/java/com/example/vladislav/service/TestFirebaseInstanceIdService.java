package com.example.vladislav.service;
import com.example.vladislav.data.api.SendRefreshTokenApi;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 24.03.2018.
 */

public class TestFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public static final String TAG = "TestFbseInstIdSvc";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String token) {
        SendRefreshTokenApi api = new Retrofit.Builder()
                .baseUrl("http://213.159.214.131")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SendRefreshTokenApi.class);

        api.sendToken(token).enqueue(new Callback<ResponseBody>() {

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "Sehr gut!");
                } else {
                    Log.d(TAG, "Sehr schlecht!");
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "ppc");
            }
        });
    }
}
