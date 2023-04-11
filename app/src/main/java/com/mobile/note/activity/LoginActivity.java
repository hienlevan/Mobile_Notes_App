package com.mobile.note.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile.note.MainActivity;
import com.mobile.note.R;
import com.mobile.note.api.ApiService;
import com.mobile.note.model.Note;
import com.mobile.note.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    private List<User> mListUser;
    private List<Note> mListNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        mListUser = new ArrayList<>();

        getListUsers();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });
    }

    private void clickLogin() {
        String strUsername = edtUsername.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        if(mListUser == null || mListUser.isEmpty()){
            return;
        }

        boolean isHasUser = false;
        for(User user:mListUser){
            if(strUsername.equals(user.getUsername()) && strPassword.equals(user.getPassword())){
                isHasUser = true;
                mListNote = user.getNotes();
                break;
            }
        }

        if(isHasUser){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable("List_notes", (Serializable) mListNote);
            intent.putExtras(bundle);
            startActivity(intent);
        } else{
            Toast.makeText(LoginActivity.this, "username or password invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListUsers(){
        ApiService.apiService.getAllUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        mListUser = response.body();
                        Log.e("List users", mListUser.size()+"");
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}