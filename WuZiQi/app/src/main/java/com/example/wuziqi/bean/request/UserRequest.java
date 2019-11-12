package com.example.wuziqi.bean.request;

public class UserRequest {
    private String userName;

    private String password;

    private String checkCode;

    private String mailAddress;

    public UserRequest() {

    }

    public UserRequest(String userName, String mailAddress, String checkCode, String password) {
        this.userName = userName;
        this.password = password;
        this.mailAddress = mailAddress;
        this.checkCode = checkCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
