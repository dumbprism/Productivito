package com.example.end_sem_project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class WeeklyPlannerActivity extends AppCompatActivity {
    private Spinner daySpinner;
    private EditText taskInput;
    private Button addTaskButton;
    private RecyclerView weeklyRecyclerView;
    private WeeklyPlannerAdapter weeklyPlannerAdapter;
    private HashMap<String, ArrayList<String>> weeklyTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_planner);

        daySpinner = findViewById(R.id.spinner_day);
        taskInput = findViewById(R.id.et_task_input);
        addTaskButton = findViewById(R.id.btn_add_task);
        weeklyRecyclerView = findViewById(R.id.recycler_view_weekly);

        // Initialize task storage
        weeklyTasks = new HashMap<>();
        for (String day : getResources().getStringArray(R.array.days_of_week)) {
            weeklyTasks.put(day, new ArrayList<>());
        }

        // Set up RecyclerView
        weeklyPlannerAdapter = new WeeklyPlannerAdapter(weeklyTasks);
        weeklyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        weeklyRecyclerView.setAdapter(weeklyPlannerAdapter);

        // Set up spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        // Add task button functionality
        addTaskButton.setOnClickListener(v -> {
            String selectedDay = daySpinner.getSelectedItem().toString();
            String task = taskInput.getText().toString().trim();

            if (!task.isEmpty()) {
                weeklyTasks.get(selectedDay).add(task);
                taskInput.setText("");
                Toast.makeText(this, "Task added for " + selectedDay, Toast.LENGTH_SHORT).show();
                weeklyPlannerAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
