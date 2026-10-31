package com.officeflow.controller;

import com.officeflow.entity.StreakLog;
import com.officeflow.service.StreakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Basic Spring Boot REST Controller for Feature 3: Daily Streaks (No DTO).
 */
@RestController
@RequestMapping("/api/streaks")
@CrossOrigin(origins = "*")
public class StreakController {

    private final StreakService streakService;

    public StreakController(StreakService streakService) {
        this.streakService = streakService;
    }

    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<Map<String, Object>> getStreakSummary(@PathVariable Long userId) {
        Map<String, Object> summary = streakService.getStreakSummary(userId);
        return ResponseEntity.ok(summary);
    }

    @PostMapping
    public ResponseEntity<StreakLog> recordStreak(@RequestBody StreakLog streakLog) {
        StreakLog saved = streakService.saveStreak(streakLog);
        return ResponseEntity.ok(saved);
    }
}
