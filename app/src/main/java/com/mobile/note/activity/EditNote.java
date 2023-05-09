package com.mobile.note.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobile.note.R;
import com.mobile.note.api.ApiService;
import com.mobile.note.model.Note;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNote extends AppCompatActivity {

    private EditText edtNoteTitle;
    private EditText edtNoteContent;
    private FloatingActionButton saveEditNote;
    ProgressBar mprogressbarofcreatenote;

    private int noteId;
    private String noteTitle;
    private String noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteId = getIntent().getIntExtra("noteId", 0);
        noteTitle = getIntent().getStringExtra("noteTitle");
        noteContent = getIntent().getStringExtra("noteContent");

        edtNoteTitle = findViewById(R.id.edtCreateNoteTitle);
        edtNoteContent = findViewById(R.id.edtCreateNoteContent);
        mprogressbarofcreatenote = findViewById(R.id.progressbarCreateNote);
        saveEditNote = findViewById(R.id.saveNote);
        Toolbar toolbar = findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNoteTitle.setText(noteTitle);
        edtNoteContent.setText(noteContent);

        saveEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSaveEditNote();
            }
        });
    }
    private void clickSaveEditNote(){
        String title = edtNoteTitle.getText().toString();
        String content = edtNoteContent.getText().toString();
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Both fields are required", Toast.LENGTH_SHORT).show();
        } else {
            mprogressbarofcreatenote.setVisibility(View.VISIBLE);
            Note editNote = new Note();
            editNote.setTitle(title);
            editNote.setContent(content);
            Call<Note> call = ApiService.apiService.updateNote(noteId, editNote);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    mprogressbarofcreatenote.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null) {
                        setResult(RESULT_OK);
                        finish(); // Kết thúc Activity hiện tại
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    mprogressbarofcreatenote.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}