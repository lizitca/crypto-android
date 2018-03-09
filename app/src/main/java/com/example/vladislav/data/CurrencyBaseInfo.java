package com.example.vladislav.data;

import android.util.Log;

import com.example.vladislav.app.Constant;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by d3m1d0v on 04.03.2018.
 */

public class CurrencyBaseInfo {

    //todo FORMAT ?
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
        // TODO: 09.03.2018 https://developer.android.com/reference/java/lang/StringBuilder.html
        // TODO: 09.03.2018 https://developer.android.com/reference/java/util/Formatter.html
        // Example
        // Calendar c = new GregorianCalendar(1995, MAY, 23);
        // String s = String.format("Duke's Birthday: %1$tb %1$te, %1$tY", c);
        // -> s == "Duke's Birthday: May 23, 1995"

        String value = FORMATTER.format(change);
        try {
            value = Float.parseFloat(value) > 0 ? '+' + value : value; // todo exception!!!
        } catch (NumberFormatException e) {
            Log.e(Constant.TAG, "getChangeValue: ", e);
        }
        value = value + '%';
        return value;
    }

    public void setChange(float change) {
        this.change = change;
    }
}
