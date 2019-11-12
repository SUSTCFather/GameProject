package com.example.wuziqi.forget;

import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;

public interface ForgetContract {
    interface ForgetView{
        void showChange(UserResponse response);

        void showVerify(UserResponse response);
    }

    interface ForgetPresenter{

        void doChange(UserRequest request);

        void doVerify(UserRequest request);

    }
}
