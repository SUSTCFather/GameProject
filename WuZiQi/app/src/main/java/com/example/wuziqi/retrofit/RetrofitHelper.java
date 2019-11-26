package com.example.wuziqi.retrofit;

import com.example.wuziqi.Constant;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static RetrofitHelper instance = null;

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
        retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        myService = retrofit.create(RetrofitService.class);
    }

    public RetrofitService getServer(){
        return myService;
    }
}
