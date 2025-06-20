package com.issakass.task_tracker.core.model;

/**
 * Author: abdallah-issakass
 */
public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String value;


    TaskStatus(String value) {
        this.value = value;
    }

    public static TaskStatus fromValue(String value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }

    public String getValue() {
        return value;
    }
}
