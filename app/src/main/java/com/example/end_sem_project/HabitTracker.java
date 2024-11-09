package com.example.end_sem_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HabitTracker extends AppCompatActivity {

    private List<Habit> habitList = new ArrayList<>();
    private HabitAdapter habitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker); // Make sure the layout name is correct

        RecyclerView habitRecyclerView = findViewById(R.id.habitRecyclerView);
        habitRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        habitAdapter = new HabitAdapter(habitList);
        habitRecyclerView.setAdapter(habitAdapter);

        Button addHabitButton = findViewById(R.id.addHabitButton);
        addHabitButton.setOnClickListener(view -> showAddHabitDialog());
    }

    private void showAddHabitDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_habit, null);
        dialogBuilder.setView(dialogView);

        EditText habitNameEditText = dialogView.findViewById(R.id.habitNameEditText);
        Button addHabitDialogButton = dialogView.findViewById(R.id.addHabitDialogButton);

        AlertDialog dialog = dialogBuilder.create();
        addHabitDialogButton.setOnClickListener(v -> {
            String habitName = habitNameEditText.getText().toString().trim();
            if (!habitName.isEmpty()) {
                Habit newHabit = new Habit(habitName, 21);
                habitList.add(newHabit);
                habitAdapter.notifyItemInserted(habitList.size() - 1);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Please enter a habit name", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
