package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.response.RankResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;

public class RankDataManager extends BaseDataManager<RankResponse>{

    public RankDataManager(HttpHandler<RankResponse> handler) {
        super(handler);
    }

    public void getRankList() {
        Observable<RankResponse> observable = retrofitService.getRankList();
        initObservable(observable);
    }

}
