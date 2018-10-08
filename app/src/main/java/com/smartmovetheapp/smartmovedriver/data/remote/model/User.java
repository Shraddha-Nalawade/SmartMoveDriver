package com.smartmovetheapp.smartmovedriver.data.remote.model;

public class User {
    private String email;

    private Long truckOwnerId;

    private String firstName;

    private String lastName;

    private String phone;

    private String address1;

    private String address2;

    private String zipCode;

    private String city;

    private String state;

    private String profilePictureURL;

    private int truckTypeId;

    private String licensePlate;

    private String truckColor;

    private String truckMake;

    private String truckModel;

    private int truckYear;

    private String driverLicenseNumber;

    private String vehicleRegNumber;

    private String driverInsurancePolicy;

    private double averageRating;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTruckOwnerId() {
        return truckOwnerId;
    }

    public void setTruckOwnerId(Long truckOwnerId) {
        this.truckOwnerId = truckOwnerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public int getTruckTypeId() {
        return truckTypeId;
    }

    public void setTruckTypeId(int truckTypeId) {
        this.truckTypeId = truckTypeId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getTruckColor() {
        return truckColor;
    }

    public void setTruckColor(String truckColor) {
        this.truckColor = truckColor;
    }

    public String getTruckMake() {
        return truckMake;
    }

    public void setTruckMake(String truckMake) {
        this.truckMake = truckMake;
    }

    public String getTruckModel() {
        return truckModel;
    }

    public void setTruckModel(String truckModel) {
        this.truckModel = truckModel;
    }

    public int getTruckYear() {
        return truckYear;
    }

    public void setTruckYear(int truckYear) {
        this.truckYear = truckYear;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public String getDriverInsurancePolicy() {
        return driverInsurancePolicy;
    }

    public void setDriverInsurancePolicy(String driverInsurancePolicy) {
        this.driverInsurancePolicy = driverInsurancePolicy;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}