package com.sanofi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanofi.model.Pharmacy;

@Repository
public interface HelloRepository extends JpaRepository<Pharmacy, Long> {
}
