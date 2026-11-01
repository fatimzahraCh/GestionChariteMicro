package com.ecom.donservice.feignClient;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Integer etat; // 1 = actif, 0 = suspendu

    public UserDto() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Integer getEtat() { return etat; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setEtat(Integer etat) { this.etat = etat; }
}