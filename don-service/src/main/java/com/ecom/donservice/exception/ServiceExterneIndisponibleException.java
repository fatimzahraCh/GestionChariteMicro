package com.ecom.donservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Exception levée lorsqu'un service distant (ex. user-service ou actioncharite-service) est indisponible ou que le circuit est ouvert
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceExterneIndisponibleException extends RuntimeException {

    // Constructeur avec message explicite destiné au client API
    public ServiceExterneIndisponibleException(String message) {
        super(message);
    }

    // Constructeur conservant la cause technique d'origine (ex. erreur Feign ou Resilience4j)
    public ServiceExterneIndisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}