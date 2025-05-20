package com.sanofi.request;

public class PurchaseRequest {

    private Long pharmacyId;
    private String drugName;
    private String manufacturer;
    private String batchNumber;
    private String expiryDate;
    private Integer stock;

    public PurchaseRequest(Long pharmacyId, String drugName, String manufacturer, String batchNumber, String expiryDate, Integer stock) {
        this.pharmacyId = pharmacyId;
        this.drugName = drugName;
        this.manufacturer = manufacturer;
        this.batchNumber = batchNumber;
        this.expiryDate = expiryDate;
        this.stock = stock;
    }

    public PurchaseRequest() {

    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
