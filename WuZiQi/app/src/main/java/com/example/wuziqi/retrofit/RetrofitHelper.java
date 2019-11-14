package com.example.wuziqi.retrofit;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static RetrofitHelper instance = null;

    private static final String BASE_URL = "http://192.168.1.104:8080";

    private Retrofit retrofit;

    private RetrofitService myService;

    private RetrofitHelper(){
        initRetrofit();
    }

    public static RetrofitHelper getInstance(){
        if(instance == null){
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private void initRetrofit(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        myService = retrofit.create(RetrofitService.class);
    }

    public RetrofitService getServer(){
        return myService;
    }
}
