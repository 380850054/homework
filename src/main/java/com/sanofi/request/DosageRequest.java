package com.sanofi.request;

public class DosageRequest {
    private String drugName;
    private double stock;
    private String usage;

    public DosageRequest() {
    }
    

    public DosageRequest(String drugName, double stock, String usage) {
        this.drugName = drugName;
        this.stock = stock;
        this.usage = usage;
    }


    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

   
}
