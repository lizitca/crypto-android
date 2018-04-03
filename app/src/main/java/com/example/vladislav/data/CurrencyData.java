package com.example.vladislav.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by d3m1d0v on 22.03.2018.
 */

@Entity(tableName = "currencies")
public final class CurrencyData {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private final String mName;

    @ColumnInfo(name = "price")
    private final float mPrice;

    @ColumnInfo(name = "change")
    private final float mChange;

    @ColumnInfo(name = "change1H")
    private final float mChange1H;

    @ColumnInfo(name = "change7D")
    private final float mChange7D;

    @ColumnInfo(name = "supply")
    private final String mSupply;

    @ColumnInfo(name = "capitalization")
    private final String mCapitalization;

    @ColumnInfo(name = "volume")
    private final String mVolume24H;

    @NonNull
    @ColumnInfo(name = "last_update")
    private final String mLastUpdate;

    @Ignore private final String mFormattedPrice;
    @Ignore private final String mFormattedChange;

    public CurrencyData(@NonNull String name, float price, float change, float change1H, float change7D,
                        @NonNull String supply, @NonNull String capitalization, @NonNull String volume24H,
                        @NonNull String lastUpdate) {
        mName = name;
        mPrice = price;
        mChange = change;
        mChange1H = change1H;
        mChange7D = change7D;
        mSupply = supply + " " + mName;
        mCapitalization = capitalization;
        mVolume24H = volume24H;
        mLastUpdate = lastUpdate;

        mFormattedPrice = String.format("$%,.2f", mPrice);
        mFormattedChange = String.format("%+,.3g%%", mChange);
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public float getPrice() {
        return mPrice;
    }

    public float getChange() {
        return mChange;
    }

    public float getChange1H() {
        return mChange1H;
    }

    public float getChange7D() {
        return mChange7D;
    }

    @NonNull
    public String getSupply() {
        return mSupply;
    }

    @NonNull
    public String getCapitalization() {
        return mCapitalization;
    }

    @NonNull
    public String getVolume24H() {
        return mVolume24H;
    }

    @NonNull
    public String getLastUpdate() {
        return mLastUpdate;
    }

    @Ignore
    @NonNull
    public String getFormattedPrice() {
        return mFormattedPrice;
    }

    @Ignore
    @NonNull
    public String getFormattedChange() {
        return mFormattedChange;
    }
}
