package com.example.gameproject.bean.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * @author 伍凯铭
 * @since 2019/10/17
 * 用户信息数据库实体
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String userName;
    private String password;
    private String mailAddress;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fromUser")
    private List<Relationship> followUsers;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toUser")
    private List<Relationship> followers;

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

    public List<Relationship> getFollowUsers() {
        return followUsers;
    }

    public void setFollowUsers(List<Relationship> followUsers) {
        this.followUsers = followUsers;
    }

    public List<Relationship> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Relationship> followers) {
        this.followers = followers;
    }
}
