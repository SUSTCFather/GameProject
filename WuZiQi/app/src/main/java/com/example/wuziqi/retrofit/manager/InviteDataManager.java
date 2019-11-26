package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.request.InviteRequest;
import com.example.wuziqi.bean.response.InviteResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;


public class InviteDataManager extends BaseDataManager<InviteRequest, InviteResponse>{

    public InviteDataManager(HttpHandler<InviteResponse> handler) {
        super(handler);
    }

    public void inviteFriend(InviteRequest request) {
        Observable<InviteResponse> observable = retrofitService.inviteFriend(request);
        initObservable(observable);
    }


}
