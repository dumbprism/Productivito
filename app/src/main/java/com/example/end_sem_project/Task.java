package com.example.end_sem_project;

public class Task {
    private String name;
    private String priority;
    private String description;

    // Constructor
    public Task(String name, String priority, String description) {
        this.name = name;
        this.priority = priority;
        this.description = description;
    }

    // Getters
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
    public void setName(String name) {
        this.name = name; // Sets the task name
    }

    public void setPriority(String priority) {
        this.priority = priority; // Sets the task priority
    }

    public void setDescription(String description) {
        this.description = description; // Sets the task description
    }
}
