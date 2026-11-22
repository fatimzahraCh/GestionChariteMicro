package com.ecom.participationservice.repository;

import com.ecom.participationservice.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findParticipationByIdempotencyKey(String idempotencyKey);
}
