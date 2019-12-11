package com.example.wuziqi.bean.request;

import android.graphics.Point;

public class PointRequest {
    private EnterRequest user;

    private Point point;

    public PointRequest() {

    }

    public PointRequest(EnterRequest user, Point point) {
        this.user = user;
        this.point = point;
    }

    public EnterRequest getUser() {
        return user;
    }

    public void setUser(EnterRequest user) {
        this.user = user;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
