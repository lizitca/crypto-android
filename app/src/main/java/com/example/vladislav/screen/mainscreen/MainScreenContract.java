package com.example.vladislav.screen.mainscreen;

import com.example.vladislav.base.BasePresenter;
import com.example.vladislav.base.BaseView;
import com.example.vladislav.data.CurrencyBaseInfo;

import java.util.List;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public interface MainScreenContract {

    interface View extends BaseView<Presenter> {

        void showAllCurrenciesInfoItems(List<CurrencyBaseInfo> currencies);

        void showInfoToast(String currencyName);

        void showUpdatedInfo();

        void showRefreshFailedToast();
    }

    interface Presenter extends BasePresenter {

        void onCurrencyItemClick(String currencyName);

        void onDestroy();

        void onRefreshRequested();
    }
}
