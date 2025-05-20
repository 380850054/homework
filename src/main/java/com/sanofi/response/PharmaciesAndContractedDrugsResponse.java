package com.sanofi.response;

import java.util.List;

public class PharmaciesAndContractedDrugsResponse {
    private Long pharmacy_id;
    
    private List<DrugResponse> drugs;
    
    
    public PharmaciesAndContractedDrugsResponse(Long pharmacy_id, List<DrugResponse> drugs) {
        this.pharmacy_id = pharmacy_id;
        this.drugs = drugs;
    }

    public PharmaciesAndContractedDrugsResponse() {
    }

    public Long getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(Long pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }
   
    public List<DrugResponse> getDrugs() {
        return drugs;
    }
    
    public void setDrugs(List<DrugResponse> drugs) {
        this.drugs = drugs;
    }


   
}
