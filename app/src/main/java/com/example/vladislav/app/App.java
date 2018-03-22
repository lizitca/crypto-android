package com.example.vladislav.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by d3m1d0v on 22.03.2018.
 */

public class App extends Application {

    private static App INSTANCE;

    public App() {
        super();

        INSTANCE = this;
    }

    public static Context getAppContext() {
        return INSTANCE.getApplicationContext();
    }
}
