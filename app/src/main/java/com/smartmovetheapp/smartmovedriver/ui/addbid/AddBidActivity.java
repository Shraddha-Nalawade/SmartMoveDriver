package com.smartmovetheapp.smartmovedriver.ui.addbid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.data.remote.ApiClient;
import com.smartmovetheapp.smartmovedriver.data.remote.model.OrderBid;
import com.smartmovetheapp.smartmovedriver.data.repository.SessionRepository;
import com.smartmovetheapp.smartmovedriver.ui.base.BaseActivity;
import com.smartmovetheapp.smartmovedriver.util.CalenderUtil;
import com.smartmovetheapp.smartmovedriver.util.FragmentHelper;

import java.net.HttpURLConnection;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBidActivity extends BaseActivity implements PaymentFragment.PaymentActionListener, BidRequestFragment.OrderRequestActionListener {

    private static final String ORDER_ID_EXTRA = "order_id";

    private int orderId;
    private OrderBid bid;

    private AlertDialog loading;
    private final Callback<Void> createBidCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            hideLoading();
            if (response.isSuccessful()) {
                new AlertDialog.Builder(AddBidActivity.this, R.style.SMDatePickerTheme)
                        .setTitle("Your Bid has been placed.")
                        .setMessage("We will notify you once Customer selects your bid.")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                            finish();
                        })
                        .show();

            } else {
                if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    showError("Bad request error");
                } else {
                    showError("Other error code");
                }
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            hideLoading();
            showError("Please try again we are facing some issue");
        }
    };
    private int runningOrderState = 1;

    public static void start(Context context, int orderId) {
        Intent starter = new Intent(context, AddBidActivity.class);
        starter.putExtra(ORDER_ID_EXTRA, orderId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);

        Toolbar toolbar = findViewById(R.id.toolbar);
        attachToolbar(toolbar, true);

        if (getIntent().hasExtra(ORDER_ID_EXTRA)) {
            orderId = getIntent().getIntExtra(ORDER_ID_EXTRA, 0);
        }

        bid = new OrderBid();

        loading = new AlertDialog.Builder(this, R.style.SMDatePickerTheme)
                .setMessage("Creating your Bid....")
                .setCancelable(false)
                .create();

        attachFragment(BidRequestFragment.newInstance(), R.id.frm_fragment_container);
    }

    @Override
    public void onToolbarBackPress() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (runningOrderState == 1) {
            finish();
            return;
        }

        switch (runningOrderState) {
            case 2:
                runningOrderState = 1;
                break;
        }
        getSupportActionBar().setTitle("Add Bid");

        super.onBackPressed();
    }

    public void attachFragment(Fragment fragment, @IdRes int fragmentContainer) {
        FragmentHelper.addFragment(
                fragment,
                getSupportFragmentManager(),
                fragmentContainer,
                null
        );
    }

    @Override
    public void onNextOfPaymentClick() {
        bid.setOrderId(orderId);
        bid.setTruckOwnerId(SessionRepository.getInstance().getDriverId());
        bid.setBidStatus("PENDING");

        showLoading();
        ApiClient.create().placeBid(bid).enqueue(createBidCallback);
    }

    private void showLoading() {
        loading.show();
    }

    private void hideLoading() {
        loading.dismiss();
    }

    @Override
    public void onNextOfOrderClick(double amount, long dateTime, double hours, int noOfTrips) {
        bid.setBidAmount(amount);
        bid.setDate(dateTime);
        bid.setTime(dateTime);
        bid.setNumberOfHours(hours);
        bid.setNumberOfTrips(noOfTrips);

        runningOrderState = 2;

        attachFragment(PaymentFragment.newInstance(), R.id.frm_fragment_container);
        getSupportActionBar().setTitle("Payment");
    }
}
