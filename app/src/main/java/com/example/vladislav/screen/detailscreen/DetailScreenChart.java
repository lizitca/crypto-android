package com.example.vladislav.screen.detailscreen;

import android.graphics.Color;

import com.example.vladislav.data.api.CryptoCompareApi;
import com.example.vladislav.data.api.models.CurrencyDataModel;
import com.example.vladislav.data.repository.CryptoRepository;
import com.example.vladislav.data.repository.MainRepository;
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

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vladislav on 24.03.2018.
 */

public class DetailScreenChart {

    private final String BASE_URL = "https://min-api.cryptocompare.com/";
    private final String USD = "USD";

    private LineChart mChart;
    private String currencyName;
    private CryptoCompareApi api;

    private final CryptoRepository mRepository = MainRepository.getInstance();

    public DetailScreenChart(LineChart mChart, String currencyName) {
        this.mChart = mChart;
        this.currencyName = currencyName;

        setApi();
    }

    public void initialize() {
        mChart.getDescription().setEnabled(false);
        mChart.setScaleEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setDrawGridBackground(false);
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.setData(new LineData());

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);

        YAxis yAxisLeft = mChart.getAxisLeft();
        yAxisLeft.setValueFormatter(new YAxisValueFormatter());
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setDrawAxisLine(true);
    }

    private void setApi() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoCompareApi.class);
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, currencyName);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.CYAN);
        set.setFillColor(Color.CYAN);
        set.setCircleColor(Color.rgb(59,127,237));
        set.setValueTextSize(10f);
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setDrawFilled(true);
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
        mChart.moveViewToX(data.getEntryCount());
    }

    public void updateChart() {
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
