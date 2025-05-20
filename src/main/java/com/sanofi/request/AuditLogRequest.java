package com.sanofi.request;

public class AuditLogRequest {

    private Long patient_id;
    private Long pharmacy_id;
    private Boolean is_success;

    public AuditLogRequest() {
    }
    
    public AuditLogRequest(Long patient_id, Long pharmacy_id, boolean is_success) {
        this.patient_id = patient_id;
        this.pharmacy_id = pharmacy_id;
        this.is_success = is_success;
    }

    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public Long getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(Long pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }

    public Boolean getIs_success() {
        return is_success;
    }

    public void setIs_success(Boolean is_success) {
        this.is_success = is_success;
    }
}
