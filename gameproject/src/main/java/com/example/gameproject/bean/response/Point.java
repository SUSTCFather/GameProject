package com.example.gameproject.bean.response;

public class Point {
    private int x;

    private int y;

    public Point() {

    }

    public Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point) {
            return this.x == ((Point) obj).x && this.y == ((Point) obj).y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 10 * x + y;
    }

    @Override
    public String toString() {
        return x+" "+y;
    }
}
