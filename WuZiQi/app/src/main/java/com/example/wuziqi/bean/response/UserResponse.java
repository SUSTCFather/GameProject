package com.example.wuziqi.bean.response;

import com.example.wuziqi.bean.User;

public class UserResponse {
    private int status;

    private String message;

    private User data;

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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
