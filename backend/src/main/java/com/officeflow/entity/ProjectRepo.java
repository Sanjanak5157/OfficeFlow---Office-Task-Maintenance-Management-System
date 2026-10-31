package com.officeflow.entity;

import jakarta.persistence.*;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 2: Project & Repository Cards.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "project_repos")
public class ProjectRepo extends BaseEntity {

    @Column(name = "project_name", nullable = false, length = 150)
    private String projectName;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "repo_url", nullable = false, length = 500)
    private String repoUrl;

    @Column(name = "live_demo_url", length = 500)
    private String liveDemoUrl;

    @Column(name = "documentation_url", length = 500)
    private String documentationUrl;

    @Column(name = "tech_stack", length = 255)
    private String techStack = "Java 17, Spring Boot, SSMS";

    @Column(length = 50)
    private String category = "Office Maintenance"; // IT Support, Facility, Security, Operations

    @Column(length = 30)
    private String status = "ACTIVE"; // ACTIVE, UNDER_REVIEW, COMPLETED, ARCHIVED

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ProjectRepo() {
    }

    public ProjectRepo(String projectName, String description, String repoUrl, String liveDemoUrl, String techStack, String category, User user) {
        this.projectName = projectName;
        this.description = description;
        this.repoUrl = repoUrl;
        this.liveDemoUrl = liveDemoUrl;
        this.techStack = techStack;
        this.category = category;
        this.user = user;
    }

    // Getters and Setters
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getLiveDemoUrl() {
        return liveDemoUrl;
    }

    public void setLiveDemoUrl(String liveDemoUrl) {
        this.liveDemoUrl = liveDemoUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transient
    private Long userId;

    public Long getUserId() {
        if (userId != null) return userId;
        return user != null ? user.getId() : null;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
