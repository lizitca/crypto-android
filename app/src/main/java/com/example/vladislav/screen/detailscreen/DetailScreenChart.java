package com.example.vladislav.screen.detailscreen;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vladislav on 24.03.2018.
 */

public class DetailScreenChart extends AppCompatActivity {

    private LineChart mChart;
    private String currencyName;

    public DetailScreenChart(LineChart mChart, String currencyName) {
        this.mChart = mChart;
        this.currencyName = currencyName;
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
        xAxis.setEnabled(false);

        YAxis yAxisLeft = mChart.getAxisLeft();
        yAxisLeft.setValueFormatter(new YAxisValueFormatter());
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);
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

    class XAxisValueFormatter implements IAxisValueFormatter {

        private long referenceTimestamp;
        private DateFormat mDataFormat;
        private Date mDate;

        public XAxisValueFormatter(long referenceTimestamp) {
            this.referenceTimestamp = referenceTimestamp;
            this.mDataFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
            this.mDate = new Date();
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            long convertedTimestamp = (long) value;
            long originalTimestamp = referenceTimestamp + convertedTimestamp;
            return getHour(originalTimestamp);
        }

        private String getHour(long timestamp) {
            try {
                mDate.setTime(timestamp * 1000);
                return mDataFormat.format(mDate);
            } catch (Exception e) {
                return "xx";
            }
        }
    }

    class YAxisValueFormatter implements IAxisValueFormatter {

        private DecimalFormat mFormat;

        public YAxisValueFormatter () {
            mFormat = new DecimalFormat("###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return "$" + mFormat.format(value);
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

    class CustomMarkerView extends MarkerView {

        private TextView mTextView;
        private MPPointF mOffset;
        private long referenceTimestamp;
        private DateFormat mDataFormat;
        private Date mDate;

        public CustomMarkerView(Context context, int layoutResource, long referenceTimestamp) {
            super(context, layoutResource);
            mTextView = (TextView) findViewById(R.id.marker_content);
            this.referenceTimestamp = referenceTimestamp;
            this.mDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            this.mDate = new Date();
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            long currentTimestamp = (int)e.getX() + referenceTimestamp;
            mTextView.setText(e.getY() + "% at " + getTimedate(currentTimestamp));
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-getWidth() / 2, -getHeight());
        }

        private String getTimedate(long timestamp){
            try{
                mDate.setTime(timestamp * 1000);
                return mDataFormat.format(mDate);
            }
            catch(Exception ex){
                return "xx";
            }
        }
    }
}
