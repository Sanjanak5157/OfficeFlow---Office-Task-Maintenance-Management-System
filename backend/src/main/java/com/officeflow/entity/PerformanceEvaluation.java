package com.officeflow.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 6: Monthly Performance Evaluation.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "performance_evaluations")
public class PerformanceEvaluation extends BaseEntity {

    @Column(name = "evaluation_month", nullable = false, length = 20)
    private String evaluationMonth; // e.g. "October 2026"

    @Column(name = "maintenance_sla_score")
    private Integer maintenanceSlaScore = 90; // 0-100

    @Column(name = "uptime_reliability_score")
    private Integer uptimeReliabilityScore = 95; // 0-100

    @Column(name = "task_completion_score")
    private Integer taskCompletionScore = 88; // 0-100

    @Column(name = "teamwork_initiative_score")
    private Integer teamworkInitiativeScore = 90; // 0-100

    @Column(name = "overall_score", precision = 5, scale = 2, nullable = false)
    private BigDecimal overallScore;

    @Column(nullable = false, length = 10)
    private String grade; // A+, A, B, C, D

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String strengths;

    @Column(name = "areas_of_improvement", columnDefinition = "NVARCHAR(MAX)")
    private String areasOfImprovement;

    @Column(name = "lead_comments", columnDefinition = "NVARCHAR(MAX)")
    private String leadComments;

    @Column(length = 30)
    private String status = "PUBLISHED"; // DRAFT, PUBLISHED

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Evaluated Member

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluator_id", nullable = false)
    private User evaluator; // Team Lead

    public PerformanceEvaluation() {
    }

    // Java Method: Calculate weighted overall score
    public void calculateOverallScoreAndGrade() {
        double weighted = (maintenanceSlaScore * 0.35) +
                          (uptimeReliabilityScore * 0.25) +
                          (taskCompletionScore * 0.25) +
                          (teamworkInitiativeScore * 0.15);
        this.overallScore = BigDecimal.valueOf(Math.round(weighted * 100.0) / 100.0);

        if (weighted >= 95.0) {
            this.grade = "A+";
        } else if (weighted >= 85.0) {
            this.grade = "A";
        } else if (weighted >= 75.0) {
            this.grade = "B";
        } else if (weighted >= 60.0) {
            this.grade = "C";
        } else {
            this.grade = "D";
        }
    }

    // Getters and Setters
    public String getEvaluationMonth() {
        return evaluationMonth;
    }

    public void setEvaluationMonth(String evaluationMonth) {
        this.evaluationMonth = evaluationMonth;
    }

    public Integer getMaintenanceSlaScore() {
        return maintenanceSlaScore;
    }

    public void setMaintenanceSlaScore(Integer maintenanceSlaScore) {
        this.maintenanceSlaScore = maintenanceSlaScore;
    }

    public Integer getUptimeReliabilityScore() {
        return uptimeReliabilityScore;
    }

    public void setUptimeReliabilityScore(Integer uptimeReliabilityScore) {
        this.uptimeReliabilityScore = uptimeReliabilityScore;
    }

    public Integer getTaskCompletionScore() {
        return taskCompletionScore;
    }

    public void setTaskCompletionScore(Integer taskCompletionScore) {
        this.taskCompletionScore = taskCompletionScore;
    }

    public Integer getTeamworkInitiativeScore() {
        return teamworkInitiativeScore;
    }

    public void setTeamworkInitiativeScore(Integer teamworkInitiativeScore) {
        this.teamworkInitiativeScore = teamworkInitiativeScore;
    }

    public BigDecimal getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(BigDecimal overallScore) {
        this.overallScore = overallScore;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getAreasOfImprovement() {
        return areasOfImprovement;
    }

    public void setAreasOfImprovement(String areasOfImprovement) {
        this.areasOfImprovement = areasOfImprovement;
    }

    public String getLeadComments() {
        return leadComments;
    }

    public void setLeadComments(String leadComments) {
        this.leadComments = leadComments;
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

    public User getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(User evaluator) {
        this.evaluator = evaluator;
    }
}
