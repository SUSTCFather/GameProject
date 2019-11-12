package com.example.wuziqi.register;

import android.util.Log;

import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.retrofit.manager.MailDataManager;
import com.example.wuziqi.retrofit.manager.UserDataManager;
import com.example.wuziqi.retrofit.HttpHandler;

public class RegisterPresenterIml implements RegisterContract.RegisterPresenter {

    private RegisterContract.RegisterView mView;

    private UserDataManager userDataManager;

    private MailDataManager mailDataManager;

    public RegisterPresenterIml() {
        this.userDataManager = new UserDataManager(new RegisterHandler());
        this.mailDataManager = new MailDataManager(new MailHandler());
    }

    public void attachView(RegisterContract.RegisterView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    @Override
    public void doRegister(UserRequest request) {
        userDataManager.userRegister(request);
    }

    @Override
    public void doVerify(UserRequest request) {
        mailDataManager.sendCheckCode(request);
    }

    class RegisterHandler implements HttpHandler<UserResponse> {
        @Override
        public void onResultSuccess(UserResponse response) {
            if(mView != null) {
                mView.showRegister(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.toString());
        }
    }

    class MailHandler implements HttpHandler<UserResponse> {
        @Override
        public void onResultSuccess(UserResponse response) {
            if(mView != null) {
                mView.showVerify(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("MailHandler",e.toString());
        }
    }
}
