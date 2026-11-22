package com.ecom.participationservice.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long actionId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateParticipation;

    @Enumerated(EnumType.STRING)
    private StatutParticipation statut;

    @Column(unique = true, nullable = false, length = 255)
    private String idempotencyKey;

    public Participation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Date getDateParticipation() {
        return dateParticipation;
    }

    public void setDateParticipation(Date dateParticipation) {
        this.dateParticipation = dateParticipation;
    }

    public StatutParticipation getStatut() {
        return statut;
    }

    public void setStatut(StatutParticipation statut) {
        this.statut = statut;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
