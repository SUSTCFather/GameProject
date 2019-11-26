package com.example.wuziqi.bean;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class GameData {

    private int color;

    private List<Point> whiteArray;

    private List<Point> blackArray;

    public GameData() {
        whiteArray = new ArrayList<>();
        blackArray = new ArrayList<>();
    }

    public List<Point> getWhiteArray() {
        return whiteArray;
    }

    public void setWhiteArray(List<Point> whiteArray) {
        this.whiteArray = whiteArray;
    }

    public List<Point> getBlackArray() {
        return blackArray;
    }

    public void setBlackArray(List<Point> blackArray) {
        this.blackArray = blackArray;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean containsPoint(Point point) {
        return whiteArray.contains(point) || blackArray.contains(point);
    }
}
