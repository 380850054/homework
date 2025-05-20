package com.sanofi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanofi.model.Drug;
import com.sanofi.model.Pharmacy;
import com.sanofi.repository.DrugRepository;
import com.sanofi.repository.PharmacyRepository;
import com.sanofi.request.PurchaseRequest;

@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final DrugRepository drugRepository;

    @Autowired
    public PharmacyService(PharmacyRepository pharmacyRepository, DrugRepository drugRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.drugRepository = drugRepository;
    }

    public String purchase(PurchaseRequest purchaseRequest) {
        try {
            Drug drug = new Drug(
                purchaseRequest.getDrugName(),
                purchaseRequest.getManufacturer(),
                purchaseRequest.getBatchNumber(),
                purchaseRequest.getExpiryDate()
            );

            Pharmacy pharmacy = new Pharmacy(
                purchaseRequest.getPharmacyId(),
                drug,
                purchaseRequest.getStock()
            );

            drugRepository.save(drug);
            pharmacyRepository.save(pharmacy);
            return "purchase successful";
        } catch (Exception e) {
            return "purchase failed";
        }
    }
}
