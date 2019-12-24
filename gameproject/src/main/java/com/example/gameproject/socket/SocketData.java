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

    /**
     * 房间列表
     */
    public static List<Hall> hallList = new CopyOnWriteArrayList<>();

    /**
     * 游戏数据
     */
    private static ConcurrentHashMap<Integer, GameData> gameDataMap = new ConcurrentHashMap<>();

    static {
        for(int i = 1;i <= 5;i++) {
            hallList.add(new Hall(i));
        }
    }

    /**
     * 放置用户，id与session对应
     * @param userId
     * @param gameController
     */
    public static void putUser(String userId, GameController gameController) {
        sessionMap.put(userId, gameController);
    }

    /**
     * 移除用户
     * @param userId
     */
    public static void removeUser(String userId) {
        sessionMap.remove(userId);
    }

    /**
     * 获取在线用户数量
     * @return
     */
    public static int getSessionSize() {
        return sessionMap.size();
    }

    /**
     * 进入房间
     * @param request
     */
    public static synchronized void enter(EnterRequest request) {
        Hall real = hallList.get(request.getHallId()-1);
        if(request.getColor() == Constant.BLACK) {
            real.setBlackPlayer(request.getPlayer());
        }else {
            real.setWhitePlayer(request.getPlayer());
        }
        broadcastHallData();
    }

    /**
     * 退出房间
     * @param request
     */
    public static synchronized void exit(EnterRequest request) throws IOException {
        Hall real = hallList.get(request.getHallId()-1);
        if(request.getPlayer().getType() == Constant.STARTED) {
            System.out.println(request.getPlayer().getUserName()+"逃跑");
            GameData gameData = gameDataMap.get(real.getHallId());
            if(request.getColor() == Constant.BLACK) {
                gameData.setWinner(Constant.WHITE);
            }else {
                gameData.setWinner(Constant.BLACK);
            }
            real.getBlackPlayer().setType(0);
            real.getWhitePlayer().setType(0);
            gameDataMap.remove(real.getHallId());
            sessionMap.get(real.getBlackPlayer().getUserId()).setStart(false);
            sessionMap.get(real.getWhitePlayer().getUserId()).setStart(false);
            sendGameData(gameData, real);
        }

        if(request.getColor() == Constant.BLACK) {
            real.setBlackPlayer(null);
        }else {
            real.setWhitePlayer(null);
        }
        broadcastHallData();
    }

    /**
     * 准备开始
     * @param request
     * @throws IOException
     */
    public static synchronized void ready(EnterRequest request) throws IOException {
        Hall real = hallList.get(request.getHallId()-1);
        if(request.getColor() == Constant.BLACK) {
            real.setBlackPlayer(request.getPlayer());
        }else {
            real.setWhitePlayer(request.getPlayer());
        }
        broadcastHallData();
        initGameData(real);
    }

    /**
     * 更新游戏数据
     * @param request
     * @throws IOException
     */
    public static synchronized GameData updatePoint(PointRequest request) throws IOException {
        int hallId = request.getUser().getHallId();
        Hall hall = hallList.get(hallId - 1);
        GameData gameData = gameDataMap.get(hallId);
        gameData.addPoint(request.getPoint(),request.getUser().getColor());
        gameData.checkWin();
        //胜利后的操作
        if(gameData.getWinner() != -1){
            hall.getBlackPlayer().setType(0);
            hall.getWhitePlayer().setType(0);
            gameDataMap.remove(hall.getHallId());
            sessionMap.get(hall.getBlackPlayer().getUserId()).setStart(false);
            sessionMap.get(hall.getWhitePlayer().getUserId()).setStart(false);
        }
        sendGameData(gameData,hall);
        return gameData;
    }

    /**
     * 房间内游戏数据同步
     * @param gameData
     * @param hall
     * @throws IOException
     */
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
     * 初始化游戏数据;
     * @param hall
     */
    private static void initGameData(Hall hall) throws IOException {
        GamePlayer white = hall.getWhitePlayer();
        GamePlayer black = hall.getBlackPlayer();
        if(white != null && black != null
                && white.getType() == Constant.READY && black.getType() == Constant.READY) {
            black.setType(Constant.STARTED);
            white.setType(Constant.STARTED);
            GameData gameData = new GameData(hall.getHallId());
            gameData.setBlackPlayer(black);
            gameData.setWhitePlayer(white);
            gameDataMap.put(hall.getHallId(),gameData);
            sessionMap.get(white.getUserId()).setStart(true);
            sessionMap.get(black.getUserId()).setStart(true);
            sendGameData(gameData,hall);
        }
    }

    /**
     * 全局广播房间信息
     */
    private static void broadcastHallData() {
        String data = getHallListData();
        for(String key : sessionMap.keySet()) {
            try {
                GameController controller = sessionMap.get(key);
                if(!controller.isStart()) {
                    controller.getSession().getBasicRemote().sendText(data);
                }
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
