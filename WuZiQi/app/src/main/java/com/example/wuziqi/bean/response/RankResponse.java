package com.example.wuziqi.bean.response;

import com.example.wuziqi.bean.User;

import java.util.List;

public class RankResponse {
    private int status;

    private String message;

    private List<User> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
