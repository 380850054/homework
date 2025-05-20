package com.sanofi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanofi.model.Drug;
import com.sanofi.model.Pharmacy;
import com.sanofi.repository.DrugRepository;
import com.sanofi.repository.PharmacyRepository;
import com.sanofi.request.DrugRequest;
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
            List<Drug> drugs = new ArrayList<>();

            for (DrugRequest drugRequest: purchaseRequest.getDrugs()) {
                drugs.add(new Drug(
                    drugRequest.getDrugName(),
                    drugRequest.getManufacturer(),
                    drugRequest.getBatchNumber(),
                    drugRequest.getExpiryDate(),
                    drugRequest.getStock()
                ));
            }

            drugRepository.saveAll(drugs);

            Pharmacy pharmacy = new Pharmacy(
                purchaseRequest.getPharmacyId(),
                drugs
            );

            pharmacyRepository.save(pharmacy);
            return "add stocks successful";
        } catch (Exception e) {
            return "add stocks failed";
        }
    }
}
