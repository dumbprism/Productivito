package com.example.end_sem_project;

import java.util.Calendar;

public class Habit {

    private String name;
    private Calendar startDate;
    private int progress; // Tracks the number of days completed
    private Calendar lastCompletionDate; // Tracks the last date the habit was completed

    // Constructor to initialize a new habit with a start date and default progress
    public Habit(String name, int totalDays) {
        this.name = name;
        this.startDate = Calendar.getInstance(); // Sets start date to current date
        this.progress = 0;
        this.lastCompletionDate = null; // No completion on the first day
    }

    // Getter for habit name
    public String getName() {
        return name;
    }

    // Getter for start date
    public Calendar getStartDate() {
        return startDate;
    }

    // Getter for progress
    public int getProgress() {
        return progress;
    }

    // Method to check if the habit was completed today
    public boolean isCompletedToday() {
        if (lastCompletionDate == null) {
            return false;
        }
        Calendar today = Calendar.getInstance();
        return (today.get(Calendar.YEAR) == lastCompletionDate.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == lastCompletionDate.get(Calendar.DAY_OF_YEAR));
    }

    // Method to mark the habit as completed for the day
    public void markDayComplete() {
        if (!isCompletedToday()) {
            progress++; // Increment progress if not already completed today
            lastCompletionDate = Calendar.getInstance(); // Set last completion date to today
        }
    }

    // Method to get the number of days left in the 21-day challenge
    public int getDaysLeft() {
        return Math.max(0, 21 - progress);
    }
}