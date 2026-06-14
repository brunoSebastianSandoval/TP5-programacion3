package com.inventory.smart.inventory.controller;

import com.inventory.smart.inventory.service.PerformanceReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final PerformanceReportService performanceService;

    public AdminController(PerformanceReportService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping("/performance-report")
    public ResponseEntity<Map<String, Object>> getReport() {
        return ResponseEntity.ok(performanceService.generarReporte());
    }
}