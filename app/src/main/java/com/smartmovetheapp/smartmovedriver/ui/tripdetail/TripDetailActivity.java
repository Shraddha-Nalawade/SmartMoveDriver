package com.smartmovetheapp.smartmovedriver.ui.tripdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.data.remote.ApiClient;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Order;
import com.smartmovetheapp.smartmovedriver.data.repository.SessionRepository;
import com.smartmovetheapp.smartmovedriver.ui.addbid.AddBidActivity;
import com.smartmovetheapp.smartmovedriver.ui.base.BaseActivity;
import com.smartmovetheapp.smartmovedriver.ui.home.HomeActivity;
import com.smartmovetheapp.smartmovedriver.util.CalenderUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailActivity extends BaseActivity implements RatingDialog.RatingActionListener {

    private static final String ORDER_EXTRA = "order";
    private Order order;

    //order
    private TextView txtPickupPlace;
    private TextView txtDropPlace;
    private TextView txtDateTime;
    private TextView txtTruckType;
    private TextView txtTripCount;

    //pickup
    private TextView txtFloorLevel;
    private SwitchCompat swtElevator;
    private TextView txtParkingDistance;
    private TextView txtWeight;
    private TextView txtArea;
    private TextView txtExtra;

    //drop
    private TextView txtFloorLevelD;
    private SwitchCompat swtElevatorD;
    private TextView txtParkingDistanceD;
    private TextView txtExtraD;

    private CardView cvBidsButton;
    private CardView cvStartButton;
    private CardView cvEndButton;
    private CardView cvCancelButton;

    private AlertDialog loading;

    private final Callback<Void> cancelBidCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            hideLoading();
            if (response.isSuccessful()) {
                new AlertDialog.Builder(TripDetailActivity.this, R.style.SMDatePickerTheme)
                        .setTitle("Bid has been cancelled.")
                        .setMessage("Your scheduled Bid has been cancelled successfully.")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();

                            HomeActivity.start(TripDetailActivity.this);
                            finish();
                        })
                        .show();
            } else {
                showError(R.string.default_error);
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            hideLoading();
            showError(R.string.default_error);
        }
    };

    private final Callback<Void> startOrderCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            hideLoading();
            if (response.isSuccessful()) {
                new AlertDialog.Builder(TripDetailActivity.this, R.style.SMDatePickerTheme)
                        .setTitle("Order has been started.")
//                        .setMessage("")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();

                            HomeActivity.start(TripDetailActivity.this);
                            finish();
                        })
                        .show();
            } else {
                showError(R.string.default_error);
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            hideLoading();
            showError(R.string.default_error);
        }
    };

    private final Callback<Void> endOrderCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            hideLoading();
            if (response.isSuccessful()) {
                new AlertDialog.Builder(TripDetailActivity.this, R.style.SMDatePickerTheme)
                        .setTitle("Order has been ended.")
                        //.setMessage("Your scheduled Bid has been cancelled successfully.")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();

                            RatingDialog.getInstance(
                                    TripDetailActivity.this,
                                    order.getOrderId(),
                                    order.getCustomerId(),
                                    TripDetailActivity.this)
                                    .show();

                            //HomeActivity.start(TripDetailActivity.this);
                            //finish();
                        })
                        .show();
            } else {
                showError(R.string.default_error);
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            hideLoading();
            showError(R.string.default_error);
        }
    };

    public static void start(Context context, Order order) {
        Intent starter = new Intent(context, TripDetailActivity.class);
        Gson gson = new Gson();
        starter.putExtra(ORDER_EXTRA, gson.toJson(order));
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        if (!getIntent().hasExtra(ORDER_EXTRA)) {
            finish();
            return;
        }

        String json = getIntent().getStringExtra(ORDER_EXTRA);
        Gson gson = new Gson();
        order = gson.fromJson(json, Order.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        attachToolbar(toolbar, true);

        txtPickupPlace = findViewById(R.id.txt_pickup_dest);
        txtDropPlace = findViewById(R.id.txt_drop_dest);
        txtDateTime = findViewById(R.id.txt_date_time);
        txtTruckType = findViewById(R.id.txt_truck_type);
        txtTripCount = findViewById(R.id.txt_trip_count);

        txtFloorLevel = findViewById(R.id.txt_floor_level);
        swtElevator = findViewById(R.id.swt_elevator);
        txtParkingDistance = findViewById(R.id.txt_parking_distance);
        txtWeight = findViewById(R.id.txt_weight);
        txtArea = findViewById(R.id.txt_area);
        txtExtra = findViewById(R.id.txt_extra);

        txtFloorLevelD = findViewById(R.id.txt_floor_level_d);
        swtElevatorD = findViewById(R.id.swt_elevator_d);
        txtParkingDistanceD = findViewById(R.id.txt_parking_distance_d);
        txtExtraD = findViewById(R.id.txt_extra_d);

        cvBidsButton = findViewById(R.id.cv_bids_click);
        cvStartButton = findViewById(R.id.cv_start_click);
        cvEndButton = findViewById(R.id.cv_end_click);
        cvCancelButton = findViewById(R.id.cv_cancel_click);

        loading = new AlertDialog.Builder(this, R.style.SMDatePickerTheme)
                .setMessage("loading..")
                .setCancelable(false)
                .create();

        cvBidsButton.setOnClickListener(button -> {
            AddBidActivity.start(this, order);
        });
        cvStartButton.setOnClickListener(button -> {
            onStartOrderClick();
        });
        cvEndButton.setOnClickListener(button -> {
            onEndOrderClick();
        });
        cvCancelButton.setOnClickListener(button -> {
            onCancelBidClick();
        });

        populateOrder(order);
        handleButtonVisibility(order.getOrderStatus());
    }

    private void handleButtonVisibility(String orderStatus) {
        long currentTimeMillis = System.currentTimeMillis();
        switch (orderStatus) {
            case "PENDING":
                cvBidsButton.setVisibility(View.VISIBLE);
                cvStartButton.setVisibility(View.GONE);
                cvEndButton.setVisibility(View.GONE);
                cvCancelButton.setVisibility(View.GONE);
                break;
            case "CONFIRMED":
                cvBidsButton.setVisibility(View.GONE);

                if (CalenderUtil.getStartOfDayTime(order.getTime()) <= currentTimeMillis
                        && CalenderUtil.getEndOfDayTime(order.getTime()) >= currentTimeMillis) {

                    cvStartButton.setVisibility(View.VISIBLE);
                    cvEndButton.setVisibility(View.GONE);
                } else {
                    cvStartButton.setVisibility(View.GONE);
                    cvEndButton.setVisibility(View.GONE);
                }
                cvCancelButton.setVisibility(View.GONE);
                break;
            case "DELIVERING":
                cvBidsButton.setVisibility(View.GONE);

                if (CalenderUtil.getStartOfDayTime(order.getTime()) <= currentTimeMillis
                        && CalenderUtil.getEndOfDayTime(order.getTime()) >= currentTimeMillis) {

                    cvStartButton.setVisibility(View.GONE);
                    cvEndButton.setVisibility(View.VISIBLE);
                } else {
                    cvStartButton.setVisibility(View.GONE);
                    cvEndButton.setVisibility(View.GONE);
                }
                cvCancelButton.setVisibility(View.GONE);
                break;
        }
    }

    private void onStartOrderClick() {
        showLoading("Starting order..");
        ApiClient.create().startOrder(order.getOrderId())
                .enqueue(startOrderCallback);
        //HomeActivity.start(this);
    }

    private void onEndOrderClick() {
        showLoading("Ending order..");
        ApiClient.create().endOrder(order.getOrderId())
                .enqueue(endOrderCallback);
        //HomeActivity.start(this);
        //FirebaseMessaging.getInstance().send(new RemoteMessage());
    }

    private void onCancelBidClick() {
        showLoading("Cancelling bid..");
    }

    private void showLoading(String loadingMessage) {
        loading.setMessage(loadingMessage);
        loading.show();
    }

    private void hideLoading() {
        loading.dismiss();
    }

    private void populateOrder(Order order) {
        txtPickupPlace.setText(order.getPickupPlace());
        txtDropPlace.setText(order.getDropPlace());
        txtDateTime.setText(CalenderUtil.getDisplayDateTime(order.getTime()));
        txtTruckType.setText(getTruckTypeText(order.getTruckTypeId()));
        txtTripCount.setText(order.getEstimatedNumOfTrips() == null ? "--" : String.valueOf(order.getEstimatedNumOfTrips()));

        txtFloorLevel.setText(order.getPickupFloor());
        swtElevator.setChecked(order.isPickupHasElevator());
        swtElevator.setEnabled(false);
        txtParkingDistance.setText(order.getPickupDistanceFromParking());
        txtWeight.setText(order.getEstimatedWeight().isEmpty() ? "--" : order.getEstimatedWeight());
        txtArea.setText(order.getEstimatedArea().isEmpty() ? "--" : order.getEstimatedArea());
        txtExtra.setText(order.getPickupAdditionalInfo().trim().isEmpty() ? "--" : order.getPickupAdditionalInfo());

        txtFloorLevelD.setText(order.getDropFloor());
        swtElevatorD.setChecked(order.isDropHasElevator());
        swtElevatorD.setEnabled(false);
        txtParkingDistanceD.setText(order.getDropDistanceFromParking());
        txtExtraD.setText(order.getDropAdditionalInfo().trim().isEmpty() ? "--" : order.getDropAdditionalInfo());
    }

    private String getTruckTypeText(int truckTypeId) {
        switch (truckTypeId) {
            case 1:
                return "Standard Car";
            case 2:
                return "Pickup Truck";
            case 3:
                return "Cargo Truck";
            default:
                return "--";
        }
    }

    @Override
    public void onSubmitRating() {
        HomeActivity.start(TripDetailActivity.this);
        finish();
    }
}
