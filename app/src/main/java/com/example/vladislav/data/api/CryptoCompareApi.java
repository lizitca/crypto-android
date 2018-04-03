package com.example.vladislav.data.api;

import com.example.vladislav.data.api.models.CurrencyDataModel;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by d3m1d0v on 10.03.2018.
 */

public interface CryptoCompareApi {

    @Deprecated
    @GET("/data/pricemultifull")
    Call<CurrencyDataModel.Response> getCurrencyData(@Query("fsyms") String fsyms, @Query("tsyms") String tsyms);

    @GET("/data/pricemultifull")
    Single<CurrencyDataModel.Response> getCurrencyDataRx(@Query("fsyms") String fsyms, @Query("tsyms") String tsyms);
}
