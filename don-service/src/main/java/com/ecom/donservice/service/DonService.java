package com.ecom.donservice.service;

import com.ecom.donservice.dto.DonDtoFull;
import com.ecom.donservice.entity.Don;
import com.ecom.donservice.entity.StatutDon;
import com.ecom.donservice.exception.RessourceIntrouvableException;
import com.ecom.donservice.feignClient.ActionDto;
import com.ecom.donservice.feignClient.ActionRequest;
import com.ecom.donservice.feignClient.UserDto;
import com.ecom.donservice.feignClient.UserRequest;
import com.ecom.donservice.repository.DonRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class DonService {

    private final DonRepository donRepository;
    private final UserRequest userRequest;
    private final ActionRequest actionRequest;

    public DonService(DonRepository donRepository,
                      UserRequest userRequest,
                      ActionRequest actionRequest) {
        this.donRepository = donRepository;
        this.userRequest = userRequest;
        this.actionRequest = actionRequest;
    }

    public Don createDon(Don don, String idempotencyKey) {

        // 1. Clé d'idempotence : avant tout appel Feign
        if (idempotencyKey == null || idempotencyKey.isBlank()
                || idempotencyKey.length() > 255) {
            throw new IllegalArgumentException("Clé d'idempotence invalide");
        }

        Optional<Don> donExistant =
                donRepository.findDonByIdempotencyKey(idempotencyKey);
        if (donExistant.isPresent()) {
            throw new IllegalArgumentException("Opération déjà traitée");
        }
        don.setIdempotencyKey(idempotencyKey);

        // 2. Utilisateur : doit exister et être actif
        UserDto user = userRequest.getUserById(don.getUserId());
        if (!Integer.valueOf(1).equals(user.getEtat())) {
            throw new IllegalArgumentException("Utilisateur suspendu");
        }

        // 3. Action : doit exister et être ouverte
        ActionDto action = actionRequest.getActionById(don.getActionId());
        if (!"OUVERTE".equals(action.getStatut())) {
            throw new IllegalArgumentException("Action clôturée");
        }

        // 4. Finaliser et sauvegarder
        don.setDateDon(new Date());
        don.setStatut(StatutDon.EN_ATTENTE);
        return donRepository.save(don);
    }

    public Don getDonById(Long id) {
        return donRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Don introuvable"));
    }

    public DonDtoFull getDonFullById(Long id) {
        // 1. Charger le don depuis la base locale
        Don don = getDonById(id);

        // 2. Copier les données locales (le montant enregistré est conservé)
        DonDtoFull dto = new DonDtoFull();
        dto.setId(don.getId());
        dto.setUserId(don.getUserId());
        dto.setActionId(don.getActionId());
        dto.setMontant(don.getMontant());
        dto.setDateDon(don.getDateDon());
        dto.setStatut(don.getStatut());
        dto.setIdempotencyKey(don.getIdempotencyKey());

        // 3. Compléter avec l'utilisateur (via Feign)
        UserDto user = userRequest.getUserById(don.getUserId());
        dto.setUserName(user.getName());
        dto.setUserEmail(user.getEmail());

        // 4. Compléter avec l'action (via Feign)
        ActionDto action = actionRequest.getActionById(don.getActionId());
        dto.setActionTitre(action.getTitre());
        dto.setActionStatut(action.getStatut());

        return dto;
    }
}