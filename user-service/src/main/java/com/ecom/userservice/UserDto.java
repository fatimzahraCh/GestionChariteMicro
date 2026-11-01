package com.ecom.userservice;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Integer etat; // 1 = actif, 0 = suspendu

    public UserDto() {}

    public UserDto(Long id, String name, String email, Integer etat) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.etat = etat;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Integer getEtat() { return etat; }
}