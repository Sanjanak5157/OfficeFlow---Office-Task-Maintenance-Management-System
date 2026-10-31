package com.officeflow.controller;

import com.officeflow.entity.IdeaProposal;
import com.officeflow.service.IdeaProposalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Basic Spring Boot REST Controller for Feature 5: Idea Proposals (No DTO).
 */
@RestController
@RequestMapping("/api/ideas")
@CrossOrigin(origins = "*")
public class IdeaProposalController {

    private final IdeaProposalService ideaProposalService;

    public IdeaProposalController(IdeaProposalService ideaProposalService) {
        this.ideaProposalService = ideaProposalService;
    }

    @GetMapping
    public ResponseEntity<List<IdeaProposal>> getAllIdeas(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(ideaProposalService.getAllIdeas(search));
    }

    @PostMapping
    public ResponseEntity<IdeaProposal> createIdea(@RequestBody IdeaProposal idea) {
        IdeaProposal saved = ideaProposalService.saveIdea(idea);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}/review")
    public ResponseEntity<IdeaProposal> reviewIdea(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        String feedback = payload.get("leadFeedback");
        IdeaProposal reviewed = ideaProposalService.reviewIdea(id, status, feedback);
        return ResponseEntity.ok(reviewed);
    }
}
