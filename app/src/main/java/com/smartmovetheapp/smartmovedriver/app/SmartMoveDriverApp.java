package com.smartmovetheapp.smartmovedriver.app;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.smartmovetheapp.smartmovedriver.data.sharedpref.SharedPrefs;

public class SmartMoveDriverApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefs.initialize(this);
        FirebaseMessaging.getInstance().subscribeToTopic("driver");
    }
}
