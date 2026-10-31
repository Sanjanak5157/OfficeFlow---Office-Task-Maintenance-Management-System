package com.officeflow.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 7: Team Excel Task Spreadsheet.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "task_sheets")
public class TaskSheet extends BaseEntity {

    @Column(name = "sheet_title", nullable = false, length = 200)
    private String sheetTitle;

    @Column(name = "sheet_description", columnDefinition = "NVARCHAR(MAX)")
    private String sheetDescription;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lead_id", nullable = false)
    private User lead;

    @OneToMany(mappedBy = "taskSheet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TaskRow> rows = new ArrayList<>();

    public TaskSheet() {
    }

    public TaskSheet(String sheetTitle, String sheetDescription, LocalDate targetDate, User lead) {
        this.sheetTitle = sheetTitle;
        this.sheetDescription = sheetDescription;
        this.targetDate = targetDate;
        this.lead = lead;
    }

    // Java Collection method: Add row to sheet
    public void addRow(TaskRow row) {
        rows.add(row);
        row.setTaskSheet(this);
    }

    // Getters and Setters
    public String getSheetTitle() {
        return sheetTitle;
    }

    public void setSheetTitle(String sheetTitle) {
        this.sheetTitle = sheetTitle;
    }

    public String getSheetDescription() {
        return sheetDescription;
    }

    public void setSheetDescription(String sheetDescription) {
        this.sheetDescription = sheetDescription;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public Boolean getIsActive() {
        return isActive != null && isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public User getLead() {
        return lead;
    }

    public void setLead(User lead) {
        this.lead = lead;
    }

    public List<TaskRow> getRows() {
        return rows;
    }

    public void setRows(List<TaskRow> rows) {
        this.rows = rows;
    }
}
