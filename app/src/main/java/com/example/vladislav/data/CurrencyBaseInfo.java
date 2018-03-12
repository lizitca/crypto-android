package com.example.vladislav.data;

import android.util.Log;

import com.example.vladislav.app.Constant;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class CurrencyBaseInfo {

    private String name;
    private float price;
    private float change;

    public CurrencyBaseInfo() {
        this(null);
    }

    public CurrencyBaseInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceValue() {
        return String.format("$%,.2f", price);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getChangeValue() {
        return String.format("%+,.3g%%", change);
    }

    public void setChange(float change) {
        this.change = change;
    }
}
