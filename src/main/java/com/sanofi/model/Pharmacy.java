package com.sanofi.model;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pharmacy")
public class Pharmacy {

    @Id
    private Long id;

    @OneToMany
    @JoinColumn(name="drug_id", referencedColumnName = "id")
    private List<Drug> drugs;

    // Constructors
    public Pharmacy() {
    }

    public Pharmacy(Long id, List<Drug> drugs) {
        this.id = id;
        this.drugs = drugs;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }
}