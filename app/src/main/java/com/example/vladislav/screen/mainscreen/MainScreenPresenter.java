package com.example.vladislav.screen.mainscreen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.repository.CryptoRepository;
import com.example.vladislav.data.CurrencyData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class MainScreenPresenter implements MainScreenContract.Presenter {

    private final MainScreenContract.View mView;
    private final CryptoRepository mRepository;
    private List<CurrencyData> mDataList;

    private CompositeDisposable mDisposables = new CompositeDisposable();

    private CurrencySortType mSortType = CurrencySortType.getDefault();

    public MainScreenPresenter(@NonNull MainScreenContract.View view, @NonNull CryptoRepository repository) {
        mView = view;
        mRepository = repository;
        mDataList = new ArrayList<>();

        mView.setPresenter(this);
    }

    @Override
    public void onCurrencyItemClick(String currencyName) {
//        mView.showInfoToast(currencyName);
    }

    @Override
    public void onRefreshRequested() {
        mView.showRefreshAnimation();
        mDisposables.add(
                mRepository.refreshCurrencyDataAll_Rx()
                        .toCompletable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {
                                onRefreshSuccessful();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d(Constant.TAG, "Refresh failed", throwable);
                                onRefreshFailed();
                            }
                        })
        );
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
    public void onDestroy() {
            mDisposables.dispose();
    }

    @Override
    public void start() {
        mView.showCurrenciesData(mDataList);
        mDisposables.add(
                mRepository.getCurrencyDataListFromDb_Rx()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<CurrencyData>>() {
                            @Override
                            public void accept(List<CurrencyData> dataList) throws Exception {
                                mDataList.clear();
                                mDataList.addAll(dataList);
                                sortDataList();
                                mView.notifyAdapter();
                                Log.d(Constant.TAG, "Presenter. dataList.size() = " + dataList.size());
                            }
                        })
                );

        onRefreshRequested();
    }

    private void updateSortType(CurrencySortType sortType) {
        mSortType = sortType;
        sortDataList();
        mView.notifyAdapter();
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

    private void onRefreshSuccessful() {
        mView.hideRefreshAnimation();
    }

    private void onRefreshFailed() {
        mView.hideRefreshAnimation();
        mView.showRefreshFailedToast();
    }
}
