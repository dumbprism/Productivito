package com.example.end_sem_project;

public class Task {
    private String name;
    private String priority;
    private String description;

    public Task(String name, String priority, String description) {
        this.name = name;
        this.priority = priority;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setName(String taskName) {
        this.name = taskName;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Convert priority to an integer for sorting
    public int getPriorityValue() {
        switch (priority) {
            case "High":
                return 3;
            case "Medium":
                return 2;
            case "Low":
                return 1;
            default:
                return 0;
        }
    }
}
