package com.example.vladislav.data;

import android.util.Log;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.api.CryptoCompareApi;
import com.example.vladislav.data.api.models.CurrencyDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by d3m1d0v on 10.03.2018.
 */

public class NetworkRepository implements CryptoRepository {

    private static NetworkRepository INSTANCE;
    private static final String BASE_URL = "https://min-api.cryptocompare.com/";

    List<CurrencyBaseInfo> currencies;
    CryptoCompareApi api;

    public static NetworkRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkRepository();
        }
        return INSTANCE;
    }

    private NetworkRepository() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoCompareApi.class);

        currencies = new ArrayList<>();
        for (String currency : TestCryptoRepository.currenciesName) {
            currencies.add(new CurrencyBaseInfo(currency));
        }

        updateCurrenciesInfo();
    }

    @Override
    public List<CurrencyBaseInfo> getAllCurrenciesInfo() {
        return currencies;
    }

    @Override
    public void updateCurrenciesInfo() {
        for (final CurrencyBaseInfo info: currencies) {
            api.getCurrencyData(info.getName(), "USD").enqueue(new Callback<HashMap<String, HashMap<String, HashMap<String, CurrencyDataModel>>>>() {
                @Override
                public void onResponse(Call<HashMap<String, HashMap<String, HashMap<String, CurrencyDataModel>>>> call, Response<HashMap<String, HashMap<String, HashMap<String, CurrencyDataModel>>>> response) {
                    if (response.isSuccessful()) {
                        CurrencyDataModel data = response.body().get("RAW").get(info.getName()).get("USD");

                        try {
                            float price = Float.parseFloat(data.getPRICE());
                            float open24hour = Float.parseFloat(data.getOPEN24HOUR());

                            info.setPrice(price);
                            info.setChange((price - open24hour) / open24hour);
                        } catch (NumberFormatException e) {
                            info.setPrice(-1F);
                            info.setChange(0F);
                            Log.e(Constant.TAG, "updateCurrenciesInfo: ", e);
                        }

                        Log.d(Constant.TAG, "Info about " + info.getName());
                    }
                }

                @Override
                public void onFailure(Call<HashMap<String, HashMap<String, HashMap<String, CurrencyDataModel>>>> call, Throwable t) {
                    Log.e(Constant.TAG, "updateCurrenciesInfo: ", t);
                }
            });
        }
    }
}
