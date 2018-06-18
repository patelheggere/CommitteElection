package com.patelheggere.committeelection.apiClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patelheggere.committeelection.util.AppConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

;

public class APIClient {

    private static Retrofit retrofit = null;

    public  void setClient(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        this.retrofit= retrofit;

    }

    public  Retrofit getClient() {
        return retrofit;
    }

}