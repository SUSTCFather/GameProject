package com.example.wuziqi.main.fragment.hall;

import android.util.Log;

import com.example.wuziqi.bean.request.EnterRequest;
import com.example.wuziqi.bean.response.EnterResponse;
import com.example.wuziqi.bean.response.HallResponse;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.EnterDataManager;
import com.example.wuziqi.retrofit.manager.HallDataManager;

public class HallPresenterIml implements HallContract.HallPresenter {

    private HallContract.HallView mView;

    private HallDataManager hallDataManager;

    private EnterDataManager enterDataManager;

    public HallPresenterIml() {
        this.hallDataManager = new HallDataManager(new HallHandler());
        this.enterDataManager = new EnterDataManager(new EnterHandler());
    }

    @Override
    public void getHall() {
        hallDataManager.getHall();
    }

    @Override
    public void enterHall(EnterRequest request) {
        enterDataManager.enterHall(request);
    }

    class HallHandler implements HttpHandler<HallResponse> {
        @Override
        public void onResultSuccess(HallResponse response) {
            if(mView != null) {
                mView.showHall(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.toString());
        }
    }

    class EnterHandler implements HttpHandler<EnterResponse> {
        @Override
        public void onResultSuccess(EnterResponse response) {
            if(mView != null) {
                mView.showEnter(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("EnterHandler",e.toString());
        }
    }


    public void attachView(HallContract.HallView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }
}
