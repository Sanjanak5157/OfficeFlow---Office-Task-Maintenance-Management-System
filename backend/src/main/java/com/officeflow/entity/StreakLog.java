package com.officeflow.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 3: Daily Streaks & Standup Updates.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "daily_streaks", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "log_date"})
})
public class StreakLog extends BaseEntity {

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "task_summary", nullable = false, length = 255)
    private String taskSummary;

    @Column(name = "hours_logged")
    private Double hoursLogged = 8.0;

    @Column(length = 30)
    private String status = "COMPLETED"; // COMPLETED, IN_PROGRESS, MISSED

    @Column(name = "streak_count_snapshot")
    private Integer streakCountSnapshot = 1;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public StreakLog() {
    }

    public StreakLog(LocalDate logDate, String taskSummary, Double hoursLogged, String status, Integer streakCountSnapshot, String notes, User user) {
        this.logDate = logDate;
        this.taskSummary = taskSummary;
        this.hoursLogged = hoursLogged;
        this.status = status;
        this.streakCountSnapshot = streakCountSnapshot;
        this.notes = notes;
        this.user = user;
    }

    // Getters and Setters
    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public String getTaskSummary() {
        return taskSummary;
    }

    public void setTaskSummary(String taskSummary) {
        this.taskSummary = taskSummary;
    }

    public Double getHoursLogged() {
        return hoursLogged;
    }

    public void setHoursLogged(Double hoursLogged) {
        this.hoursLogged = hoursLogged;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStreakCountSnapshot() {
        return streakCountSnapshot;
    }

    public void setStreakCountSnapshot(Integer streakCountSnapshot) {
        this.streakCountSnapshot = streakCountSnapshot;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
