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
}
