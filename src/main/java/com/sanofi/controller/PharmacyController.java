package com.sanofi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanofi.service.PharmacyService;


@RestController
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @GetMapping("/test-conn")
    public String index() {
        return this.pharmacyService.testConn();
    }

    @GetMapping("/")
    public String hello() {
        return "Greetings from Spring Boot!";
    }

}
