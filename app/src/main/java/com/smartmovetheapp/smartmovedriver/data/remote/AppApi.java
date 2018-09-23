package com.smartmovetheapp.smartmovedriver.data.remote;

import com.smartmovetheapp.smartmovedriver.data.remote.model.Driver;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Order;
import com.smartmovetheapp.smartmovedriver.data.remote.model.TripResponse;
import com.smartmovetheapp.smartmovedriver.data.remote.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface AppApi {
    /*api calls*/
    @POST("api/Account/Login")
    Call<User> login(@Body Driver request);

    @POST("api/users")
    Call<User> signup(@Body User request);

    @GET("api/TruckOwner/GetNewOrders")
    Call<List<Order>> getTrips(@Query("truckOwnerId") Long truckOwnerId);

    /*api/TruckOwner/GetMyOrders?truckOwnerId=value
    *
    * api/TruckOwner/GetNewOrders?truckOwnerId=value
    *
    * */
}
