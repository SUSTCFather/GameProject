package com.example.wuziqi.game;

import android.util.Log;

import com.example.wuziqi.Constant;
import com.example.wuziqi.websocket.GameWebSocketClient;
import com.example.wuziqi.websocket.OnMessageHandler;

import java.net.URI;

public class GamePresenterIml implements GameContract.GamePresenter, OnMessageHandler {

    private GameContract.GameView mView;

    private GameWebSocketClient client;

    public void attachView(GameContract.GameView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    @Override
    public void openConnect(String userId) {
        if(client != null) {
            return;
        }
        URI uri = URI.create(Constant.SOCKET_URL + userId);
        Log.e("fuck",uri.toString());
        client = new GameWebSocketClient(uri);
        client.setMessageHandler(this);
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnect() {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    @Override
    public void onMessage(String message) {
        if(mView != null) {
            mView.showMessage(message);
        }
    }
}
