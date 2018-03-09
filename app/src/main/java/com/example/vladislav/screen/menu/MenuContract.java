package com.example.vladislav.screen.menu;

import com.example.vladislav.base.BasePresenter;
import com.example.vladislav.base.BaseView;

/**
 * Created by d3m1d0v on 01.03.2018.
 */

public interface MenuContract {

    interface View extends BaseView<Presenter> {

        void showMainScreen();

        void showAboutApp();

        void showNotImplementedToast();
    }

    interface Presenter extends BasePresenter {

        void onMainMenuSelected();

        void onSettingsSelected();

        void onAboutAppSelected();
    }
}
