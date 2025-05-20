package com.sanofi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanofi.model.Pharmacy;
import com.sanofi.repository.PharmacyRepository;

@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyService(PharmacyRepository helloRepository) {
        this.pharmacyRepository = helloRepository;
    }

    public String testConn() {
        try {
            Optional<Pharmacy> result = this.pharmacyRepository.findById(1L);
            result.ifPresent(pharmacy -> System.out.println("inventory: " + pharmacy.getInventory()));
        } catch (Exception e) {
            System.out.println("514");
        }
        return "Greetings from Spring Boot!";
    }

}
