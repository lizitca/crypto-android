package com.example.vladislav.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by d3m1d0v on 08.04.2018.
 */

@Entity(tableName = "notifications")
public class NotificationSetting {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    public final String mCurrencyName;

    @ColumnInfo(name = "highlimiton")
    public boolean highLimit;

    @NonNull
    @ColumnInfo(name = "highprice")
    public Float highPrice;

    @ColumnInfo(name = "lowlimiton")
    public boolean lowLimit;

    @NonNull
    @ColumnInfo(name = "lowprice")
    public Float lowPrice;

    public NotificationSetting(@NonNull String mCurrencyName, boolean highLimit,
                               @NonNull Float highPrice, boolean lowLimit, @NonNull Float lowPrice) {
        this.mCurrencyName = mCurrencyName;
        this.highLimit = highLimit;
        this.highPrice = highPrice;
        this.lowLimit = lowLimit;
        this.lowPrice = lowPrice;
    }

    @NonNull
    public Float getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(@NonNull Float highPrice) {
        this.highPrice = highPrice;
    }

    @NonNull
    public Float getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(@NonNull Float lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Ignore
    public void highLimitOn() {
        highLimit = true;
    }

    @Ignore
    public void highLimitOff() {
        highLimit = false;
    }

    public boolean isHighLimit() {
        return highLimit;
    }

    @Ignore
    public void lowLimitOn() {
        lowLimit = true;
    }

    @Ignore
    public void lowLimitOff() {
        lowLimit = false;
    }

    public boolean isLowLimit() {
        return lowLimit;
    }
}
