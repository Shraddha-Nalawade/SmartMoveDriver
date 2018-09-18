package com.smartmovetheapp.smartmovedriver.data.remote;

import com.smartmovetheapp.smartmovedriver.data.remote.model.LoginResponse;
import com.smartmovetheapp.smartmovedriver.data.remote.model.User;

import retrofit2.Call;
import retrofit2.http.*;

public interface AppApi {
    /*api calls*/
    @POST("api/Account/Login")
    Call<LoginResponse> login(@Body User request);

    @POST("api/users")
    Call<LoginResponse> signup(@Body User request);

}
