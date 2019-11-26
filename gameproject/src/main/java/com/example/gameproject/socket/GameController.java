package com.example.gameproject.socket;

import com.alibaba.fastjson.JSON;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Hashtable;

@Component
@ServerEndpoint("/game/{user}")
public class GameController {
    /**
     * 自己的名字
     */
    private String userId;

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String userId){
        //获取用户ID
        this.userId = userId;
        SocketData.put(userId,session);
        System.out.println("有新的连接，总数 "+SocketData.getSessionSize()+"userId:"+userId);
    }

    @OnClose
    public void onClose(){
        SocketData.remove(this.userId);
        System.out.println("连接断开，总数 "+SocketData.getSessionSize()+"userId:"+userId);
    }

    /**
     * 收到信息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println(message);
        send("fuck");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    private void send(String message){

    }

}
