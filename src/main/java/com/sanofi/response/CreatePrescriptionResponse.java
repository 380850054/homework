package com.sanofi.response;

public class CreatePrescriptionResponse {
    private boolean is_success;
    private Long prescription_id;
    private String failedReason;

    public CreatePrescriptionResponse() {
    }

    public CreatePrescriptionResponse(boolean is_success, Long prescription_id, String failedReason) {
        this.is_success = is_success;
        this.prescription_id = prescription_id;
        this.failedReason = failedReason;
    }

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Long getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(Long prescription_id) {
        this.prescription_id = prescription_id;
    }
}
