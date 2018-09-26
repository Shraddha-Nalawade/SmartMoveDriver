package com.smartmovetheapp.smartmovedriver.data.remote;

import com.smartmovetheapp.smartmovedriver.data.remote.model.BidsResponse;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Driver;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Order;
import com.smartmovetheapp.smartmovedriver.data.remote.model.OrderBid;
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

    @POST("api/TruckOwner/PlaceBid")
    Call<Void> placeBid(@Body OrderBid bid);

    @POST("api/TruckOwner/StartTrip")
    Call<Void> startOrder(@Query("orderId") int orderId);

    @POST("api/TruckOwner/EndTrip")
    Call<Void> endOrder(@Query("orderId") int orderId);

    @POST("api/TruckOwner/RemoveMyBid")
    Call<Void> cancelBid(@Query("bidId") int orderId);

    @GET("api/TruckOwner/GetMyBids")
    Call<BidsResponse> getMyBids(@Query("truckOwnerId") int orderId);
}
