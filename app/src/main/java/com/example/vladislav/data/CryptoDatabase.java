package com.example.vladislav.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.vladislav.app.App;

/**
 * Created by d3m1d0v on 22.03.2018.
 */

@Database(entities = {CurrencyData.class, NotificationSetting.class}, version = 4)
public abstract class CryptoDatabase extends RoomDatabase {

    private static CryptoDatabase INSTANCE;

    public abstract CurrencyDataDao currencyDataDao();

    public abstract NotificationSettingDao settingDao();

    public static CryptoDatabase getInstance() {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(App.getAppContext(),
                    CryptoDatabase.class, "Crypto.db")
                    .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
