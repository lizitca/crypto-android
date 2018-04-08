package com.example.vladislav.data.repository;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.CryptoDatabase;
import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.data.NotificationSetting;
import com.example.vladislav.data.api.CryptoCompareApi;
import com.example.vladislav.data.api.models.CurrencyDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by d3m1d0v on 10.03.2018.
 */

public class MainRepository implements CryptoRepository {

    private static MainRepository INSTANCE;
    private static final String BASE_URL = "https://min-api.cryptocompare.com/";
    private static final String USD = "USD";

    private final Executor dbExecutor;

    private Map<String, CurrencyData> currenciesMap;

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CryptoCompareApi.class);

        dbExecutor = Executors.newSingleThreadExecutor();
        db = CryptoDatabase.getInstance();
        currenciesMap = new TreeMap<>();

        StringBuilder fsymsBuilder = new StringBuilder();
        for (String currency : Constant.CURRENCIES_NAME) {
            fsymsBuilder.append(currency);
            fsymsBuilder.append(',');
        }
        FSYMS = fsymsBuilder.toString();
    }

    @Deprecated
    @Override
    public void getCurrenciesDataList(GetDataListCallback callback) {
        if (currenciesMap.size() == 0) {
            getCurrenciesDataFromDb(callback);
        } else {
            callback.onData(new ArrayList<>(currenciesMap.values()));
        }
    }

    @Deprecated
    @Override
    public void updateCurrenciesData(final RefreshCallback callback) {
        api.getCurrencyData(FSYMS, USD).enqueue(new Callback<CurrencyDataModel.Response>() {
            @Override
            public void onResponse(Call<CurrencyDataModel.Response> call, Response<CurrencyDataModel.Response> response) {
                if (!response.isSuccessful() || response.body().getData() == null) {
                    callback.notify(false);
                    Log.d(Constant.TAG, "response.isSuccessful(): false");
                    return;
                }

                int exCurrenciesSize = currenciesMap.size();
                currenciesMap.clear();
                for (String currency : Constant.CURRENCIES_NAME) {
                    CurrencyDataModel dataModel = response.body().getData().get(currency).get(USD);
                    CurrencyData data = MainRepository.DataModelToData(dataModel);

                    currenciesMap.put(data.getName(), data);
                }

                if (exCurrenciesSize == 0) {
                    insertDataListIntoDb();
                } else {
                    updateDataListIntoDb();
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

    @Deprecated
    @Override
    public void getCurrencyData(@NonNull final String currencyName, @NonNull final GetDataCallback callback) {
        if (currenciesMap.containsKey(currencyName)) {
            callback.onData(currenciesMap.get(currencyName));
            return;
        }

        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final CurrencyData data = db.currencyDataDao().getCurrencyDataByName(currencyName);
                if (data == null) {
                    callback.onData(null);
                    return;
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        currenciesMap.put(data.getName(), data);
                        callback.onData(data);
                    }
                });
            }
        });
    }

    @Deprecated
    @Override
    public void updateCurrencyData(@NonNull final String currencyName, @NonNull final RefreshCallback callback) {
        api.getCurrencyData(currencyName, USD).enqueue(new Callback<CurrencyDataModel.Response>() {
            @Override
            public void onResponse(Call<CurrencyDataModel.Response> call, Response<CurrencyDataModel.Response> response) {
                if (!response.isSuccessful() || response.body().getData() == null) {
                    callback.notify(false);
                    Log.d(Constant.TAG, "response.isSuccessful(): false");
                    return;
                }

                CurrencyDataModel dataModel = response.body().getData().get(currencyName).get(USD);
                CurrencyData data = MainRepository.DataModelToData(dataModel);

                insertDataIntoDb(data);
                currenciesMap.put(data.getName(),data);
                callback.notify(true);
            }

            @Override
            public void onFailure(Call<CurrencyDataModel.Response> call, Throwable t) {
                callback.notify(false);
                Log.d(Constant.TAG, "onFailure()");
            }
        });
    }

    @Override
    public void getNotificationSettingsAll(@NonNull final DataCallback<List<NotificationSetting>> callback) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final List<NotificationSetting> settings = db.settingDao().getSettingsAll();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onData(settings);
                    }
                });
            }
        });
    }

    @Override
    public void getNotificationSetting(@NonNull final String currencyName,
                                       @NonNull final DataCallback<NotificationSetting> callback) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final NotificationSetting setting = db.settingDao().getSetting(currencyName);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onData(setting);
                    }
                });
            }
        });
    }

    private void getCurrenciesDataFromDb(final GetDataListCallback callback) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final List<CurrencyData> dataList = db.currencyDataDao().getCurrenciesData();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        currenciesMap.clear();
                        for (CurrencyData data : dataList) {
                            currenciesMap.put(data.getName(), data);
                        }
                        callback.onData(new ArrayList<>(currenciesMap.values()));
                    }
                });
            }
        });
    }

    private void insertDataIntoDb(final CurrencyData data) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.currencyDataDao().insertCurrencyData(data);
            }
        });
    }

    private void insertDataListIntoDb() {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.currencyDataDao().insertCurrenciesData(new ArrayList<>(currenciesMap.values()));
            }
        });
    }

    private void updateDataListIntoDb() {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.currencyDataDao().updateCurrenciesData(new ArrayList<>(currenciesMap.values()));
            }
        });
    }

    @NonNull
    private static CurrencyData DataModelToData(@NonNull CurrencyDataModel dataModel) {
        return new CurrencyData(
                dataModel.getFROMSYMBOL(),
                Float.parseFloat(dataModel.getPRICE()),
                Float.parseFloat(dataModel.getCHANGEPCT24HOUR()),
                Float.parseFloat(dataModel.getCHANGEPCT24HOUR()),
                Float.parseFloat(dataModel.getCHANGEPCTDAY()),
                dataModel.getSUPPLY(),
                dataModel.getMKTCAP(),
                dataModel.getVOLUME24HOURTO(),
                dataModel.getLASTUPDATE()
        );
    }


    ///////////////////////////////
    /// Using reactive behavior ///
    ///////////////////////////////

    @Override
    public Flowable<List<CurrencyData>> getCurrencyDataListFromDb_Rx() {
        return db.currencyDataDao().getCurrencyDataAll_Rx();
    }

    @Override
    public Single<List<CurrencyData>> refreshCurrencyDataAll_Rx() {
        return api.getCurrencyDataRx(FSYMS, USD)
                .subscribeOn(Schedulers.io())
                .map(new Function<CurrencyDataModel.Response, List<CurrencyData>>() {
                    @Override
                    public List<CurrencyData> apply(CurrencyDataModel.Response res) throws Exception {
                        if (res.getData() == null) {
                            throw new Exception("Response body is null");
                        }

                        List<CurrencyData> dataList = new ArrayList<>();
                        for (Map<String, CurrencyDataModel> map : res.getData().values()) {
                            dataList.add(DataModelToData(map.get(USD)));
                        }
                        return dataList;
                    }
                })
                .doOnSuccess(new Consumer<List<CurrencyData>>() {
                    @Override
                    public void accept(List<CurrencyData> dataList) throws Exception {
                        db.currencyDataDao().insertCurrenciesData(dataList);
                    }
                });
    }

    @Override
    public Flowable<CurrencyData> getCurrencyDataFromDb_Rx(@NonNull String currencyName) {
        return db.currencyDataDao().getCurrencyDataByName_Rx(currencyName);
    }

    @Override
    public Single<CurrencyData> refreshCurrencyData_Rx(@NonNull final String currencyName) {
        return api.getCurrencyDataRx(currencyName, USD)
                .subscribeOn(Schedulers.io())
                .map(new Function<CurrencyDataModel.Response, CurrencyData>() {
                    @Override
                    public CurrencyData apply(CurrencyDataModel.Response res) throws Exception {
                        if (res.getData() == null) {
                            throw new Exception("Response body is null");
                        }

                        return DataModelToData(res.getData().get(currencyName).get(USD));
                    }
                })
                .doOnSuccess(new Consumer<CurrencyData>() {
                    @Override
                    public void accept(CurrencyData data) throws Exception {
                        db.currencyDataDao().insertCurrencyData(data);
                    }
                });
    }

    @Override
    public Flowable<List<NotificationSetting>> getNotificationSettingListFromDb_Rx() {
        return db.settingDao().getSettingsAll_Rx();
    }

    @Override
    public Flowable<NotificationSetting> getNotificationSettingFromDb_Rx(@NonNull String currencyName) {
        return db.settingDao().getSetting_Rx(currencyName);
    }
}
