package com.officeflow.controller;

import com.officeflow.entity.MonthlyReport;
import com.officeflow.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Basic Spring Boot REST Controller for Feature 4: Monthly Reports (No DTO).
 */
@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class MonthlyReportController {

    private final ReportService reportService;

    public MonthlyReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<MonthlyReport>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @PostMapping
    public ResponseEntity<MonthlyReport> createReport(@RequestBody MonthlyReport report) {
        MonthlyReport saved = reportService.saveReport(report);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyReport> updateReport(@PathVariable Long id, @RequestBody MonthlyReport report) {
        report.setId(id);
        MonthlyReport updated = reportService.saveReport(report);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }
}
