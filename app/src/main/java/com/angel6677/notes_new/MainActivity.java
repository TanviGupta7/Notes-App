package com.angel6677.notes_new;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnCreateNote;
    FloatingActionButton fabAdd;
    RecyclerView recyclerNotes;
    DatabaseHelper databaseHelper;
    LinearLayout llNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVar();
        showNotes();

        fabAdd.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.add_note_layout);
            EditText edtTitle = dialog.findViewById(R.id.edTitle);
            EditText edtContent = dialog.findViewById(R.id.edtContent);
            Button btnAdd = dialog.findViewById(R.id.btnAdd);

            btnAdd.setOnClickListener(v1 -> {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                if (!content.isEmpty()) {
                    databaseHelper.noteDao().addNote(new Note(title, content));
                    showNotes();
                    dialog.dismiss(); // Close the dialog after adding the note
                } else {
                    Toast.makeText(MainActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show(); // Show the dialog
        });

        btnCreateNote.setOnClickListener(v -> fabAdd.performClick());
    }

    private void initVar() {
        btnCreateNote = findViewById(R.id.btnCreateNote);
        fabAdd = findViewById(R.id.fabAdd);
        recyclerNotes = findViewById(R.id.recyclerNotes);
        llNotes = findViewById(R.id.llNotes); // Make sure llNotes is initialized

        recyclerNotes.setLayoutManager(new GridLayoutManager(this, 2));
        databaseHelper = DatabaseHelper.getInstance(this);
    }

    public void showNotes() {
        List<Note> arrNotes = databaseHelper.noteDao().getNotes();
        if (arrNotes != null && !arrNotes.isEmpty()) {
            recyclerNotes.setVisibility(View.VISIBLE);
            llNotes.setVisibility(View.GONE);
            recyclerNotes.setAdapter(new RecyclerNotesAdapter(this, (ArrayList<Note>) arrNotes, databaseHelper)); // Ensure RecyclerNotesAdapter is imported
        } else {
            llNotes.setVisibility(View.VISIBLE);
            recyclerNotes.setVisibility(View.GONE);
        }
    }
}
