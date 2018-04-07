package com.example.vladislav.screen.detailscreen;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.vladislav.menu.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vladislav on 24.03.2018.
 */

public class DetailScreenChart extends AppCompatActivity implements OnChartValueSelectedListener {

    private LineChart mChart;
    private String currencyName;
    private Context mContext;

    private ArrayList<String> xValues;
    private SimpleDateFormat dateFormat;

    public DetailScreenChart(LineChart mChart, String currencyName, Context context) {
        this.mChart = mChart;
        this.currencyName = currencyName;
        this.mContext = context;
        this.xValues = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("HH:mm:ss");
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
        mChart.setOnChartValueSelectedListener(this);

        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mv.setChartView(mChart);
        mChart.setMarker(mv);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setEnabled(false);

        YAxis yAxisLeft = mChart.getAxisLeft();
        yAxisLeft.setValueFormatter(new YAxisValueFormatter());
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, currencyName);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.CYAN);
        set.setFillColor(Color.CYAN);
        set.setDrawCircles(false);
        set.setDrawValues(false);
        set.setLineWidth(2f);
        set.setFillAlpha(65);
        set.setDrawFilled(true);

        return set;
    }

    public void addEntry(float yValue) {
        Date date = new Date(new Timestamp(Calendar.getInstance().getTimeInMillis()).getTime());
        xValues.add(dateFormat.format(date));

        LineData data = mChart.getData();

        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        data.addEntry(new Entry(data.getDataSetByIndex(0).getEntryCount(), yValue), 0);
        data.notifyDataChanged();
        mChart.notifyDataSetChanged();
        mChart.moveViewToX(data.getEntryCount());
    }

    class YAxisValueFormatter implements IAxisValueFormatter {

        private DecimalFormat mFormat;

        public YAxisValueFormatter () {
            this.mFormat = new DecimalFormat("###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return "$" + mFormat.format(value);
        }
    }

    class MyMarkerView extends MarkerView {

        private TextView tvContent;
        private DecimalFormat mFormat;
        private String[] format = new String[] {
                "0.000",
                "0.0000",
                "0.00000",
                "0.000000"
        };

        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);

            tvContent = (TextView) findViewById(R.id.tvContent);
            this.mFormat = new DecimalFormat(format[0]);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            if (e.getY() < 100) {
                this.mFormat = new DecimalFormat(format[1]);
            }

            if (e.getY() < 10) {
                this.mFormat = new DecimalFormat(format[2]);
            }

            if (e.getY() < 1) {
                this.mFormat = new DecimalFormat(format[3]);
            }

            tvContent.setText("$: " + mFormat.format(e.getY()) + ", время: " + xValues.get((int) e.getX()));

            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }
    }
}
