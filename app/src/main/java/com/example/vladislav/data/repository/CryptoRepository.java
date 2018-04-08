package com.example.vladislav.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.AsyncListUtil;

import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.data.NotificationSetting;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public interface CryptoRepository {

    @Deprecated
    void getCurrenciesDataList(@NonNull GetDataListCallback callback);

    @Deprecated
    void updateCurrenciesData(@NonNull RefreshCallback callback);

    @Deprecated
    void getCurrencyData(@NonNull String currencyName, @NonNull GetDataCallback callback);

    @Deprecated
    void updateCurrencyData(@NonNull String currencyName, @NonNull RefreshCallback callback);

    @Deprecated
    void getNotificationSettingsAll(@NonNull DataCallback<List<NotificationSetting>> callback);

    @Deprecated
    void getNotificationSetting(@NonNull String currencyName, @NonNull DataCallback<NotificationSetting> callback);

    @Deprecated
    interface GetDataCallback {

        void onData(@Nullable CurrencyData data);
    }

    @Deprecated
    interface GetDataListCallback {

        void onData(@NonNull List<CurrencyData> dataList);
    }

    @Deprecated
    interface DataCallback<T> {
        void onData(T data);
    }

    @Deprecated
    interface RefreshCallback {

        void notify(boolean successful);
    }


      ///////////////////////////////
     /// Using reactive behavior ///
    ///////////////////////////////

    Flowable<List<CurrencyData>> getCurrencyDataListFromDb_Rx();

    Single<List<CurrencyData>> refreshCurrencyDataAll_Rx();

    Flowable<CurrencyData> getCurrencyDataFromDb_Rx(@NonNull String currencyName);

    Single<CurrencyData> refreshCurrencyData_Rx(@NonNull String currencyName);

    Flowable<List<NotificationSetting>> getNotificationSettingListFromDb_Rx();

    Flowable<NotificationSetting> getNotificationSettingFromDb_Rx(@NonNull String currencyName);
}
