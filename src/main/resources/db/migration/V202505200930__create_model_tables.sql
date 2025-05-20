CREATE TABLE drug (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100),
    batch_number VARCHAR(50),
    expiry_date VARCHAR(50),
    stock DOUBLE,
);

CREATE TABLE patient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE pharmacy (
    id INT AUTO_INCREMENT PRIMARY KEY,
    drug_id INT NOT NULL,
    FOREIGN KEY (drug_id) REFERENCES drugs(id)
);

-- Optional index for better performance on frequently queried columns
-- CREATE INDEX idx_pharmacy_drug_id ON pharmacy(drug_id);

CREATE TABLE prescription (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    pharmacy_id INT,
    patient_id INT,
    dosage_id INT,
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacy(Id),
    FOREIGN KEY (patient_id) REFERENCES patient(Id),
    FOREIGN KEY (dosage_id) REFERENCES dosage(Id)
);

CREATE TABLE pharmacy_drug_contract (
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacy(Id),
    drug_name VARCHAR(100) NOT NULL
);

CREATE TABLE dosage (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    drug_name VARCHAR(100) NOT NULL,
    usage VARCHAR(100) NOT NULL
);