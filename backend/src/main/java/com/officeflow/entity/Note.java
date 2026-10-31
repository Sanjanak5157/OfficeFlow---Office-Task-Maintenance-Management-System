package com.officeflow.entity;

import jakarta.persistence.*;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 1: Daily Notes inside Notepad.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "daily_notes")
public class Note extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String content;

    @Column(length = 50)
    private String category = "General"; // General, Maintenance, Daily Standup, Urgent, Checklist

    @Column(name = "is_pinned")
    private Boolean isPinned = false;

    @Column(name = "color_tag", length = 30)
    private String colorTag = "#8b5cf6"; // Purple accent default

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Note() {
    }

    public Note(String title, String content, String category, Boolean isPinned, String colorTag, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.isPinned = isPinned;
        this.colorTag = colorTag;
        this.user = user;
    }

    // Encapsulation: Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsPinned() {
        return isPinned != null && isPinned;
    }

    public void setIsPinned(Boolean isPinned) {
        this.isPinned = isPinned;
    }

    public String getColorTag() {
        return colorTag;
    }

    public void setColorTag(String colorTag) {
        this.colorTag = colorTag;
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
