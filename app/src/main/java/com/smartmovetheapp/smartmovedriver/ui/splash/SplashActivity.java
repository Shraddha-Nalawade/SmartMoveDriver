package com.smartmovetheapp.smartmovedriver.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.smartmovetheapp.smartmovedriver.data.sharedpref.StateMachine;
import com.smartmovetheapp.smartmovedriver.ui.home.HomeActivity;
import com.smartmovetheapp.smartmovedriver.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME = 0L;

    public static void start(Context context) {
        Intent starter = new Intent(context, SplashActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper())
                .postDelayed(this::openScreenBasedOnState, SPLASH_TIME);
    }

    private void openScreenBasedOnState() {

        switch (StateMachine.getCurrentState()) {
            case StateMachine.State.NO_LOGIN:
                LoginActivity.start(this);
                break;
            case StateMachine.State.LOGGED_IN:
                HomeActivity.start(this);
                break;
        }
        finish();
    }
}
