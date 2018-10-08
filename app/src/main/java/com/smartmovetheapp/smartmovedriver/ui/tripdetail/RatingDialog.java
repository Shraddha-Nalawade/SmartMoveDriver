package com.smartmovetheapp.smartmovedriver.ui.tripdetail;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smartmovetheapp.smartmovedriver.R;
import com.smartmovetheapp.smartmovedriver.data.remote.ApiClient;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Rating;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingDialog {

    private AlertDialog alertDialog;
    private RatingActionListener actionListener;
    private Rating rating;
    private RatingBar ratingBar;
    private TextView txtMessage;

    private Callback<Void> callback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            hideLoading();
            if (response.isSuccessful()) {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                actionListener.onSubmitRating();
            } else {
                showMessage(alertDialog.getContext().getString(R.string.default_error));
                //Toast.makeText(alertDialog.getContext(), R.string.default_error, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            showMessage(alertDialog.getContext().getString(R.string.default_error));
            //Toast.makeText(alertDialog.getContext(), R.string.default_error, Toast.LENGTH_SHORT).show();
        }
    };
    private Button submitButton;

    public static RatingDialog getInstance(Context context, int orderId, int customerId, RatingActionListener actionListener) {
        return new RatingDialog(context, orderId, customerId, actionListener);
    }

    private RatingDialog(Context context, int orderId, int customerId, RatingActionListener actionListener) {
        this.actionListener = actionListener;
        rating = new Rating();
        rating.setOrderId(orderId);
        rating.setCustomerId(customerId);

        alertDialog = new AlertDialog.Builder(context)
                .setTitle("Rate this Customer")
                .setView(R.layout.dialog_rating)
                .setCancelable(false)
                .create();
    }

    private void performServerCallForRating(int userRating) {
        rating.setRating(userRating);

        ApiClient.create().subitRating(rating)
                .enqueue(callback);
    }

    public void show() {
        alertDialog.show();

        ratingBar = alertDialog.findViewById(R.id.ratingBar);
        txtMessage = alertDialog.findViewById(R.id.txt_message);
        submitButton = alertDialog.findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(button -> {
            int userRating = ratingBar.getNumStars();
            showLoading();
            performServerCallForRating(userRating);
        });
    }

    private void showLoading() {
        showMessage("Submitting your review..");

        /*if (submitButton != null) {
            submitButton.setEnabled(false);
        }
        if (ratingBar != null) {
            ratingBar.setEnabled(false);
        }*/
    }

    private void hideLoading() {
        showMessage("");
    }

    private void showMessage(String message) {
        if (txtMessage == null) {
            return;
        }

        txtMessage.setText(message);
    }

    public interface RatingActionListener {
        void onSubmitRating();
    }
}
