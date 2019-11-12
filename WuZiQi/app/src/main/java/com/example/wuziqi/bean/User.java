package com.example.wuziqi.bean;

/**
 * @author 伍凯铭
 * @since 2019/10/17
 * 用户信息数据库实体
 */
public class User {

    private long userId;
    private String userName;
    private String password;
    private String mailAddress;

    public User(){

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}
