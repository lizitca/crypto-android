package com.example.vladislav.data.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.CryptoDatabase;
import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.data.api.CryptoCompareApi;
import com.example.vladislav.data.api.models.CurrencyDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by d3m1d0v on 10.03.2018.
 */

public class MainRepository implements CryptoRepository {

    private static MainRepository INSTANCE;
    private static final String BASE_URL = "https://min-api.cryptocompare.com/";
    private static final String USD = "USD";

    private final Executor dbExecutor;

    private List<CurrencyData> currencies;
    private final CryptoCompareApi api;
    private final CryptoDatabase db;
    private final String FSYMS;

    public static MainRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainRepository();
        }
        return INSTANCE;
    }

    private MainRepository() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoCompareApi.class);

        dbExecutor = Executors.newSingleThreadExecutor();
        db = CryptoDatabase.getInstance();
        currencies = new ArrayList<>();

        StringBuilder fsymsBuilder = new StringBuilder();
        for (String currency : Constant.CURRENCIES_NAME) {
            fsymsBuilder.append(currency);
            fsymsBuilder.append(',');
        }
        FSYMS = fsymsBuilder.toString();
    }

    @Override
    public void getCurrenciesDataList(GetDataCallback callback) {
        if (currencies.size() == 0) {
            getCurrenciesDataFromDb(callback);
        } else {
            callback.onData(currencies);
        }
    }

    @Override
    public void updateCurrenciesData(final RefreshCallback callback) {
        api.getCurrencyData(FSYMS, USD).enqueue(new Callback<CurrencyDataModel.Response>() {
            @Override
            public void onResponse(Call<CurrencyDataModel.Response> call, Response<CurrencyDataModel.Response> response) {
                if (!response.isSuccessful()) {
                    callback.notify(false);
                    Log.d(Constant.TAG, "response.isSuccessful(): false");
                    return;
                }

                int exCurrenciesSize = currencies.size();
                currencies.clear();
                for (String currency : Constant.CURRENCIES_NAME) {
                    CurrencyDataModel dataModel = response.body().getData().get(currency).get(USD);

                    CurrencyData data = new CurrencyData(
                            dataModel.getFROMSYMBOL(),
                            Float.parseFloat(dataModel.getPRICE()),
                            Float.parseFloat(dataModel.getCHANGEPCT24HOUR()),
                            dataModel.getLASTUPDATE()
                    );

                    currencies.add(data);
                }

                if (exCurrenciesSize == 0) {
                    insertIntoDb();
                } else {
                    updateDb();
                }

                callback.notify(true);
            }

            @Override
            public void onFailure(Call<CurrencyDataModel.Response> call, Throwable t) {
                callback.notify(false);
                Log.d(Constant.TAG, "onFailure()");
            }
        });
    }

    private void getCurrenciesDataFromDb(final GetDataCallback callback) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final List<CurrencyData> dataList = db.currencyDataDao().getCurrenciesData();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        currencies.clear();
                        currencies.addAll(dataList);
                        callback.onData(currencies);
                    }
                });
            }
        });
    }

    private void insertIntoDb() {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.currencyDataDao().insertCurrenciesData(currencies);
            }
        });
    }

    private void updateDb() {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.currencyDataDao().updateCurrenciesData(currencies);
            }
        });
    }
}
