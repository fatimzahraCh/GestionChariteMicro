package com.ecom.donservice.controller;

import com.ecom.donservice.dto.CreateDonDto;
import com.ecom.donservice.entity.Don;
import com.ecom.donservice.service.DonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ecom.donservice.dto.DonDtoFull;

@RestController
@RequestMapping("/dons")
@Validated
public class DonController {

    private final DonService donService;

    public DonController(DonService donService) {
        this.donService = donService;
    }

    @PostMapping("/new")
    public ResponseEntity<Don> createDon(
            @Valid @RequestBody CreateDonDto dto,
            @NotBlank @Size(max = 255)
            @RequestHeader(name = "idempotencyKey") String idempotencyKey) {

        Don don = new Don();
        don.setUserId(dto.getUserId());
        don.setActionId(dto.getActionId());
        don.setMontant(dto.getMontant());

        Don cree = donService.createDon(don, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(cree);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Don> getDon(@PathVariable Long id) {
        return ResponseEntity.ok(donService.getDonById(id));
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<DonDtoFull> getDonDetail(@PathVariable Long id) {
        return ResponseEntity.ok(donService.getDonFullById(id));
    }
}