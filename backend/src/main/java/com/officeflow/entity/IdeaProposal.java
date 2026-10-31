package com.officeflow.entity;

import jakarta.persistence.*;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 5: Project Idea Comparison & Proposal.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "idea_proposals")
public class IdeaProposal extends BaseEntity {

    @Column(name = "project_name", nullable = false, length = 150)
    private String projectName;

    @Column(name = "old_idea_description", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String oldIdeaDescription;

    @Column(name = "new_suggestion_idea", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String newSuggestionIdea;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String benefits;

    @Column(name = "estimated_impact", length = 50)
    private String estimatedImpact = "HIGH"; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(length = 30)
    private String status = "PROPOSED"; // PROPOSED, UNDER_REVIEW, ACCEPTED, REJECTED

    @Column(name = "lead_feedback", columnDefinition = "NVARCHAR(MAX)")
    private String leadFeedback;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_repo_id")
    private ProjectRepo projectRepo;

    public IdeaProposal() {
    }

    public IdeaProposal(String projectName, String oldIdeaDescription, String newSuggestionIdea, String benefits, String estimatedImpact, User user) {
        this.projectName = projectName;
        this.oldIdeaDescription = oldIdeaDescription;
        this.newSuggestionIdea = newSuggestionIdea;
        this.benefits = benefits;
        this.estimatedImpact = estimatedImpact;
        this.user = user;
    }

    // Getters and Setters
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOldIdeaDescription() {
        return oldIdeaDescription;
    }

    public void setOldIdeaDescription(String oldIdeaDescription) {
        this.oldIdeaDescription = oldIdeaDescription;
    }

    public String getNewSuggestionIdea() {
        return newSuggestionIdea;
    }

    public void setNewSuggestionIdea(String newSuggestionIdea) {
        this.newSuggestionIdea = newSuggestionIdea;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getEstimatedImpact() {
        return estimatedImpact;
    }

    public void setEstimatedImpact(String estimatedImpact) {
        this.estimatedImpact = estimatedImpact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeadFeedback() {
        return leadFeedback;
    }

    public void setLeadFeedback(String leadFeedback) {
        this.leadFeedback = leadFeedback;
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

    public ProjectRepo getProjectRepo() {
        return projectRepo;
    }

    public void setProjectRepo(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }
}
