package com.ecom.donservice.service;

import com.ecom.donservice.dto.DonDtoFull;
import com.ecom.donservice.entity.Don;
import com.ecom.donservice.entity.StatutDon;
import com.ecom.donservice.exception.RessourceIntrouvableException;
import com.ecom.donservice.feignClient.ActionDto;
import com.ecom.donservice.feignClient.ResilientRequests;
import com.ecom.donservice.feignClient.UserDto;
import com.ecom.donservice.repository.DonRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class DonService {

    private final DonRepository donRepository;
    private final ResilientRequests resilientRequests;

    public DonService(DonRepository donRepository,
                      ResilientRequests resilientRequests) {
        this.donRepository = donRepository;
        this.resilientRequests = resilientRequests;
    }

    public Don createDon(Don don, String idempotencyKey) {

        // 1. Clé d'idempotence : vérifiée avant tout appel distant pour éviter tout doublon
        if (idempotencyKey == null || idempotencyKey.isBlank()
                || idempotencyKey.length() > 255) {
            throw new IllegalArgumentException("Clé d'idempotence invalide");
        }

        Optional<Don> donExistant =
                donRepository.findDonByIdempotencyKey(idempotencyKey);
        if (donExistant.isPresent()) {
            // Retourne le don déjà enregistré (ou lever IllegalArgumentException si vous préférez un 400)
            return donExistant.get();
        }
        don.setIdempotencyKey(idempotencyKey);

        // 2. Utilisateur : vérification stricte (503 si indisponible, 400 si suspendu)
        UserDto user = resilientRequests.getUserByIdCreation(don.getUserId());
        if (!Integer.valueOf(1).equals(user.getEtat())) {
            throw new IllegalArgumentException("Utilisateur suspendu");
        }

        // 3. Action : vérification stricte (503 si indisponible, 400 si clôturée)
        ActionDto action = resilientRequests.getActionByIdCreation(don.getActionId());
        if (!"OUVERTE".equals(action.getStatut())) {
            throw new IllegalArgumentException("Action clôturée");
        }

        // 4. Finaliser et sauvegarder en base locale
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

        // 3. Enrichir avec l'utilisateur via le repli tolérant (null si indisponible)
        UserDto user = resilientRequests.getUserByIdLecture(don.getUserId());
        dto.setUserName(user.getName());
        dto.setUserEmail(user.getEmail());

        // 4. Enrichir avec l'action via le repli tolérant (null si indisponible)
        ActionDto action = resilientRequests.getActionByIdLecture(don.getActionId());
        dto.setActionTitre(action.getTitre());
        dto.setActionStatut(action.getStatut());

        return dto;
    }
}