package com.ecom.actionchariteservice;

public class ActionDto {
    private Long id;
    private String titre;
    private String description;
    private String statut; // OUVERTE ou CLOTUREE

    public ActionDto() {}

    public ActionDto(Long id, String titre, String description, String statut) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.statut = statut;
    }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public String getStatut() { return statut; }
}