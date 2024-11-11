package com.example.end_sem_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardPage extends AppCompatActivity {
    TextView name;
    Button pomodoroButton;
    Button todoButton;// Declare the button here
    Button habitTrackerButton;
    Button dailyJournalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Set the user's name
        name = findViewById(R.id.name);
        Bundle b1 = getIntent().getExtras();
        String s1 = b1.getString("user");
        name.setText(s1);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the Pomodoro button
        pomodoroButton = findViewById(R.id.pomodoroButton);
        todoButton = findViewById(R.id.toDoButton);
       habitTrackerButton = findViewById(R.id.habitTrackerButton);
       dailyJournalButton = findViewById(R.id.dailyJournalButton);

        // Set the OnClickListener to open the Pomodoro activity
        pomodoroButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardPage.this, Pomodoro.class);
            startActivity(intent);
        });

        todoButton.setOnClickListener(v -> {
            Intent i = new Intent(DashboardPage.this,Todo.class);
            startActivity(i);
        });

        habitTrackerButton.setOnClickListener(v -> {
            Intent i = new Intent(DashboardPage.this,HabitTracker.class);
            startActivity(i);
        });
        dailyJournalButton.setOnClickListener(v -> {
            Intent i = new Intent(DashboardPage.this,DailyJournal.class);
            startActivity(i);
        });
    }
}
