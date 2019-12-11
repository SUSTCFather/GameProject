package com.example.wuziqi.bean;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {
    private int hallId;

    private GamePlayer blackPlayer;

    private GamePlayer whitePlayer;

    private List<Point> blackPoints;

    private List<Point> whitePoints;

    private int winner;

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

    public List<Point> getBlackPoints() {
        return blackPoints;
    }

    public void setBlackPoints(List<Point> blackPoints) {
        this.blackPoints = blackPoints;
    }

    public List<Point> getWhitePoints() {
        return whitePoints;
    }

    public void setWhitePoints(List<Point> whitePoints) {
        this.whitePoints = whitePoints;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public boolean containsPoint(Point point) {
        return blackPoints.contains(point) || whitePoints.contains(point);
    }
}
