package com.smartmovetheapp.smartmovedriver.data.remote.model;

public class Order {

    private Place pickupPlace;

    private Place dropPlace;

    private long date;

    private long time;

    private String truckType;

    private String noOfTrips;

    private String status;

    public Place getPickupPlace() {
        return pickupPlace;
    }

    public void setPickupPlace(Place pickupPlace) {
        this.pickupPlace = pickupPlace;
    }

    public Place getDropPlace() {
        return dropPlace;
    }

    public void setDropPlace(Place dropPlace) {
        this.dropPlace = dropPlace;
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

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getNoOfTrips() {
        return noOfTrips;
    }

    public void setNoOfTrips(String noOfTrips) {
        this.noOfTrips = noOfTrips;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}