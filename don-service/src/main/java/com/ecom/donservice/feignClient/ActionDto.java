package com.ecom.donservice.feignClient;

public class ActionDto {
    private Long id;
    private String titre;
    private String description;
    private String statut;

    public ActionDto() {}

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public String getStatut() { return statut; }

    public void setId(Long id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setDescription(String description) { this.description = description; }
    public void setStatut(String statut) { this.statut = statut; }
}