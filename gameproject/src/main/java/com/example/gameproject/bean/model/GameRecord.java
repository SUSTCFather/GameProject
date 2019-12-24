package com.example.gameproject.bean.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class GameRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @JoinColumn(name = "winner", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private User winner;

    @JoinColumn(name = "loser", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private User loser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gameTime;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public User getLoser() {
        return loser;
    }

    public void setLoser(User loser) {
        this.loser = loser;
    }

    public Date getGameTime() {
        return gameTime;
    }

    public void setGameTime(Date gameTime) {
        this.gameTime = gameTime;
    }
}
