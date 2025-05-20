package com.sanofi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.sanofi.request.DrugRequest;
import com.sanofi.request.PurchaseRequest;
import com.sanofi.response.CreatePrescriptionResponse;
import com.sanofi.response.DrugResponse;
import com.sanofi.response.PharmaciesAndContractedDrugsResponse;
import static com.sanofi.util.util.getCurrentDateWithFormat;

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
            List<Drug> drugs = new ArrayList<>();

            Pharmacy pharmacy = new Pharmacy(
                purchaseRequest.getPharmacyId(),
                drugs
            );

            pharmacyRepository.save(pharmacy);

            for (DrugRequest drugRequest: purchaseRequest.getDrugs()) {
                drugs.add(new Drug(
                    drugRequest.getDrugName(),
                    drugRequest.getManufacturer(),
                    drugRequest.getBatchNumber(),
                    drugRequest.getExpiryDate(),
                    drugRequest.getStock(),
                    pharmacy
                ));
            }

            drugRepository.saveAll(drugs);

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

        List<String> drugNames = request.getDosages().stream().map(dosage -> dosage.getDrugName()).distinct().collect(Collectors.toList());

        String currentDate = getCurrentDateWithFormat("yyyy-MM-dd");

        List<Drug> drugs = drugRepository.findAllByNameInAndPharmacyIdAndStockGreaterThanAndExpiryDateGreaterThan(
            drugNames, pharmacy_id, 0, currentDate
            );
        
        Map<String, Double> drugStock = new HashMap<>();
        drugs.stream().forEach(drug -> {
            String drugName = drug.getName();
            Double stock = drugStock.getOrDefault(drugName, 0D);
            drugStock.put(drugName, stock + drug.getStock());
        });

        Map<String, Double> dosageRequiredStock = new HashMap<>();
        request.getDosages().stream().forEach(dosage -> {
            String drugName = dosage.getDrugName();
            Double requiredStock = dosageRequiredStock.getOrDefault(drugName, 0D);
            dosageRequiredStock.put(drugName, requiredStock + dosage.getStock());
        });

        List <String> insufficientDrugs = new ArrayList<>();

        dosageRequiredStock.entrySet().stream().peek(entry -> {
            double stock = drugStock.getOrDefault(entry.getKey(), 0D);
            double requiredStock = entry.getValue();
            String drugName = entry.getKey();
            if(stock < requiredStock) {
                insufficientDrugs.add(drugName);
            }
        });

        if (insufficientDrugs.isEmpty()) {
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
            return new ResponseEntity<>(new CreatePrescriptionResponse(true, savedPrescriptionId, null) , HttpStatus.OK);
        } else {
            String failedReason = "Insufficient stock for drugs: " + insufficientDrugs;
            return new ResponseEntity<>(new CreatePrescriptionResponse(false, null, failedReason) , HttpStatus.BAD_REQUEST);
        }
    }
}
