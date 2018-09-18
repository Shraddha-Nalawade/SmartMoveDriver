package com.smartmovetheapp.smartmovedriver.data.remote.model;

public class Place {

    private String locationName;

    private String floorLevel;

    private boolean elevator;

    private String parkingDistance;

    private String weight;

    private String area;

    private String additionalInfo;

    public Place() {
    }

    public Place(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }

    public boolean isElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    public String getParkingDistance() {
        return parkingDistance;
    }

    public void setParkingDistance(String parkingDistance) {
        this.parkingDistance = parkingDistance;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}