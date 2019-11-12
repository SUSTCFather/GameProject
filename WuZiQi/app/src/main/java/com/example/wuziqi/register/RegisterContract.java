package com.example.wuziqi.register;

import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;

public interface RegisterContract {

    interface RegisterView{
        void showRegister(UserResponse response);

        void showVerify(UserResponse response);
    }

    interface RegisterPresenter{

        void doRegister(UserRequest request);

        void doVerify(UserRequest request);

    }
}
