package com.ecom.donservice.repository;

import com.ecom.donservice.entity.Don;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonRepository extends JpaRepository<Don, Long> {

    Optional<Don> findDonByIdempotencyKey(String idempotencyKey);
}