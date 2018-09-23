package com.smartmovetheapp.smartmovedriver.ui.addbid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.ui.base.BaseActivity;
import com.smartmovetheapp.smartmovedriver.util.CalenderUtil;

import java.util.Calendar;

public class BidRequestFragment extends Fragment {

    private FloatingActionButton fabNext;
    private EditText edtAmount;
    private EditText edtNoOfTrips;
    private EditText edtHours;
    private TextView txtDateTime;
    private CardView cvDateTime;

    private long orderDateTime;

    private OrderRequestActionListener actionListener;

    public static BidRequestFragment newInstance() {
        return new BidRequestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabNext = view.findViewById(R.id.fab_next);
        cvDateTime = view.findViewById(R.id.cv_date_time);
        txtDateTime = view.findViewById(R.id.txt_date_time);
        edtAmount = view.findViewById(R.id.edt_amount);
        edtNoOfTrips = view.findViewById(R.id.edt_trip_count);
        edtHours = view.findViewById(R.id.edt_hours);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        orderDateTime = System.currentTimeMillis();

        cvDateTime.setOnClickListener(button -> onDateTimeClick());
        fabNext.setOnClickListener(button -> onNextClick());
    }

    private void onNextClick() {
        try {
            validateInput();
            int estimatedNoOfTrips;
            try {
                estimatedNoOfTrips = Integer.parseInt(edtNoOfTrips.getText().toString());
            } catch (NumberFormatException nanError) {
                estimatedNoOfTrips = 0;
            }

            double hours;
            try {
                hours = Double.parseDouble(edtHours.getText().toString());
            } catch (NumberFormatException nanError) {
                hours = 0.0;
            }

            double amount;
            try {
                amount = Double.parseDouble(edtAmount.getText().toString());
            } catch (NumberFormatException nanError) {
                amount = 0.0;
            }
            actionListener.onNextOfOrderClick(
                    amount,
                    orderDateTime,
                    hours,
                    estimatedNoOfTrips
            );
        } catch (IllegalArgumentException error) {
            showError(error.getMessage());
        }
    }

    private void showError(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showError(message);
        }
    }

    private void validateInput() throws IllegalArgumentException {

    }

    private void onDateTimeClick() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(orderDateTime);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                R.style.SMDatePickerTheme,
                (view, year, month, dayOfMonth) -> {
                    storeSelectedDate(year, month, dayOfMonth);
                    showTimePicker();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );

        datePickerDialog.getDatePicker().setMinDate(CalenderUtil.getStartOfDayTime(System.currentTimeMillis()));

        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(orderDateTime);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                R.style.SMDatePickerTheme,
                (view, hourOfDay, minute) -> {
                    storeSelectedTime(hourOfDay, minute);
                    txtDateTime.setText(CalenderUtil.getDisplayDateTime(orderDateTime));
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        );

        timePickerDialog.show();
    }

    private void storeSelectedTime(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(orderDateTime);

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        orderDateTime = calendar.getTimeInMillis();
    }

    private void storeSelectedDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(orderDateTime);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        orderDateTime = calendar.getTimeInMillis();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderRequestActionListener) {
            actionListener = (OrderRequestActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        actionListener = null;
    }

    public interface OrderRequestActionListener {
        void onNextOfOrderClick(double amount, long dateTime, double hours, int noOfTrips);
    }
}
