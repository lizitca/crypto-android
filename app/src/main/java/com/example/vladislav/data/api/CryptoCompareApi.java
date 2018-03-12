package com.example.vladislav.data.api;

import com.example.vladislav.data.api.models.CurrencyDataModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by d3m1d0v on 10.03.2018.
 */

public interface CryptoCompareApi {

    @GET("/data/pricemultifull")
    Call<CurrencyDataModel.Response> getCurrencyData(@Query("fsyms") String fsyms, @Query("tsyms") String tsyms);
}
