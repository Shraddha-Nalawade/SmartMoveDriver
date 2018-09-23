package com.smartmovetheapp.smartmovedriver.data.remote.model;

import java.util.List;

public class TripResponse {
    private List<Order> runningOrders;

    private List<Order> completedOrders;

    private List<Order> cancelledOrders;

    public List<Order> getRunningOrders() {
        return runningOrders;
    }

    public void setRunningOrders(List<Order> runningOrders) {
        this.runningOrders = runningOrders;
    }

    public List<Order> getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(List<Order> completedOrders) {
        this.completedOrders = completedOrders;
    }

    public List<Order> getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(List<Order> cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }
}
