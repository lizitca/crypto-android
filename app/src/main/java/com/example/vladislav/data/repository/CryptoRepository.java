package com.example.vladislav.data.repository;

import android.support.annotation.NonNull;

import com.example.vladislav.data.CurrencyData;

import java.util.List;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public interface CryptoRepository {

    void getCurrenciesDataList(@NonNull GetDataCallback callback);

    void updateCurrenciesData(@NonNull RefreshCallback callback);

    interface GetDataCallback {

        void onData(@NonNull List<CurrencyData> dataList);
    }

    interface RefreshCallback {

        void notify(boolean successful);
    }
}
