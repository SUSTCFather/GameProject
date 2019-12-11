package com.example.wuziqi.websocket;

public interface OnMessageHandler {

    void onMessage(String message);

    void onClose(String reason);

}
