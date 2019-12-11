package com.example.wuziqi.bean.response;

/**
 * @author 伍凯铭
 * @since 2019/10/17
 * 接口返回信息实体类
 */
public class HttpResult {
    private int status;

    private String message;

    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
