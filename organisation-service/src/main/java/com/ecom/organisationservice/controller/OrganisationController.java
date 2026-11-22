package com.ecom.organisationservice.controller;

import com.ecom.organisationservice.entity.Organisation;
import com.ecom.organisationservice.service.OrganisationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organisations")
@Validated
public class OrganisationController {

    private final OrganisationService organisationService;

    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @PostMapping("/new")
    public ResponseEntity<Organisation> createOrganisation(@Valid @RequestBody Organisation organisation) {
        Organisation created = organisationService.createOrganisation(organisation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Organisation>> getAllOrganisations() {
        List<Organisation> organisations = organisationService.getAllOrganisations();
        return new ResponseEntity<>(organisations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organisation> getOrganisationById(@PathVariable Long id) {
        Organisation organisation = organisationService.getOrganisationById(id);
        return new ResponseEntity<>(organisation, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organisation> updateOrganisation(@PathVariable Long id, @Valid @RequestBody Organisation organisation) {
        Organisation updated = organisationService.updateOrganisation(id, organisation);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganisation(@PathVariable Long id) {
        organisationService.deleteOrganisation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
