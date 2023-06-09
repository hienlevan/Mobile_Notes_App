package com.mobile.note.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private int id;
    private String email;
    private String password;
    private List<Note> notes;

    public int getUserId() {
        return id;
    }

    public void setUserId(String userId) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", notes=" + notes +
                '}';
    }
}
