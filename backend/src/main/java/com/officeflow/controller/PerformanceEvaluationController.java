package com.officeflow.controller;

import com.officeflow.entity.PerformanceEvaluation;
import com.officeflow.service.PerformanceEvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Basic Spring Boot REST Controller for Feature 6: Performance Evaluation (No DTO).
 */
@RestController
@RequestMapping("/api/performance")
@CrossOrigin(origins = "*")
public class PerformanceEvaluationController {

    private final PerformanceEvaluationService evaluationService;

    public PerformanceEvaluationController(PerformanceEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping
    public ResponseEntity<List<PerformanceEvaluation>> getAllEvaluations() {
        return ResponseEntity.ok(evaluationService.getAllEvaluations());
    }

    @PostMapping
    public ResponseEntity<PerformanceEvaluation> saveEvaluation(@RequestBody PerformanceEvaluation evaluation) {
        PerformanceEvaluation saved = evaluationService.saveEvaluation(evaluation);
        return ResponseEntity.ok(saved);
    }
}
