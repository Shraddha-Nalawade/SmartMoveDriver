package com.smartmovetheapp.smartmovedriver.ui.tripdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Order;
import com.smartmovetheapp.smartmovedriver.ui.base.BaseActivity;
import com.smartmovetheapp.smartmovedriver.util.CalenderUtil;

public class TripDetailActivity extends BaseActivity {

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

        populateOrder(order);
    }

    private void populateOrder(Order order) {
        txtPickupPlace.setText(order.getPickupPlace());
        txtDropPlace.setText(order.getDropPlace());
        txtDateTime.setText(CalenderUtil.getDisplayDateTime(order.getDate()));
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
}
