package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.response.HistoryResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;

public class HistoryDataManager extends BaseDataManager<HistoryResponse>{

    public HistoryDataManager(HttpHandler<HistoryResponse> handler) {
        super(handler);
    }

    public void getHistory(long userId) {
        Observable<HistoryResponse> observable = retrofitService.getHistory(userId);
        initObservable(observable);
    }
}
