package com.smartmovetheapp.smartmovedriver.data.remote;

import android.util.Log;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static AppApi create() { return create(HttpUrl.parse(BASE_URL)); }

    private static final String BASE_URL = "http://d2244b62.ngrok.io/";

    private static AppApi create(HttpUrl httpUrl) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor(
                message -> Log.d("==API==", message));
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();
        return new Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AppApi.class);
    }
}