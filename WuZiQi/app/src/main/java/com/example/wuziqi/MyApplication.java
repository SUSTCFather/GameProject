package com.example.wuziqi;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wuziqi.util.Constant;
import com.example.wuziqi.websocket.GameWebSocketClient;
import com.example.wuziqi.websocket.OnMessageHandler;

import java.net.URI;


public class MyApplication extends Application {

    private static GameWebSocketClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                ActivityManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                ActivityManager.getInstance().removeActivity(activity);
            }
        });

    }

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
