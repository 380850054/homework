-- todo: use elastic search to record the audit logs
CREATE TABLE audit_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    FOREIGN KEY (prescription_id) REFERENCES prescription(Id),
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacy(Id),
    FOREIGN KEY (patient_id) REFERENCES patient(Id),
    FOREIGN KEY (drug_id) REFERENCES drug(Id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_success BOOLEAN,
    failue_reasons VARCHAR(200)
);