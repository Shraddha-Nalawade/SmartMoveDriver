package com.smartmovetheapp.smartmovedriver.data.remote.model;

public class OrderBid {
    public int bidId; //Not In UI as field, Not Mandatory

    public int orderId;

    public int truckOwnerId;

    private long date;

    private long time;

    public double numberOfHours;

    public int numberOfTrips;

    public double bidAmount;

    public String bidStatus; //Not In UI as field, Not Mandatory

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTruckOwnerId() {
        return truckOwnerId;
    }

    public void setTruckOwnerId(int truckOwnerId) {
        this.truckOwnerId = truckOwnerId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(double numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }
}
