package com.ecom.organisationservice.repository;

import com.ecom.organisationservice.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
}
