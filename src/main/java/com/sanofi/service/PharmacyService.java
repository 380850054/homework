package com.sanofi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sanofi.model.Drug;
import com.sanofi.model.Pharmacy;
import com.sanofi.model.PharmacyDrugContract;
import com.sanofi.repository.DrugRepository;
import com.sanofi.repository.PharmacyDrugContractRepository;
import com.sanofi.repository.PharmacyRepository;
import com.sanofi.request.DrugRequest;
import com.sanofi.request.PurchaseRequest;
import com.sanofi.response.DrugResponse;
import com.sanofi.response.PharmaciesAndContractedDrugsResponse;

@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final DrugRepository drugRepository;
    private final PharmacyDrugContractRepository pharmacyDrugContractRepository;

    @Autowired
    public PharmacyService(
        PharmacyRepository pharmacyRepository, 
        DrugRepository drugRepository, 
        PharmacyDrugContractRepository pharmacyDrugContractRepository) {
            this.pharmacyRepository = pharmacyRepository;
            this.drugRepository = drugRepository;
            this.pharmacyDrugContractRepository = pharmacyDrugContractRepository;
    }

    public ResponseEntity<String> purchase(PurchaseRequest purchaseRequest) {
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
            return new ResponseEntity<>("add stocks successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("add stocks failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<PharmaciesAndContractedDrugsResponse>> getAllPharmaciesWithContractedDrugs() {
        try {
            List<PharmacyDrugContract> result = this.pharmacyDrugContractRepository.findAll();

            List<PharmaciesAndContractedDrugsResponse> response = result.stream().map(
                pharmacyDrugContract -> new PharmaciesAndContractedDrugsResponse(
                    pharmacyDrugContract.getPharmacy().getId(),
                    pharmacyDrugContract.getDrugs().stream().map(
                        drug -> new DrugResponse(
                            drug.getName(),
                            drug.getManufacturer(),
                            drug.getBatchNumber(),
                            drug.getExpiryDate(),
                            drug.getStock()
                    )).collect(Collectors.toList()))
            ).collect(Collectors.toList());

            return new ResponseEntity<>(response , HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
