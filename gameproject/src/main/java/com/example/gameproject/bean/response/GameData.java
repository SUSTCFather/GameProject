package com.example.gameproject.bean.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {

    private Player playerA;

    private Player playerB;

    public GameData() {

    }

    public Player getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }

//    public boolean containsPoint(Point point) {
//        return whiteArray.contains(point) || blackArray.contains(point);
//    }
}
