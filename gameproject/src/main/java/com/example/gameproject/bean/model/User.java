package com.example.gameproject.bean.model;

import com.example.gameproject.Constant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    private String password;
    private String mailAddress;
    private int winNum;
    private int loseNum;
    private int score;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "winner")
    private List<GameRecord> winGames;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loser")
    private List<GameRecord> loseGames;

    public User() {

    }

    public User(long id) {
        this.userId = id;
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

    public List<GameRecord> getWinGames() {
        return winGames;
    }

    public void setWinGames(List<GameRecord> winGames) {
        this.winGames = winGames;
    }

    public List<GameRecord> getLoseGames() {
        return loseGames;
    }

    public void setLoseGames(List<GameRecord> loseGames) {
        this.loseGames = loseGames;
    }

    public int getWinNum() {
        return winNum;
    }

    public void setWinNum(int winNum) {
        this.winNum = winNum;
    }

    public int getLoseNum() {
        return loseNum;
    }

    public void setLoseNum(int loseNum) {
        this.loseNum = loseNum;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void lostGame() {
        this.loseNum++;
        this.score += Constant.LOSE_POINT;
    }

    public void winGame() {
        this.winNum++;
        this.score += Constant.WIN_POINT;
    }
}
