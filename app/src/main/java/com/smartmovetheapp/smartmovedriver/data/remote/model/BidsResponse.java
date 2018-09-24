package com.smartmovetheapp.smartmovedriver.data.remote.model;

import java.util.List;

public class BidsResponse {
    private List<OrderBid> PendingBids;

    private List<OrderBid> AcceptedBids;

    private List<OrderBid> cancelledOrders;

    public List<OrderBid> getPendingBids() {
        return PendingBids;
    }

    public void setPendingBids(List<OrderBid> pendingBids) {
        this.PendingBids = pendingBids;
    }

    public List<OrderBid> getAcceptedBids() {
        return AcceptedBids;
    }

    public void setAcceptedBids(List<OrderBid> acceptedBids) {
        this.AcceptedBids = acceptedBids;
    }

    public List<OrderBid> getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(List<OrderBid> cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }
}
