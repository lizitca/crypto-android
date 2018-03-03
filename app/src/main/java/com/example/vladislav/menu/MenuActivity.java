package com.example.vladislav.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.menu.R;
import com.example.vladislav.menu.fragments.FragmentAboutApp;
import com.example.vladislav.menu.fragments.FragmentMainScreen;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MenuContract.View {

    private MenuContract.Presenter mPresenter;

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


        new MenuPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter.start();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        initMainScreen();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main_menu) {
            showMainScreen();
        } else if (id == R.id.settings) {
            mPresenter.onSettingsSelected();
        } else if (id == R.id.about_app) {
            showAboutApp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void onMainMenuSelected(){

    }

    @Override
    public void showAboutApp() {
        replaceFragment(new FragmentAboutApp());

        setTitle(getResources().getString(R.string.about_app));
    }

    @Override
    public void showMainScreen() {
        replaceFragment(new FragmentMainScreen());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.title_bar, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
        initMainScreen();
    }

    private void initMainScreen(){  //init buttons value
        String[][] buttons = new String[][]{    //array of main screen buttons
                {"Bitcoin", "11.262\u20BD", "+1,63%", Integer.toString(R.id.button1)},
                {"Ethereum", "856.11\u20BD","-1,14%", Integer.toString(R.id.button2)},
                {"Ripple", "0.897\u20BD","-0,40%", Integer.toString(R.id.button3)},
                {"Bitcoin Cash", "1271.4\u20BD", "+1,64%", Integer.toString(R.id.button4)},
                {"Litecoin", "211.48\u20BD","-0,53%", Integer.toString(R.id.button5)},
                {"NEO", "123.8\u20BD","-2,22%", Integer.toString(R.id.button6)},
                {"Cardano", "0.865\u20BD", "+0,87%", Integer.toString(R.id.button7)},
                {"Stellar", "0.763\u20BD","-4,51%", Integer.toString(R.id.button8)},
                {"Monero", "358.07\u20BD","+10,6%", Integer.toString(R.id.button9)},
        };

        int color1 = (255 & 0xff) << 24 | (0 & 0xff) << 16 | (121 & 0xff) << 8 | (107 & 0xff);
        int color2 = (255 & 0xff) << 24 | (8 & 0xff) << 16 | (90 & 0xff) << 8 | (2 & 0xff);
        int color3 = (255 & 0xff) << 24 | (90 & 0xff) << 16 | (2 & 0xff) << 8 | (29 & 0xff);
        try {
            for (String[] button : buttons) { //each buttons array
                Button b = this.findViewById(Integer.parseInt(button[3]));
                Spannable span = new SpannableString(button[0] + " " + button[1] + " " + button[2]);
                span.setSpan(
                        new ForegroundColorSpan(color1),
                        0,
                        button[0].length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(
                        new RelativeSizeSpan(2.0f),
                        0,
                        button[0].length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(
                        new ForegroundColorSpan(Color.BLACK),
                        button[0].length(),
                        (button[0].length() + button[1].length() + 1),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(
                        new RelativeSizeSpan(1.8f),
                        button[0].length(),
                        (button[0].length() + button[1].length() + 1),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                span.setSpan(
                        new ForegroundColorSpan(button[2].charAt(0)=='+'?color2:color3),// if value > 0 then color = green, else red
                        (button[0].length() + button[1].length() + 1),
                        (button[0].length() + button[1].length() + button[2].length() + 2),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(
                        new RelativeSizeSpan(1.8f),
                        (button[0].length() + button[1].length() + 1),
                        (button[0].length() + button[1].length() + button[2].length() + 2),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                b.setText(span);
            }

        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void showNotImplementedToast() {
        Toast.makeText(this, R.string.item_not_implemented, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(@NonNull MenuContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.screen_area, newFragment);
        fragmentTransaction.commit();
    }
}
