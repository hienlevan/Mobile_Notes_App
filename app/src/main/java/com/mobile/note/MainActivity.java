package com.mobile.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.note.activity.CreateNoteActivity;
import com.mobile.note.activity.LoginActivity;
import com.mobile.note.activity.ProfileActivity;
import com.mobile.note.api.ApiService;
import com.mobile.note.model.Note;
import com.mobile.note.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView btnCreateNote;
    private NoteAdapter noteAdapter;
    private List<Note> notes;
    private SearchView searchView;

    private int user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvNote);
        btnCreateNote = findViewById(R.id.btnCreateNote);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return true;
            }
        });
        // Lấy id của user đăng nhập thành công từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        user_id = userId;
        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null) {
            notes = (List<Note>) bundleReceive.get("List_notes");
        }
        noteAdapter = new NoteAdapter(notes);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(noteAdapter);

        btnCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
    }

    private void setFilteredList(List<Note> filteredList) {
        noteAdapter.setFilteredList(filteredList);
    }
    private void filterlist(String newText) {
        List<Note> filteredlist = new ArrayList<>();
        for(Note note:notes){
            if(note.getTitle().toLowerCase().contains(newText.toLowerCase())){
                filteredlist.add(note);
            }
        }
        if(filteredlist.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else {
            setFilteredList(filteredlist);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem2 = menu.findItem(R.id.viewProfile);
        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Call<User> call = ApiService.apiService.getUserById(user_id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user =  response.body();
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("User", (Serializable) user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        });
        MenuItem menuItem1 = menu.findItem(R.id.logout);
        menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Redirect to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Optional: Close current screen when logout
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Call<List<Note>> call = ApiService.apiService.getListOfNotes(user_id);
            call.enqueue(new Callback<List<Note>>() {
                @Override
                public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Note> update_notes = response.body();
                        notes.clear();
                        notes.addAll(update_notes);
                        noteAdapter.notifyDataSetChanged();
                        setFilteredList(notes); // Cập nhật danh sách ghi chú đã được lọc
                        Toast.makeText(getApplicationContext(), "Updated list of notes: " + response.message(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Note>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();
    }
}