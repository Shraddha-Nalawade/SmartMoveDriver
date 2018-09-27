package com.smartmovetheapp.smartmovedriver.data.remote.model;

public class Rating {

    private int truckOwnerId;

    private int orderId;

    private int rating;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTruckOwnerId() {
        return truckOwnerId;
    }

    public void setTruckOwnerId(int truckOwnerId) {
        this.truckOwnerId = truckOwnerId;
    }
}
