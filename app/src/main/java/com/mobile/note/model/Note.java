package com.mobile.note.model;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String title;
    private String content;

    public int getNoteId() {
        return id;
    }

    public void setNoteId(int Id) {
        this.id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
