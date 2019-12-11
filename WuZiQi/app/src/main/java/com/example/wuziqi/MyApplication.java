package com.example.wuziqi;

import android.app.Application;
import android.util.Log;

import com.example.wuziqi.websocket.GameWebSocketClient;
import com.example.wuziqi.websocket.OnMessageHandler;

import java.net.URI;


public class MyApplication extends Application {

    private static GameWebSocketClient client;

    public static void initClient(String userId) {
        if(client != null) {
            openConnect();
            return;
        }
        URI uri = URI.create(Constant.SOCKET_URL + userId);
        client = new GameWebSocketClient(uri);
        client.connect();
    }

    public static void openConnect() {
        if(client.isClosed()) {
            client.reconnect();
        }
    }

    public static boolean sendMessage(String message) {
        if(client.isOpen()) {
            client.send(message);
            return true;
        }
        return false;
    }

    public static void setMessageHandler(OnMessageHandler handler) {
        client.setMessageHandler(handler);
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
