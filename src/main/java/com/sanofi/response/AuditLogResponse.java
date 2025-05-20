package com.sanofi.response;

public class AuditLogResponse {

    private Long patientId;
    private Long pharmacyId;
    private Boolean isSuccess;
    private String drugName;
    private double drugDispensed;
    private String failureReason;

    public AuditLogResponse() {
    }

    public AuditLogResponse(Long patientId, Long pharmacyId, Boolean isSuccess, String drugName, double drugDispensed,
            String failureReason) {
        this.patientId = patientId;
        this.pharmacyId = pharmacyId;
        this.isSuccess = isSuccess;
        this.drugName = drugName;
        this.drugDispensed = drugDispensed;
        this.failureReason = failureReason;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patient_id) {
        this.patientId = patient_id;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacy_id) {
        this.pharmacyId = pharmacy_id;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public double getDrugDispensed() {
        return drugDispensed;
    }

    public void setDrugDispensed(double drug_dispensed) {
        this.drugDispensed = drug_dispensed;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
