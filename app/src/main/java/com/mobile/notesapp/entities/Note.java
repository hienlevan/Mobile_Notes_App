package com.mobile.notesapp.entities;

import java.io.Serializable;

public class Note implements Serializable {
    private int noteId;
    private String title;
    private String noteText;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Note() {
    }

    public Note(int noteId, String title, String noteText) {
        this.noteId = noteId;
        this.title = title;
        this.noteText = noteText;
    }
}
