package com.officeflow.entity;

import jakarta.persistence.*;

/**
 * PACKAGE: com.officeflow.entity
 * FEATURE 8: Organization Email Access Grant.
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "email_access_grants")
public class EmailAccessGrant extends BaseEntity {

    @Column(name = "granted_org_email", nullable = false, length = 150)
    private String grantedOrgEmail;

    @Column(length = 255)
    private String permissions = "SEND_LEAVE,SEND_ANNOUNCEMENT,SEND_BROADCAST";

    @Column(length = 30)
    private String status = "ACTIVE"; // ACTIVE, REVOKED, EXPIRED

    @Column(name = "grant_notes", length = 255)
    private String grantNotes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lead_id", nullable = false)
    private User lead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public EmailAccessGrant() {
    }

    public EmailAccessGrant(String grantedOrgEmail, String permissions, String grantNotes, User lead, User user) {
        this.grantedOrgEmail = grantedOrgEmail;
        this.permissions = permissions;
        this.grantNotes = grantNotes;
        this.lead = lead;
        this.user = user;
    }

    public String getGrantedOrgEmail() {
        return grantedOrgEmail;
    }

    public void setGrantedOrgEmail(String grantedOrgEmail) {
        this.grantedOrgEmail = grantedOrgEmail;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrantNotes() {
        return grantNotes;
    }

    public void setGrantNotes(String grantNotes) {
        this.grantNotes = grantNotes;
    }

    public User getLead() {
        return lead;
    }

    public void setLead(User lead) {
        this.lead = lead;
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
