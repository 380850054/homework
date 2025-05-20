package com.sanofi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanofi.model.Prescription;
import com.sanofi.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public Long createPrescription(Prescription prescription) {
        Prescription savedPrescription = this.prescriptionRepository.save(prescription);
        return savedPrescription.getId();
    }
}
