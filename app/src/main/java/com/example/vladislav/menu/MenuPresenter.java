package com.example.vladislav.menu;

import android.support.annotation.NonNull;

/**
 * Created by d3m1d0v on 01.03.2018.
 */

public class MenuPresenter implements MenuContract.Presenter {

    private final MenuContract.View mMenuView;


    public MenuPresenter(@NonNull MenuContract.View menuView) {
        mMenuView = menuView;

        mMenuView.setPresenter(this);
    }

    @Override
    public void onMainMenuSelected() {
        mMenuView.showEmptyFragment();
    }

    @Override
    public void onCryptoCurrenciesSelected() {
        mMenuView.showEmptyFragment();
    }

    @Override
    public void onMyCurrenciesSelected() {
        mMenuView.showEmptyFragment();
    }

    @Override
    public void onJournalSelected() {
        mMenuView.showEmptyFragment();
    }

    @Override
    public void onNoticeSelected() {
        mMenuView.showEmptyFragment();
    }

    @Override
    public void onAboutAppSelected() {
        mMenuView.showAboutApp();
    }

    @Override
    public void start() {

    }
}
