package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.request.EnterRequest;
import com.example.wuziqi.bean.response.EnterResponse;
import com.example.wuziqi.bean.response.HallResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;

public class EnterDataManager extends BaseDataManager<EnterResponse>{

    public EnterDataManager(HttpHandler<EnterResponse> handler) {
        super(handler);
    }

    public void enterHall(EnterRequest request) {
        Observable<EnterResponse> observable = retrofitService.enterHall(request);
        initObservable(observable);
    }

    public void exitHall(EnterRequest request) {
        Observable<EnterResponse> observable = retrofitService.exitHall(request);
        initObservable(observable);
    }

    public void ready(EnterRequest request) {
        Observable<EnterResponse> observable = retrofitService.ready(request);
        initObservable(observable);
    }



}
