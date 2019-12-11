package com.example.wuziqi.bean.response;

import com.example.wuziqi.bean.GameData;

public class GameDataResponse {
    private int status;

    private String message;

    private GameData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GameData getData() {
        return data;
    }

    public void setData(GameData data) {
        this.data = data;
    }
}
