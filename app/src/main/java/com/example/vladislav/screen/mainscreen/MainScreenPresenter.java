package com.example.vladislav.screen.mainscreen;

import android.support.annotation.NonNull;

import com.example.vladislav.data.CryptoRepository;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class MainScreenPresenter implements MainScreenContract.Presenter, CryptoRepository.RepoListener {

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
    public void onDestroy() {
        mRepository.deleteListener(this);
    }

    @Override
    public void start() {
        mView.showAllCurrenciesInfoItems(mRepository.getAllCurrenciesInfo());
        mRepository.addListener(this);
    }

    /**
     * {@link CryptoRepository.RepoListener} implementation
     */
    @Override
    public void update() {
        mView.showUpdatedInfo();
    }
}
