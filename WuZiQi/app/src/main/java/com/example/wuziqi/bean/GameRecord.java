package com.example.wuziqi.bean;

import com.example.wuziqi.util.DateUtil;

public class GameRecord {

    private Long gameId;

    private User winner;

    private User loser;

    private String gameTime;

    private int type;

    private String weekday;

    public GameRecord() {

    }

    public GameRecord(String gameTime, int type) {
        this.gameTime = gameTime;
        this.type = type;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public User getLoser() {
        return loser;
    }

    public void setLoser(User loser) {
        this.loser = loser;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getDate() {
        return gameTime.substring(0,10);
    }

    public String getTime() {
        return gameTime.substring(11,16);
    }

    public String getWeek() {
        if(weekday == null) {
            weekday = DateUtil.dateToWeek(getDate());
        }
        return weekday;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
