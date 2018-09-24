package com.smartmovetheapp.smartmovedriver.data.remote.model;

import java.util.List;

public class BidsResponse {
    private List<OrderBid> runningOrders;

    private List<OrderBid> completedOrders;

    private List<OrderBid> cancelledOrders;

    public List<OrderBid> getRunningOrders() {
        return runningOrders;
    }

    public void setRunningOrders(List<OrderBid> runningOrders) {
        this.runningOrders = runningOrders;
    }

    public List<OrderBid> getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(List<OrderBid> completedOrders) {
        this.completedOrders = completedOrders;
    }

    public List<OrderBid> getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(List<OrderBid> cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }
}
