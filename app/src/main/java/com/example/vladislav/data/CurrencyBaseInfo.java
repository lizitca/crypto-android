package com.example.vladislav.data;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class CurrencyBaseInfo {

    private static final String FORMAT = "#.###";
    private static final DecimalFormat FORMATTER;

    static {
        FORMATTER = new DecimalFormat();
        FORMATTER.setRoundingMode(RoundingMode.CEILING);
    }

    private String name;
    private float price;
    private float change;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceValue() {
        return "$" + FORMATTER.format(price);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getChangeValue() {
        String value = FORMATTER.format(change);
        value = Float.parseFloat(value) > 0 ? '+' + value : value;
        value = value + '%';

        return value;
    }

    public void setChange(float change) {
        this.change = change;
    }
}
