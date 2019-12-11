package com.example.wuziqi.bean;

public class GamePlayer {
    private String userId;

    private String userName;

    private int type;

    public GamePlayer(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public GamePlayer() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
