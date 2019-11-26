package com.example.gameproject.socket;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class SocketData {
    /**
     * userId 维护的 session
     */
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    public static void put(String userId, Session session) {
        sessionMap.put(userId,session);
    }

    public static void remove(String userId) {
        sessionMap.remove(userId);
    }

    public static int getSessionSize() {
        return sessionMap.size();
    }

    public static Session getSession(String userId) {
        return sessionMap.get(userId);
    }


}
