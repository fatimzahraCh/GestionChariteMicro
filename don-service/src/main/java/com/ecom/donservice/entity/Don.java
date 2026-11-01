package com.ecom.donservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Don {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Références vers d'autres services : pas de relation JPA
    private Long userId;
    private Long actionId;

    private BigDecimal montant;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDon;

    @Enumerated(EnumType.STRING)
    private StatutDon statut;

    @Column(unique = true, nullable = false, length = 255)
    private String idempotencyKey;

    public Don() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getActionId() { return actionId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public Date getDateDon() { return dateDon; }
    public void setDateDon(Date dateDon) { this.dateDon = dateDon; }

    public StatutDon getStatut() { return statut; }
    public void setStatut(StatutDon statut) { this.statut = statut; }

    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
}