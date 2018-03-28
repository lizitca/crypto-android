package com.example.vladislav.screen.mainscreen;

import android.support.annotation.NonNull;

import com.example.vladislav.data.repository.CryptoRepository;
import com.example.vladislav.data.CurrencyData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class MainScreenPresenter implements MainScreenContract.Presenter,
        CryptoRepository.RefreshCallback, CryptoRepository.GetDataListCallback {

    private final MainScreenContract.View mView;
    private final CryptoRepository mRepository;
    private List<CurrencyData> mDataList;

    private CurrencySortType mSortType = CurrencySortType.getDefault();

    public MainScreenPresenter(@NonNull MainScreenContract.View view, @NonNull CryptoRepository repository) {
        mView = view;
        mRepository = repository;
        mDataList = new ArrayList<>();

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
    public void onAlphabetSortSelected() {
        updateSortType(CurrencySortType.ALPHABETICAL);
    }

    @Override
    public void onPriceSortSelected() {
        updateSortType(CurrencySortType.PRICE);
    }

    @Override
    public void onChangeSortSelected() {
        updateSortType(CurrencySortType.CHANGE24H);
    }

    @Override
    public void start() {
        mView.showCurrenciesData(mDataList);
        mRepository.getCurrenciesDataList(this);
    }

    private void updateSortType(CurrencySortType sortType) {
        mSortType = sortType;
        sortDataList();
        mView.showUpdatedInfo();
    }

    private void sortDataList() {
        Collections.sort(mDataList, new Comparator<CurrencyData>() {
            @Override
            public int compare(CurrencyData o1, CurrencyData o2) {
                switch (mSortType) {
                    case ALPHABETICAL:
                        return o1.getName().compareTo(o2.getName());

                    case PRICE:
                        return o1.getPrice() > o2.getPrice() ? -1 : o2.getPrice() > o1.getPrice() ? 1 : 0;

                    case CHANGE24H:
                        return o1.getChange() > o2.getChange() ? -1 : o2.getChange() > o1.getChange() ? 1 : 0;
                }
                return 0;
            }
        });
    }


    /**
     * {@link CryptoRepository.GetDataListCallback} implementation
     */
    @Override
    public void onData(@NonNull List<CurrencyData> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        sortDataList();
        mView.showUpdatedInfo();
    }

    /**
     * {@link CryptoRepository.RefreshCallback} implementation
     */
    @Override
    public void notify(boolean successful) {
        if (successful) {
            mRepository.getCurrenciesDataList(this);
        } else {
            mView.showRefreshFailedToast();
        }
    }
}
