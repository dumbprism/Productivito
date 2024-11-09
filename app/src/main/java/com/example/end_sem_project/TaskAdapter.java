package com.example.end_sem_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
            // Logic to edit the task
            // You can implement a dialog here similar to the one in Todo.java for adding tasks
            // Populate the dialog with current task data and update taskList after editing
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
