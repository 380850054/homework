package com.sanofi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanofi.request.PurchaseRequest;
import com.sanofi.response.PharmaciesAndContractedDrugsResponse;
import com.sanofi.service.PharmacyService;


@RestController
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @PostMapping("/pharmacies")
    public ResponseEntity<String>  purchase(PurchaseRequest purchaseRequest) {
        return this.pharmacyService.purchase(purchaseRequest);
    }

    @GetMapping("/")
    public String hello() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/pharmacies-and-contracted-drugs")
    public ResponseEntity<List<PharmaciesAndContractedDrugsResponse>> getPharmaciesAndContractedDrugs() {
        return this.pharmacyService.getAllPharmaciesWithContractedDrugs();
    }

}
