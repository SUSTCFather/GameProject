package com.example.wuziqi.bean.response;

import com.example.wuziqi.bean.request.EnterRequest;

public class EnterResponse {
    private int status;

    private String message;

    private EnterRequest data;

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

    public EnterRequest getData() {
        return data;
    }

    public void setData(EnterRequest data) {
        this.data = data;
    }
}
