package com.officeflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 7: Task Row in Team Excel Sheet.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "task_sheet_rows")
public class TaskRow extends BaseEntity {

    @Column(name = "task_name", nullable = false, length = 200)
    private String taskName;

    @Column(length = 100)
    private String category = "Maintenance";

    @Column(length = 20)
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH, URGENT

    @Column(name = "assigned_member_name", length = 100)
    private String assignedMemberName;

    @Column(name = "member_work_update", columnDefinition = "NVARCHAR(MAX)")
    private String memberWorkUpdate; // Field that team member fills

    @Column(name = "time_spent_hours")
    private Double timeSpentHours = 0.0;

    @Column(length = 30)
    private String status = "PENDING"; // PENDING, IN_PROGRESS, COMPLETED, BLOCKED

    @Column(name = "last_updated_by", length = 100)
    private String lastUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_sheet_id", nullable = false)
    @JsonIgnore
    private TaskSheet taskSheet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    public TaskRow() {
    }

    public TaskRow(String taskName, String category, String priority, String assignedMemberName, String memberWorkUpdate, Double timeSpentHours, String status, User assignedUser) {
        this.taskName = taskName;
        this.category = category;
        this.priority = priority;
        this.assignedMemberName = assignedMemberName;
        this.memberWorkUpdate = memberWorkUpdate;
        this.timeSpentHours = timeSpentHours;
        this.status = status;
        this.assignedUser = assignedUser;
    }

    // Getters and Setters
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAssignedMemberName() {
        return assignedMemberName;
    }

    public void setAssignedMemberName(String assignedMemberName) {
        this.assignedMemberName = assignedMemberName;
    }

    public String getMemberWorkUpdate() {
        return memberWorkUpdate;
    }

    public void setMemberWorkUpdate(String memberWorkUpdate) {
        this.memberWorkUpdate = memberWorkUpdate;
    }

    public Double getTimeSpentHours() {
        return timeSpentHours;
    }

    public void setTimeSpentHours(Double timeSpentHours) {
        this.timeSpentHours = timeSpentHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public TaskSheet getTaskSheet() {
        return taskSheet;
    }

    public void setTaskSheet(TaskSheet taskSheet) {
        this.taskSheet = taskSheet;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
}
