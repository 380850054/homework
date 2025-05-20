package com.sanofi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanofi.request.PurchaseRequest;
import com.sanofi.service.PharmacyService;


@RestController
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @PostMapping("/pharmacy/inventories")
    public String purchase(PurchaseRequest purchaseRequest) {
        return this.pharmacyService.purchase(purchaseRequest);
    }

    @GetMapping("/")
    public String hello() {
        return "Greetings from Spring Boot!";
    }

}
