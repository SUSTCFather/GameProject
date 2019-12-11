package com.example.gameproject.bean.request;

import com.example.gameproject.bean.response.GamePlayer;

public class EnterRequest {
    private int hallId;

    private int color;

    private GamePlayer player;

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
