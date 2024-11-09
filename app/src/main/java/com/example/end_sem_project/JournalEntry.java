package com.example.end_sem_project;

import java.util.Date;

public class JournalEntry {
    private String title;
    private String content;
    private Date createdAt;

    public JournalEntry(String title, String content) {
        this.title = title.isEmpty() ? new Date().toString() : title; // Set current date if no title is provided
        this.content = content;
        this.createdAt = new Date();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
