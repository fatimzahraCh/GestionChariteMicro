package com.ecom.participationservice.controller;

import com.ecom.participationservice.dto.CreateParticipationDto;
import com.ecom.participationservice.entity.Participation;
import com.ecom.participationservice.service.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participations")
@Validated
public class ParticipationController {

    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/new")
    public ResponseEntity<Participation> createParticipation(
            @Valid @RequestBody CreateParticipationDto dto,
            @RequestHeader("idempotencyKey") String idempotencyKey) {
        Participation created = participationService.createParticipation(dto, idempotencyKey);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Participation>> getAllParticipations() {
        List<Participation> participations = participationService.getAllParticipations();
        return new ResponseEntity<>(participations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long id) {
        Participation participation = participationService.getParticipationById(id);
        return new ResponseEntity<>(participation, HttpStatus.OK);
    }
}
