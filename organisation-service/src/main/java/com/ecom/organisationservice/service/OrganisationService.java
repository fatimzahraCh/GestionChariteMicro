package com.ecom.organisationservice.service;

import com.ecom.organisationservice.entity.Organisation;
import com.ecom.organisationservice.exception.RessourceIntrouvableException;
import com.ecom.organisationservice.repository.OrganisationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public Organisation createOrganisation(Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public Organisation getOrganisationById(Long id) {
        return organisationRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Organisation non trouvée avec l'id : " + id));
    }

    public Organisation updateOrganisation(Long id, Organisation organisationDetails) {
        Organisation organisation = getOrganisationById(id);
        organisation.setNom(organisationDetails.getNom());
        organisation.setDescription(organisationDetails.getDescription());
        organisation.setEmail(organisationDetails.getEmail());
        organisation.setTelephone(organisationDetails.getTelephone());
        organisation.setAdresse(organisationDetails.getAdresse());
        return organisationRepository.save(organisation);
    }

    public void deleteOrganisation(Long id) {
        Organisation organisation = getOrganisationById(id);
        organisationRepository.delete(organisation);
    }
}
