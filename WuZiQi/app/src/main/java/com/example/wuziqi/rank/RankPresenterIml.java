package com.example.wuziqi.rank;

import android.util.Log;

import com.example.wuziqi.bean.response.RankResponse;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.RankDataManager;

public class RankPresenterIml implements RankContract.RankPresenter {

    private RankContract.RankView mView;

    private RankDataManager rankDataManager;

    public RankPresenterIml() {
        this.rankDataManager = new RankDataManager(new RankHandler());
    }

    @Override
    public void getRankList() {
        rankDataManager.getRankList();
    }

    public void attachView(RankContract.RankView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    class RankHandler implements HttpHandler<RankResponse> {
        @Override
        public void onResultSuccess(RankResponse response) {
            if(mView != null) {
                mView.showRankList(response);
            }
        }
        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.toString());
        }
    }

}
