package com.sanofi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sanofi.model.AuditLog;
import com.sanofi.repository.AuditLogRepository;
import com.sanofi.request.AuditLogRequest;
import com.sanofi.response.AuditLogResponse;

import jakarta.persistence.criteria.Predicate;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
            this.auditLogRepository = auditLogRepository;
    }

    public List<AuditLog> saveAll(List<AuditLog> auditLogs) {
        return this.auditLogRepository.saveAll(auditLogs);
    }

    public ResponseEntity<List<AuditLogResponse>> getAuditLogs(AuditLogRequest request) {
        Specification<AuditLog> querySpec = this.getQuerySpec(request);
        List<AuditLog> result = this.auditLogRepository.findAll(querySpec);

        List<AuditLogResponse> response = result.stream().map(auditLog -> {
            return new AuditLogResponse(
                auditLog.getPatient().getId(),
                auditLog.getPharmacy().getId(),
                auditLog.getIsSuccess(),
                auditLog.getDrugName(),
                auditLog.getDrugDispensed(),
                auditLog.getFailureReasons()
            );
        }).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Specification<AuditLog> getQuerySpec(AuditLogRequest request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getPharmacy_id() != null) {
                predicates.add(criteriaBuilder.equal(root.get("pharmacy_id"), request.getPharmacy_id()));
            }

            if (request.getPatient_id() != null) {
                predicates.add(criteriaBuilder.equal(root.get("patient_id"), request.getPatient_id()));
            }

            if (request.getIs_success() != null) {
                predicates.add(criteriaBuilder.equal(root.get("is_success"), request.getIs_success()));
            }
            
            return criteriaBuilder.and((Predicate[]) predicates.toArray());
        };
    }
}
