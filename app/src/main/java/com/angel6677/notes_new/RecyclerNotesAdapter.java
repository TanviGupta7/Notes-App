package com.angel6677.notes_new;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerNotesAdapter extends RecyclerView.Adapter<RecyclerNotesAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Note> arrNotes;
    private final DatabaseHelper databaseHelper;

    public RecyclerNotesAdapter(Context context, ArrayList<Note> arrNotes, DatabaseHelper databaseHelper) {
        this.context = context;
        this.arrNotes = arrNotes;
        this.databaseHelper = databaseHelper; // Correctly assign databaseHelper here
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(arrNotes.get(position).getTitle());
        holder.txtContent.setText(arrNotes.get(position).getContent());

        holder.llRow.setOnLongClickListener(v -> {
            deleteItem(position); // Pass the position to deleteItem
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtTitle;
        final TextView txtContent;
        final LinearLayout llRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            llRow = itemView.findViewById(R.id.llRow);
        }
    }

    public void deleteItem(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Get the note to be deleted
                    Note noteToDelete = arrNotes.get(position);
                    // Delete note from the database
                    databaseHelper.noteDao().deleteNote(noteToDelete);
                    // Remove the note from the list and notify the adapter
                    arrNotes.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, arrNotes.size());
                })
                .setNegativeButton("No", null)
                .show();
    }
}
