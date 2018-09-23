package com.smartmovetheapp.smartmovedriver.data.remote.model;

public class Order {

    private int orderId; // Not Mandatory

    private int customerId;

    private int truckTypeId;

    private long date;

    private long time;

    private String pickupPlace;

    private double pickupLat;

    private double pickupLong;

    private String pickupUnitNumber;// Not Mandatory

    private String pickupFloor;

    private boolean pickupHasElevator;

    private String pickupDistanceFromParking;

    private String pickupAdditionalInfo;// Not Mandatory

    private String dropPlace;

    private double dropLat;

    private double dropLong;

    private String dropUnitNumber;// Not Mandatory

    private String dropFloor;

    private boolean dropHasElevator;

    private String dropDistanceFromParking;

    private String dropAdditionalInfo;// Not Mandatory

    private Integer estimatedNumOfTrips;// Not Mandatory

    private String estimatedWeight;// Not Mandatory

    private String estimatedArea;// Not Mandatory

    private String orderStatus;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTruckTypeId() {
        return truckTypeId;
    }

    public void setTruckTypeId(int truckTypeId) {
        this.truckTypeId = truckTypeId;
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

    public String getPickupPlace() {
        return pickupPlace;
    }

    public void setPickupPlace(String pickupPlace) {
        this.pickupPlace = pickupPlace;
    }

    public double getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public double getPickupLong() {
        return pickupLong;
    }

    public void setPickupLong(double pickupLong) {
        this.pickupLong = pickupLong;
    }

    public String getPickupUnitNumber() {
        return pickupUnitNumber;
    }

    public void setPickupUnitNumber(String pickupUnitNumber) {
        this.pickupUnitNumber = pickupUnitNumber;
    }

    public String getPickupFloor() {
        return pickupFloor;
    }

    public void setPickupFloor(String pickupFloor) {
        this.pickupFloor = pickupFloor;
    }

    public boolean isPickupHasElevator() {
        return pickupHasElevator;
    }

    public void setPickupHasElevator(boolean pickupHasElevator) {
        this.pickupHasElevator = pickupHasElevator;
    }

    public String getPickupDistanceFromParking() {
        return pickupDistanceFromParking;
    }

    public void setPickupDistanceFromParking(String pickupDistanceFromParking) {
        this.pickupDistanceFromParking = pickupDistanceFromParking;
    }

    public String getPickupAdditionalInfo() {
        return pickupAdditionalInfo;
    }

    public void setPickupAdditionalInfo(String pickupAdditionalInfo) {
        this.pickupAdditionalInfo = pickupAdditionalInfo;
    }

    public String getDropPlace() {
        return dropPlace;
    }

    public void setDropPlace(String dropPlace) {
        this.dropPlace = dropPlace;
    }

    public double getDropLat() {
        return dropLat;
    }

    public void setDropLat(double dropLat) {
        this.dropLat = dropLat;
    }

    public double getDropLong() {
        return dropLong;
    }

    public void setDropLong(double dropLong) {
        this.dropLong = dropLong;
    }

    public String getDropUnitNumber() {
        return dropUnitNumber;
    }

    public void setDropUnitNumber(String dropUnitNumber) {
        this.dropUnitNumber = dropUnitNumber;
    }

    public String getDropFloor() {
        return dropFloor;
    }

    public void setDropFloor(String dropFloor) {
        this.dropFloor = dropFloor;
    }

    public boolean isDropHasElevator() {
        return dropHasElevator;
    }

    public void setDropHasElevator(boolean dropHasElevator) {
        this.dropHasElevator = dropHasElevator;
    }

    public String getDropDistanceFromParking() {
        return dropDistanceFromParking;
    }

    public void setDropDistanceFromParking(String dropDistanceFromParking) {
        this.dropDistanceFromParking = dropDistanceFromParking;
    }

    public String getDropAdditionalInfo() {
        return dropAdditionalInfo;
    }

    public void setDropAdditionalInfo(String dropAdditionalInfo) {
        this.dropAdditionalInfo = dropAdditionalInfo;
    }

    public Integer getEstimatedNumOfTrips() {
        return estimatedNumOfTrips;
    }

    public void setEstimatedNumOfTrips(Integer estimatedNumOfTrips) {
        this.estimatedNumOfTrips = estimatedNumOfTrips;
    }

    public String getEstimatedWeight() {
        return estimatedWeight;
    }

    public void setEstimatedWeight(String estimatedWeight) {
        this.estimatedWeight = estimatedWeight;
    }

    public String getEstimatedArea() {
        return estimatedArea;
    }

    public void setEstimatedArea(String estimatedArea) {
        this.estimatedArea = estimatedArea;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}