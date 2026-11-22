package com.ecom.participationservice.service;

import com.ecom.participationservice.dto.CreateParticipationDto;
import com.ecom.participationservice.entity.Participation;
import com.ecom.participationservice.entity.StatutParticipation;
import com.ecom.participationservice.exception.RessourceIntrouvableException;
import com.ecom.participationservice.feignClient.ActionDto;
import com.ecom.participationservice.feignClient.ResilientRequests;
import com.ecom.participationservice.feignClient.UserDto;
import com.ecom.participationservice.repository.ParticipationRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final ResilientRequests resilientRequests;

    public ParticipationService(ParticipationRepository participationRepository, ResilientRequests resilientRequests) {
        this.participationRepository = participationRepository;
        this.resilientRequests = resilientRequests;
    }

    public Participation createParticipation(CreateParticipationDto dto, String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("La clé d'idempotence est obligatoire");
        }

        // Vérifier l'idempotence avant tout appel distant
        Participation existing = participationRepository.findParticipationByIdempotencyKey(idempotencyKey).orElse(null);
        if (existing != null) {
            return existing;
        }

        // Valider l'utilisateur via Feign (strict — 503 si indisponible)
        UserDto user;
        try {
            user = resilientRequests.getUserByIdCreation(dto.getUserId());
        } catch (FeignException.NotFound e) {
            throw new RessourceIntrouvableException("Utilisateur non trouvé avec l'id : " + dto.getUserId());
        }
        if (user.getEtat() != null && user.getEtat() == 0) {
            throw new IllegalArgumentException("Utilisateur suspendu");
        }

        // Valider l'action via Feign (strict — 503 si indisponible)
        ActionDto action;
        try {
            action = resilientRequests.getActionByIdCreation(dto.getActionId());
        } catch (FeignException.NotFound e) {
            throw new RessourceIntrouvableException("Action non trouvée avec l'id : " + dto.getActionId());
        }
        if (action.getStatut() != null && !"OUVERTE".equals(action.getStatut())) {
            throw new IllegalArgumentException("Action clôturée");
        }

        // Sauvegarder la participation
        Participation participation = new Participation();
        participation.setUserId(dto.getUserId());
        participation.setActionId(dto.getActionId());
        participation.setDateParticipation(new Date());
        participation.setStatut(StatutParticipation.EN_ATTENTE);
        participation.setIdempotencyKey(idempotencyKey);

        return participationRepository.save(participation);
    }

    public Participation getParticipationById(Long id) {
        return participationRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Participation non trouvée avec l'id : " + id));
    }

    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }
}
