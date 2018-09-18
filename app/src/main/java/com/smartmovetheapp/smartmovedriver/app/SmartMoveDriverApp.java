package com.smartmovetheapp.smartmovedriver.app;

import android.app.Application;

import com.smartmovetheapp.smartmovedriver.data.sharedpref.SharedPrefs;

public class SmartMoveDriverApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefs.initialize(this);
    }
}
