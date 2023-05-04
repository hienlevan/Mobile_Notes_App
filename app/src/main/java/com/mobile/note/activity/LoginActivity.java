package com.mobile.note.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

    private TextView textViewSignup;
    private List<User> mListUser;
    private List<Note> mListNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.inputEmail);
        edtPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textViewSignup = findViewById(R.id.textViewSignup);

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
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
        String strUsername = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        if(mListUser == null || mListUser.isEmpty()){
            return;
        }
        boolean isHasUser = false;
        for(User user:mListUser){
            if(strUsername.equals(user.getEmail()) && strPassword.equals(user.getPassword())){
                isHasUser = true;
                mListNote = user.getNotes();
                break;
            }
        }
        if(isHasUser){
            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable("List_notes", (Serializable) mListNote);
            intent.putExtras(bundle);
            startActivity(intent);
        } else{
            Toast.makeText(LoginActivity.this, "Email or Password invalid", Toast.LENGTH_SHORT).show();
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