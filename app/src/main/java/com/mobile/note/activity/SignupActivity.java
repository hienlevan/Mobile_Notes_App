package com.mobile.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.note.R;
import com.mobile.note.api.ApiService;
import com.mobile.note.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnSignup;
    private TextView textViewLogin;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUsername = findViewById(R.id.inputEmail);
        edtPassword = findViewById(R.id.inputPassword);
        btnSignup = findViewById(R.id.btnSignup);
        textViewLogin = findViewById(R.id.textViewLogin);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListUsers();

            }
        });
    }

    private void getListUsers(){
        String strEmail = edtUsername.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        user = new User();
        user.setEmail(strEmail);
        user.setPassword(strPassword);
        ApiService.apiService.createUser(user)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user1 = response.body();
//                        Toast.makeText(getApplicationContext(), user1.getEmail() + " " + user1.getPassword(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), user1.getEmail() + " \n" +
                                "successful registration", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}