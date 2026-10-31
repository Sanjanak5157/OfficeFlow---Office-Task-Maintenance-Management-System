package com.officeflow.controller;

import com.officeflow.entity.TaskRow;
import com.officeflow.entity.TaskSheet;
import com.officeflow.service.TaskSheetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Basic Spring Boot REST Controller for Feature 7: Excel Task Sheets (No DTO).
 */
@RestController
@RequestMapping("/api/tasksheets")
@CrossOrigin(origins = "*")
public class TaskSheetController {

    private final TaskSheetService taskSheetService;

    public TaskSheetController(TaskSheetService taskSheetService) {
        this.taskSheetService = taskSheetService;
    }

    @GetMapping
    public ResponseEntity<List<TaskSheet>> getAllSheets() {
        return ResponseEntity.ok(taskSheetService.getAllSheets());
    }

    @PostMapping
    public ResponseEntity<TaskSheet> createSheet(@RequestBody TaskSheet sheet) {
        TaskSheet saved = taskSheetService.saveSheet(sheet);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/rows/{rowId}")
    public ResponseEntity<TaskRow> updateRow(@PathVariable Long rowId, @RequestBody TaskRow rowData) {
        TaskRow updated = taskSheetService.updateTaskRow(rowId, rowData);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
}
