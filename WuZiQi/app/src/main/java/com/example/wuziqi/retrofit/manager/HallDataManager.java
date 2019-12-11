package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.response.HallResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;

public class HallDataManager extends BaseDataManager<HallResponse>{

    public HallDataManager(HttpHandler<HallResponse> handler) {
        super(handler);
    }

    public void getHall() {
        Observable<HallResponse> observable = retrofitService.getHallList();
        initObservable(observable);
    }

    public void getHall(int hallId) {
        Observable<HallResponse> observable = retrofitService.getHall(hallId);
        initObservable(observable);
    }
}
