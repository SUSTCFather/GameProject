package com.example.gameproject.socket;

import com.alibaba.fastjson.JSON;
import com.example.gameproject.Constant;
import com.example.gameproject.bean.model.Question;
import com.example.gameproject.bean.request.EnterRequest;
import com.example.gameproject.bean.request.PointRequest;
import com.example.gameproject.bean.response.*;
import com.example.gameproject.service.QuestionService;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SocketData {
    /**
     * userId 维护的 session
     */
    private static ConcurrentHashMap<String, GameController> sessionMap = new ConcurrentHashMap<>();

    public static List<Hall> hallList = new CopyOnWriteArrayList<>();

    private static ConcurrentHashMap<Integer, GameData> gameDataMap = new ConcurrentHashMap<>();

    static {
        for(int i = 1;i <= 5;i++) {
            hallList.add(new Hall(i));
        }
    }

    public static void putUser(String userId, GameController gameController) {
        sessionMap.put(userId, gameController);
    }

    public static void removeUser(String userId) {
        sessionMap.remove(userId);
    }

    public static int getSessionSize() {
        return sessionMap.size();
    }

    public static void enter(EnterRequest request) {
        Hall real = hallList.get(request.getHallId()-1);
        if(request.getColor() == Constant.BLACK) {
            real.setBlackPlayer(request.getPlayer());
        }else {
            real.setWhitePlayer(request.getPlayer());
        }
        broadcastHallData();
    }

    public static void exit(EnterRequest request) {
        Hall real = hallList.get(request.getHallId()-1);
        if(request.getColor() == Constant.BLACK) {
            real.setBlackPlayer(null);
        }else {
            real.setWhitePlayer(null);
        }
        broadcastHallData();
    }

    public static void ready(EnterRequest request) throws IOException {
        Hall real = hallList.get(request.getHallId()-1);
        if(request.getColor() == Constant.BLACK) {
            real.setBlackPlayer(request.getPlayer());
        }else {
            real.setWhitePlayer(request.getPlayer());
        }
        broadcastHallData(real);
        initGameData(real);
    }

    public static void updatePoint(PointRequest request) throws IOException {
        int hallId = request.getUser().getHallId();
        Hall hall = hallList.get(hallId - 1);
        GameData gameData = gameDataMap.get(hallId);
        gameData.addPoint(request.getPoint(),request.getUser().getColor());
        gameData.checkWin();
        //胜利后的操作
        if(gameData.getWinner() != -1){
            hall.getBlackPlayer().setType(0);
            hall.getWhitePlayer().setType(0);
            //todo 保存状态？
           // gameDataMap.remove(hall.getHallId());
        }
        sendGameData(gameData,hall);
    }

    private static void sendGameData(GameData gameData, Hall hall) throws IOException {
        GamePlayer white = hall.getWhitePlayer();
        GamePlayer black = hall.getBlackPlayer();
        HttpResult<GameData> result = new HttpResult<>();
        result.setMessage("GameData");
        result.setData(gameData);
        String s = JSON.toJSONString(result);
        sessionMap.get(white.getUserId()).getSession().getBasicRemote().sendText(s);
        sessionMap.get(black.getUserId()).getSession().getBasicRemote().sendText(s);
    }

    /**
     * 广播gameData;
     * @param hall
     */
    private static void initGameData(Hall hall) throws IOException {
        GamePlayer white = hall.getWhitePlayer();
        GamePlayer black = hall.getBlackPlayer();
        if(white != null && black != null
                && white.getType() == Constant.READY && black.getType() == Constant.READY) {
            GameData gameData = new GameData(hall.getHallId());
            gameData.setBlackPlayer(black);
            gameData.setWhitePlayer(white);
            gameDataMap.put(hall.getHallId(),gameData);
            sendGameData(gameData,hall);
        }
    }

    /**
     * 房内广播房间信息
     * @param hall
     */
    private static void broadcastHallData(Hall hall) throws IOException {
        String data = getHallListData();
        GamePlayer white = hall.getWhitePlayer();
        GamePlayer black = hall.getBlackPlayer();
        if(white != null) {
            sessionMap.get(white.getUserId()).getSession().getBasicRemote().sendText(data);
        }
        if(black != null) {
            sessionMap.get(black.getUserId()).getSession().getBasicRemote().sendText(data);
        }
    }

    /**
     * 全局广播房间信息
     */
    private static void broadcastHallData() {
        String data = getHallListData();
        for(String key : sessionMap.keySet()) {
            try {
                sessionMap.get(key).getSession().getBasicRemote().sendText(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getHallListData() {
        HttpResult<List<Hall>> result = new HttpResult<>();
        result.setStatus(Constant.SUCCESS);
        result.setMessage("HallList");
        result.setData(hallList);
        return JSON.toJSONString(result);
    }

}
