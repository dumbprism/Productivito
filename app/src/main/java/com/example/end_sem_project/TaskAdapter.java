package com.example.end_sem_project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> taskList;
    private Context context;

    public TaskAdapter(ArrayList<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getName());
        holder.taskPriority.setText("Priority: " + task.getPriority());
        holder.taskDescription.setText(task.getDescription());

        // Complete Button Action
        holder.completeButton.setOnClickListener(v -> {
            Toast.makeText(context, "Task Completed: " + task.getName(), Toast.LENGTH_SHORT).show();
        });

        // Edit Button Action
        holder.editButton.setOnClickListener(v -> {
            showEditTaskDialog(task, position);
        });

        // Delete Button Action
        holder.deleteButton.setOnClickListener(v -> {
            taskList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, taskList.size());
            Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    private void showEditTaskDialog(Task task, int position) {
        // Inflate the edit task dialog layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_task, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText taskNameInput = dialogView.findViewById(R.id.taskNameInput);
        Spinner prioritySpinner = dialogView.findViewById(R.id.prioritySpinner);
        EditText descriptionInput = dialogView.findViewById(R.id.descriptionInput);
        Button addTaskDialogButton = dialogView.findViewById(R.id.addTaskDialogButton);

        // Pre-fill the dialog with current task data
        taskNameInput.setText(task.getName());
        descriptionInput.setText(task.getDescription());

        // Set the spinner for priority
        String[] priorities = {"High", "Medium", "Low"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, priorities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        // Set the current priority in the spinner
        int priorityPosition = adapter.getPosition(task.getPriority());
        prioritySpinner.setSelection(priorityPosition);

        addTaskDialogButton.setText("Update Task");

        addTaskDialogButton.setOnClickListener(v -> {
            String taskName = taskNameInput.getText().toString();
            String priority = prioritySpinner.getSelectedItem().toString(); // Get selected priority
            String description = descriptionInput.getText().toString();

            if (!taskName.isEmpty() && !priority.isEmpty() && !description.isEmpty()) {
                // Update task in the list
                task.setName(taskName);
                task.setPriority(priority);
                task.setDescription(description);

                // Notify adapter about the data change
                notifyItemChanged(position);
                dialog.dismiss();
                Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show();
            } else {
                // Show a toast if any field is empty
                Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskPriority, taskDescription;
        Button editButton, deleteButton, completeButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            taskPriority = itemView.findViewById(R.id.taskPriority);
            taskDescription = itemView.findViewById(R.id.taskDescription);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            completeButton = itemView.findViewById(R.id.completeButton);
        }
    }
}
