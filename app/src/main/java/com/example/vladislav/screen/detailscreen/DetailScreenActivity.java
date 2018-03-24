package com.example.vladislav.screen.detailscreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.vladislav.data.api.CryptoCompareApi;
import com.example.vladislav.data.api.models.CurrencyDataModel;
import com.example.vladislav.menu.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailScreenActivity extends AppCompatActivity {

    private final String BASE_URL = "https://min-api.cryptocompare.com/";
    private final String USD = "USD";

    private CryptoCompareApi api;

    private String currencyName;
    private LineChart mChart;

    String[] values = { "Капитализация 100.000.000.000$", "Выпущено 111.111.111 BTC", "Объем(24ч) 10.000.000$" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currencyName = getIntent().getStringExtra("currencyName");
        getSupportActionBar().setTitle(currencyName);
        chartSettings();

        ListView lvMain = (ListView) findViewById(R.id.lvData);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        lvMain.setAdapter(adapter);

        setApi();
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
                this.startActivity(intent);
                break;

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
                            updateChart();
                        }
                    });

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void chartSettings() {
        mChart = findViewById(R.id.chart);
        mChart.getDescription().setEnabled(false);
        mChart.setScaleEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);

        mChart.setDrawGridBackground(false);
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);

        LineData lineData = new LineData();
        mChart.setData(lineData);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis yAxisleft = mChart.getAxisLeft();
        yAxisleft.setValueFormatter(new YAxisValueFormatter());
        yAxisleft.setTextColor(Color.WHITE);
        yAxisleft.setDrawGridLines(true);
        yAxisleft.setDrawAxisLine(true);
    }

    private void setApi() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoCompareApi.class);
    }

    private LineDataSet createSet(){
        LineDataSet set = new LineDataSet(null, currencyName);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(getResources().getColor(R.color.color_value));
        set.setValueTextColor(getResources().getColor(R.color.color_value));
        set.setFillColor(getResources().getColor(R.color.color_value));
        set.setCircleColor(getResources().getColor(R.color.color_circle));
        set.setValueTextSize(10f);
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setValueFormatter(new ValueFormatter());

        return set;
    }

    private void addEntry(float yValue) {
        LineData data = mChart.getData();

        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        data.addEntry(new Entry(data.getDataSetByIndex(0).getEntryCount(), yValue), 0);
        data.notifyDataChanged();
        mChart.notifyDataSetChanged();
        mChart.setVisibleXRangeMaximum(5);
    }

    private void updateChart() {
        api.getCurrencyData(currencyName, USD).enqueue(new Callback<CurrencyDataModel.Response>() {
            @Override
            public void onResponse(Call<CurrencyDataModel.Response> call, Response<CurrencyDataModel.Response> response) {
                CurrencyDataModel data = response.body().getData().get(currencyName).get(USD);

                addEntry(Float.parseFloat(data.getPRICE()));
            }

            @Override
            public void onFailure(Call<CurrencyDataModel.Response> call, Throwable t) {

            }
        });
    }

    class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public XAxisValueFormatter(String[] mValues) {
            this.mValues = mValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }

    class YAxisValueFormatter implements IAxisValueFormatter {

        private DecimalFormat mFormat;

        public YAxisValueFormatter () {
            mFormat = new DecimalFormat("###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + " $";
        }
    }

    class ValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public ValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.000");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }
    }
}
