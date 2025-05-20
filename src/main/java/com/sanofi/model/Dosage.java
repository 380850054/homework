package com.sanofi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dosage")
public class Dosage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "drug_name", nullable = false, length = 100)
    private String drugName;

    @Column(name = "usage", nullable = false, length = 100)
    private String usage;

    @Column(name = "stock", nullable = false)
    private double stock;

    // Constructors
    public Dosage() {
    }

    public Dosage(String usage, String drugName, double stock) {
        this.usage = usage;
        this.drugName = drugName;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

}