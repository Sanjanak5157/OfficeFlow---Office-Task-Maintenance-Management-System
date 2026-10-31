package com.officeflow.controller;

import com.officeflow.entity.ProjectRepo;
import com.officeflow.service.ProjectRepoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Basic Spring Boot REST Controller for Feature 2: Projects & Repos (No DTO).
 */
@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectRepoController {

    private final ProjectRepoService projectRepoService;

    public ProjectRepoController(ProjectRepoService projectRepoService) {
        this.projectRepoService = projectRepoService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectRepo>> getProjects(@RequestParam(required = false) String category) {
        List<ProjectRepo> list = projectRepoService.getAllProjects(category);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<ProjectRepo> createProject(@RequestBody ProjectRepo project) {
        ProjectRepo saved = projectRepoService.saveProject(project);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectRepo> updateProject(@PathVariable Long id, @RequestBody ProjectRepo project) {
        project.setId(id);
        ProjectRepo updated = projectRepoService.saveProject(project);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectRepoService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}
