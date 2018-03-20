package com.example.vladislav.screen.detailscreen;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by vladislav on 19.03.2018.
 */

public class XAxisValueFormatter implements IAxisValueFormatter {

    private String[] mValues;

    public XAxisValueFormatter(String[] mValues) {
        this.mValues = mValues;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValues[(int) value];
    }
}
