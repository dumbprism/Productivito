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
import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private List<JournalEntry> journalEntries;
    private Context context;
    private OnJournalClickListener onJournalClickListener;

    public JournalAdapter(Context context, List<JournalEntry> journalEntries, OnJournalClickListener listener) {
        this.context = context;
        this.journalEntries = journalEntries;
        this.onJournalClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_journal_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry journalEntry = journalEntries.get(position);
        holder.titleTextView.setText(journalEntry.getTitle());
        holder.createdAtTextView.setText(journalEntry.getCreatedAt().toString());

        // View/Edit/Delete button click listeners
        holder.itemView.setOnClickListener(v -> onJournalClickListener.onJournalClicked(journalEntry));
        holder.editButton.setOnClickListener(v -> onJournalClickListener.onEditClicked(journalEntry, position));
        holder.deleteButton.setOnClickListener(v -> onJournalClickListener.onDeleteClicked(journalEntry, position));
    }

    @Override
    public int getItemCount() {
        return journalEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, createdAtTextView;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.journalTitle);
            createdAtTextView = itemView.findViewById(R.id.journalCreatedAt);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface OnJournalClickListener {
        void onJournalClicked(JournalEntry journalEntry);
        void onEditClicked(JournalEntry journalEntry, int position);
        void onDeleteClicked(JournalEntry journalEntry, int position);
    }
}
