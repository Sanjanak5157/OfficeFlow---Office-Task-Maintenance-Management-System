package com.officeflow.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 4: Monthly Reports Document Builder (Word-like editor).
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "monthly_reports")
public class MonthlyReport extends BaseEntity {

    @Column(name = "report_title", nullable = false, length = 200)
    private String reportTitle;

    @Column(name = "report_month", nullable = false, length = 20)
    private String reportMonth; // e.g. "October 2026"

    @Column(length = 100)
    private String department = "Facility & IT Maintenance";

    @Column(name = "content_html", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String contentHtml; // Rich document body

    @Column(name = "executive_summary", columnDefinition = "NVARCHAR(MAX)")
    private String executiveSummary;

    @Column(name = "total_tasks_completed")
    private Integer totalTasksCompleted = 0;

    @Column(name = "maintenance_cost", precision = 12, scale = 2)
    private BigDecimal maintenanceCost = BigDecimal.ZERO;

    @Column(length = 30)
    private String status = "DRAFT"; // DRAFT, SUBMITTED, APPROVED

    @Column(name = "reviewed_by", length = 100)
    private String reviewedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public MonthlyReport() {
    }

    public MonthlyReport(String reportTitle, String reportMonth, String department, String contentHtml, String executiveSummary, Integer totalTasksCompleted, BigDecimal maintenanceCost, String status, User user) {
        this.reportTitle = reportTitle;
        this.reportMonth = reportMonth;
        this.department = department;
        this.contentHtml = contentHtml;
        this.executiveSummary = executiveSummary;
        this.totalTasksCompleted = totalTasksCompleted;
        this.maintenanceCost = maintenanceCost;
        this.status = status;
        this.user = user;
    }

    // Getters and Setters
    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getExecutiveSummary() {
        return executiveSummary;
    }

    public void setExecutiveSummary(String executiveSummary) {
        this.executiveSummary = executiveSummary;
    }

    public Integer getTotalTasksCompleted() {
        return totalTasksCompleted;
    }

    public void setTotalTasksCompleted(Integer totalTasksCompleted) {
        this.totalTasksCompleted = totalTasksCompleted;
    }

    public BigDecimal getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(BigDecimal maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
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
