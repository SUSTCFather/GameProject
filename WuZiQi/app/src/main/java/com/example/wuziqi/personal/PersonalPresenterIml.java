package com.example.wuziqi.personal;


import android.util.Log;

import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.UserDataManager;

public class PersonalPresenterIml implements PersonalContract.PersonalPresenter{

    private PersonalContract.PersonalView mView;

    private UserDataManager getInfoDataManager;

    public PersonalPresenterIml() {
        this.getInfoDataManager = new UserDataManager(new PersonalHandler());
    }

    public void attachView(PersonalContract.PersonalView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    @Override
    public void getUserInfo(long userId) {
        getInfoDataManager.getPersonalInfo(userId);
    }

    class PersonalHandler implements HttpHandler<UserResponse> {
        @Override
        public void onResultSuccess(UserResponse response) {
            if(mView != null) {
                mView.showUserInfo(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.getMessage());
        }
    }



}
