package com.officeflow.controller;

import com.officeflow.entity.EmailAccessGrant;
import com.officeflow.entity.OrgEmailLog;
import com.officeflow.service.EmailAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Basic Spring Boot REST Controller for Feature 8: Organization Email Permissions & Dispatch (No DTO).
 */
@RestController
@RequestMapping("/api/emails")
@CrossOrigin(origins = "*")
public class EmailAccessController {

    private final EmailAccessService emailAccessService;

    public EmailAccessController(EmailAccessService emailAccessService) {
        this.emailAccessService = emailAccessService;
    }

    @GetMapping("/grants")
    public ResponseEntity<List<EmailAccessGrant>> getGrants() {
        return ResponseEntity.ok(emailAccessService.getEmailGrants());
    }

    @PostMapping("/grants")
    public ResponseEntity<EmailAccessGrant> grantAccess(@RequestBody EmailAccessGrant grant) {
        EmailAccessGrant saved = emailAccessService.grantEmailAccess(grant);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/grants/{grantId}/revoke")
    public ResponseEntity<Void> revokeAccess(@PathVariable Long grantId) {
        emailAccessService.revokeEmailAccess(grantId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send")
    public ResponseEntity<OrgEmailLog> sendEmail(@RequestBody OrgEmailLog emailLog) {
        OrgEmailLog sent = emailAccessService.sendEmail(emailLog);
        return ResponseEntity.ok(sent);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<OrgEmailLog>> getLogs() {
        return ResponseEntity.ok(emailAccessService.getEmailLogs());
    }
}
