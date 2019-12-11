package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.request.FriendRequest;
import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.FriendResponse;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;

public class FriendDataManager extends BaseDataManager<FriendResponse>{

    public FriendDataManager(HttpHandler<FriendResponse> handler) {
        super(handler);
    }

    public void addFriend(FriendRequest request) {
        Observable<FriendResponse> observable = retrofitService.addFriend(request);
        initObservable(observable);
    }

    public void getFriends(FriendRequest request) {
        Observable<FriendResponse> observable = retrofitService.getFriends(request);
        initObservable(observable);
    }
}
