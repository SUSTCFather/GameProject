package com.example.wuziqi.history;

import android.util.Log;

import com.example.wuziqi.bean.response.HistoryResponse;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.HistoryDataManager;

public class HistoryPresenterIml implements HistoryContract.HistoryPresenter {

    private HistoryContract.HistoryView mView;

    private HistoryDataManager historyDataManager;

    public HistoryPresenterIml() {
        this.historyDataManager = new HistoryDataManager(new HistoryHandler());
    }

    @Override
    public void getHistory(long userId) {
        historyDataManager.getHistory(userId);
    }

    public void attachView(HistoryContract.HistoryView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }


    class HistoryHandler implements HttpHandler<HistoryResponse> {
        @Override
        public void onResultSuccess(HistoryResponse response) {
            if(mView != null) {
                mView.showHistory(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.toString());
        }
    }

}
