package com.example.wuziqi.login;

import android.util.Log;

import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.UserDataManager;

public class LoginPresenterIml implements LoginContract.LoginPresenter{

    private LoginContract.LoginView mView;

    private UserDataManager dataManager;

    public LoginPresenterIml() {
        this.dataManager = new UserDataManager(new LoginHandler());
    }

    public void attachView(LoginContract.LoginView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    @Override
    public void doLogin(UserRequest request) {
        dataManager.userLogin(request);
    }

    class LoginHandler implements HttpHandler<UserResponse> {
        @Override
        public void onResultSuccess(UserResponse response) {
            if(mView != null) {
                mView.showLogin(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.getMessage());
        }
    }

}
