package com.example.vladislav.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by d3m1d0v on 08.04.2018.
 */

@Dao
public interface NotificationSettingDao {

    @Deprecated
    @Query("SELECT * FROM notifications")
    List<NotificationSetting> getSettingsAll();

    @Deprecated
    @Query("SELECT * FROM notifications WHERE name = :name")
    NotificationSetting getSetting(String name);

    @Query("SELECT * FROM notifications")
    Flowable<List<NotificationSetting>> getSettingsAll_Rx();

    @Query("SELECT * FROM notifications WHERE name = :name")
    Flowable<NotificationSetting> getSetting_Rx(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotificationSetting(NotificationSetting setting);
}
