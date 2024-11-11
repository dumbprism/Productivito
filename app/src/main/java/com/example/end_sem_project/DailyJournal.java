package com.example.end_sem_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DailyJournal extends AppCompatActivity {
    private static final int REQUEST_CODE_NEW_ENTRY = 1;
    private Button buttonAddEntry;
    private RecyclerView recyclerViewEntries;
    private JournalAdapter journalAdapter;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "JournalPrefs";
    private static final String KEY_JOURNAL_ENTRIES = "journalEntries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_journal);

        // Set background color
//        findViewById(R.id.mainLayout).setBackgroundColor(getResources().getColor(android.R.color.black));

        buttonAddEntry = findViewById(R.id.buttonAddEntry);
        recyclerViewEntries = findViewById(R.id.recyclerViewEntries);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        List<String> journalEntries = loadJournalEntries();

        // Set up RecyclerView and Adapter
        recyclerViewEntries.setLayoutManager(new LinearLayoutManager(this));
        journalAdapter = new JournalAdapter(journalEntries);
        recyclerViewEntries.setAdapter(journalAdapter);

        buttonAddEntry.setOnClickListener(view -> {
            // Start NewEntryActivity to get a new journal entry
            Intent intent = new Intent(DailyJournal.this, NewEntryActivity.class);
            startActivityForResult(intent, REQUEST_CODE_NEW_ENTRY);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NEW_ENTRY && resultCode == RESULT_OK && data != null) {
            String newEntry = data.getStringExtra("newEntry");
            if (newEntry != null) {
                saveJournalEntry(newEntry);
                journalAdapter.addEntry(newEntry);
                Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<String> loadJournalEntries() {
        List<String> entries = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString(KEY_JOURNAL_ENTRIES, "[]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                entries.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return entries;
    }

    private void saveJournalEntry(String entry) {
        List<String> entries = loadJournalEntries();
        entries.add(entry);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_JOURNAL_ENTRIES, new JSONArray(entries).toString());
        editor.apply();
    }

    // RecyclerView Adapter for displaying journal entries in colorful cards
    private static class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
        private final List<String> journalEntries;
        private final Random random = new Random();

        // Constructor for the adapter
        JournalAdapter(List<String> journalEntries) {
            this.journalEntries = journalEntries;
        }

        // Adds a new entry to the RecyclerView and notifies the adapter
        void addEntry(String entry) {
            journalEntries.add(entry);
            notifyItemInserted(journalEntries.size() - 1);
        }

        // Called when RecyclerView needs a new ViewHolder of the given type to represent an item
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_journal_entry, parent, false);
            return new ViewHolder(view);
        }

        // Binds the data to the ViewHolder
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textViewEntry.setText(journalEntries.get(position));

            // Set a random background color for each entry card
            int color = 0xff000000 | random.nextInt(0x00ffffff);
            holder.itemView.setBackgroundColor(color);

            // Set delete and edit buttons functionality
            holder.buttonDelete.setOnClickListener(v -> {
                journalEntries.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(holder.itemView.getContext(), "Entry Deleted", Toast.LENGTH_SHORT).show();
            });

            holder.buttonEdit.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), NewEntryActivity.class);
                intent.putExtra("editEntry", journalEntries.get(position));
                holder.itemView.getContext().startActivity(intent);
            });
        }

        // Returns the total number of items in the data set held by the adapter
        @Override
        public int getItemCount() {
            return journalEntries.size();
        }

        // ViewHolder class to hold reference to each item view
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textViewEntry;
            Button buttonDelete, buttonEdit;

            ViewHolder(View itemView) {
                super(itemView);
                textViewEntry = itemView.findViewById(R.id.textViewEntry);
                buttonDelete = itemView.findViewById(R.id.buttonDelete);
                buttonEdit = itemView.findViewById(R.id.buttonEdit);
            }
        }
    }
}
