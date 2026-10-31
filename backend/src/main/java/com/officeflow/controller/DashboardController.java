package com.officeflow.controller;

import com.officeflow.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Basic Spring Boot REST Controller for Telemetry & Dashboard Stats (No DTO).
 */
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(@RequestParam(required = false, defaultValue = "1") Long userId) {
        return ResponseEntity.ok(dashboardService.getDashboardStats(userId));
    }
}
