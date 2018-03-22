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

    @NonNull
    @ColumnInfo(name = "last_update")
    private final String mLastUpdate;

    @Ignore private final String mFormattedPrice;
    @Ignore private final String mFormattedChange;

    public CurrencyData(@NonNull String name, float price,
                        float change, @NonNull String lastUpdate) {
        mName = name;
        mPrice = price;
        mChange = change;
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
