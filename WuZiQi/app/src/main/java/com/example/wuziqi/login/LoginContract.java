package com.example.wuziqi.login;

import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;

public interface LoginContract {

    interface LoginView{
        void showLogin(UserResponse loginResponse);
    }

    interface LoginPresenter{

        void doLogin(UserRequest request);

    }
}
