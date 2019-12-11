package com.example.gameproject.bean.response;

import java.util.List;

public class Hall {
    private int hallId;

    //0为黑,1为白,保证只有两个
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
