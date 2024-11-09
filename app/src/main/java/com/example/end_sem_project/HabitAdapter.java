package com.example.end_sem_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private final List<Habit> habitList;

    public HabitAdapter(List<Habit> habitList) {
        this.habitList = habitList;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);

        holder.habitNameTextView.setText(habit.getName());

        // Format and display the start date
        String startDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(habit.getStartDate().getTime());
        holder.currentDateTextView.setText("Start Date: " + startDate);

        // Configure the progress bar
        holder.progressBar.setMax(21);
        holder.progressBar.setProgress(habit.getProgress());

        // Display days remaining
        int daysLeft = habit.getDaysLeft();
        holder.daysRemainingTextView.setText(daysLeft + " days left");

        // Configure the "Mark Complete" button
        holder.markCompleteButton.setEnabled(!habit.isCompletedToday());

        holder.markCompleteButton.setOnClickListener(v -> {
            if (!habit.isCompletedToday()) {
                // Mark the habit complete for today
                habit.markDayComplete();

                // Update UI components
                holder.progressBar.setProgress(habit.getProgress());
                holder.daysRemainingTextView.setText(habit.getDaysLeft() + " days left");

                // Disable the button for today
                holder.markCompleteButton.setEnabled(false);

                // Inform the user
                Toast.makeText(v.getContext(), "Habit marked complete for today!", Toast.LENGTH_SHORT).show();
            } else {
                // Show a reminder message if the button is clicked again today
                Toast.makeText(v.getContext(),
                        "Habit already marked complete for today. Come back tomorrow to continue your habit building!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitNameTextView, currentDateTextView, daysRemainingTextView;
        ProgressBar progressBar;
        Button markCompleteButton;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitNameTextView = itemView.findViewById(R.id.habitNameTextView);
            currentDateTextView = itemView.findViewById(R.id.currentDateTextView);
            progressBar = itemView.findViewById(R.id.progressBar);
            daysRemainingTextView = itemView.findViewById(R.id.daysRemainingTextView);
            markCompleteButton = itemView.findViewById(R.id.markCompleteButton);
        }
    }
}
