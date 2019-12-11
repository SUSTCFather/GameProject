package com.example.wuziqi.bean.response;

import com.example.wuziqi.bean.Hall;

import java.util.List;

public class HallResponse {
    private int status;

    private String message;

    private List<Hall> data;

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

    public List<Hall> getData() {
        return data;
    }

    public void setData(List<Hall> data) {
        this.data = data;
    }
}
