package com.officeflow.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 8: Organization Email Log.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "org_email_logs")
public class OrgEmailLog extends BaseEntity {

    @Column(name = "sender_org_email", nullable = false, length = 150)
    private String senderOrgEmail;

    @Column(name = "recipient_type", nullable = false, length = 50)
    private String recipientType; // ALL_ORGANIZATION, MAINTENANCE_TEAM, TEAM_LEAD, HR

    @Column(name = "recipient_emails", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String recipientEmails;

    @Column(name = "email_type", nullable = false, length = 50)
    private String emailType; // LEAVE_REQUEST, MAINTENANCE_ALERT, DAILY_STATUS, GENERAL_BROADCAST

    @Column(nullable = false, length = 255)
    private String subject;

    @Column(name = "email_body", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String emailBody;

    @Column(name = "sent_status", length = 30)
    private String sentStatus = "SENT"; // SENT, QUEUED, FAILED

    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_user_id", nullable = false)
    private User senderUser;

    public OrgEmailLog() {
    }

    public OrgEmailLog(String senderOrgEmail, String recipientType, String recipientEmails, String emailType, String subject, String emailBody, User senderUser) {
        this.senderOrgEmail = senderOrgEmail;
        this.recipientType = recipientType;
        this.recipientEmails = recipientEmails;
        this.emailType = emailType;
        this.subject = subject;
        this.emailBody = emailBody;
        this.senderUser = senderUser;
        this.sentAt = LocalDateTime.now();
        this.sentStatus = "SENT";
    }

    // Getters and Setters
    public String getSenderOrgEmail() {
        return senderOrgEmail;
    }

    public void setSenderOrgEmail(String senderOrgEmail) {
        this.senderOrgEmail = senderOrgEmail;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public String getRecipientEmails() {
        return recipientEmails;
    }

    public void setRecipientEmails(String recipientEmails) {
        this.recipientEmails = recipientEmails;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(String sentStatus) {
        this.sentStatus = sentStatus;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    @Transient
    private Long senderUserId;

    public Long getSenderUserId() {
        if (senderUserId != null) return senderUserId;
        return senderUser != null ? senderUser.getId() : null;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }
}
