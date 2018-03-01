package com.example.vladislav.menu;

import com.example.vladislav.BasePresenter;
import com.example.vladislav.BaseView;

/**
 * Created by d3m1d0v on 01.03.2018.
 */

public interface MenuContract {

    interface View extends BaseView<Presenter> {

        void showAboutApp();

        void showNotImplementedToast();
    }

    interface Presenter extends BasePresenter {

        void onMainMenuSelected();

        void onCryptoCurrenciesSelected();

        void onMyCurrenciesSelected();

        void onJournalSelected();

        void onNoticeSelected();

        void onAboutAppSelected();
    }
}
