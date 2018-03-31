package com.example.vladislav.screen.detailscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vladislav.data.CurrencyData;
import com.example.vladislav.menu.R;
import com.github.mikephil.charting.charts.LineChart;

public class DetailScreenActivity extends AppCompatActivity {

    private String currencyName;
    private DetailScreenChart mChart;

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
                Intent intent = new Intent(this, ICONotification.class);
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
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChart.updateRepository();
                            mChart.updateChart();
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
