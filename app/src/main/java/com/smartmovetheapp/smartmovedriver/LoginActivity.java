package com.smartmovetheapp.smartmovedriver;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smartmovetheapp.smartmovedriver.data.remote.model.LoginResponse;
import com.smartmovetheapp.smartmovedriver.data.repository.AuthRepository;
import com.smartmovetheapp.smartmovedriver.ui.home.HomeActivity;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private CardView cvSignInButton;
    private EditText edtUsername;
    private EditText edtPassword;
    private ProgressBar pbLoading;

    private final Callback<LoginResponse> loginCallback = new Callback<LoginResponse>() {
        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            hideLoading();
            showError("Please try again we are facing some issue");
        }

        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            hideLoading();
            moveToHomeScreen(); //todo: remove this - used for skipping Login API call
            if (response.isSuccessful() && response.body() != null) {
                if (response.body() != null) {
                    //SessionRepository.instance().storeUser(response.body());
                    moveToHomeScreen();
                } else {
                    showError("Please try again we are facing some issue");
                }
            } else {
                if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    showError("Invalid email/password");
                } else {
                    showError("Please try again we are facing some issue");
                }
            }
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cvSignInButton = findViewById(R.id.cv_sign_in_button);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        pbLoading = findViewById(R.id.login_progress);

        cvSignInButton.setOnClickListener(button -> attemptLogin());
    }

    private void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
        edtUsername.setEnabled(false);
        edtPassword.setEnabled(false);
        cvSignInButton.setEnabled(false);
    }

    private void hideLoading() {
        pbLoading.setVisibility(View.GONE);
        edtUsername.setEnabled(true);
        edtPassword.setEnabled(true);
        cvSignInButton.setEnabled(true);
    }

    private void showError(String error) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
        snackbar.show();
    }

    private void attemptLogin() {
        try {
            validateFields();
            showLoading();
            performServerCall();
            //moveToHomeScreen(); //todo: remove once login call implemented
        } catch (IllegalArgumentException error) {
            showError(error.getMessage());
        }
    }

    private void validateFields() throws IllegalArgumentException {
        if (edtUsername.getText().toString().trim().isEmpty()) {
            throw new IllegalArgumentException(getString(R.string.empty_email));
        }

        if (edtPassword.getText().toString().trim().isEmpty()) {
            throw new IllegalArgumentException(getString(R.string.empty_password));
        }
    }

    private void performServerCall() {
        AuthRepository.getInstance().attemptLogin(
                edtUsername.getText().toString().trim(),
                edtPassword.getText().toString().trim()
        ).enqueue(loginCallback);
    }

    private void moveToHomeScreen() {
        finish();
        HomeActivity.start(this);
    }
}