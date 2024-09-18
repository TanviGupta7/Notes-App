package com.angel6677.notes_new;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void addNote(Note note);

    @Query("SELECT * FROM note")
    List<Note> getNotes();

    @Delete
    void deleteNote(Note noteToDelete);
}
