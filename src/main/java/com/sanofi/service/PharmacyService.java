package com.sanofi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sanofi.model.Dosage;
import com.sanofi.model.Drug;
import com.sanofi.model.Patient;
import com.sanofi.model.Pharmacy;
import com.sanofi.model.PharmacyDrugContract;
import com.sanofi.model.Prescription;
import com.sanofi.repository.DosageRepository;
import com.sanofi.repository.DrugRepository;
import com.sanofi.repository.PatientRepository;
import com.sanofi.repository.PharmacyDrugContractRepository;
import com.sanofi.repository.PharmacyRepository;
import com.sanofi.request.CreatePrescriptionRequest;
import com.sanofi.request.PurchaseRequest;
import com.sanofi.response.CreatePrescriptionResponse;
import com.sanofi.response.DrugResponse;
import com.sanofi.response.FullfillPrescriptionResponse;
import com.sanofi.response.PharmaciesAndContractedDrugsResponse;

@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final DrugRepository drugRepository;
    private final PharmacyDrugContractRepository pharmacyDrugContractRepository;
    private final PrescriptionService prescriptionService;
    private final PatientRepository patientRepository;
    private final DosageRepository dosageRepository;

    @Autowired
    public PharmacyService(
        PharmacyRepository pharmacyRepository, 
        DrugRepository drugRepository, 
        PharmacyDrugContractRepository pharmacyDrugContractRepository,
        PrescriptionService prescriptionService,
        PatientRepository patientRepository,
        DosageRepository dosageRepository) {
            this.pharmacyRepository = pharmacyRepository;
            this.drugRepository = drugRepository;
            this.pharmacyDrugContractRepository = pharmacyDrugContractRepository;
            this.prescriptionService = prescriptionService;
            this.patientRepository = patientRepository;
            this.dosageRepository = dosageRepository;
    }

    public ResponseEntity<String> purchase(PurchaseRequest purchaseRequest) {
        try {
            Optional<Pharmacy> pharmacyOptional = this.pharmacyRepository.findById(purchaseRequest.getPharmacyId());
            if (pharmacyOptional.isEmpty()) {
                return new ResponseEntity<>("Pharmacy not found for: " + purchaseRequest.getPharmacyId(), HttpStatus.NOT_FOUND);
            }

            Pharmacy pharmacy = pharmacyOptional.get();

            List<Drug> drugs = purchaseRequest.getDrugs().stream().map(drugRequest -> {
                return new Drug(
                    drugRequest.getDrugName(),
                    drugRequest.getManufacturer(),
                    drugRequest.getBatchNumber(),
                    drugRequest.getExpiryDate(),
                    drugRequest.getStock(),
                    pharmacy
                );
            }).collect(Collectors.toList());
            drugRepository.saveAll(drugs);

            return new ResponseEntity<>("Add stocks successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Add stocks failed", HttpStatus.INTERNAL_SERVER_ERROR);
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

    public ResponseEntity<CreatePrescriptionResponse> createPrescription(CreatePrescriptionRequest request) {
        Long pharmacy_id = request.getPharmacy_id();
        Optional<Pharmacy> pharmacy = this.pharmacyRepository.findById(pharmacy_id);
        if (pharmacy.isEmpty()) {
            String failedReason = "Pharmacy not found for: " + pharmacy_id;
            return new ResponseEntity<>(new CreatePrescriptionResponse(false, null, failedReason) , HttpStatus.NOT_FOUND);
        }

        Long patient_id = request.getPharmacy_id();
        Optional<Patient> patient = this.patientRepository.findById(patient_id);
        if (patient.isEmpty()) {
            String failedReason = "Patient not found for: " + patient_id;
            return new ResponseEntity<>(new CreatePrescriptionResponse(false, null, failedReason) , HttpStatus.NOT_FOUND);
        }

        List<Dosage> dosages = request.getDosages().stream().map(dosage -> new Dosage(
                dosage.getUsage(),
                dosage.getDrugName(),
                dosage.getStock()
            )).collect(Collectors.toList());

        List<Dosage> savedDosages = this.dosageRepository.saveAll(dosages);

        Prescription prescription = new Prescription(
            pharmacy.get(),
            patient.get(),
            savedDosages
        );
        Long savedPrescriptionId = prescriptionService.createPrescription(prescription);
        
        List<String> insufficientDrugs = this.prescriptionService.tryFullfillPrescriptionById(savedPrescriptionId, false);

        if (insufficientDrugs.isEmpty()) {
            return new ResponseEntity<>(new CreatePrescriptionResponse(true, savedPrescriptionId, null) , HttpStatus.OK);
        } else {
            String failedReason = "Insufficient stock for drugs: " + insufficientDrugs;
            return new ResponseEntity<>(new CreatePrescriptionResponse(false, null, failedReason) , HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<FullfillPrescriptionResponse> fullfillPrescription(Long prescription_id) {
        Optional<Prescription> prescription = this.prescriptionService.getPrescriptionById(prescription_id);
        if (prescription.isEmpty()) {
            String failedReason = "prescription not found for: " + prescription_id;
            return new ResponseEntity<>(new FullfillPrescriptionResponse(false, failedReason) , HttpStatus.NOT_FOUND);
        }

        List<String> insufficientDrugs = this.prescriptionService.tryFullfillPrescriptionById(prescription_id, true);
        if (insufficientDrugs.isEmpty()) {
            return new ResponseEntity<>(new FullfillPrescriptionResponse(true, null), HttpStatus.OK);
        } else {
            String failedReason = "Insufficient stock for drugs: " + insufficientDrugs;
            return new ResponseEntity<>(new FullfillPrescriptionResponse(false, failedReason), HttpStatus.OK);
        }
    }
}
