package com.example.vladislav.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class TestCryptoRepository implements CryptoRepository {

    private static TestCryptoRepository INSTANCE;

    private List<CurrencyBaseInfo> currencies;
    private String[] currenciesName = {
            "Bitcoin",
            "Ethereum",
            "Ripple",
            "Bitcoin Cash",
            "Litecoin",
            "NEO",
            "Cardano",
            "Stellar",
            "EOS",
            "Monero",
            "IOTA",
            "Dash",
            "NEM",
            "TRON",
            "Zcash",
            "GOLOS"
    };

    private TestCryptoRepository() {
        currencies = new ArrayList<>(currenciesName.length);
        updateCurrenciesInfo();
    }

    public static TestCryptoRepository getInstance() {
        if (INSTANCE == null){
            INSTANCE = new TestCryptoRepository();
        }
        return INSTANCE;
    }

    @Override
    public List<CurrencyBaseInfo> getAllCurrenciesInfo() {
        return currencies;
    }

    @Override
    public void updateCurrenciesInfo() {
        currencies.clear();

        for (String name: currenciesName) {
            CurrencyBaseInfo info = new CurrencyBaseInfo();
            info.setName(name);
            info.setPrice(genPriceValue());
            info.setChange(genChangeValue());
            currencies.add(info);
        }
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
