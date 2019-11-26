package com.example.wuziqi.websocket;

import android.util.Log;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class GameWebSocketClient extends WebSocketClient {

    private OnMessageHandler messageHandler;

    public GameWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handShakeData) {
        Log.e("GameWebSocketClient", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
        if(messageHandler != null) {
            messageHandler.onMessage(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("GameWebSocketClient", "onClose()");
    }

    @Override
    public void onError(Exception ex) {
        Log.e("GameWebSocketClient", "onError()");
    }

    public void setMessageHandler(OnMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

}
