package com.example.wuziqi.game;

import android.util.Log;

import com.example.wuziqi.bean.request.EnterRequest;
import com.example.wuziqi.bean.request.PointRequest;
import com.example.wuziqi.bean.response.EnterResponse;
import com.example.wuziqi.bean.response.HallResponse;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.EnterDataManager;
import com.example.wuziqi.retrofit.manager.HallDataManager;

public class GamePresenterIml implements GameContract.GamePresenter{

    private GameContract.GameView mView;

    private EnterDataManager exitDataManager;

    private HallDataManager hallDataManager;

    private EnterDataManager readyDataManager;

    private EnterDataManager putChessDataManager;

    public GamePresenterIml() {
        this.exitDataManager = new EnterDataManager(new ExitHandler());
        this.hallDataManager = new HallDataManager(new HallHandler());
        this.readyDataManager = new EnterDataManager(new ReadyHandler());
        this.putChessDataManager = new EnterDataManager(new PutChessHandler());
    }

    @Override
    public void doExit(EnterRequest request) {
        exitDataManager.exitHall(request);
    }

    @Override
    public void getHallInfo(int hallId) {
        hallDataManager.getHall(hallId);
    }

    @Override
    public void doReady(EnterRequest request) {
        readyDataManager.ready(request);
    }

    @Override
    public void putChess(PointRequest request) {
        putChessDataManager.putChess(request);
    }

    public void attachView(GameContract.GameView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    class ExitHandler implements HttpHandler<EnterResponse> {
        @Override
        public void onResultSuccess(EnterResponse response) {
            if(mView != null) {
                mView.showExit(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("ExitHandler",e.toString());
        }
    }

    class HallHandler implements HttpHandler<HallResponse> {
        @Override
        public void onResultSuccess(HallResponse response) {
            if(mView != null) {
                mView.showHallInfo(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {

        }
    }

    class ReadyHandler implements HttpHandler<EnterResponse> {
        @Override
        public void onResultSuccess(EnterResponse response) {
            if(mView != null) {
                mView.showReady(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {

        }
    }

    class PutChessHandler implements HttpHandler<EnterResponse> {
        @Override
        public void onResultSuccess(EnterResponse response) {
            if(mView != null) {
                mView.putChessSuccess(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            if(mView != null) {
                mView.putChessError(e.getMessage());
            }
        }
    }

}
