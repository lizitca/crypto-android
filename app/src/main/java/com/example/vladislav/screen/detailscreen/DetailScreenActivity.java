package com.example.vladislav.screen.detailscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.data.repository.CryptoRepository;
import com.example.vladislav.screen.notificationscreen.ICONotificationActivity;
import com.example.vladislav.menu.R;
import com.github.mikephil.charting.charts.LineChart;

import com.example.vladislav.data.repository.MainRepository;

import java.util.Timer;
import java.util.TimerTask;

public class DetailScreenActivity extends AppCompatActivity implements CryptoRepository.GetDataCallback, CryptoRepository.RefreshCallback {

    private String currencyName;
    private DetailScreenChart mChart;
    private CurrencyData currentCurrency;

    private final CryptoRepository mRepository = MainRepository.getInstance();
    private Timer mTimer;
    private UpdateChart mUpdateChart;

    String[] values = { "Капитализация 100.000.000.000$", "Выпущено 111.111.111 BTC", "Объем(24ч) 10.000.000$" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        currencyName = getIntent().getStringExtra("currencyName");
        setTitle(currencyName);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mChart = new DetailScreenChart((LineChart) findViewById(R.id.chart), currencyName);
        mChart.initialize();

        ListView lvMain = (ListView) findViewById(R.id.lvData);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        lvMain.setAdapter(adapter);

        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new Timer();
        mUpdateChart = new UpdateChart();

        mTimer.schedule(mUpdateChart, 0, 1000);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_detail_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_notification:
                Intent intent = new Intent(this, ICONotificationActivity.class);
//                intent.putExtra("id", id);
                this.startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                return true;

        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    public void onData(@Nullable CurrencyData data) {
        if (currentCurrency == null || !currentCurrency.getLastUpdate().equals(data.getLastUpdate())) {
            currentCurrency = data;
            updateViewChart();
            updateViewFields();
        }
    }

    @Override
    public void notify(boolean successful) {
        if (successful) {
            updateChart();
        } else {
            Toast.makeText(this, "Refresh failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateChart() {
        mRepository.getCurrencyData(currencyName, this);
    }

    private void updateRepository() {
        mRepository.updateCurrencyData(currencyName, this);
    }

    private void updateViewChart() {
        mChart.addEntry(currentCurrency.getPrice());
    }

    private void updateViewFields() {
        // .. butter knife и вперед
    }

    class UpdateChart extends TimerTask {
        @Override
        public void run() {
            updateRepository();
//            Log.d(Constant.TAG, "Timer tick");
        }
    }
}
