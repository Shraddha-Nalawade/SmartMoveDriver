package com.smartmovetheapp.smartmovedriver.ui.base;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.util.NetworkUtils;


public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onToolbarBackPress();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onToolbarBackPress() {
        finish();
    }

    public void attachToolbar(Toolbar toolbar, boolean hasBackButton) {
        setSupportActionBar(toolbar);
        if (hasBackButton) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

/*
    public void attachFragment(Fragment fragment, @IdRes int fragmentContainer) {
        FragmentHelper.addFragment(
                fragment,
                getSupportFragmentManager(),
                fragmentContainer,
                null
        );
    }

    public void changeFragment(Fragment fragment, @IdRes int fragmentContainer) {
        FragmentHelper.replaceFragment(
                fragment,
                getSupportFragmentManager(),
                fragmentContainer,
                null
        );
    }
*/

    public void showError(String message) {
        if (message != null) {
            showSnackBar(message, R.color.colorErrorRed);
        } else {
            showSnackBar(getString(R.string.default_error), R.color.colorErrorRed);
        }
    }

    public void showError(@StringRes int resId) {
        showError(getString(resId));
    }

    public void showInternetError() {
        showError(R.string.snackbar_internet_unavailable);
    }

    public void showSnackBar(String message) {
        showSnackBar(message, R.color.colorWhite);
    }

    public void showSnackBar(String message, @ColorRes int textColor) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, textColor));
        snackbar.show();
    }

    public void showSnackBar(@StringRes int resId) {
        showSnackBar(getString(resId));
    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
