package com.example.end_sem_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class WeeklyPlannerAdapter extends RecyclerView.Adapter<WeeklyPlannerAdapter.WeeklyViewHolder> {

    private final HashMap<String, ArrayList<String>> weeklyTasks;
    private final String[] orderedDays = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };
    private final String[] cardColors = {
            "#BB86FC", "#03DAC5", "#FFC107", "#2196F3", "#4CAF50", "#FF5722", "#F44336"
    };

    public WeeklyPlannerAdapter(HashMap<String, ArrayList<String>> weeklyTasks) {
        this.weeklyTasks = weeklyTasks;
    }

    @NonNull
    @Override
    public WeeklyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weekly_task_card, parent, false);
        return new WeeklyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyViewHolder holder, int position) {
        String day = orderedDays[position];
        ArrayList<String> tasks = weeklyTasks.get(day);

        holder.cardTitle.setText(day);
        holder.cardView.setCardBackgroundColor(android.graphics.Color.parseColor(cardColors[position % cardColors.length]));

        if (tasks != null && !tasks.isEmpty()) {
            StringBuilder taskList = new StringBuilder();
            for (String task : tasks) {
                taskList.append("• ").append(task).append("\n");
            }
            holder.taskList.setText(taskList.toString().trim());
        } else {
            holder.taskList.setText("No tasks added.");
        }
    }

    @Override
    public int getItemCount() {
        return orderedDays.length;
    }

    static class WeeklyViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle, taskList;
        CardView cardView;

        public WeeklyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.card_title);
            taskList = itemView.findViewById(R.id.task_list);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
