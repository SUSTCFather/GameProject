package com.example.gameproject.bean.request;

import com.example.gameproject.bean.response.Point;

public class PointRequest {
    private EnterRequest user;

    private Point point;

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
