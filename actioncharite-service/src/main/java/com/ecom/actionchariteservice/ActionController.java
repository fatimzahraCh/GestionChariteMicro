package com.ecom.actionchariteservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private final Map<Long, ActionDto> actions = Map.of(
            1L, new ActionDto(1L, "Collecte de vêtements", "Collecte pour les familles démunies", "OUVERTE"),
            2L, new ActionDto(2L, "Repas de Ramadan", "Distribution de repas", "OUVERTE"),
            3L, new ActionDto(3L, "Campagne scolaire 2025", "Fournitures scolaires", "CLOTUREE")
    );

    @GetMapping("/{id}")
    public ActionDto getActionById(@PathVariable Long id) {
        ActionDto action = actions.get(id);
        if (action == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Action introuvable");
        }
        return action;
    }
}