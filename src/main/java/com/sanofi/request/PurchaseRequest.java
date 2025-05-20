package com.sanofi.request;

import java.util.List;


public class PurchaseRequest {
    
    private Long pharmacyId;
    private List<DrugRequest> drugs;

    public PurchaseRequest(Long pharmacyId, List<DrugRequest> drugs) {
        this.pharmacyId = pharmacyId;
        this.drugs = drugs;
    }

    public PurchaseRequest() {

    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public List<DrugRequest> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugRequest> drugs) {
        this.drugs = drugs;
    }

}
