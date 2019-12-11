package com.example.wuziqi.main.fragment.home;

import com.example.wuziqi.bean.request.FriendRequest;
import com.example.wuziqi.bean.request.InviteRequest;
import com.example.wuziqi.bean.response.FriendResponse;
import com.example.wuziqi.bean.response.InviteResponse;

public class HomeContract {
    interface HomeView {
        void showFriend(FriendResponse response);

        void showFriendList(FriendResponse response);

        void showInvite(InviteResponse response);
    }

    interface HomePresenter {
        void addFriend(FriendRequest request);

        void getFriendList(FriendRequest request);

        void inviteFriend(InviteRequest request);
    }


}
