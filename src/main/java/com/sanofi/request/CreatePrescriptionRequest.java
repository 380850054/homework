package com.sanofi.request;

import java.util.List;

public class CreatePrescriptionRequest {
    private Long pharmacy_id; 
    private Long patient_id;
    private List<DosageRequest> dosages;

    public CreatePrescriptionRequest(Long pharmacy_id, Long patient_id, List<DosageRequest> dosages) {
        this.pharmacy_id = pharmacy_id;
        this.patient_id = patient_id;
        this.dosages = dosages;
    }

    public CreatePrescriptionRequest() {
    }

    public Long getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(Long pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }

    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public List<DosageRequest> getDosages() {
        return dosages;
    }

    public void setDosages(List<DosageRequest> dosages) {
        this.dosages = dosages;
    }

    
}
