package com.example.vladislav.data.repository;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.CurrencyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class TestCryptoRepository implements CryptoRepository {

    private static TestCryptoRepository INSTANCE;

    private List<CurrencyData> currencies;

    private TestCryptoRepository() {
        currencies = new ArrayList<>(Constant.CURRENCIES_NAME.length);
    }

    public static TestCryptoRepository getInstance() {
        if (INSTANCE == null){
            INSTANCE = new TestCryptoRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getCurrenciesDataList(GetDataCallback callback) {
        callback.onData(currencies);
    }

    @Override
    public void updateCurrenciesData(RefreshCallback callback) {
        currencies.clear();

        for (String name: Constant.CURRENCIES_NAME) {
            CurrencyData data = new CurrencyData(
                    name,
                    genPriceValue(),
                    genChangeValue(),
                    "-1"
            );
            currencies.add(data);
        }

        callback.notify(true);
    }

    private float genPriceValue() {
        float leftLimit = 0.1F;
        float rightLimit = 1000F;
        float generatedFloat = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);
        return generatedFloat;
    }

    private float genChangeValue() {
        float leftLimit = -10F;
        float rightLimit = 10F;
        float generatedFloat = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);
        return generatedFloat;
    }
}