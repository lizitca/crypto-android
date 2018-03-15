package com.example.vladislav.data;

import android.util.Log;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.api.CryptoCompareApi;
import com.example.vladislav.data.api.models.CurrencyDataModel;

import java.util.ArrayList;
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
    private static final String USD = "USD";

    private List<CurrencyBaseInfo> currencies;
    private List<RepoListener> listeners;
    private CryptoCompareApi api;
    private final String FSYMS;

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

        currencies = new ArrayList<>(TestCryptoRepository.currenciesName.length);
        listeners = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        for (String currency : TestCryptoRepository.currenciesName) {
            builder.append(currency);
            builder.append(',');
        }
        FSYMS = builder.toString();

        updateCurrenciesInfo();
    }

    @Override
    public List<CurrencyBaseInfo> getAllCurrenciesInfo() {
        return currencies;
    }

    @Override
    public void updateCurrenciesInfo() {
        currencies.clear();
        api.getCurrencyData(FSYMS, USD).enqueue(new Callback<CurrencyDataModel.Response>() {
            @Override
            public void onResponse(Call<CurrencyDataModel.Response> call, Response<CurrencyDataModel.Response> response) {
                if (!response.isSuccessful()) {
                    notifyListeners(false);
                    Log.d(Constant.TAG, "response.isSuccessful(): false");
                }

                for (String currency : TestCryptoRepository.currenciesName) {
                    CurrencyDataModel data = response.body().getData().get(currency).get(USD);
                    CurrencyBaseInfo info = new CurrencyBaseInfo(data.getFROMSYMBOL());

                    try {
                        info.setPrice(Float.parseFloat(data.getPRICE()));
                        info.setChange(Float.parseFloat(data.getCHANGEPCT24HOUR()));
                    } catch (NumberFormatException e) {
                        info.setPrice(-1F);
                        info.setChange(0F);
                        Log.e(Constant.TAG, "updateCurrenciesInfo: ", e);
                    }

                    currencies.add(info);
                }

                notifyListeners(true);
            }

            @Override
            public void onFailure(Call<CurrencyDataModel.Response> call, Throwable t) {
                notifyListeners(false);
                Log.d(Constant.TAG, "onFailure()");
            }
        });
    }

    @Override
    public void addListener(RepoListener listener) {
        listeners.add(listener);
    }

    @Override
    public void deleteListener(RepoListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(boolean successful) {
        if (successful) {
            for (RepoListener listener : listeners) {
                listener.refreshSuccessful();
            }
        } else {
            for (RepoListener listener : listeners) {
                listener.refreshFailed();
            }
        }
    }
}
