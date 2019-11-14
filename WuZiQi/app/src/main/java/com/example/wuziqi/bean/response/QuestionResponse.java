package com.example.wuziqi.bean.response;

import com.example.wuziqi.bean.Question;

public class QuestionResponse {
    private int status;

    private String message;

    private Question data;

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

    public Question getData() {
        return data;
    }

    public void setData(Question data) {
        this.data = data;
    }
}
