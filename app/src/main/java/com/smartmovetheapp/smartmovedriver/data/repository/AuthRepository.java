package com.smartmovetheapp.smartmovedriver.data.repository;

import com.smartmovetheapp.smartmovedriver.data.remote.ApiClient;
import com.smartmovetheapp.smartmovedriver.data.remote.model.Driver;
import com.smartmovetheapp.smartmovedriver.data.remote.model.User;

import retrofit2.Call;

public class AuthRepository {

    private static AuthRepository repositoryInstance;

    public static AuthRepository getInstance() {
        if (repositoryInstance == null) {
            repositoryInstance = new AuthRepository();
        }

        return repositoryInstance;
    }

    public Call<User> attemptLogin(String username, String password) {
        return ApiClient.create().login(new Driver(username, password, "D"));
    }

    public Call<User> registerNewUser(User user) { return ApiClient.create().signup(user); }
}
