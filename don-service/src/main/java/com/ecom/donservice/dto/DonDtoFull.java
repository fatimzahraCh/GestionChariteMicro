package com.ecom.donservice.dto;

import com.ecom.donservice.entity.StatutDon;

import java.math.BigDecimal;
import java.util.Date;

public class DonDtoFull {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long actionId;
    private String actionTitre;
    private String actionStatut;
    private BigDecimal montant;
    private Date dateDon;
    private StatutDon statut;
    private String idempotencyKey;

    public DonDtoFull() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public Long getActionId() { return actionId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }
    public String getActionTitre() { return actionTitre; }
    public void setActionTitre(String actionTitre) { this.actionTitre = actionTitre; }
    public String getActionStatut() { return actionStatut; }
    public void setActionStatut(String actionStatut) { this.actionStatut = actionStatut; }
    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }
    public Date getDateDon() { return dateDon; }
    public void setDateDon(Date dateDon) { this.dateDon = dateDon; }
    public StatutDon getStatut() { return statut; }
    public void setStatut(StatutDon statut) { this.statut = statut; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
}