package com.example.wuziqi.game;

import com.example.wuziqi.bean.request.EnterRequest;
import com.example.wuziqi.bean.response.EnterResponse;
import com.example.wuziqi.bean.response.HallResponse;

public interface GameContract {

    interface GameView {
       void showExit(EnterResponse response);

       void showHallInfo(HallResponse response);

       void showReady(EnterResponse response);
    }

    interface GamePresenter {
       void doExit(EnterRequest request);

       void getHallInfo(int hallId);

       void doReady(EnterRequest request);
    }

}
