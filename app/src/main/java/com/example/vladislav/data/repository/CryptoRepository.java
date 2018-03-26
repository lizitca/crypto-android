package com.example.vladislav.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.data.api.models.CurrencyDataModel;

import java.util.List;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public interface CryptoRepository {

    void getCurrenciesDataList(@NonNull GetDataListCallback callback);

    void updateCurrenciesData(@NonNull RefreshCallback callback);

    void getCurrencyData(@NonNull String currencyName, @NonNull GetDataCallback callback);

    void updateCurrencyData(@NonNull String currencyName, @NonNull RefreshCallback callback);

    interface GetDataCallback {

        void onData(@Nullable CurrencyData data);
    }

    interface GetDataListCallback {

        void onData(@NonNull List<CurrencyData> dataList);
    }

    interface RefreshCallback {

        void notify(boolean successful);
    }
}
