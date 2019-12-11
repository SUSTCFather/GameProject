package com.example.wuziqi.bean;

import java.util.List;

public class Hall {
    private int hallId;

    private GamePlayer blackPlayer;

    private GamePlayer whitePlayer;

    public Hall() {

    }

    public Hall(int hallId) {
        this.hallId = hallId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public GamePlayer getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(GamePlayer blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public GamePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(GamePlayer whitePlayer) {
        this.whitePlayer = whitePlayer;
    }
}
