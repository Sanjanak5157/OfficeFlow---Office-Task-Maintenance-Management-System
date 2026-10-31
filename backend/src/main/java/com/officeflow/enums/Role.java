package com.officeflow.enums;

public enum Role {
    TEAM_LEAD("Team Leader / Manager"),
    TEAM_MEMBER("Technician / Operations Member"),
    ADMIN("System Administrator");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
