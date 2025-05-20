CREATE TABLE pharmacy_drug_contract (
    id INT PRIMARY KEY AUTO_INCREMENT,
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacy(Id),
    FOREIGN KEY (drug_id) REFERENCES drug(Id)
);