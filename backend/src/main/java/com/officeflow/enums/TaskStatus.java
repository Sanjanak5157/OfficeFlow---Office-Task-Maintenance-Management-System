package com.officeflow.enums;

public enum TaskStatus {
    PENDING("Awaiting start"),
    IN_PROGRESS("Currently being worked on"),
    COMPLETED("Task finished successfully"),
    BLOCKED("Impediment encountered");

    private final String label;

    TaskStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
