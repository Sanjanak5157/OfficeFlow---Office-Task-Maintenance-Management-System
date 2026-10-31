package com.officeflow.enums;

public enum EmailType {
    LEAVE_REQUEST("Employee Leave Application"),
    MAINTENANCE_ALERT("Office Maintenance Downtime Alert"),
    DAILY_STATUS("Daily Operations Progress Report"),
    GENERAL_BROADCAST("Organization-wide Broadcast Announcement");

    private final String subjectPrefix;

    EmailType(String subjectPrefix) {
        this.subjectPrefix = subjectPrefix;
    }

    public String getSubjectPrefix() {
        return subjectPrefix;
    }
}
