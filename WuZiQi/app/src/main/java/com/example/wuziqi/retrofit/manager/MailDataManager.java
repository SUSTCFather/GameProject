package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;

public class MailDataManager extends BaseDataManager<UserResponse> {

    public MailDataManager(HttpHandler<UserResponse> handler) {
        super(handler);
    }

    public void sendCheckCode(UserRequest request) {
        Observable<UserResponse> observable = retrofitService.sendCheckCode(request);
        initObservable(observable);
    }

    public void verifyChange(UserRequest request) {
        Observable<UserResponse> observable = retrofitService.verifyChange(request);
        initObservable(observable);
    }


}
