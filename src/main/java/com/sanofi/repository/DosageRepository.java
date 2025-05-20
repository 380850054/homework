package com.sanofi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanofi.model.Dosage;

@Repository
public interface DosageRepository extends JpaRepository<Dosage, Long> {
}
