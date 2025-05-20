package com.sanofi.service;

import static com.sanofi.util.util.getCurrentDateWithFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanofi.model.Dosage;
import com.sanofi.model.Drug;
import com.sanofi.model.Prescription;
import com.sanofi.repository.DrugRepository;
import com.sanofi.repository.PrescriptionRepository;

import jakarta.transaction.Transactional;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DrugRepository drugRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository, DrugRepository drugRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.drugRepository = drugRepository;
    }

    public Long createPrescription(Prescription prescription) {
        Prescription savedPrescription = this.prescriptionRepository.save(prescription);
        return savedPrescription.getId();
    }

    public Optional<Prescription> getPrescriptionById(Long prescription_id) {
        return this.prescriptionRepository.findById(prescription_id);
    }

    @Transactional
    public List<String> tryFullfillPrescriptionById(Long prescription_id, boolean persist) {
        Prescription prescription= this.prescriptionRepository.findById(prescription_id).get();
        
        List<Dosage> dosages = prescription.getDosages();

        List<String> drugNames = dosages.stream().map(dosage -> dosage.getDrugName()).distinct().collect(Collectors.toList());

        String currentDate = getCurrentDateWithFormat("yyyy-MM-dd");
        Long pharmacy_id = prescription.getPharmacy().getId();

        List<Drug> drugs = drugRepository.findAllByNameInAndPharmacyIdAndStockGreaterThanAndExpiryDateGreaterThan(
            drugNames, pharmacy_id, 0, currentDate
        );

        List <String> insufficientDrugs = new ArrayList<>();

        for (Dosage dosage: dosages) {
            double requiredStock = dosage.getStock();
            for (Drug drug: drugs) {
                if (drug.getName() == dosage.getDrugName()) {
                    double existingStock = drug.getStock();
                    if (existingStock > requiredStock) {
                        drug.setStock(existingStock - requiredStock);
                        break;
                    } else {
                        drug.setStock(0);
                        requiredStock = requiredStock - existingStock;
                    }
                }
            }
            if (requiredStock > 0) {
                insufficientDrugs.add(dosage.getDrugName());
            }
        }
        if (persist) {
            this.drugRepository.saveAll(drugs);
        }

        return insufficientDrugs;
    }

}
