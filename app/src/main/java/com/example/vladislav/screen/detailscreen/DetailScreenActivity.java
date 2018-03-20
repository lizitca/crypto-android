package com.example.vladislav.screen.detailscreen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.vladislav.menu.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DetailScreenActivity extends AppCompatActivity {

    private String currencyName;
    private LineChart chart;
    private LineData lineData;
    private Legend legend;

    String[] values = { "Капитализация 100.000.000.000$", "Выпущено 111.111.111 BTC", "Объем(24ч) 10.000.000$" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currencyName = (String) getIntent().getStringExtra("currencyName");
        getSupportActionBar().setTitle(currencyName);
        chartSettings();

        ListView lvMain = (ListView) findViewById(R.id.lvData);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        lvMain.setAdapter(adapter);
    }

    private void chartSettings() {
        chart = findViewById(R.id.chart);
        lineData = new LineData();
        chart.setData(lineData);

        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(true);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setTouchEnabled(false);

        legend = chart.getLegend();
        legend.setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis yAxisleft = chart.getAxisLeft();
        yAxisleft.setValueFormatter(new YAxisValueFormatter());
        yAxisleft.setTextColor(Color.WHITE);
        yAxisleft.setDrawGridLines(false);
        yAxisleft.setDrawAxisLine(false);

        YAxis yAxisright = chart.getAxisRight();
        yAxisright.setEnabled(false);
    }

}
