package com.example.wuziqi.game;

public interface GameContract {

    interface GameView {

        void showMessage(String message);

    }

    interface GamePresenter {

        void openConnect(String userId);

        void closeConnect();

    }



}
