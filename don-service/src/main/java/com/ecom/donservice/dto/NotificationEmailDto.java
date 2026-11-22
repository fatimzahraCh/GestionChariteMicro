package com.ecom.donservice.dto;


public record NotificationEmailDto(
        String destinataire,
        String objet,
        String contenu
) {}