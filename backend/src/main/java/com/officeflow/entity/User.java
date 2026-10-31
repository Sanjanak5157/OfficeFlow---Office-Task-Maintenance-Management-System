package com.officeflow.entity;

import com.officeflow.enums.Role;
import jakarta.persistence.*;

/**
 * PACKAGE: com.officeflow.entity
 * PURPOSE: Represents staff members, technicians, and team leads.
 * Extends BaseEntity (OOP Inheritance).
 * Database: SSMS (Microsoft SQL Server).
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role = Role.TEAM_MEMBER;

    @Column(length = 100)
    private String department = "Maintenance & Facility";

    @Column(length = 100)
    private String designation = "Operations Specialist";

    @Column(name = "organization_email", length = 150)
    private String organizationEmail;

    @Column(name = "has_org_email_access")
    private Boolean hasOrgEmailAccess = false;

    @Column(name = "avatar_url")
    private String avatarUrl;

    public User() {
    }

    public User(String name, String email, String password, Role role, String department, String designation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.department = department;
        this.designation = designation;
    }

    // Encapsulation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganizationEmail() {
        return organizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }

    public Boolean getHasOrgEmailAccess() {
        return hasOrgEmailAccess != null && hasOrgEmailAccess;
    }

    public void setHasOrgEmailAccess(Boolean hasOrgEmailAccess) {
        this.hasOrgEmailAccess = hasOrgEmailAccess;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
