package com.sanofi.response;

public class FullfillPrescriptionResponse {
    private boolean is_success;
    private String failedReason;

    public FullfillPrescriptionResponse() {
    }

    public FullfillPrescriptionResponse(boolean is_success, String failedReason) {
        this.is_success = is_success;
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
}
