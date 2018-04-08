package com.example.vladislav.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
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
    public String currencyName;

    @ColumnInfo(name = "highlimiton")
    public boolean isHighLimitOn;

    @NonNull
    @ColumnInfo(name = "highprice")
    public Float highPrice;

    @ColumnInfo(name = "lowlimiton")
    public boolean isLowLimitOn;

    @NonNull
    @ColumnInfo(name = "lowprice")
    public Float lowPrice;
}
