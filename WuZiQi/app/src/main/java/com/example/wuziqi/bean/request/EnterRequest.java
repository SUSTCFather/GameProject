package com.example.wuziqi.bean.request;

import com.example.wuziqi.bean.GamePlayer;

public class EnterRequest {
    private int hallId;

    private int color;

    private GamePlayer player;

    public EnterRequest(int hallId, int color, GamePlayer player) {
        this.hallId = hallId;
        this.color = color;
        this.player = player;
    }

    public EnterRequest() {

    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public void setPlayer(GamePlayer player) {
        this.player = player;
    }
}
