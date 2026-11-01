package com.ecom.donservice.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Refus métier : clé invalide, doublon, utilisateur suspendu, action clôturée
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> refus(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Ressource introuvable côté service distant
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> notFound(FeignException.NotFound ex) {
        return ResponseEntity.status(404).body("Ressource externe introuvable");
    }

    // Autres erreurs Feign
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> feign(FeignException ex) {
        if (ex.status() == -1) {
            return ResponseEntity.status(503).body("Échec de communication avec un service");
        }
        if (ex.status() == 503) {
            return ResponseEntity.status(503).body("Service ou instance indisponible");
        }
        return ResponseEntity.status(502)
                .body("Erreur HTTP du service distant : " + ex.status());
    }
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<String> validation(jakarta.validation.ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body("Paramètre invalide : " + ex.getMessage());
    }
    @ExceptionHandler(RessourceIntrouvableException.class)
    public ResponseEntity<String> introuvable(RessourceIntrouvableException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}