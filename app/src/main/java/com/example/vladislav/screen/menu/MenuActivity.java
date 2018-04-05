package com.example.vladislav.screen.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vladislav.app.Constant;
import com.example.vladislav.menu.R;
import com.example.vladislav.screen.about.FragmentAboutApp;
import com.example.vladislav.screen.mainscreen.MainScreenFragment;

public class MenuActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, MenuContract.View {

    private MenuContract.Presenter mPresenter;
    private MainScreenFragment mMainScreenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new MenuPresenter(this).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                mPresenter.onMainMenuSelected();
                break;
            case R.id.settings:
                mPresenter.onSettingsSelected();
                break;
            case R.id.about_app:
                mPresenter.onAboutAppSelected();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showMainScreen() {
        setTitle(getResources().getString(R.string.main));
        if (mMainScreenFragment == null) {
            mMainScreenFragment = new MainScreenFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_area, mMainScreenFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .popBackStack();
        }
    }

    @Override
    public void showAboutApp() {
        setTitle(getResources().getString(R.string.about_app));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.screen_area, new FragmentAboutApp())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showNotImplementedToast() {
        Toast.makeText(this, R.string.item_not_implemented, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(@NonNull MenuContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
