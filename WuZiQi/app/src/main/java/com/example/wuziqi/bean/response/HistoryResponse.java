package com.example.wuziqi.bean.response;

import com.example.wuziqi.bean.GameRecord;

import java.util.List;

public class HistoryResponse {
    private int status;

    private String message;

    private List<GameRecord> data;

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

    public List<GameRecord> getData() {
        return data;
    }

    public void setData(List<GameRecord> data) {
        this.data = data;
    }
}
