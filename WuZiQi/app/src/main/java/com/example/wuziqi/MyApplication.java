package com.example.wuziqi;

import android.app.Application;

import com.example.wuziqi.websocket.GameWebSocketClient;

import java.net.URI;


public class MyApplication extends Application {

    private static GameWebSocketClient client;

    public static void startClient(String userId) {
        if(client != null) {
            return;
        }
        URI uri = URI.create(Constant.SOCKET_URL + userId);
        client = new GameWebSocketClient(uri);
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static GameWebSocketClient getClient() {
        return client;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        closeConnect();
    }

    /**
     * 断开连接
     */
    public static void closeConnect() {
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
}
