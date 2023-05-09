package com.mobile.note.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView textViewSignup;
    private List<User> mListUser = new ArrayList<>();
    private List<Note> mListNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.inputEmail);
        edtPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textViewSignup = findViewById(R.id.textViewSignup);

        // Disable login button until mListUser is loaded
        btnLogin.setEnabled(false);

        // Load list of users using AsyncTask
        new LoadUsersTask().execute();

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

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

        if (mListUser == null || mListUser.isEmpty()) {
            return;
        }

        boolean isHasUser = false;
        User loginUser = new User(); // Tạo một biến để lưu thông tin user đăng nhập thành công
        for(User user:mListUser){
            if(strUsername.equals(user.getEmail()) && strPassword.equals(user.getPassword())){
                isHasUser = true;
                mListNote = user.getNotes();
                loginUser = user; // Lưu thông tin user đăng nhập thành công vào biến loginUser
                break;
            }
        }

        if(isHasUser){
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userId", loginUser.getUserId()); // Lưu id của user đăng nhập thành công vào SharedPreferences
            editor.apply();

            int userId = sharedPreferences.getInt("userId", 0); // Lấy id của user đăng nhập thành công từ SharedPreferences

            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show(); // Hiển thị thông báo về id đã lấy được

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("List_notes", (Serializable) mListNote);
            intent.putExtras(bundle);
            startActivity(intent);
        } else{
            Toast.makeText(LoginActivity.this, "Email or Password invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private class LoadUsersTask extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... voids) {
            try {
                Response<List<User>> response = ApiService.apiService.getAllUsers().execute();
                return response.body();
            } catch (IOException e) {
                Log.e("LoadUsersTask", "Failed to load users", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<User> userList) {
            if (userList != null) {
                mListUser = userList;
                // Enable login button after mListUser is loaded
                btnLogin.setEnabled(true);
            } else {
                Toast.makeText(LoginActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
