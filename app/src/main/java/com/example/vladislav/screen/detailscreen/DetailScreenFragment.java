package com.example.vladislav.screen.detailscreen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.app.Constant;
import com.example.vladislav.data.CryptoRepository;
import com.example.vladislav.data.api.CryptoCompareApi;
import com.example.vladislav.data.api.models.CurrencyDataModel;
import com.example.vladislav.menu.R;
import com.example.vladislav.screen.menu.MenuActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vladislav on 17.03.2018.
 */

public class DetailScreenFragment extends Fragment {

    private static final String BASE_URL = "https://min-api.cryptocompare.com/";
    private static final String USD = "USD";
    private CryptoCompareApi api;

    private String currencyName;
    private LineChart chart;
    private ArrayList<Entry> entries;
    private LineDataSet dataSet;
    private LineData lineData;
    private Legend legend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_screen, container, false);

        cryptoCompareApi();

        chartSettings(view);

        return view;
    }

    private void cryptoCompareApi() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoCompareApi.class);
    }

    private void chartSettings(View view) {
        chart = view.findViewById(R.id.chart);
        entries = new ArrayList<>();
        entries.add(new Entry(5f,5f));
        dataSet = new LineDataSet(entries, "");
        lineData = new LineData(dataSet);
        chart.setData(lineData);

        dataSet.setDrawFilled(true);
        dataSet.setColor(getResources().getColor(R.color.color_chart));

        chart.getDescription().setText("");
        chart.setScaleEnabled(true);
        chart.setAutoScaleMinMaxEnabled(true);

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

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
