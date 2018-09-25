package com.smartmovetheapp.smartmovedriver.data.remote.model;

import java.util.List;

public class BidsResponse {
    private List<OrderBid> pendingBids;

    private List<OrderBid> acceptedBids;

    public List<OrderBid> getPendingBids() {
        return pendingBids;
    }

    public void setPendingBids(List<OrderBid> pendingBids) {
        this.pendingBids = pendingBids;
    }

    public List<OrderBid> getAcceptedBids() {
        return acceptedBids;
    }

    public void setAcceptedBids(List<OrderBid> acceptedBids) {
        this.acceptedBids = acceptedBids;
    }
}
