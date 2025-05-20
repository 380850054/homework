package com.sanofi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanofi.model.PharmacyDrugContract;

@Repository
public interface PharmacyDrugContractRepository extends JpaRepository<PharmacyDrugContract, Long> {
    Optional<PharmacyDrugContract> findByPharmacyId(Long pharmacy_id);
}
