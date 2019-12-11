package com.example.wuziqi.websocket;

import android.util.Log;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GameWebSocketClient extends WebSocketClient implements Observer<String> {

    private OnMessageHandler messageHandler;

    public GameWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String s) {
        if(messageHandler != null) {
            messageHandler.onMessage(s);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onOpen(ServerHandshake handShakeData) {
        Log.e("GameWebSocketClient", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
       Observable.just(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("GameWebSocketClient", "onClose()");
        if(messageHandler != null) {
            messageHandler.onClose(reason);
        }
    }

    @Override
    public void onError(Exception ex) {
        Log.e("GameWebSocketClient", "onError() "+ex.getMessage());
    }

    public void setMessageHandler(OnMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

}
