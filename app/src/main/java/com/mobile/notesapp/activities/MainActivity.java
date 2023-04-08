package com.mobile.notesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mobile.notesapp.R;
import com.mobile.notesapp.adapters.NoteAdapter;
import com.mobile.notesapp.entities.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Note mnote;
    private ArrayList<Note> listNote;
    private NoteAdapter mNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.listNotes);

        listNote = new ArrayList<>();
        data();
        mNoteAdapter  = new NoteAdapter(listNote,this);
        recyclerView.setAdapter(mNoteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void data(){
        listNote.add(new Note(1,"Note số 1", "Nội dung note 1"));
        listNote.add(new Note(2,"Note số 2", "Nội dung note 2"));
        listNote.add(new Note(3,"Note số 3", "Nội dung note 3"));
    }
}