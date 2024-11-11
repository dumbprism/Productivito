package com.example.end_sem_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewEntryActivity extends AppCompatActivity {
    private EditText editTextNewEntryContent;
    private Button buttonSaveNewEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        editTextNewEntryContent = findViewById(R.id.editTextNewEntryContent);
        buttonSaveNewEntry = findViewById(R.id.buttonSaveNewEntry);

        buttonSaveNewEntry.setOnClickListener(view -> {
            String entryText = editTextNewEntryContent.getText().toString();
            if (!entryText.isEmpty()) {
                // Pass entry back to DailyJournalActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newEntry", entryText);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Entry cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
