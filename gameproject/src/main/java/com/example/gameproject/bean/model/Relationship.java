package com.example.gameproject.bean.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "relationship")
public class Relationship{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rs_id")
    private Long relationshipId;

    @JoinColumn(name = "rs_fromuser", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private User fromUser;


    @JoinColumn(name = "rs_touser", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private User toUser;

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
