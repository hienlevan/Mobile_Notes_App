package com.mobile.note.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobile.note.R;
import com.mobile.note.api.ApiService;
import com.mobile.note.model.Note;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText mCreateNoteTitle,mCreateNoteContent;
    private FloatingActionButton msavenote;
    private ProgressBar mprogressbarofcreatenote;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        mCreateNoteTitle = findViewById(R.id.edtCreateNoteTitle);
        mCreateNoteContent = findViewById(R.id.edtCreateNoteContent);
        msavenote = findViewById(R.id.saveNote);
        mprogressbarofcreatenote = findViewById(R.id.progressbarCreateNote);
        Toolbar toolbar = findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundleReceive = getIntent().getExtras();
        if(bundleReceive != null){
            userId = (int) bundleReceive.get("userId");
        }

        msavenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSaveNote();
            }
        });
    }

    private void clickSaveNote() {
        String title = mCreateNoteTitle.getText().toString();
        String content = mCreateNoteContent.getText().toString();
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Both fields are required", Toast.LENGTH_SHORT).show();
        } else {
            mprogressbarofcreatenote.setVisibility(View.VISIBLE);
            Note newNote = new Note();
            newNote.setTitle(title);
            newNote.setContent(content);
            Call<Note> call = ApiService.apiService.createNote(userId, newNote);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    mprogressbarofcreatenote.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null) {
                        // Xử lý kết quả trả về khi tạo note thành công
                        setResult(RESULT_OK);
                        finish(); // Kết thúc Activity hiện tại
                    } else {
                        // Xử lý kết quả trả về khi tạo note thất bại
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
