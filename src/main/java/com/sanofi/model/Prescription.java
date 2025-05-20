package com.sanofi.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToMany
    @JoinColumn(name = "dosage_id", referencedColumnName = "id")
    private List<Dosage> dosages;

    // Constructors
    public Prescription() {
    }

    public Prescription(Pharmacy pharmacy, Patient patient, List<Dosage> dosages) {
        this.pharmacy = pharmacy;
        this.patient = patient;
        this.dosages = dosages;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Dosage> getDosages() {
        return dosages;
    }

    public void setDosages(List<Dosage> dosages) {
        this.dosages = dosages;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pharmacy, that.pharmacy) &&
                Objects.equals(patient, that.patient) &&
                Objects.equals(dosages, that.dosages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pharmacy, patient, dosages);
    }

    // toString
    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", pharmacy=" + pharmacy +
                ", patient=" + patient +
                ", dosages='" + dosages +
                '}';
    }
}