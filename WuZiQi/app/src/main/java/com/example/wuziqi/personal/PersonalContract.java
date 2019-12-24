package com.example.wuziqi.personal;

import com.example.wuziqi.bean.response.UserResponse;

public interface PersonalContract {

    interface PersonalView{
        void showUserInfo(UserResponse response);

    }

    interface PersonalPresenter{

        void getUserInfo(long userId);
    }
}
