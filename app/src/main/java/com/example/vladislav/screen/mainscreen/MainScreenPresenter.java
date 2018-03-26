package com.example.vladislav.screen.mainscreen;

import android.support.annotation.NonNull;

import com.example.vladislav.data.repository.CryptoRepository;
import com.example.vladislav.data.CurrencyData;

import java.util.List;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class MainScreenPresenter implements MainScreenContract.Presenter,
        CryptoRepository.RefreshCallback, CryptoRepository.GetDataListCallback {

    private final MainScreenContract.View mView;
    private final CryptoRepository mRepository;

    public MainScreenPresenter(@NonNull MainScreenContract.View view, @NonNull CryptoRepository repository) {
        mView = view;
        mRepository = repository;

        mView.setPresenter(this);
    }

    @Override
    public void onCurrencyItemClick(String currencyName) {
        mView.showInfoToast(currencyName);
    }


    @Override
    public void onRefreshRequested() {
        mRepository.updateCurrenciesData(this);
    }

    @Override
    public void start() {
        mRepository.getCurrenciesDataList(this);
    }

    /**
     * {@link CryptoRepository.GetDataListCallback} implementation
     */
    @Override
    public void onData(@NonNull List<CurrencyData> dataList) {
        mView.showCurrenciesData(dataList);
    }

    /**
     * {@link CryptoRepository.RefreshCallback} implementation
     */
    @Override
    public void notify(boolean successful) {
        if (successful) {
            mView.showUpdatedInfo();
        } else {
            mView.showRefreshFailedToast();
        }
    }
}
