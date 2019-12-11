package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;


public class UserDataManager extends BaseDataManager<UserResponse>{

    public UserDataManager(HttpHandler<UserResponse> handler) {
        super(handler);
    }

    public void userRegister(UserRequest request) {
        Observable<UserResponse> observable = retrofitService.userRegister(request);
        initObservable(observable);
    }

    public void changePassword(UserRequest request) {
        Observable<UserResponse> observable = retrofitService.changePassword(request);
        initObservable(observable);
    }

    public void userLogin(UserRequest request) {
        Observable<UserResponse> observable = retrofitService.userLogin(request);
        initObservable(observable);
    }

}
