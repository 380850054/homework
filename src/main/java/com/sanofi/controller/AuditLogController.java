package com.sanofi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanofi.request.AuditLogRequest;
import com.sanofi.response.AuditLogResponse;
import com.sanofi.service.AuditLogService;

@RestController
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogs(AuditLogRequest request) {
        return this.auditLogService.getAuditLogs(request);
    }

}
