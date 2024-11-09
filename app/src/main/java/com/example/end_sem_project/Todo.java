package com.example.end_sem_project;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Todo extends AppCompatActivity {

    private TextView currentDate;
    private RecyclerView taskRecyclerView;
    private Button addTaskButton;
    private ArrayList<Task> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        currentDate = findViewById(R.id.currentDate);
        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        addTaskButton = findViewById(R.id.addTaskButton);

        // Display current date
        String date = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault()).format(new Date());
        currentDate.setText(date);

        // Set up RecyclerView
        taskAdapter = new TaskAdapter(taskList, this); // Pass both taskList and context
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(taskAdapter);

        updateUI();

        // Set up add task button click listener
        addTaskButton.setOnClickListener(view -> showAddTaskDialog());
    }

    private void updateUI() {
        // Show RecyclerView if tasks are present, else show "Add Task" button only
        if (taskList.isEmpty()) {
            taskRecyclerView.setVisibility(View.GONE);
            addTaskButton.setVisibility(View.VISIBLE);
        } else {
            taskRecyclerView.setVisibility(View.VISIBLE);
            addTaskButton.setVisibility(View.VISIBLE);
        }
    }

    private void showAddTaskDialog() {
        // Inflate custom dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText taskNameInput = dialogView.findViewById(R.id.taskNameInput);
        EditText priorityInput = dialogView.findViewById(R.id.priorityInput);
        EditText descriptionInput = dialogView.findViewById(R.id.descriptionInput);
        Button addTaskDialogButton = dialogView.findViewById(R.id.addTaskDialogButton);

        addTaskDialogButton.setOnClickListener(v -> {
            String taskName = taskNameInput.getText().toString();
            String priority = priorityInput.getText().toString();
            String description = descriptionInput.getText().toString();

            if (!taskName.isEmpty() && !priority.isEmpty() && !description.isEmpty()) {
                // Add task to list and update UI
                taskList.add(new Task(taskName, priority, description));
                taskAdapter.notifyDataSetChanged();
                updateUI();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
