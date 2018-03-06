package com.example.vladislav.data;

import java.util.List;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public interface CryptoRepository {

    List<CurrencyBaseInfo> getAllCurrenciesInfo();

    void updateCurrenciesInfo();
}
