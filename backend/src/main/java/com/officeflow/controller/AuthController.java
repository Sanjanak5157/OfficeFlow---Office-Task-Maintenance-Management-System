package com.officeflow.controller;

import com.officeflow.entity.User;
import com.officeflow.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic Spring Boot REST Controller for Authentication & Users (No DTO).
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = authService.login(email, password);

        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("success", true);
            response.put("token", "jwt_demo_" + user.getId() + "_" + System.currentTimeMillis());
            response.put("userId", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            response.put("department", user.getDepartment());
            response.put("designation", user.getDesignation());
            response.put("organizationEmail", user.getOrganizationEmail());
            response.put("hasOrgEmailAccess", user.getHasOrgEmailAccess());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = authService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
