package com.officeflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================================
 * OfficeFlow - Office Task & Maintenance Management System
 * Main Spring Boot Application Entry Point
 * 
 * Target JDK: Java 17 (LTS)
 * Database: Microsoft SQL Server (SSMS)
 * Framework: Spring Boot 3.2.x
 * IDE: IntelliJ IDEA
 * ============================================================================
 */
@SpringBootApplication
public class OfficeFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeFlowApplication.class, args);
        System.out.println("=========================================================");
        System.out.println(" OfficeFlow Backend (SSMS / SQL Server) Started on 8080!");
        System.out.println(" REST API Base URL: http://localhost:8080/api");
        System.out.println(" Frontend UI: Open frontend/index.html in browser");
        System.out.println("=========================================================");
    }
}
