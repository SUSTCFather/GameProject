package com.example.wuziqi.main.fragment;

import android.util.Log;

import com.example.wuziqi.bean.request.FriendRequest;
import com.example.wuziqi.bean.request.InviteRequest;
import com.example.wuziqi.bean.response.FriendResponse;
import com.example.wuziqi.bean.response.InviteResponse;
import com.example.wuziqi.forget.ForgetContract;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.FriendDataManager;
import com.example.wuziqi.retrofit.manager.InviteDataManager;

public class HomePresenterIml implements HomeContract.HomePresenter {

    private HomeContract.HomeView mView;

    private FriendDataManager addFriendDataManager;

    private FriendDataManager getFriendsDataManager;

    private InviteDataManager inviteDataManager;

    public HomePresenterIml() {
        this.addFriendDataManager = new FriendDataManager(new AddFriendHandler());
        this.getFriendsDataManager = new FriendDataManager(new GetFriendsHandler());
        this.inviteDataManager = new InviteDataManager(new InviteHandler());
    }

    @Override
    public void addFriend(FriendRequest request) {
        addFriendDataManager.addFriend(request);
    }

    @Override
    public void inviteFriend(InviteRequest request) {
        inviteDataManager.inviteFriend(request);
    }

    @Override
    public void getFriendList(FriendRequest request) {
        getFriendsDataManager.getFriends(request);
    }

    public void attachView(HomeContract.HomeView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    class AddFriendHandler implements HttpHandler<FriendResponse> {
        @Override
        public void onResultSuccess(FriendResponse response) {
            if(mView != null) {
                mView.showFriend(response);
            }
        }
        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.toString());
        }
    }

    class GetFriendsHandler implements HttpHandler<FriendResponse> {
        @Override
        public void onResultSuccess(FriendResponse response) {
            if(mView != null) {
                mView.showFriendList(response);
            }
        }
        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.toString());
        }
    }

    class InviteHandler implements HttpHandler<InviteResponse> {
        @Override
        public void onResultSuccess(InviteResponse response) {
            if(mView != null) {
                mView.showInvite(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.toString());
        }
    }

}
