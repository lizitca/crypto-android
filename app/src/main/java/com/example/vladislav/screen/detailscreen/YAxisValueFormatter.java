package com.example.vladislav.screen.detailscreen;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by vladislav on 17.03.2018.
 */

public class YAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat mFormat;

    public YAxisValueFormatter () {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " $";
    }
}
