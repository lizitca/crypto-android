
package com.example.vladislav.data.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface SendRefreshTokenApi {

    @POST("/api/tokens")
    Call<ResponseBody> sendToken(@Query("registration_id") String token);
}
