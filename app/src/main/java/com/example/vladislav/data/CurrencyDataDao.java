package com.example.vladislav.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by d3m1d0v on 22.03.2018.
 */

@Dao
public interface CurrencyDataDao {

    @Query("SELECT * FROM currencies")
    List<CurrencyData> getCurrenciesData();

    @Query("SELECT * FROM currencies WHERE name = :name")
    CurrencyData getCurrencyDataByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrencyData(CurrencyData data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrenciesData(List<CurrencyData> dataList);

    @Update
    void updateCurrencyData(CurrencyData data);

    @Update
    void updateCurrenciesData(List<CurrencyData> dataList);

    @Delete
    void deleteCurrencyDataByName(CurrencyData data);

    @Delete
    void deleteCurrenciesData(List<CurrencyData> dataList);
}
