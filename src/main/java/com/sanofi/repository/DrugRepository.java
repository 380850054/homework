package com.sanofi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanofi.model.Drug;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    List<Drug> findAllByNameInAndPharmacyIdAndStockGreaterThanAndExpiryDateGreaterThan(List<String> names, Long pharmacyId, double stock, String expiryDate);
}
