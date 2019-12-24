package com.example.gameproject.socket;

import com.alibaba.fastjson.JSON;

import com.example.gameproject.bean.request.PointRequest;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint("/game/{user}")
public class GameController {
    /**
     * 自己的名字
     */
    private String userId;

    /**
     * session
     */
    private Session session;

    private boolean isStart;

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String userId){
        //获取用户ID
        this.userId = userId;
        this.session = session;
        SocketData.putUser(userId,this);
        System.out.println("有新的连接，总数 "+SocketData.getSessionSize()+"userId:"+userId);
    }

    @OnClose
    public void onClose(){
        SocketData.removeUser(this.userId);
        System.out.println("连接断开，总数 "+SocketData.getSessionSize()+"userId:"+userId);
    }

    /**
     * 收到信息
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        PointRequest request = JSON.parseObject(message,PointRequest.class);
        try {
            SocketData.updatePoint(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public Session getSession() {
        return session;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isStart() {
        return isStart;
    }
}
