package com.example.wuziqi.forget;

import android.util.Log;

import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.register.RegisterContract;
import com.example.wuziqi.register.RegisterPresenterIml;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.MailDataManager;
import com.example.wuziqi.retrofit.manager.UserDataManager;

public class ForgetPresenterIml implements ForgetContract.ForgetPresenter {

    private ForgetContract.ForgetView mView;

    private UserDataManager userDataManager;

    private MailDataManager mailDataManager;

    public ForgetPresenterIml() {
        this.userDataManager = new UserDataManager(new ChangeHandler());
        this.mailDataManager = new MailDataManager(new MailHandler());
    }

    public void attachView(ForgetContract.ForgetView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    @Override
    public void doChange(UserRequest request) {
        userDataManager.changePassword(request);
    }

    @Override
    public void doVerify(UserRequest request) {
        mailDataManager.verifyChange(request);
    }

    class ChangeHandler implements HttpHandler<UserResponse> {
        @Override
        public void onResultSuccess(UserResponse response) {
            if(mView != null) {
                mView.showChange(response);
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
