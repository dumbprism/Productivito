package com.example.end_sem_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeApp extends AppCompatActivity {
    private EditText input;
    private Button getStarted;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_app);

        input = findViewById(R.id.input_name);
        getStarted = findViewById(R.id.button);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = input.getText().toString().trim();

                if (!userName.isEmpty()) {
                    // Insert the user's name into the database
                    dbHelper.insertUserName(userName);

                    // Proceed to the next activity and pass the user's name
                    Intent intent = new Intent(WelcomeApp.this, DashboardPage.class);
                    intent.putExtra("user", userName);
                    startActivity(intent);
                } else {
                    input.setError("Name cannot be empty");
                }
            }
        });
    }
}
